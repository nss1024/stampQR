//console.log('Admin page here!');

function addNewCustomer() {
    var valid = true;
    var formTextElements = [
        document.getElementById('newCustomerUsername').value,
        document.getElementById('newCustomerPassword').value,
        document.getElementById('newCustomerFName').value,
        document.getElementById('newCustomerLName').value,
        document.getElementById('newCustomerCompany').value
    ];
    var customerActive = document.getElementById('newCustomerActive').checked;
    var customerEmail = document.getElementById('newCustomerEmail').value;

    //validate text fields
    for (let i = 0; i < formTextElements.length; i++) {
        if (!validateText(formTextElements[i], 3)) {
            handleValidationFail(document.getElementById('newCustomerCardBody'), 'Text validation failed! Please amend: ' + formTextElements[i]);
            valid = false;
        }
    }

    //validate email
    if (!validateEmail(customerEmail)) {
        handleValidationFail(document.getElementById('newCustomerCardBody'), 'Email validation failed, plese check email field for @ or . !');
        valid = false;
    }

    for (let j = 0; j < formTextElements.length; j++) {
        console.log(formTextElements[j]);
    }
    try {
        if (valid) {
            var uri = "http://localhost:8081/user/createNewuser/" + formTextElements[0] + "/" + formTextElements[1];
            $.post(uri, {},
                function (data, status) {
                    //console.log(data+"  "+status);
                    if (status == "success") {
                        var uri2 = "http://localhost:8081/userDetails/createNewUser/" + formTextElements[0] + "/" + formTextElements[2] + "/" + formTextElements[3] + "/" + customerEmail + "/" + formTextElements[4];
                        $.post(uri2, {},
                            function (data, status) {
                                //console.log(data+"  "+status);
                                if (status == "success") {
                                    notifySuccess(document.getElementById("newCustomerCardBody"), "New customer account was created successfully. Status: " + status);
                                }
                            });
                    }

                });
        }
        //Wait 1 sec to show success/fail message then reload page
        setTimeout(function () {
            window.location.reload();
        }, 1000);
    } catch (err) {
        handleValidationFail(document.getElementById('newCustomerCardBody'), 'Operation failed');
        console.log(err);
    }

}

function toggleUserActive(username, element) {
    var elementStateBeforeClick = !element.checked;
    var uri = "http://localhost:8081/user/updateStatus/" + username + "/" + elementStateBeforeClick;
    try {
        $.post(uri, {},
            function (data, status) {
                //console.log(data+"  "+status);
                if (status == "success") {
                    notifySuccess(document.getElementById("manageCustomersModayBody"), "Customer active status changed to: " + !elementStateBeforeClick);
                }
            });
    } catch (err) {
        handleValidationFail(document.getElementById('manageCustomersModayBody'), 'Failed to update customer status!');
        console.log(err);
    }

}

function toggleUserAdmin(username, element) {
    var elementStateBeforeClick = !element.checked;
    var uri = "http://localhost:8081/user/createAdminUser/" + username + "/" + elementStateBeforeClick;
    try {
        $.post(uri, {},
            function (data, status) {
                //console.log(data+"  "+status);
                if (status == "success") {
                    notifySuccess(document.getElementById("manageCustomersModayBody"), "Customer admin status changed to: " + !elementStateBeforeClick);
                }
            });
    } catch (err) {
        handleValidationFail(document.getElementById('manageCustomersModayBody'), 'Failed to update customer admin status!');
        console.log(err);
    }

}


function createNewMobileUser(creatingUserId) {
    var valid = true;
    var formTextElements = [
        document.getElementById('newMobileUserUsername').value,
        document.getElementById('newMobileUserPassword').value,
        document.getElementById('newMobileUserFName').value,
        document.getElementById('newMobileUserLName').value,
        document.getElementById('newMobileUserCompany').value
    ];
    var mobileUserActive = document.getElementById('newMobileUserActive').checked;
    var mobileEmail = document.getElementById('newMobileUserEmail').value;

    //validate text fields
    for (let i = 0; i < formTextElements.length; i++) {
        if (!validateText(formTextElements[i], 3)) {
            handleValidationFail(document.getElementById('newMobileUserCardBody'), 'Text validation failed! Please amend: ' + formTextElements[i]);
            valid = false;
        }
    }

    //validate email
    if (!validateEmail(mobileEmail)) {
        handleValidationFail(document.getElementById('newMobileUserCardBody'), 'Email validation failed, plese check email field for @ or . !');
        valid = false;
    }

    for (let j = 0; j < formTextElements.length; j++) {
        console.log(formTextElements[j]);
    }

    if (valid) {
        try {
            var uri = "http://localhost:8081/mobileUsers/addNewUser/" + formTextElements[0] + "/" + formTextElements[1];
            $.post(uri, {},
                function (data, status) {
                    //console.log(data+"  "+status);
                    if (status == "success") {
                        var uri2 = "http://localhost:8081/mobileUserDetails/addNewDetails/" + formTextElements[0] + "/" + formTextElements[2] + "/" + formTextElements[3] + "/" + mobileEmail + "/" + formTextElements[4] + "/" + creatingUserId + "/" + true;
                        $.post(uri2, {},
                            function (data, status) {
                                //console.log(data+"  "+status);
                                if (status == "success") {
                                    notifySuccess(document.getElementById("newMobileUserCardBody"), "New mobile user was created successfully. Status: " + status);
                                }
                            });
                    }

                });
        } catch (err) {
            handleValidationFail(document.getElementById('newMobileUserCardBody'), 'Operation failed!');
            console.log(err);
        }
    }
    setTimeout(function () {
        window.location.reload();
    }, 2000);
    //console.log(mobileEmail + ' ' + mobileUserActive);
}

var dataToUpdate = [];
var changedElements = [];

function collectUpdatedMobileUserData(element) {
    if (element.hasAttribute('checked')) { element = element.parentElement; }//if we have a checkbox, get its parrent element, input
    var row = element.closest('tr');//get the row the data is in
    setElementbackgrounfColor(element);
    changedElements.push(element);//store the element that has been changed 
    dataToUpdate.push(row);//store the changed rows
}

function updateMobileUsersData() {
    var validation = true;
    const colsToCheck = [2, 3, 4, 5];
    const emailColumn = 4;
    const idColumn = 1;
    const activeColumn = 6;
    //validate data
    if (dataToUpdate.length > 0) {
        for (let i = 0; i < dataToUpdate.length; i++) {
            var row = dataToUpdate[i];
            for (let j = 0; j < row.cells.length; j++) {
                if (colsToCheck.includes(j)) {
                    if (j == emailColumn) {
                        if (!validateEmail(row.cells[j].innerHTML)) {
                            handleValidationFail(document.getElementById('manageMobileUsersModayBody'), "Email validation failed!");
                            validation = false;
                            break;
                        }
                    } else {
                        if (!validateText(row.cells[j].innerHTML, 2)) {
                            handleValidationFail(document.getElementById('manageMobileUsersModayBody'), "Text validation failed! " + row.cells[j].innerHTML);
                            validation = false;
                            break;
                        }
                    }
                    //console.log(row.cells[j].childNodes[1].checked);
                }



            }

        }
    }
    for (let i = 0; i < dataToUpdate.length; i++) {
        var dataToSend = dataToUpdate[i];
        if (validation) {
            try {
                var uri2 = "http://localhost:8081/mobileUserDetails/updateDetails/" + dataToSend.cells[1].innerHTML + "/" + dataToSend.cells[0].innerHTML + "/" + dataToSend.cells[2].innerHTML + "/" + dataToSend.cells[3].innerHTML + "/" + dataToSend.cells[4].innerHTML + "/" + dataToSend.cells[5].innerHTML + "/" + dataToSend.cells[6].childNodes[1].checked;
                $.post(uri2, {},
                    function (data, status) {
                        if (status == "success") {
                            notifySuccess(document.getElementById("manageMobileUsersModayBody"), "Mobile user data updated successfully. Status: " + status);
                        }
                    });
            } catch (err) {
                handleValidationFail(document.getElementById("manageMobileUsersModayBody"), "Operation failed! ");
                console.log(err);
                break;
            }

        }
    }
    setTimeout(function () {
        window.location.reload();
    }, 2000);

}


function createNewStatusMessage() {
    var validation = true;
    var elements = [
        document.getElementById('newStatusTitle'),
        document.getElementById('newStatusMessage')
    ];
    var active = document.getElementById('newStatusActive').checked;

    for (let i = 0; i < elements.length; i++) {
        if (!validateText(elements[i].value, 5)) {
            handleValidationFail(document.getElementById('newStatusMessageModalBody'), "Validation failed for " + elements[i].value + " !");
            validation = false;
            break;
        }

    }
    if (validation) {
        try {
            //Used ajax as had issues with callback running in case of error
            var uri2 = "http://localhost:8081/websiteStatus/createNewStatusMessage/" + elements[0].value + "/" + elements[1].value + "/" + active;
            $.ajax({
                type: "POST",
                url: uri2,
                data: {},//there is no data returned
                //handle success
                success: function (data) {
                    notifySuccess(document.getElementById("newStatusMessageModalBody"), "New status message was created successfully.");
                },
                //handle error
                error: function (jqXHR, textStatus, errorThrown) {
                    handleValidationFail(document.getElementById("newStatusMessageModalBody"), "Operation failed! Status may already exist!");
                }
            });
        } catch (err) {
            handleValidationFail(document.getElementById("newStatusMessageModalBody"), "Operation failed! Status may already exist!");
            console.log(err);
        }

    }

}

var statusDataToUpdate = [];
var changedStatusElements = [];

function collectUpdatedStatusMsgData(element) {
    if (element.hasAttribute('checked')) { element = element.parentElement; }//if we have a checkbox, get its parrent element, input
    var row = element.closest('tr');//get the row the data is in
    setElementbackgrounfColor(element);
    changedStatusElements.push(element);//store the element that has been changed 
    statusDataToUpdate.push(row);//store the changed rows
}

function updateStatusMsgData() {
    var validation = true;
    const colsToCheck = [1, 2];
    const idColumn = 1;
    const activeColumn = 3;
    //validate data
    if (statusDataToUpdate.length > 0) {
        for (let i = 0; i < statusDataToUpdate.length; i++) {
            var row = statusDataToUpdate[i];
            for (let j = 0; j < row.cells.length; j++) {
                if (colsToCheck.includes(j)) {
                    if (!validateText(row.cells[j].innerHTML, 2)) {
                        handleValidationFail(document.getElementById('updatStatusMsgModalBody'), "Text validation failed! " + row.cells[j].innerHTML);
                        validation = false;
                        break;
                    }
                }
                //console.log(row.cells[j].childNodes[1].checked);
            }
        }
    }
    //send data if valiadtion is OK
    for (let i = 0; i < statusDataToUpdate.length; i++) {
        var dataToSend = statusDataToUpdate[i];
        if (validation) {
            try {
                var uri2 = "http://localhost:8081/websiteStatus/updateWebStatusMessageStatus/" + dataToSend.cells[0].innerHTML + "/" + dataToSend.cells[1].innerHTML + "/" + dataToSend.cells[2].innerHTML + "/" + dataToSend.cells[3].childNodes[1].checked;
                console.log(uri2);

                $.ajax({
                    type: "POST",
                    url: uri2,
                    data: {},//there is no data returned
                    //handle success
                    success: function (data) {
                        notifySuccess(document.getElementById("updateStatusMsgModalBody"), "Status message successfully updated.");
                    },
                    //handle error
                    error: function (jqXHR, textStatus, errorThrown) {
                        handleValidationFail(document.getElementById("updateStatusMsgModalBody"), "Operation failed! Status may already exist!");
                    }
                });

            } catch (err) {
                handleValidationFail(document.getElementById("updateStatusMsgModalBody"), "Operation failed! ");
                console.log(err);
                break;
            }

        }
    }
    setTimeout(function () {
        window.location.reload();
    }, 2000);
}

function deleteStatusMessage(msgId, element) {
    console.log(msgId);
    console.log(element.closest('tr'));
    try {
        var uri2 = "http://localhost:8081/websiteStatus/deleteStatusMessage/" + msgId;
        console.log(uri2);
        $.ajax({
            type: "POST",
            url: uri2,
            data: {},//there is no data returned
            //handle success
            success: function (data) {
                notifySuccess(document.getElementById("updateStatusMsgModalBody"), "Status message was deleted successfully.");
            },
            //handle error
            error: function (jqXHR, textStatus, errorThrown) {
                handleValidationFail(document.getElementById("updateStatusMsgModalBody"), "Operation failed! Status has not been deleted!");
            }
        });
    } catch (err) {
        handleValidationFail(document.getElementById("updatStatusMsgModalBody"), "Operation failed! ");
        console.log(err);
    }
    element.closest('tr').remove();
}

function createNewWebContent(element) {
    //console.log(element.getElementsByTagName('input'));
    var inputs = element.getElementsByTagName('input');
    var values = [];

    for (let i = 0; i < inputs.length; i++) {
        if (inputs[i].hasAttribute('checked')) {
            inputValue = inputs[i].checked;
        } else { inputValue = inputs[i].value; }
        values.push(inputValue);
    }
    var uriAddress = 'http://localhost:8081/webContent/addWebContent/'+values[0]+'/'+values[1]+'/'+values[2]+'/'+values[3]+'/'+values[4]+'/'+values[5]+'/'+values[6];
    sendData(uriAddress,'POST', 'newWebContentModalBody', 'New web content saved!', 'An error occured, content was not saved!', 'operation error')
    setTimeout(function () {
        window.location.reload();
    }, 2000);  
    //console.log(values);

}

var webContentDataToUpdate=[];
var changedWebContentElements=[];

function collectUpdatedWebContentData(element){
    collectTableData(element, webContentDataToUpdate, changedWebContentElements);
}

function deleteWebsiteContent(id, element){
    try {
        var dwcurl ='http://localhost:8081/webContent/deleteWebsiteContent/'+id ;
        sendData(dwcurl, 'POST', 'manageWebsiteContentModayBody', 'Content deleted!', 'Operation failed!', 'Operation failed!');
    } catch (error) {
        console.log(error);
        handleValidationFail(document.getElementById(manageWebsiteContentModayBody),'Operation Failed!');
    }
    element.closest('tr').remove();

}

function updateWbContentData(){
    var validation = true;
    validateTableRowArrayOfInputs(webContentDataToUpdate, [1,2,3,4,5,6], emailColumn = 'na', 3, 'manageWebsiteContentModayBody', 'Validation successfull!', 'Validation failed', 'Operation failed!');
    for (let i = 0; i < webContentDataToUpdate.length; i++) {            
        var uriAddress = 'http://localhost:8081/webContent/updateWebContent/'+webContentDataToUpdate[i].cells[0].innerHTML+'/'+webContentDataToUpdate[i].cells[1].innerHTML+
        '/'+webContentDataToUpdate[i].cells[2].innerHTML+'/'+webContentDataToUpdate[i].cells[3].innerHTML+'/'+webContentDataToUpdate[i].cells[4].innerHTML+
        '/'+webContentDataToUpdate[i].cells[5].innerHTML+'/'+webContentDataToUpdate[i].cells[6].innerHTML+'/'+webContentDataToUpdate[i].cells[7].childNodes[3].checked;
        sendData(uriAddress, 'POST', 'manageWebsiteContentModayBody', 'Webcontent updated!', 'Update failed!', 'Operation failed!');    
    }
    setTimeout(function () {
        window.location.reload();
    }, 2000);     
}

function createNewCard(element){
    var inputs = element.getElementsByTagName('input');
    var values = [];
    var validation=true;
    for (let i = 0; i < inputs.length; i++) {
        if (inputs[i].hasAttribute('checked')) {
            inputValue = inputs[i].checked;
        } else { inputValue = inputs[i].value; }
        values.push(inputValue);
    } 

    for (let j = 0; j < values.length; j++) {
        if (!inputs[j].hasAttribute('checked')) {
            if(!validateText(inputs[j].value, 1)){
                validation=false;
                handleValidationFail(element, 'Validation failed, fields must not be blank!')
            }
        }
    }

    if (values[4]=='') {values[4]='  ' }   // if no image provided , replace with '  '

    var uriAddress = 'http://localhost:8081/websitecards/addNewCard/'+values[0]+'/'+values[1]+'/'+values[4]+'/'+values[2]+'/'+values[3]+'/'+values[5];
    
    if(validation){
    sendData(uriAddress,'POST', 'newCardModalBody', 'New card saved!', 'An error occured, card was not saved!', 'Operation error!');
    }

    setTimeout(function () {
        window.location.reload();
    }, 2000); 
}

function deleteCard(id, element){
    try {
        var deleteCardUrl ='http://localhost:8081/websitecards/deleteCard/'+id ;
        sendData(deleteCardUrl, 'POST', 'updateCardModalBody', 'Content deleted!', 'Operation failed!', 'Operation failed!');
    } catch (error) {
        console.log(error);
        handleValidationFail(document.getElementById(manageWebsiteContentModayBody),'Operation Failed!');
    }
    element.closest('tr').remove();
}

var cardsToUpdate=[];
var changedCardElements=[];

function collectUpdatedCardData(element){
    collectTableData(element, cardsToUpdate, changedCardElements);
    
}

function updateCardData(){
    var validation = true;
    validateTableRowArrayOfInputs(cardsToUpdate, [1,2,3,4,5], emailColumn = 'na', 3, 'updateCardModalBody', 'Validation successfull!', 'Validation failed', 'Operation failed!');
    console.log(cardsToUpdate[0].cells[0].innerHTML);
    console.log(cardsToUpdate[0].cells[1].innerHTML);
    console.log(cardsToUpdate[0].cells[2].innerHTML);
    console.log(cardsToUpdate[0].cells[3].innerHTML);
    console.log(cardsToUpdate[0].cells[4].innerHTML);
    console.log(cardsToUpdate[0].cells[5].innerHTML);
    console.log(cardsToUpdate[0].cells[6].childNodes[1].checked);
   
    for (let i = 0; i < cardsToUpdate.length; i++) {               
        var uriAddress = 'http://localhost:8081/websitecards/updateCard/'+cardsToUpdate[i].cells[0].innerHTML+'/'+cardsToUpdate[i].cells[1].innerHTML+
        '/'+cardsToUpdate[i].cells[2].innerHTML+'/'+cardsToUpdate[i].cells[3].innerHTML+'/'+cardsToUpdate[i].cells[4].innerHTML+
        '/'+cardsToUpdate[i].cells[5].innerHTML+'/'+cardsToUpdate[0].cells[6].childNodes[1].checked;
        sendData(uriAddress, 'POST', 'updateCardModalBody', 'Card updated!', 'Update failed!', 'Operation failed!');    
    }
    
   
    setTimeout(function () {
        window.location.reload();
    }, 2000); 
}

function createNewFaq(element){
    var inputs = element.getElementsByTagName('input');
    var values = [];
    var validation = true;

    for (let i = 0; i < inputs.length; i++) {
        if (inputs[i].hasAttribute('checked')) {
            inputValue = inputs[i].checked;
        } else { inputValue = inputs[i].value; }
        values.push(inputValue);
    } 
    
    for (let j = 0; j < values.length; j++) {
        if (!inputs[j].hasAttribute('checked')) {
            if(!validateText(inputs[j].value, 1)){
                validation=false;
                handleValidationFail(element, 'Validation failed, fields must not be blank!')
            }
        }
    }


    var uriAddress = 'http://localhost:8081/faq/addNewFaq/'+values[0]+'/'+values[1]+'/'+values[2]+'/'+values[3];
    //console.log(uriAddress);
    //console.log(validation);
    if(validation){
    sendData(uriAddress,'POST', 'newFaqModalBody', 'New faq saved!', 'An error occured, faq was not saved!', 'Operation error!');
        }
    
    setTimeout(function () {
        window.location.reload();
    }, 2000); 
    
}

function deleteFaq(id){
    var uriAddress = 'http://localhost:8081/faq/deleteFaq/'+id;
    sendData(uriAddress,'POST', 'updateFAQModalBody', 'Faq deleted!!', 'An error occured, faq was not deleted!', 'Operation error!');
    element.closest('tr').remove();
}

var faqsToUpdate=[];
var changedFaqElements=[];

function collectUpdatedFaqData(element){
    collectTableData(element, faqsToUpdate, changedFaqElements);
}

function updateFaqData(){
    console.log(faqsToUpdate[0]);
}


//returns true if email is valid
function validateEmail(htmlElement) {
    if (htmlElement.includes('@') & htmlElement.includes('.')) {
        if (htmlElement.length > 5) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }

}

//returns true if html element equal to or greater than the required length
function validateText(htmlElement, requiredLength) {
    if (htmlElement.length != 0) {
        if (htmlElement.length >= requiredLength) { return true; } else { return false; }
    } else { return false; }
}


//create a "danger" alert div and add it to a sepcified element
function handleValidationFail(parentElement, errorMessage) {
    const alertDiv = document.createElement("div");
    alertDiv.id = 'validationFailAlertDiv';
    alertDiv.classList = "alert alert-danger";
    alertDiv.setAttribute("role", "alert");
    const node = document.createTextNode(errorMessage);
    alertDiv.appendChild(node);
    parentElement.append(alertDiv);
    var delayInMilliseconds = 2000; // 2 sec
    setTimeout(function () {
        document.getElementById('validationFailAlertDiv').remove();
    }, delayInMilliseconds);

}

//create a "success" alert div and add it to a sepcified element
function notifySuccess(parentElement, message) {
    const alertDiv = document.createElement("div");
    alertDiv.id = 'successAlertDiv';
    alertDiv.classList = "alert alert-success";
    alertDiv.setAttribute("role", "alert");
    const node = document.createTextNode(message);
    alertDiv.appendChild(node);
    parentElement.append(alertDiv);
    var delayInMilliseconds = 2000; // 2 sec
    setTimeout(function () {
        document.getElementById('successAlertDiv').remove();
    }, delayInMilliseconds);

}

function setElementbackgrounfColor(element) {
    element.style.backgroundColor = "yellow";
}

function sendData(uriAddress, opType, parentElementName, successmessage, failedMessage, opErrorMessage) {
    try {
        $.ajax({
            type: opType,
            url: uriAddress,
            data: {},//there is no data returned
            //handle success
            success: function (data) {
                notifySuccess(document.getElementById(parentElementName), successmessage);
            },
            //handle error
            error: function (jqXHR, textStatus, errorThrown) {
                handleValidationFail(document.getElementById(parentElementName), failedMessage);
            }
        });

    } catch (err) {
        handleValidationFail(document.getElementById(parentElementName), opErrorMessage);
        console.log(err);
    }
}

function validateTableRowArrayOfInputs(dataToCheck, columnsToCheck, emailColumn = 'na', textLength, parentElementName, successMessage, failedMessage, opErrorMessage) {

    try {
        if (dataToCheck.length > 0 & columnsToCheck.length != 0) {
            for (let i = 0; i < dataToCheck.length; i++) {// Go through each row of the array
                var row = dataToCheck[i];
                for (let j = 0; j < row.cells.length; j++) {//go through each cell in the array
                    if (columnsToCheck.includes(j)) { //do we need to check that data
                        if (emailColumn != 'na') { //do we need to check for emails
                            if (j == emailColumn) { //if yes, is this a cell containing an email 
                                if (!validateEmail(row.cells[j].innerHTML)) {
                                    handleValidationFail(document.getElementById(parentElementName), successMessage);
                                    validation = false;
                                    break;
                                }
                            }
                        } else { // if nor email , validate text
                            if (!validateText(row.cells[j].innerHTML, textLength)) {
                                handleValidationFail(document.getElementById(parentElementName), failedMessage);
                                validation = false;
                                break;
                            }
                        }
                        //console.log(row.cells[j].childNodes[1].checked);
                    }



                }

            }
        }
    } catch (err) {
        console.log(err);
        handleValidationFail(document.getElementById(parentElement), opErrorMessage);
    }
}

function collectTableData(element, changedRows, changedElement){
    if (element.hasAttribute('checked')) { element = element.parentElement; }//if we have a checkbox, get its parrent element, input
    var row = element.closest('tr');//get the row the data is in
    setElementbackgrounfColor(element);
    changedElement.push(element);//store the element that has been changed 
    changedRows.push(row);//store the changed rows
}

//Show active status messages
document.onload(
    setTimeout(function () {
        document.getElementById('stampQRStatusMessageSection').remove();
    }, 5000)
);
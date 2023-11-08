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
    }
    setTimeout(function () {

        window.location.reload();

    }, 2000);
    
    //console.log(mobileEmail + ' ' + mobileUserActive);
}

//************************************************Update User Details**********************************************************************************/
function updateUserDetails(thUserId, thFirstName, thLastName, thEmail, thCompany) {

    const passwordField = 1;
    var validation = true;

    console.log(thUserId+" "+thFirstName+" "+thLastName+" "+thEmail+" "+thCompany);

    var formTextElements = [
        document.getElementById('updatedUserUsername').value,
        document.getElementById('updatedUserPassword').value,
        document.getElementById('updatedUserFName').value,
        document.getElementById('updatedUserLName').value,
        document.getElementById('updatedUserCompany').value
    ];
    var email = document.getElementById('updatedUserEmail').value;

    //validate text fields
    for (let i = 0; i < formTextElements.length; i++) {
        if(i==passwordField){
            if(formTextElements[i].length==0){break;}            
            }//skip the password field if it is empty, pnly used if user wants to ament their password
        if (!validateText(formTextElements[i], 3)) {
            handleValidationFail(document.getElementById('userDetailsCardBody'), 'Text validation failed! Please amend: ' + formTextElements[i]);
            validation=false;
        }
    }

    //validate email
    if (!validateEmail(email)) {
        handleValidationFail(document.getElementById('userDetailsCardBody'), 'Email validation failed, plese check email field for @ or . !');
        validation=false;
    }

    if(validation){
        var uri = "http://localhost:8081/userDetails/update/"+thUserId+"/"+formTextElements[2]+"/"+formTextElements[3]+"/"+email+"/"+formTextElements[4];
        $.post(uri, {},
            function (data, status) {
                //console.log(data+"  "+status);
                if (status == "success") {
                    notifySuccess(document.getElementById("userDetailsCardBody"), "QR Code was created successfully. Status: " + status);                    
                }

            });
    }

    for (let j = 0; j < formTextElements.length; j++) {
        console.log(formTextElements[j]);

    }
    console.log(email);
}

//************************************************ Create new QR code ********************************************************************************************/

function createNewQRCode() {
    
    var validation = true;

    var qrPlainText = document.getElementById('textToEncode').value;
    var qrTag = document.getElementById('qrTag').value;
    var eccLevel = document.getElementsByName('errorCorrectionLevelRadio');
    var selectedEccLevel;

    for (let i = 0; i < eccLevel.length; i++) {
        if (eccLevel[i].checked) { selectedEccLevel = eccLevel[i].value; }
    }

    if (!validateText(qrPlainText, 1)) {
        handleValidationFail(document.getElementById('createNewQRCodeModalBody'), 'Text validation failed! Please amend to 1+ characters: ' + qrPlainText);
        validation = false;
    }

    if (!validateText(qrTag, 3)) {
        handleValidationFail(document.getElementById('createNewQRCodeModalBody'), 'Text validation failed! Please amend to 3+ characters: ' + qrTag);
        validation = false;
    }

    if (validation) {
        var uri = "http://localhost:8090/createQRCode/" + qrPlainText + "/" + qrTag + "/" + selectedEccLevel;
        sendData(uri,"GET","createNewQRCodeModalBody","New QR code has been created!",'Operation failed!','Operation failed!');
        document.getElementById('textToEncode').value = "";
        document.getElementById('qrTag').value = "";        
    }
    
    setTimeout(function () {

        window.location.reload();

    }, 2000);
}
//*************************************************Update mobile user details ***************************************************************************************************************

var originalTableData = [];

function updateMobileUsersData() {
    const emailColumn = 4;
    const idColumn = 1;
    const activeColumn = 6;
    var delayInMilliseconds = 2000; // 2 sec to close modal after operation
    var dataValidation = true;
    var dataToUpdate = [];
    var newTableData = [];
    var rowdata = [];

    //get updated table data
    var rows = document.getElementById('mobileUsersTable').rows;
    for (let i = 1; i < rows.length; i++) {//iterate through each row
        for (let j = 0; j < rows[i].cells.length; j++) { // iterate through each cell 
            if (j == activeColumn) {
                rowdata.push(document.getElementById('mobileUserActive' + rowdata[idColumn]).checked);
            } else { rowdata.push(rows[i].cells[j].innerHTML); }

        }
        newTableData.push(rowdata);
        rowdata = [];
    }
    var modifiedCells = [];
    //go through rows
    for (let k = 0; k < newTableData.length; k++) {

        let newCells = newTableData[k];
        let oldCells = originalTableData[k];

        //got through cells
        for (let l = 0; l < newCells.length; l++) {
            //if any of the cells of the row have changed, put the row into dataToUpdate
            if (newCells[l] != oldCells[l]) {              
                dataToUpdate.push(newCells);
                break;
            }

        }

    }

    if (!dataToUpdate.length == 0) {
        for (let i = 0; i < dataToUpdate.length; i++) {
            let newDataRow = dataToUpdate[i];//get a row
            for (let j = 0; j < newDataRow.length; j++) {// validate data in each element of the row
                if (j != emailColumn & j != idColumn & j != activeColumn) { //if the cell does not contain an email address validate it againtst text                    
                    if (!validateText(newDataRow[j], 3)) {
                        handleValidationFail(document.getElementById('manageMobileUsersModayBody'), 'Text validation failed! Please amend to 1+ characters: ' + newDataRow[j]);
                        dataValidation = false;
                    }
                }
            }
            // validate a cell containing an email
            if (!validateEmail(newDataRow[emailColumn])) {
                handleValidationFail(document.getElementById('manageMobileUsersModayBody'), 'Email validation failed, plese check email field for @ or . !');
                dataValidation = false;
            }

        }
    }
    //console.log('Array length: '+dataToUpdate.length+" "+"Data validation:"+ dataValidation);
    if (!dataToUpdate.length == 0 & dataValidation == true) {
        console.log('In sendoing loop');
        for (let index = 0; index < dataToUpdate.length; index++) {
            dataToSend = dataToUpdate[index];
            try {
                var uri2 = "http://localhost:8081/mobileUserDetails/updateDetails/" + dataToSend[1] + "/" + dataToSend[0] + "/" + dataToSend[2] + "/" + dataToSend[3] + "/" + dataToSend[4] + "/" + dataToSend[5] + "/" + dataToSend[6];
                $.post(uri2, {},
                    function (data, status) {
                        //console.log(data+"  "+status);
                        //if(status=="success"){     }
                        console.log('Update mobile user details sent!');

                    });
            } catch (err) {
                console.log(err);
                break;
            }
        }
        originalTableData=newTableData;
        newTableData=[];
        dataToUpdate=[];

        //Go through each cell and change the background color back to white
        for (let i = 1; i < rows.length; i++) {//iterate through each row
            for (let j = 0; j < rows[i].cells.length; j++) { // iterate through each cell 
                rows[i].cells[j].style.backgroundColor = "white";
            }            
        }

        notifySuccess(document.getElementById("manageMobileUsersModayBody"), "Mobile users updated successfully!");
    }
    console.log(originalTableData);
    console.log(newTableData);
    console.log(dataToUpdate);
}


//*************************************************Update mobile user details ***************************************************************************************************************

function getOriginalTableData() {
    const emailColumn = 4;
    const idColumn = 1;
    const activeColumn = 6;
    var originalRows = [];
    var rowdata = [];

    //get rows and data from a cell 
    var rows = document.getElementById('mobileUsersTable').rows;
    for (let i = 1; i < rows.length; i++) {//iterate through each row
        for (let j = 0; j < rows[i].cells.length; j++) { // iterate through each cell 
            if (j == activeColumn) {
                rowdata.push(document.getElementById('mobileUserActive' + rowdata[idColumn]).checked);
            } else { rowdata.push(rows[i].cells[j].innerHTML); }
        }
        originalRows.push(rowdata);
        rowdata = [];
    }

    originalTableData = originalRows;

    console.log(originalRows);

}

function assignMobileUserToQRCode(mobileUserName, qrCodeId,element){       
    var assigned = element.checked;
    console.log(mobileUserName);
    console.log(qrCodeId);
    console.log(element.checked);
    
   
    if(assigned){
        var uri1 = "http://localhost:8081/assingQrToMobileUser/"+mobileUserName+"/"+qrCodeId;
        sendData(uri1,"POST","manageQRCodesModal","QR code assigned!",'Operation failed!','Operation failed!');
    }        

   
    if(!assigned){
        var uri2 = "http://localhost:8081/deleteQrToMobileUser/"+mobileUserName+"/"+qrCodeId;
        sendData(uri2,"POST","manageQRCodesModal","QR code unassigned!",'Operation failed!','Operation failed!');
    }
    if(element.name=="mobileUserRC"){       
    setTimeout(function () {
        window.location.reload();
    }, 2000);
    }
}

function deleteExistingQRCodes(qrDataId, element){
    var uri = "http://localhost:8081/qrData/deleteQRCode/"+qrDataId;
    sendData(uri,"POST","manageQRCodesModal","QR code successfully deleted!",'Operation failed!','Operation failed!');        
    element.parentElement.parentElement.remove();
}

function changeQRCodeActiveStatus(qrCodeId, element){
    console.log(element.checked);
    console.log('QR code ID: '+qrCodeId);
    var uri = "http://localhost:8081/qrData/updateActiveStatus/"+qrCodeId+"/"+element.checked;
    sendData(uri,"POST","manageQRCodesModal","QR code status updated!",'Operation failed!','Operation failed!');    
}


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

function setElementbackgrounfColor(element){
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

document.onload(
    setTimeout(function () {

        document.getElementById('stampQRStatusMessageSection').remove();

    }, 5000)
);
function createNewMobileUser(){   
    
    console.log('in processing function');


    var mobileUsername = document.getElementById('newMobileUserUsername').value;
    var mobilePassword = document.getElementById('newMobileUserPassword').value;
    var mobileUserActive = document.getElementById('newMobileUserActive').checked;
    var mobileFirstName = document.getElementById('newMobileUserFName').value;
    var mobileLastName = document.getElementById('newMobileUserLName').value;
    var mobileEmail = document.getElementById('newMobileUserEmail').value;
    var mobileCompany = document.getElementById('newMobileUserCompany').value;

    console.log(mobileUsername+' '+mobilePassword+' '+mobileUserActive+' '+ mobileFirstName+' '+mobileLastName+' '+mobileEmail+' '+mobileCompany);
}

console.log('customscript loaded');
package com.stampqr.dbaccessService;

import com.stampqr.dbaccessService.helper.UserDetailsWeb;
import com.stampqr.dbaccessService.resources.*;
import com.stampqr.dbaccessService.service.*;
import com.stampqr.dbaccessService.wrapperClasses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class MainController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UsersServiceImpl usersService;

    @Autowired
    AuthoritiesService authoritiesService;

    @Autowired
    WebSiteContentServiceImpl webSiteContentService;

    @Autowired
    WebStatusMessageServiceImpl webStatusMessage;

    @Autowired
    QRDataService qrDataService;

    @Autowired
    WebsiteCardsServiceImpl websiteCardsService;

    @Autowired
    MobileUsersServiceImpl mobileUsersService;

    @Autowired
    MobileAuthoritiesService mobileAuthoritiesService;

    @Autowired
    MobileUserDetailsServiceimpl mobileUserDetailsServiceimpl;

    @Autowired
    MobileUserQRDataServiceImpl mobileUserQRDataService;

    @Autowired
    FaqServiceImpl faqService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @PostMapping(value="/users/adduser/{userName}/{password}")
    public boolean addNewUser(@PathVariable("userName") String userName,@PathVariable("password") String password){

    boolean success = usersService.saveNewUser(userName,passwordEncoder.encode(password));

    return success;
    }

    @GetMapping(value="/users/getUserByUsername/{username}")
    public Users getUserByUsername(@PathVariable("username") String username){
        return usersService.getUserByUsername(username);
    }

    @GetMapping(value="/authorities/getAuthoritiesByUsername/{username}")
    public AuthoritiesList getAuthoritiesByUsername(@PathVariable("username") String username){
        AuthoritiesList al = new AuthoritiesList();
        al.setAuthoritiesList(authoritiesService.getAuthoritiesByUsername(username));
        return al;
    }

    @PostMapping(value="webContent/addWebContent/{pageName}/{sectionName}/{sectionComponent}/{sectionSubcomponent}/{contentText}/{imageUrl}/{active}")
    public void addWebSiteContent(@PathVariable("pageName") String pageName,@PathVariable("sectionName") String sectionName,@PathVariable("sectionComponent") String sectionComponent,
                                  @PathVariable("sectionSubcomponent") String sectionSubcomponent,@PathVariable("contentText") String contentText,@PathVariable("imageUrl") String imageUrl,
                                  @PathVariable("active") Boolean active){

        webSiteContentService.addWebSiteContent(pageName,sectionName,sectionComponent,sectionSubcomponent,contentText,imageUrl,active);
    }

    @PostMapping(value="webContent/updateWebContent/{id}/{pageName}/{sectionName}/{sectionComponent}/{sectionSubcomponent}/{contentText}/{imageUrl}/{active}")
    public void updateWebSiteContent(@PathVariable("id") Long id, @PathVariable("pageName") String pageName,@PathVariable("sectionName") String sectionName,@PathVariable("sectionComponent") String sectionComponent,
                                  @PathVariable("sectionSubcomponent") String sectionSubcomponent,@PathVariable("contentText") String contentText,@PathVariable("imageUrl") String imageUrl,
                                  @PathVariable("active") Boolean active){

        webSiteContentService.updateWebsiteContent(id,pageName,sectionName,sectionComponent,sectionSubcomponent,contentText,imageUrl,active);
    }

    @GetMapping(value ="/webContent/getPageContent/{pageName}")
    public WebSiteContentList getSiteContentbyPage(@PathVariable("pageName") String pageName){

        WebSiteContentList wscl = new WebSiteContentList();
        wscl.setWebSiteContentList(webSiteContentService.getAllActiveContentByPage(pageName));

        return wscl;
    }

    @GetMapping(value="/webContent/getAllWebsiteContent")
    public WebSiteContentList getAllWebsiteContent(){
        WebSiteContentList wscl = new WebSiteContentList();
        wscl.setWebSiteContentList(webSiteContentService.getAllContent());
        return wscl;
    }

    @PostMapping(value="/webContent/deleteWebsiteContent/{id}")
    public void deleteWebsiteContent(@PathVariable("id") Long id){
        webSiteContentService.deleteContent(id);
    }


    @PostMapping(value="/websiteStatus/createNewStatusMessage/{title}/{message}/{active}")
    public void createNewStatusMessage(@PathVariable("title") String title, @PathVariable("message") String message, @PathVariable("active") Boolean active){
        webStatusMessage.addNewStatusMessage(title,active,message);
    }

    @PostMapping(value="/websiteStatus/updateWebStatusMessageStatus/{id}/{title}/{message}/{active}")
    public void updateStatus (@PathVariable("id") Long id,@PathVariable("title") String title,@PathVariable("message") String message,@PathVariable("active") Boolean active){
         webStatusMessage.updateStatusMessage(id, title, message, active);
    }


    @GetMapping(value="/websiteStatus/getActiveStatusMessages")
    public WebStatusMessageList getActiveStatusMessages(){
        return webStatusMessage.getActiveStatusMessages();
    }

    @PostMapping(value="/websiteStatus/deleteStatusMessage/{id}")
    public void deleteStatus(@PathVariable("id") Long id){
        webStatusMessage.deleteStatusMessage(id);
    }


    @GetMapping(value="/websiteStatus/getAll")
    public WebStatusMessageList getAllStatuses(){

        WebStatusMessageList wsml = new WebStatusMessageList();
        wsml.setWebStatusMessages(webStatusMessage.getAllStatusMessages());
        return wsml;
    }

    @GetMapping(value="/qrdata/save/{plaintText}/{encodedText}/{errorCorrectionLevel}/{qRDataTag}/{imgUrl}/{ver}/{userId}/{active}")
    public void saveQRData(@PathVariable("plaintText") String plaintText,@PathVariable("encodedText") String encodedText,@PathVariable("errorCorrectionLevel") String errorCorrectionLevel,
                           @PathVariable("qRDataTag") String qRDataTag,@PathVariable("imgUrl") String imgUrl,@PathVariable("ver") Long ver,
                           @PathVariable("userId") Long userId, @PathVariable("active") Boolean active){

        QRData qrData = new QRData();
        qrData.setPlainText(plaintText);
        qrData.setEncodedText(encodedText);
        qrData.setErrorCorrectionLevel(errorCorrectionLevel);
        qrData.setQrDataTag(qRDataTag);
        qrData.setImageURL(imgUrl);
        qrData.setVer(ver);
        qrData.setUserId(userId);
        qrData.setActive(active);

        //System.out.println(encodedText);

        qrDataService.saveQRData(qrData);

    }

    @PostMapping(value="/qrData/deleteQRCode/{id}")
    public void deleteQrData(@PathVariable("id") Long id){
        qrDataService.deleteQrData(id);
    }

    @PostMapping(value="/qrData/updateActiveStatus/{id}/{active}")
    public void updateQrDataActiveStatus(@PathVariable("id") Long id,@PathVariable("active") Boolean active){
        qrDataService.updateQRDataActiveStatus(id, active);
    }

    @GetMapping(value="/qrData/getActiveDataByUserId/{userId}")
    public QRDataList getActiveQRDataByUserId(@PathVariable("userId") Long userId){
        QRDataList qrDataList = new QRDataList();

        qrDataList.setQrDataList(qrDataService.getActiveQRDataByUserId(userId));

        return qrDataList;
    }

    @GetMapping(value="/qrData/getAllDataByUserId/{userId}")
    public QRDataList getAllQRDataByUserId(@PathVariable("userId") Long userId){
        QRDataList qrDataList = new QRDataList();

        qrDataList.setQrDataList(qrDataService.getQRDataByUserId(userId));

        return qrDataList;
    }

    @GetMapping(value="/qrData/getLastThreeDataByUserId/{userId}")
    public QRDataList getLastThreeQRDataByUserId(@PathVariable("userId") Long userId){
        QRDataList qrDataList = new QRDataList();

        qrDataList.setQrDataList(qrDataService.findLastThreeByUserIdAndActiveOrderByCreateDate(userId));

        return qrDataList;
    }


    @GetMapping(value="/qrData/getAssignedUsers/{qrDataId}")
    public QRDataAssignmentList getQRDataAssignedUsers(@PathVariable("qrDataId") Long qrDataId){//get a list of assigned users for each qr data
        QRDataAssignmentList qrDataAssignmentList = new QRDataAssignmentList();
        qrDataAssignmentList.setMobileUserQRDataList(mobileUserQRDataService.getMobileUsersOfAQRCode(qrDataId));
        return qrDataAssignmentList;
    }


    @GetMapping(value="/mobileUsers/getAssignedQRData/{userName}")
    public QRDataAssignmentList getUserAssignedQRData(@PathVariable("userName") String userName){
        QRDataAssignmentList qrDataAssignmentList = new QRDataAssignmentList();
        qrDataAssignmentList.setMobileUserQRDataList(mobileUserQRDataService.getQRCoderForMobileUser(userName));
        return qrDataAssignmentList;
    }

    @PostMapping(value="/assingQrToMobileUser/{mobileUserUserName}/{qrDataId}")
    public void saveMobileUserQrDataAssignment(@PathVariable("mobileUserUserName") String mobileUserUserName,@PathVariable("qrDataId") Long qrDataId){
        System.out.println("Saving user QR assignment");
        mobileUserQRDataService.saveMobileUserQrDataAssignment(mobileUserUserName,qrDataId);
    }

    @PostMapping(value="/deleteQrToMobileUser/{mobileUserUserName}/{qrDataId}")
    public void deleteMobileUserQrDataAssignment(@PathVariable("mobileUserUserName") String mobileUserUserName,@PathVariable("qrDataId") Long qrDataId){
        System.out.println("Deleting user QR assignment");
        mobileUserQRDataService.deleteMobileUserQrDataAssignment(mobileUserUserName,qrDataId);
    }


    @GetMapping(value="/websitecards/getactivecardsforpage/{pageName}")
    public WebsiteCardsList getActiveCardsForPage(@PathVariable("pageName") String pageName){
        WebsiteCardsList websiteCardsList = new WebsiteCardsList();
        websiteCardsList.setWebsiteCardList(websiteCardsService.getActiveCardsByPageName(pageName));

         return websiteCardsList;
    }
    @PostMapping(value="websitecards/addNewCard/{pageName}/{sectionName}/{image}/{title}/{text}/{active}")
    public void addNewCard(@PathVariable("pageName")String pageName,@PathVariable("sectionName") String sectionName,@PathVariable("image")  String image,@PathVariable("title")  String title,@PathVariable("text") String text,@PathVariable("active") Boolean active){
        websiteCardsService.addNewCard(pageName,sectionName,image,title,text,active);
    }

    @PostMapping(value="/websitecards/updateCard/{id}/{pageName}/{sectionName}/{image}/{title}/{text}/{active}")
    public void updateCard(@PathVariable("id") Long id, @PathVariable("pageName")String pageName,@PathVariable("sectionName") String sectionName,@PathVariable("image")  String image,@PathVariable("title")  String title,@PathVariable("text") String text,@PathVariable("active") Boolean active){
        websiteCardsService.updateCard(id,pageName,sectionName,image,title,text,active);
    }
    @PostMapping(value="/websitecards/deleteCard/{id}")
    public void deleteCard(@PathVariable("id") Long id){
        websiteCardsService.deleteCard(id);
    }
    @GetMapping(value="/websitecards/getAllCards")
    public WebsiteCardsList getAllCards(){
        WebsiteCardsList wcl = new WebsiteCardsList();
        wcl.setWebsiteCardList(websiteCardsService.getAllCards());
        return wcl;
    }

    @GetMapping(value="/websitecards/getactivecardsforpagesection/{pageName}/{sectionName}")
    public WebsiteCardsList getActiveCardsForPAgeSection(@PathVariable("pageName") String pageName,@PathVariable("sectionName")  String sectionName){
        WebsiteCardsList websiteCardsList = new WebsiteCardsList();
        websiteCardsList.setWebsiteCardList(websiteCardsService.getActiveCardsByPageNameAndSectionName(pageName,sectionName));
        return websiteCardsList;
    }

    @GetMapping(value="/userDetails/getUserDetails/{userName}")
    public UserDetails getUserDetails(@PathVariable("userName") String username){

        return userDetailsService.getUserDetails(username);

    }
    @PostMapping(value="userDetails/update/{id}/{fname}/{lname}/{email}/{company}")
    public void updateUserDetails(@PathVariable("id") Long id,@PathVariable("fname") String fName,@PathVariable("lname") String lName,@PathVariable("email") String email,@PathVariable("company") String company){

        userDetailsService.updateUserDetails(id,fName,lName,email,company);

    }

    @PostMapping(value="userDetails/createNewUser/{userName}/{fname}/{lname}/{email}/{company}")
    public void createUserDetails(@PathVariable("userName") String username,@PathVariable("fname") String fName,@PathVariable("lname") String lName,@PathVariable("email") String email,@PathVariable("company") String company){

        userDetailsService.saveNewUserDetails (username,fName,lName,email,company);

    }


    @PostMapping(value="/user/createNewuser/{userName}/{password}")
    public void createNewUser(@PathVariable("userName") String userName,@PathVariable("password") String password){
        usersService.saveNewUser(userName,password);
        authoritiesService.setAuthority("USER",userName);
    }

    @PostMapping(value="/user/createAdminUser/{userName}/{isCurrentlyAdmin}")
    public void createAdminUser(@PathVariable("userName") String userName,@PathVariable("isCurrentlyAdmin") Boolean isCurrentlyAdmin){
        if(isCurrentlyAdmin){
            authoritiesService.setAuthority("USER", userName);
        }else{
            authoritiesService.setAuthority("ADMINISTRATOR", userName);
        }

    }
    @PostMapping(value="/user/updateStatus/{username}/{currentStatus}")
    public void changeUserEnabledStatus(@PathVariable("username") String userName,@PathVariable("currentStatus") Boolean currentStatus){
        usersService.updateUserStatus(userName, !currentStatus);
    }



    @GetMapping(value="/userDetails/getAllUserDetails")
    public UserDetailsWebList getUserDetails(){
        UserDetailsWebList udwl = new UserDetailsWebList();
        List<UserDetails> udl = userDetailsService.getAllUserDetails().getUserDetailsList();

        for (UserDetails userDetail:udl) {
            UserDetailsWeb udw = new UserDetailsWeb();
            udw.setUd(userDetail);
            udw.setIsActive(usersService.isEnabled(userDetail.getUsername()));
            udw.setIsAdmin(authoritiesService.isAdmin(userDetail.getUsername(),"ADMINISTRATOR"));
            udwl.getUserDetailsWebList().add(udw);
        }
        return udwl;
    }

    @PostMapping(value = "mobileUsers/addNewUser/{username}/{password}")
    public void addUNewMobileUser(@PathVariable("username") String username,@PathVariable("password") String password){
        mobileUsersService.addNewMobileUser(username,passwordEncoder.encode(password));
        mobileAuthoritiesService.SetMobileAuthority(username);

    }

    @PostMapping(value="mobileUserDetails/addNewDetails/{username}/{fname}/{lname}/{email}/{company}/{creatingUserId}/{active}")
    public void addNewMobileUserDetails(@PathVariable("username") String username,@PathVariable("fname") String fName,@PathVariable("lname") String lName,@PathVariable("email") String email,@PathVariable("company") String company,@PathVariable("creatingUserId") Long creatingUserId,@PathVariable("active") Boolean active ){
        mobileUserDetailsServiceimpl.addNewMobileUserDetails(username,fName,lName,email,company,creatingUserId,active);
    }

    @PostMapping(value="mobileUserDetails/updateDetails/{id}/{username}/{fname}/{lname}/{email}/{company}/{active}")
    public void updateMobileUserDetails(@PathVariable("id") Long id, @PathVariable("username") String username,@PathVariable("fname") String fName,@PathVariable("lname") String lName,@PathVariable("email") String email,@PathVariable("company") String company,@PathVariable("active") Boolean active ){
        System.out.println("Updating mobile user details!");
        mobileUserDetailsServiceimpl.updateMobileUserDetails(id,username,fName,lName,email,company,active);
    }

    @GetMapping(value="mobileUserDetails/getUsersByCreatingUser/{userId}")
    public MobileUsersDetailsWraper getMobileUsersByUserId(@PathVariable("userId") Long userId){
        MobileUsersDetailsWraper mudw = new MobileUsersDetailsWraper();

        mudw.setMobileUserDetailsList(mobileUserDetailsServiceimpl.getMobileUserDetailsByUserId(userId));

        return mudw;
    }

    @GetMapping(value="/mobileUserDetails/getAll")
    public MobileUsersDetailsWraper getAllMobileusers(){
        MobileUsersDetailsWraper mudw = new MobileUsersDetailsWraper();
        mudw.setMobileUserDetailsList(mobileUserDetailsServiceimpl.getAllMobileUsers());

        return mudw;
    }

    @GetMapping(value="/faq/getAllFaq")
    public FaqWrapper getAllFaqs(){
        FaqWrapper faqw = new FaqWrapper();
        faqw.setFaqList(faqService.getAllFaqs());
        return faqw;
    }

    @GetMapping(value="/faq/getActiveFaq")
    public FaqWrapper getActiveFaqs(){
        FaqWrapper faqw = new FaqWrapper();
        faqw.setFaqList(faqService.getActiveFaq());
        return faqw;
    }

    @PostMapping(value="/faq/addNewFaq/{question}/{answer}/{category}/{active}")
    public void addNewFaq(@PathVariable("question") String question,@PathVariable("answer") String answer,@PathVariable("category") String category,@PathVariable("active") Boolean active){
        faqService.createNewFaq(question,answer,category,active);
    }
    @PostMapping(value="/faq/updateFaq/{id}/{question}/{answer}/{category}/{active}")
    public void updateFaq(@PathVariable("id") Long id, @PathVariable("question") String question,@PathVariable("answer") String answer,@PathVariable("category") String category,@PathVariable("active") Boolean active){
        faqService.updateFaq(id,question,answer,category,active);
    }

    @PostMapping(value="/faq/deleteFaq/{id}")
    public void deleteFaq(@PathVariable("id") Long id){
        faqService.deleteFaq(id);
    }

    @GetMapping(value="/faq/getAllActive")
    public FaqWrapper getActiveFAQs(){
        FaqWrapper faqWrapper = new FaqWrapper();
        faqWrapper.setFaqList(faqService.getActiveFaq());
        return faqWrapper;
    }

    @GetMapping(value="qrData/getCodesForMobileUser/{username}")
    public QRDataList  getMobileQrData(@PathVariable("username") String username){
        //We know the mobile user exists because we got this far, auth already confirmed that

        QRDataList qrDataList = new QRDataList();
        List<QRData> qrDataArrayList = new ArrayList<>();


        //Get QR code ids assigned to this user
        List<MobileUserQRData> mobileUserQRDataList = mobileUserQRDataService.getQRCoderForMobileUser(username);

        //if the user has assigned qr codes, retrieve them
        if(Objects.nonNull(mobileUserQRDataList)){
            for (MobileUserQRData muq : mobileUserQRDataList) {
                    QRData qrData = qrDataService.getQRDataById(muq.getQrDataId());
                    qrDataArrayList.add(qrData);
            }
        }
        qrDataList.setQrDataList(qrDataArrayList);
        return qrDataList;
    }


}

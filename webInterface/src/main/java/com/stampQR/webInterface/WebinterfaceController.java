package com.stampQR.webInterface;
//DB : 10.0.1.126:8081
//App: 10.0.1.20:8070
//Image: localhost:8000

import com.stampQR.webInterface.WrapperClasses.*;
import com.stampQR.webInterface.data.IndexContent;
import com.stampQR.webInterface.data.IndexQuickLaunchItem;
import com.stampQR.webInterface.helperClasses.QRAssignments;
import com.stampQR.webInterface.helperClasses.UserDetailsWeb;
import com.stampQR.webInterface.resources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
public class WebinterfaceController {

    @Autowired
    RestTemplate restTemplate;
    Long globalUserId = 0L; // used when creating the QR code
    String baseURL="";



    @GetMapping(value="/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping({"/home","/"})
    public String qrStampHome(Model model, Authentication auth){

        Boolean userLoggedIn = !Objects.isNull(auth);
        Boolean isAdmin = false;
        List<String> gl = new ArrayList<>();
        if(!Objects.isNull(auth)){
           auth.getAuthorities().forEach(a -> {gl.add(a.getAuthority());});
        }
        if(gl.size()>0){
            for (String s:gl) {
                if(s.equals("ADMINISTRATOR")){
                    isAdmin=true;
                }
            }
        }
        //System.out.println(isAdmin);


        String siteContentURL = "http://10.0.1.126:8081/webContent/getPageContent/"+"index";
        WebSiteContentList wscl = restTemplate.getForObject(siteContentURL, WebSiteContentList.class);

        model.addAttribute("isAdmin",isAdmin);
        model.addAttribute("userLoggedIn",userLoggedIn);
        model.addAttribute("HeaderTitle","One stamp for all your information");
        model.addAttribute("HeaderText","Think it, make it, leave your stamp on traceability.");
        List<IndexContent> indexContent = new ArrayList<>();
        List<IndexQuickLaunchItem> indexQuickLaunchItems = new ArrayList<>();
        indexContent.add(new IndexContent("Index Content 1","This is a sample content 1","Image lint for content 1"));
        indexContent.add(new IndexContent("Index Content 2","This is a sample content 2","Image lint for content 2"));
        indexContent.add(new IndexContent("Index Content 3","This is a sample content 3","Image lint for content 3"));

        indexQuickLaunchItems.add(new IndexQuickLaunchItem("About",
                "Check out all there is to know about us and our products",
                "bi bi-qr-code","about"));
        indexQuickLaunchItems.add(new IndexQuickLaunchItem("Contact",
                "If you have more questions, get in touch.",
                "bi bi-qr-code-scan","contact"));
        indexQuickLaunchItems.add(new IndexQuickLaunchItem("Login",
                "If you are an exisitng customer, follow this link to log in.",
                "bi bi-people","login"));
        indexQuickLaunchItems.add(new IndexQuickLaunchItem("FAQ",
                "For frequent questions about our product, visit our FAQ page.",
                "bi bi-person-circle","faq"));

        model.addAttribute("indexContent",indexContent);
        model.addAttribute("quickLaunchItems",indexQuickLaunchItems);

        return "index";
    }
    @RequestMapping("/admin")
    public String stampQrAdmin(Model model, Authentication auth){
        //Get username of logged in user
        String userName = auth.getName();
        Boolean userLoggedIn = !userName.isEmpty();

        restTemplate = new RestTemplate();

        //Get Website content
        WebSiteContentList wscl = restTemplate.getForObject("http://10.0.1.126:8081/webContent/getPageContent/admin", WebSiteContentList.class);

        //Get user details (web)
        String userDetailsWebListUri = "http://10.0.1.126:8081/userDetails/getAllUserDetails";
        UserDetailsWebList udwl = restTemplate.getForObject(userDetailsWebListUri, UserDetailsWebList.class);
        List<UserDetailsWeb> userDetailsWList = udwl.getUserDetailsWebList();
        //System.out.println(userDetailsWList.size());

        //Get loggedin user details
        String userDetailsUri = "http://10.0.1.126:8081//userDetails/getUserDetails/"+userName;
        UserDetails userDetails = restTemplate.getForObject(userDetailsUri, UserDetails.class);
        Long userId = userDetails.getUserId();
        globalUserId = userId;

        //Get mobile users
        String mobileUsersUri = "http://10.0.1.126:8081/mobileUserDetails/getAll";
        MobileUsersDetailsWraper mobileUserDetailsWraper = restTemplate.getForObject(mobileUsersUri, MobileUsersDetailsWraper.class);
        List<MobileUserDetails> mobileUserDetailsList = mobileUserDetailsWraper.getMobileUserDetailsList();

        //Get all status messages
        String webStatusURI = "http://10.0.1.126:8081/websiteStatus/getAll";
        WebStatusMessageList webStatusMessageList = restTemplate.getForObject(webStatusURI, WebStatusMessageList.class);
        List<WebStatusMessage> allWebStatusMessages = webStatusMessageList.getWebStatusMessages();
        //cerate a list of active messages to show when the page loads
        List<WebStatusMessage> activeWebStatusMessages = new ArrayList<>();
        for (WebStatusMessage wsm: allWebStatusMessages){
            if(wsm.getStatusActive()==Boolean.TRUE){
                activeWebStatusMessages.add(wsm);
            }
        }

        //Get all webcontent data
        String allWebContentURL = "http://10.0.1.126:8081/webContent/getAllWebsiteContent";
        WebSiteContentList wsclAll = restTemplate.getForObject(allWebContentURL, WebSiteContentList.class);

        //Get all cards
        String wsCardsAllUrl = "http://10.0.1.126:8081/websitecards/getAllCards";
        WebsiteCardsList wsCardsAll = restTemplate.getForObject(wsCardsAllUrl, WebsiteCardsList.class);

        //Get all faqs
        String allFaqUrl =  "http://10.0.1.126:8081/faq/getAllFaq";
        FaqWrapper faqw =  restTemplate.getForObject(allFaqUrl, FaqWrapper.class);


        model.addAttribute("userDetails",userDetailsWList);
        model.addAttribute("userLoggedIn",userLoggedIn);
        model.addAttribute("userId",userId);
        model.addAttribute("LoggedInUserDetails",userDetails);
        model.addAttribute("mobileUsersList",mobileUserDetailsList);
        model.addAttribute("allWebStatusMessages",allWebStatusMessages);
        model.addAttribute("activeWebStatusMessages",activeWebStatusMessages);
        model.addAttribute("allWebsiteContent",wsclAll.getWebSiteContentList());
        model.addAttribute("websiteCards",wsCardsAll.getWebsiteCardList());
        model.addAttribute("faqList",faqw.getFaqList());

        return "admin";
    }
    @RequestMapping("/contact")
    public String contactPage(Model model, Authentication auth){
        Boolean userLoggedIn = !Objects.isNull(auth);

        WebsiteCardsList wscl = restTemplate.getForObject("http://10.0.1.126:8081/websitecards/getactivecardsforpage/contact", WebsiteCardsList.class);
        model.addAttribute("contactCards",wscl.getWebsiteCardList());
        model.addAttribute("userLoggedIn",userLoggedIn);

        return"contact";
    }


    @RequestMapping("/about")
    public String aboutPage(Model model, Authentication auth){
        Boolean userLoggedIn = !Objects.isNull(auth);

        String siteContentURL = "http://10.0.1.126:8081//webContent/getPageContent/"+"about";
        WebSiteContentList wscl = restTemplate.getForObject(siteContentURL, WebSiteContentList.class);
        //System.out.println(wscl.getWebSiteContentList().size());
        WebSiteContent aboutTitle=null;
        WebSiteContent aboutSubTitle=null;
        WebSiteContent aboutIdeaTitle=null;
        WebSiteContent aboutIdeaBody=null;
        WebSiteContent aboutMissionTitle=null;
        WebSiteContent aboutMissionBody=null;
        WebSiteContent aboutSolutionTitle=null;
        WebSiteContent aboutSolutionBody=null;

        if(!wscl.getWebSiteContentList().equals(null)) {
            for (WebSiteContent content : wscl.getWebSiteContentList()) {
                switch (content.getSectionComponent()) {
                    case "pageTitle":
                        if (content.getSectionSubcomponent().equals("title")) {
                            aboutTitle = content;
                        } else if (content.getSectionSubcomponent().equals("subTitle")) {
                            aboutSubTitle = content;
                        }
                        break;
                    case "section1":
                        if (content.getSectionSubcomponent().equals("title")) {
                            aboutIdeaTitle = content;
                        } else if (content.getSectionSubcomponent().equals("content")) {
                            aboutIdeaBody = content;
                        }
                        break;
                    case "section2":
                        if (content.getSectionSubcomponent().equals("title")) {
                            aboutMissionTitle = content;
                        } else if (content.getSectionSubcomponent().equals("content")) {
                            aboutMissionBody = content;
                        }
                        break;
                    case "section3":
                        if (content.getSectionSubcomponent().equals("title")) {
                            aboutSolutionTitle = content;
                        } else if (content.getSectionSubcomponent().equals("content")) {
                            aboutSolutionBody = content;
                        }
                        break;

                }
            }
        }
        model.addAttribute("pageTitle",aboutTitle.getContentText());
        model.addAttribute("pageSubTitle",aboutSubTitle.getContentText());
        model.addAttribute("ideaTitle",aboutIdeaTitle.getContentText());
        model.addAttribute("ideaContent",aboutIdeaBody.getContentText());
        model.addAttribute("missionTitle",aboutMissionTitle.getContentText());
        model.addAttribute("missionContent",aboutMissionBody.getContentText());
        model.addAttribute("solutionTitle",aboutSolutionTitle.getContentText());
        model.addAttribute("solutionContent",aboutSolutionBody.getContentText());
        model.addAttribute("userLoggedIn",userLoggedIn);

        return "about";
    }

    @RequestMapping("/faq")
    public String faqPage(Model model, Authentication auth){
        Boolean userLoggedIn = !Objects.isNull(auth);

        //Get all active faqs
        String allActiveFaqUrl =  "http://10.0.1.126:8081/faq/getAllActive";
        FaqWrapper activeFaqs =  restTemplate.getForObject(allActiveFaqUrl, FaqWrapper.class);

        model.addAttribute("faqList",activeFaqs.getFaqList());
        model.addAttribute("userLoggedIn",userLoggedIn);

        return "faq";
    }


    @RequestMapping("/login")
    public String loginPage(Model model){

        return "login";
    }

    @RequestMapping("/login_user")
    public String loginUserPage(Model model, Authentication auth){

        //Get username of logged in user
        String userName = auth.getName();
        Boolean userLoggedIn = !Objects.isNull(auth);

        Boolean isAdmin = false;
        List<String> gl = new ArrayList<>();
        if(!Objects.isNull(auth)){
            auth.getAuthorities().forEach(a -> {gl.add(a.getAuthority());});
        }
        if(gl.size()>0){
            for (String s:gl) {
                if(s.equals("ADMINISTRATOR")){
                    isAdmin=true;
                }
            }
        }
        //System.out.println(isAdmin);

        //Get the users details
        String userDetailsUri = "http://10.0.1.126:8081//userDetails/getUserDetails/"+userName;
        UserDetails userDetails = restTemplate.getForObject(userDetailsUri, UserDetails.class);
        Long userId = userDetails.getUserId();
        globalUserId = userId;

        //Get status messages
        String webStatusURI = "http://10.0.1.126:8081/websiteStatus/getActiveStatusMessages";
        WebStatusMessageList webStatusMessageList = restTemplate.getForObject(webStatusURI, WebStatusMessageList.class);
        List<WebStatusMessage> webStatusMessages = webStatusMessageList.getWebStatusMessages();


        //Get the last 3 QR codes created
        String qruri = "http://10.0.1.126:8081/qrData/getAllDataByUserId/"+userId;
        //Get QR codes relating to user
        QRDataList qrDataList = restTemplate.getForObject(qruri, QRDataList.class);
        Collections.reverse(qrDataList.getQrDataList());

        String lastThreeQrUri = "http://10.0.1.126:8081/qrData/getLastThreeDataByUserId/"+userId;
        QRDataList lastThreeQRDatalist = restTemplate.getForObject(lastThreeQrUri, QRDataList.class);
        List<QRData> lastThreeCreated =  lastThreeQRDatalist.getQrDataList();


        //Get mobile users relating to user
        String mobileUsersUri = "http://10.0.1.126:8081/mobileUserDetails/getUsersByCreatingUser/"+userId;
        MobileUsersDetailsWraper mobileUserDetailsWraper = restTemplate.getForObject(mobileUsersUri, MobileUsersDetailsWraper.class);
        List<MobileUserDetails> mobileUserDetailsList = mobileUserDetailsWraper.getMobileUserDetailsList();

        //Get QR Code assignments

        for (QRData qrData:qrDataList.getQrDataList()) {
            String qrDataAssignmentListUri = "http://10.0.1.126:8081/qrData/getAssignedUsers/"+qrData.getQrDataId();
            QRDataAssignmentList qrDataAssignmentList = restTemplate.getForObject(qrDataAssignmentListUri, QRDataAssignmentList.class);
            if(qrDataAssignmentList.getMobileUserQRDataList()!=null){
                for (MobileUserQRData muqd: qrDataAssignmentList.getMobileUserQRDataList()) {
                    qrData.getAssignedUsers().add(muqd.getUserName());
                }
            }
        }
        // set assigned users for the latest created qr data as well
        for (QRData ltqrData:lastThreeCreated) {
            for (QRData qrData:qrDataList.getQrDataList()){
                if(qrData.getQrDataId().equals(ltqrData.getQrDataId())){
                    ltqrData.setAssignedUsers(qrData.getAssignedUsers());
                }
            }
        }

        model.addAttribute("isAdmin",isAdmin);
        model.addAttribute("userLoggedIn",userLoggedIn);
        model.addAttribute("latestQRCodesList",lastThreeCreated);
        model.addAttribute("qrDataList",qrDataList.getQrDataList());
        model.addAttribute("qrCodesList",qrDataList.getQrDataList());
        model.addAttribute("mobileUsersList",mobileUserDetailsList);
        model.addAttribute("userId",userId);
        model.addAttribute("userDetails",userDetails);
        model.addAttribute("webStatusMessages",webStatusMessages);

        return "login_user";
    }
    @ResponseBody()
    @GetMapping(value = "/createQRCode/{plainText}/{tag}/{errorCorrectionLevel}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createQRCode(@PathVariable("plainText") String plainText, @PathVariable("tag")  String tag, @PathVariable("errorCorrectionLevel")  String eccLevel, Authentication auth){


        String uri = "http://10.0.1.20:8070/qrdata/produceQrCode/"+plainText+"/"+eccLevel+"/"+tag+"/"+globalUserId;
            try {
                restTemplate.getForObject(uri, String.class);
                return new ResponseEntity(HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    /*********************************************************************************************************************************************************
     **************************************************************     POST     *****************************************************************************
    **********************************************************************************************************************************************************/

    private ResponseEntity doUriPost(String uri) {
        try {
            //System.out.println("URI: " + uri);
            restTemplate.postForEntity(uri, null, Object.class);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value="/user/createNewuser/{userName}/{password}")
    public ResponseEntity createNewUser(@PathVariable("userName") String userName,@PathVariable("password") String password){

        return doUriPost("http://10.0.1.126:8081/user/createNewuser/" + userName + "/" + password);
    }

    @PostMapping(value="userDetails/createNewUser/{userName}/{fname}/{lname}/{email}/{company}")
    public ResponseEntity createUserDetails(@PathVariable("userName") String username,@PathVariable("fname") String fName,@PathVariable("lname") String lName,@PathVariable("email") String email,@PathVariable("company") String company){

        return doUriPost("http://10.0.1.126:8081/userDetails/createNewUser/" + username + "/" + fName+"/"+lName+"/"+email+"/"+company);

    }

    @PostMapping(value="/user/updateStatus/{username}/{currentStatus}")
    public ResponseEntity changeUserEnabledStatus(@PathVariable("username") String userName,@PathVariable("currentStatus") Boolean currentStatus){
        return doUriPost("http://10.0.1.126:8081/user/updateStatus/" + userName + "/" + currentStatus);
    }

    @PostMapping(value="/user/createAdminUser/{userName}/{isCurrentlyAdmin}")
    public ResponseEntity createAdminUser(@PathVariable("userName") String userName,@PathVariable("isCurrentlyAdmin") Boolean isCurrentlyAdmin){
        return doUriPost("http://10.0.1.126:8081/user/createAdminUser/" + userName + "/" + isCurrentlyAdmin);
    }

    @PostMapping(value="userDetails/update/{id}/{fname}/{lname}/{email}/{company}")
    public ResponseEntity updateUserDetails(@PathVariable("id") Long id,@PathVariable("fname") String fName,@PathVariable("lname") String lName,@PathVariable("email") String email,@PathVariable("company") String company){

        return doUriPost("http://10.0.1.126:8081/userDetails/update/" + id + "/" + fName+"/"+lName+"/"+email+"/"+company);

    }

    @PostMapping(value = "mobileUsers/addNewUser/{username}/{password}")
    public ResponseEntity addUNewMobileUser(@PathVariable("username") String username,@PathVariable("password") String password){
        return doUriPost("http://10.0.1.126:8081/mobileUsers/addNewUser/" + username + "/" + password);

    }

    @PostMapping(value="mobileUserDetails/addNewDetails/{username}/{fname}/{lname}/{email}/{company}/{creatingUserId}/{active}")
    public ResponseEntity addNewMobileUserDetails(@PathVariable("username") String username,@PathVariable("fname") String fName,@PathVariable("lname") String lName,@PathVariable("email") String email,@PathVariable("company") String company,@PathVariable("creatingUserId") Long creatingUserId,@PathVariable("active") Boolean active ){
        return doUriPost("http://10.0.1.126:8081/mobileUserDetails/addNewDetails/" + username + "/" + fName+"/"+lName+"/"+email+"/"+company+"/"+creatingUserId+"/"+active);
    }

    @PostMapping(value="mobileUserDetails/updateDetails/{id}/{username}/{fname}/{lname}/{email}/{company}/{active}")
    public ResponseEntity updateMobileUserDetails(@PathVariable("id") Long id, @PathVariable("username") String username,@PathVariable("fname") String fName,@PathVariable("lname") String lName,@PathVariable("email") String email,@PathVariable("company") String company,@PathVariable("active") Boolean active ){
        return doUriPost("http://10.0.1.126:8081/mobileUserDetails/updateDetails/"+id+"/" + username + "/" + fName+"/"+lName+"/"+email+"/"+company+"/"+active);
    }

    @PostMapping(value="/websiteStatus/createNewStatusMessage/{title}/{message}/{active}")
    public ResponseEntity createNewStatusMessage(@PathVariable("title") String title, @PathVariable("message") String message, @PathVariable("active") Boolean active){
        return doUriPost("http://10.0.1.126:8081/websiteStatus/createNewStatusMessage/" + title + "/" + message+"/"+active);
    }

    @PostMapping(value="/websiteStatus/updateWebStatusMessageStatus/{id}/{title}/{message}/{active}")
    public ResponseEntity updateStatus (@PathVariable("id") Long id,@PathVariable("title") String title,@PathVariable("message") String message,@PathVariable("active") Boolean active){
        return doUriPost("http://10.0.1.126:8081/websiteStatus/updateWebStatusMessageStatus/"+id+"/" + title + "/" + message+"/"+active);
    }

    @PostMapping(value="/websiteStatus/deleteStatusMessage/{id}")
    public ResponseEntity deleteStatus(@PathVariable("id") Long id){
        return doUriPost("http://10.0.1.126:8081/websiteStatus/deleteStatusMessage/"+id);

    }

    @PostMapping(value="webContent/addWebContent/{pageName}/{sectionName}/{sectionComponent}/{sectionSubcomponent}/{contentText}/{imageUrl}/{active}")
    public ResponseEntity addWebSiteContent(@PathVariable("pageName") String pageName,@PathVariable("sectionName") String sectionName,@PathVariable("sectionComponent") String sectionComponent,
                                  @PathVariable("sectionSubcomponent") String sectionSubcomponent,@PathVariable("contentText") String contentText,@PathVariable("imageUrl") String imageUrl,
                                  @PathVariable("active") Boolean active){

        return doUriPost("http://10.0.1.126:8081/webContent/addWebContent/"+pageName+"/"+sectionName+"/"+sectionComponent+"/"+sectionSubcomponent+"/"+contentText+"/"+imageUrl+"/"+active);

    }

    @PostMapping(value="/webContent/deleteWebsiteContent/{id}")
    public ResponseEntity deleteWebsiteContent(@PathVariable("id") Long id){

        return doUriPost("http://10.0.1.126:8081/webContent/deleteWebsiteContent/"+id);

    }

    @PostMapping(value="webContent/updateWebContent/{id}/{pageName}/{sectionName}/{sectionComponent}/{sectionSubcomponent}/{contentText}/{imageUrl}/{active}")
    public ResponseEntity updateWebSiteContent(@PathVariable("id") Long id, @PathVariable("pageName") String pageName,@PathVariable("sectionName") String sectionName,@PathVariable("sectionComponent") String sectionComponent,
                                     @PathVariable("sectionSubcomponent") String sectionSubcomponent,@PathVariable("contentText") String contentText,@PathVariable("imageUrl") String imageUrl,
                                     @PathVariable("active") Boolean active){

        return doUriPost("http://10.0.1.126:8081/webContent/updateWebContent/"+id+"/"+pageName+"/"+sectionName+"/"+sectionComponent+"/"+sectionSubcomponent+"/"+contentText+"/"+imageUrl+"/"+active);

    }

    @PostMapping(value="websitecards/addNewCard/{pageName}/{sectionName}/{image}/{title}/{text}/{active}")
    public ResponseEntity addNewCard(@PathVariable("pageName")String pageName,@PathVariable("sectionName") String sectionName,@PathVariable("image")  String image,@PathVariable("title")  String title,@PathVariable("text") String text,@PathVariable("active") Boolean active){
        return doUriPost("http://10.0.1.126:8081/websitecards/addNewCard/"+pageName+"/"+sectionName+"/"+image+"/"+title+"/"+text+"/"+active);
    }

    @PostMapping(value="/websitecards/deleteCard/{id}")
    public ResponseEntity deleteCard(@PathVariable("id") Long id){

        return doUriPost("http://10.0.1.126:8081/websitecards/deleteCard/"+id);

    }

    @PostMapping(value="/websitecards/updateCard/{id}/{pageName}/{sectionName}/{image}/{title}/{text}/{active}")
    public ResponseEntity updateCard(@PathVariable("id") Long id, @PathVariable("pageName")String pageName,@PathVariable("sectionName") String sectionName,@PathVariable("image")  String image,@PathVariable("title")  String title,@PathVariable("text") String text,@PathVariable("active") Boolean active){
        return doUriPost("http://10.0.1.126:8081/websitecards/updateCard/"+id+"/"+pageName+"/"+sectionName+"/"+image+"/"+title+"/"+text+"/"+active);
    }

    @PostMapping(value="/faq/addNewFaq/{question}/{answer}/{category}/{active}")
    public ResponseEntity addNewFaq(@PathVariable("question") String question,@PathVariable("answer") String answer,@PathVariable("category") String category,@PathVariable("active") Boolean active){
        return doUriPost("http://10.0.1.126:8081/faq/addNewFaq/"+question+"/"+answer+"/"+category+"/"+active);
    }

    @PostMapping(value="/faq/deleteFaq/{id}")
    public ResponseEntity deleteFaq(@PathVariable("id") Long id){

        return doUriPost("http://10.0.1.126:8081/faq/deleteFaq/"+id);

    }

    @PostMapping(value="/faq/updateFaq/{id}/{question}/{answer}/{category}/{active}")
    public ResponseEntity updateFaq(@PathVariable("id") Long id, @PathVariable("question") String question,@PathVariable("answer") String answer,@PathVariable("category") String category,@PathVariable("active") Boolean active){
        return doUriPost("http://10.0.1.126:8081/faq/updateFaq/"+id+"/"+question+"/"+answer+"/"+category+"/"+active);
    }

    @PostMapping(value="/assingQrToMobileUser/{mobileUserUserName}/{qrDataId}")
    public ResponseEntity saveMobileUserQrDataAssignment(@PathVariable("mobileUserUserName") String mobileUserUserName,@PathVariable("qrDataId") Long qrDataId){
        return doUriPost("http://10.0.1.126:8081/assingQrToMobileUser/"+mobileUserUserName+"/"+qrDataId);
    }

    @PostMapping(value="/deleteQrToMobileUser/{mobileUserUserName}/{qrDataId}")
    public ResponseEntity deleteMobileUserQrDataAssignment(@PathVariable("mobileUserUserName") String mobileUserUserName,@PathVariable("qrDataId") Long qrDataId){
        return doUriPost("http://10.0.1.126:8081/deleteQrToMobileUser/"+mobileUserUserName+"/"+qrDataId);
    }

    @PostMapping(value="/qrData/deleteQRCode/{id}")
    public ResponseEntity deleteQrData(@PathVariable("id") Long id){

        return doUriPost("http://10.0.1.126:8081/qrData/deleteQRCode/"+id);
    }

    @PostMapping(value="/qrData/updateActiveStatus/{id}/{active}")
    public ResponseEntity updateQrDataActiveStatus(@PathVariable("id") Long id,@PathVariable("active") Boolean active){
        return doUriPost("http://10.0.1.126:8081/qrData/updateActiveStatus/"+id+"/"+active);
    }

}

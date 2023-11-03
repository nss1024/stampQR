package com.stampQR.webInterface;

import com.stampQR.webInterface.WrapperClasses.*;
import com.stampQR.webInterface.data.IndexContent;
import com.stampQR.webInterface.data.IndexQuickLaunchItem;
import com.stampQR.webInterface.helperClasses.QRAssignments;
import com.stampQR.webInterface.helperClasses.UserDetailsWeb;
import com.stampQR.webInterface.resources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    //****************Mock user details*********************
    Long userId = 1L;
    String userName = "simon1";

    //******************************************************

    @RequestMapping("/home")
    public String qrStampHome(Model model){

        String siteContentURL = "http://localhost:8081//webContent/getPageContent/"+"index";
        WebSiteContentList wscl = restTemplate.getForObject("http://localhost:8081//webContent/getPageContent/", WebSiteContentList.class);



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
    public String stampQrAdmin(Model model){

        restTemplate = new RestTemplate();

        //Get Website content
        WebSiteContentList wscl = restTemplate.getForObject("http://localhost:8081/webContent/getPageContent/admin", WebSiteContentList.class);

        //Get user details (web)
        String userDetailsWebListUri = "http://localhost:8081/userDetails/getAllUserDetails";
        UserDetailsWebList udwl = restTemplate.getForObject(userDetailsWebListUri, UserDetailsWebList.class);
        List<UserDetailsWeb> userDetailsWList = udwl.getUserDetailsWebList();
        System.out.println(userDetailsWList.size());

        //Get loggedin user details
        String userDetailsUri = "http://localhost:8081//userDetails/getUserDetails/"+userName;
        UserDetails userDetails = restTemplate.getForObject(userDetailsUri, UserDetails.class);

        //Get mobile users
        String mobileUsersUri = "http://localhost:8081/mobileUserDetails/getAll";
        MobileUsersDetailsWraper mobileUserDetailsWraper = restTemplate.getForObject(mobileUsersUri, MobileUsersDetailsWraper.class);
        List<MobileUserDetails> mobileUserDetailsList = mobileUserDetailsWraper.getMobileUserDetailsList();

        //Get all status messages
        String webStatusURI = "http://localhost:8081/websiteStatus/getAll";
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
        String allWebContentURL = "http://localhost:8081/webContent/getAllWebsiteContent";
        WebSiteContentList wsclAll = restTemplate.getForObject(allWebContentURL, WebSiteContentList.class);

        //Get all cards
        String wsCardsAllUrl = "http://localhost:8081/websitecards/getAllCards";
        WebsiteCardsList wsCardsAll = restTemplate.getForObject(wsCardsAllUrl, WebsiteCardsList.class);

        //Get all faqs
        String allFaqUrl =  "http://localhost:8081/faq/getAllFaq";
        FaqWrapper faqw =  restTemplate.getForObject(allFaqUrl, FaqWrapper.class);


        model.addAttribute("userDetails",userDetailsWList);
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
    public String contactPage(Model model){

        WebsiteCardsList wscl = restTemplate.getForObject("http://localhost:8081/websitecards/getactivecardsforpage/contact", WebsiteCardsList.class);
        model.addAttribute("contactCards",wscl.getWebsiteCardList());

        return"contact";
    }


    @RequestMapping("/about")
    public String aboutPage(Model model){

        String siteContentURL = "http://localhost:8081//webContent/getPageContent/"+"about";
        WebSiteContentList wscl = restTemplate.getForObject(siteContentURL, WebSiteContentList.class);
        System.out.println(wscl.getWebSiteContentList().size());
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

        return "about";
    }

    @RequestMapping("/faq")
    public String faqPage(Model model){

        //Get all active faqs
        String allActiveFaqUrl =  "http://localhost:8081/faq/getAllActive";
        FaqWrapper activeFaqs =  restTemplate.getForObject(allActiveFaqUrl, FaqWrapper.class);



        model.addAttribute("faqList",activeFaqs.getFaqList());

        return "faq";
    }


    @RequestMapping("/login")
    public String loginPage(Model model){

        return "login";
    }

    @RequestMapping("/login_user")
    public String loginUserPage(Model model){

        //Get the users details
        String userDetailsUri = "http://localhost:8081//userDetails/getUserDetails/"+userName;
        UserDetails userDetails = restTemplate.getForObject(userDetailsUri, UserDetails.class);

        //Get status messages
        String webStatusURI = "http://localhost:8081/websiteStatus/getActiveStatusMessages";
        WebStatusMessageList webStatusMessageList = restTemplate.getForObject(webStatusURI, WebStatusMessageList.class);
        List<WebStatusMessage> webStatusMessages = webStatusMessageList.getWebStatusMessages();


        //Get the last 3 QR codes created
        String qruri = "http://localhost:8081/qrData/getAllDataByUserId/"+userId;
        //Get QR codes relating to user
        QRDataList qrDataList = restTemplate.getForObject(qruri, QRDataList.class);
        Collections.reverse(qrDataList.getQrDataList());

        String lastThreeQrUri = "http://localhost:8081/qrData/getLastThreeDataByUserId/"+userId;
        QRDataList lastThreeQRDatalist = restTemplate.getForObject(lastThreeQrUri, QRDataList.class);
        List<QRData> lastThreeCreated =  lastThreeQRDatalist.getQrDataList();


        //Get mobile users relating to user
        String mobileUsersUri = "http://localhost:8081/mobileUserDetails/getUsersByCreatingUser/"+userId;
        MobileUsersDetailsWraper mobileUserDetailsWraper = restTemplate.getForObject(mobileUsersUri, MobileUsersDetailsWraper.class);
        List<MobileUserDetails> mobileUserDetailsList = mobileUserDetailsWraper.getMobileUserDetailsList();

        //Get QR Code assignments

        for (QRData qrData:qrDataList.getQrDataList()) {
            String qrDataAssignmentListUri = "http://localhost:8081/qrData/getAssignedUsers/"+qrData.getQrDataId();
            QRDataAssignmentList qrDataAssignmentList = restTemplate.getForObject(qrDataAssignmentListUri, QRDataAssignmentList.class);
            if(qrDataAssignmentList.getMobileUserQRDataList()!=null){
                for (MobileUserQRData muqd: qrDataAssignmentList.getMobileUserQRDataList()) {
                    qrData.getAssignedUsers().add(muqd.getUserName());
                }
            }
        }

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
    public void createQRCode(@PathVariable("plainText") String plainText,@PathVariable("tag")  String tag,@PathVariable("errorCorrectionLevel")  String eccLevel){


        String uri = "http://localhost:8070/qrdata/"+plainText+"/"+eccLevel+"/"+tag+"/"+userId;


            restTemplate.getForObject(uri, String.class);

    }


}

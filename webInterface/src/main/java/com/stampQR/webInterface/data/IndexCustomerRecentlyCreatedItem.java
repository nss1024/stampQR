package com.stampQR.webInterface.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class IndexCustomerRecentlyCreatedItem {

    private String titleText;
    private String descriptionText;
    private String RecentlyCreatedItemImageLink;
    private List<String> assignedUsers ;

}

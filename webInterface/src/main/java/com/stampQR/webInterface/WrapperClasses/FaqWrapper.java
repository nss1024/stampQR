package com.stampQR.webInterface.WrapperClasses;



import com.stampQR.webInterface.resources.FAQ;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FaqWrapper {

    private List<FAQ> faqList;

}

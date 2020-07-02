package com.topics.knowledgeBase.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomExceptionBean {

    private String timestamp;
    private String message;
    private String errorDetails;

}

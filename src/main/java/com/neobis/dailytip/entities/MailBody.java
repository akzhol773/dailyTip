package com.neobis.dailytip.entities;

import lombok.Data;

@Data
public class MailBody {
    private String subject;
    private String quote;
    private String author;


    public MailBody() {
        this.subject = "Daily quote for you";
    }
}

package com.neobis.dailytip.service;

import com.neobis.dailytip.entities.MailBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service

public class TipService {

    private JavaMailSender mailSender;

    public TipService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String fromMail;
    public void sendMail(String mail, MailBody mailStructure){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(mail);


        mailSender.send(simpleMailMessage);


    }

    public MailBody getQuote(){
        MailBody mailBody = new MailBody();
        String url = "https://zenquotes.io/api/random/671c06f7f34cb7f6357ff41b960b38e0";
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();
                String json = response.toString();

                json = json.substring(1, json.length() - 1);
                JSONArray jsonArray = new JSONArray(json);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String quote = jsonObject.getString("q");
                String author = jsonObject.getString("a");
                mailBody.setQuote(quote);
                mailBody.setAuthor(author);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mailBody;
    }


}



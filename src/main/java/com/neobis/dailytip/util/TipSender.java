package com.neobis.dailytip.util;

import com.neobis.dailytip.entities.MailBody;
import com.neobis.dailytip.entities.Users;
import com.neobis.dailytip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor

public class TipSender {

    private final JavaMailSender mailSender;
    private final UserService userService;
    String key = System.getenv("QUOTE_KEY");



    @Value("${spring.mail.username}")
    private String fromMail;


    @Scheduled(cron = "0 * * * * *")
    public void sendEmail(){
        sendDailyTip();
    }


    public void sendDailyTip() {
        MailBody mailBody = getQuote();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailBody.getSubject());
        simpleMailMessage.setText(mailBody.getQuote());


        List<Users> subscribers = userService.getAllUsers();
        for (Users subscriber : subscribers) {
            simpleMailMessage.setTo(subscriber.getEmail());
            simpleMailMessage.setSubject(mailBody.getSubject());
            simpleMailMessage.setText(mailBody.getQuote());
            mailSender.send(simpleMailMessage);
        }
    }

    public MailBody getQuote(){
        MailBody mailBody = new MailBody();
        String url = "https://zenquotes.io/api/random/"+key;
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


                JSONArray jsonArray = new JSONArray(json);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String quote = jsonObject.getString("q");
                String author = jsonObject.getString("a");
                String urlToUnsubscribe = "https://neobis-dailytip.up.railway.app/deletePage";


                String text = String.format("Here is a daily inspirational quote for you: \n \n%s \n \nAuthor: %s \n \nTo unsubscribe click here: %s", quote, author, urlToUnsubscribe);
                mailBody.setQuote(text);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mailBody;
    }

    public void sendFirstTip(String email, String name) {
        MailBody mailBody = getQuote();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailBody.getSubject());
        simpleMailMessage.setText(String.format("Dear %s, you are very welcome. \n\n%s", name,  mailBody.getQuote()));
        simpleMailMessage.setTo(email);
        mailSender.send(simpleMailMessage);
    }

}



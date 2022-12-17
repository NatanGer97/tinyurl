package com.backend.tinyurl.Services;

import com.mailgun.api.v3.*;
import com.mailgun.client.*;
import com.mailgun.model.message.*;
import com.mailjet.client.*;
import com.mailjet.client.errors.*;
import com.mailjet.client.resource.*;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.*;
import org.json.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.web.client.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

@Service
public class EmailService {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Value("${email.api.key}")
    private String API_KEY;


    @Value("${email.secret.key}")
    private String SECRET_KEY;

    public void sendRegistrationEmail(String email, String token) {

    }

    public void sendSimpleMessage(String to, String text)   {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient(API_KEY,SECRET_KEY, new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "projects.natan@gmail.com")
                                        .put("Name", "Admin"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", to)
//                                                .put("Email", "projects.natan@gmail.com")
                                                .put("Name", "Natan")))
                                .put(Emailv31.Message.SUBJECT, "Registration.")
                                .put(Emailv31.Message.TEXTPART, "My first Mailjet email")
                                .put(Emailv31.Message.HTMLPART,
                                      String.format("<h3>Code: %s </h3><br />", text))
                                .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
        try {
            response = client.post(request);
            logger.info("Email sent to: " + to);
            logger.info("Status: " + response.getStatus());
        } catch (MailjetException e) {
            e.printStackTrace();
        } catch (MailjetSocketTimeoutException e) {
            e.printStackTrace();
        }

    }




}

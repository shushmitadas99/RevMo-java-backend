package com.revature.utility;
import com.sendgrid.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
//import java.sql.Connection;

public class EmailUtility {

    // Test Sending Email
//    public static void main(String[] args) throws Exception {
//       // Connection conn = ConnectionUtility.createConnection();
//       email("mazizi.c@gmail.com",
//               "Reset your RevMo password",
//               "<strong>We will be sending an html forget password template</strong>");
//    }

    public static int email(String email_to, String sub, String email_content) throws IOException {
        Dotenv dotenv = Dotenv.load();
        int stats =400;
        Email from = new Email("revmobank@gmail.com");
        String subject = sub;
        Email to = new Email(email_to);
        Content content = new Content("text/html", email_content);

        Mail mail = new Mail(from, subject, to, content);;
        SendGrid sg = new SendGrid(dotenv.get("SENDGRID_API_KEY"));

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            stats = response.getStatusCode();
        } catch (IOException ex) {
            System.out.println("Exception Occured While Sending Email! \n\n" + ex);
//            throw ex;
        }
        return stats;
    }
}

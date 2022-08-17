package com.revature.utility;
import com.sendgrid.*;
import java.io.IOException;
import java.sql.Connection;

public class EmailUtility {

    public static void main(String[] args) throws Exception {
        Connection conn = ConnectionUtility.createConnection();
       // email("mazizi.c@gmail.com", "Reset Password Link", "We will be sending an html forget password template");
    }
    public static void email(String email_to, String sub, String email_content) throws IOException {

        Email from = new Email("revmobank@gmail.com");
        String subject = sub;
        Email to = new Email(email_to);
        Content content = new Content("text/plain", email_content);

        Mail mail = new Mail(from, subject, to, content);;
        SendGrid sg = new SendGrid("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");

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println("Exception Occured While Sending Email! \n\n" + ex);
//            throw ex;
        }
    }
}

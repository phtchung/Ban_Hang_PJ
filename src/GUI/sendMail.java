
package GUI;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author MyPC
 */
/*
public class sendMail {
//    public void sendmail(String mailNguoiNhan) {
//        try {
//            Email email = new SimpleEmail();
//
//            // Cấu hình thông tin Email Server
//            email.setHostName("smtp.googlemail.com");
//            email.setSmtpPort(465);
//            email.setAuthenticator(new DefaultAuthenticator("floratiennuthiennhien0990@gmail.com", "sucmanhEnchantix")); //Nhớ nhập đúng với tài khoản thật nhé :))
//
//            // Với gmail cái này là bắt buộc.
//            email.setSSLOnConnect(true);
//
//            // Người gửi
//            email.setFrom("floratiennuthiennhien0990@gmail.com", "Công chúa phép thuật");
//
//            // Tiêu đề
//            email.setSubject("Ta sẽ trùng trị ngươi"); //Tiêu đề khi gửi email
//            Object rand = null;
//
//            // Nội dung email
//            String covert = String.valueOf(rand);
//            email.setMsg("Hãy xem đây, biến hình Winx\n EN CHAN TÍT\n"); //Nội dung email bạn muốn gửi.
//            // Người nhận
//            email.addTo(mailNguoiNhan); //Đia chỉ email người nhận
//            email.send(); //Thực hiện gửi.
//            System.err.println("Gửi email thành công ! Vui lòng kiểm tra email !");
//            System.out.println("\n");
//        } catch (Exception e) {
//            System.out.println("Gửi không thành công !");
//        }
//    }
    public void sendmail(String mail) {
        try {
            Email email = new SimpleEmail();

            // Cấu hình thông tin Email Server
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("duchuy01102001@gmail.com", "huy01102001")); //Nhớ nhập đúng với tài khoản thật nhé :))

            // Với gmail cái này là bắt buộc.
            email.setSSLOnConnect(true);

            // Người gửi
            email.setFrom("duchuy01102001@gmail.com", "Công chúa phép thuật");

            // Tiêu đề
            email.setSubject("Ta sẽ trùng trị ngươi"); //Tiêu đề khi gửi email

            // Nội dung email
            //String covert = String.valueOf(rand);
            email.setMsg("Hãy xem đây, biến hình Winx\\n EN CHAN TÍT\\n"); //Nội dung email bạn muốn gửi.
            // Người nhận
            email.addTo(mail); //Đia chỉ email người nhận
            email.send(); //Thực hiện gửi.
            System.err.println("Gửi email thành công ! Vui lòng kiểm tra email !");
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println("Gửi không thành công !");
        }
    }
    
    public static void main(String[] args) {
        sendMail sm=new sendMail();
       // sm.sendmail();
       sm.sendmail("floratiennuthiennhien0990@gmail.com");
        
    }
}
 */
public class sendMail {

    public static void send(String to, String sub, String msg, final String user, final String pass) {
        //create an instance of Properties Class  
        try{
        Properties props = new Properties();
        } catch(Exception e)
        {System.out.println("Loi 0: "+ e.toString());}

        /* Specifies the IP address of your default mail server
     	   for e.g if you are using gmail server as an email sever
           you will pass smtp.gmail.com as value of mail.smtp host. 
           As shown here in the code. 
           Change accordingly, if your email id is not a gmail id
         */
        try{
            Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        //below mentioned mail.smtp.port is optional
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        } catch(Exception e){
            System.out.println("Loi 1: "+ e.toString());
        }

        /* Pass Properties object(props) and Authenticator object   
           for authentication to Session instance 
         */
        try{
            Properties props = new Properties();
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        } catch (Exception e)
        {
            System.out.println("Loi 2: "+ e.toString());
        }

        try {
            Session session = null;

            /* Create an instance of MimeMessage, 
 	      it accept MIME types and headers 
             */
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setContent(msg, "text/html");

            /* Transport class is used to deliver the message to the recipients */
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String subject = "Your order has been processing.";
        String message = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "\n"
                + "<head>\n"
                + "</head>\n"
                + "\n"
                + "<body>\n"
                + "    <h3 style=\"color: blue;\">Your order has been processing.</h3>\n"
                + "    <div>Full Name :Le Hong Quan</div>\n"
                + "    <div>Phone : 0866823499</div>\n"
                + "    <div>address : Vinh Hung, Vinh Loc, Thanh Hoa</div>\n"
                + "    <h3 style=\"color: blue;\">Thank you very much!</h3>\n"
                + "\n"
                + "</body>\n"
                + "\n"
                + "</html>";
        try {
            sendMail.send("duchuy01102001gmail.com", subject, message, "duchuy01102001gmail.com", "zjctlsbkgtdevjkq");
        } catch (Exception e) {
            System.out.println("Loi: " + e.toString());
        }
    }
}

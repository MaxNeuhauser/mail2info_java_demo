import javax.mail.*;
import javax.mail.internet.*;

/**
 * Demo application to send mails to the Cordaware bestinformed Mail2Info API.
 * See this help page for details: https://www.cordaware.com/help_english/mailtoinfo.htm
 *
 * Requires the com.sun.mail package (Java 7+):
 * <dependency>
 *     <groupId>com.sun.mail</groupId>
 *     <artifactId>javax.mail</artifactId>
 *     <version>1.6.2</version>
 * </dependency>
 */
public class MailTest {
    static String username = "admin";
    static String password = "bestinformed";
    static String info = "Hallo Welt - Testnachricht von Mail2Info";
    static String host = "192.168.11.11";
    static String port = "8025";

    public static void main(String[] args) {
        log("Starte Mail2Info Programm...");
        sendMail("newinfo", "info=" + info + "\n");
    }

    /**
     * Creates a new info, edits one, cancels one, etc...
     * @param action newinfo, etc.
     * @param txt The Mail2Info syntax. Authentication data is included automatically.
     * @return true if successful. Otherwise false.
     */
    public static boolean sendMail(String action, String txt) {
        // Set up the SMTP server.
        log("Erstelle Übergabeparameter...");
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "false");

        // Construct the message
        log("Erstelle Nachricht...");
        Session session = Session.getDefaultInstance(props, null);
        String to = "demo@cordaware.com"; // Ignored by Mail2Info API
        String from = "demo@cordaware.com"; // Ignored by Mail2Info API
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(action);
            msg.setText("username=" + username + "\npassword=" + password + "\n" + txt);

            // Send the message.
            log("Sende Nachricht...");
            Transport.send(msg);

            log("Nachricht wurde erfolgreich an Cordaware bestinformed übermittelt.");
            return true;
        } catch (MessagingException e) {
            // Error.
            logErr("... Fehler beim Senden!");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Logs a message to the stdout.
     * @param msg The message log.
     */
    private static void log(String msg) {
        System.out.println(msg);
    }
    /**
     * Logs a message to the stderr.
     * @param msg The error message log.
     */
    private static void logErr(String msg) {
        System.err.println(msg);
    }
}

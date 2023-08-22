package correo;

import javax.mail.internet.*;
import javax.mail.*;
import java.util.*;

public class SMTPSession {

  private String host;
  private String user;
  private String password;
  private int port = 25;

  private boolean debug = false;

  private static int DEFAULT_PORT = 25;

  public SMTPSession(String host) {
    this(host, DEFAULT_PORT , null, null);
  }

  public SMTPSession(String host, String user, String password) {
    this(host, DEFAULT_PORT, user, password);
  }

  public SMTPSession(String host, int port, String user, String password) {
    this.host = host;
    this.user = user;
    this.password = password;
    this.port = port;
  }

  public void setDebug(boolean value) {
    debug = value;
  }

  public void sendMail(SMTPMail mail)
    throws Exception {
    // creamos algunas de las propiedades y la sesión real en si misma
    Properties props = new Properties();
    props.put("mail.smtp.host", host);

    Session session = Session.getDefaultInstance(props, null);
    session.setDebug(debug);

    //creamos un mensaje de JavaMail real!
    MimeMessage message = new MimeMessage(session);

    //rellenamos ese mail real con los datos de nuestra clase auxiliar
    this.prepareMessage(message, mail);

    //finalmente nos conectamos y enviamos el mail
    Transport transport = session.getTransport("smtp");
    if (user != null && password != null) {
      transport.connect(host, user, password);
    }
    else {
      transport.connect();
    }
    Transport.send(message);
    transport.close();
  }

  private MimeMessage prepareMessage(MimeMessage message, SMTPMail mail)
    throws Exception {

    // ajustamos las iferentes propiedades del mensaje
    message.setFrom(mail.getFrom());
    message.setSubject(mail.getSubject());
    message.setSentDate(new java.util.Date());
    message.setText(mail.getMessageText());

    //ahora las distintas direcciones
    InternetAddress[] addresses = mail.getReplyToAddresses();
    if (addresses != null) {
      message.setReplyTo(addresses);
    }
    addresses = mail.getToAddresses();
    if (addresses != null) {
      message.setRecipients(Message.RecipientType.TO, addresses);
    }
    addresses = mail.getCcAddresses();
    if (addresses != null) {
      message.setRecipients(Message.RecipientType.CC, addresses);
    }
    addresses = mail.getBccAddresses();
    if (addresses != null) {
      message.setRecipients(Message.RecipientType.BCC, addresses);
    }

    //almacenamos estos cambios en el mesnaje y listos
    message.saveChanges();
    return message;
  }

}


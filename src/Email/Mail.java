/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Email;

import Booking.Booking;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Szymon
 */
public class Mail
{
    private static final String USER_NAME = "kinoio.booking";
    private static final String PASSWORD = "ProjektNaIo";
    
    /**
     * Metoda statyczna generująca email składający się z danych przekazaych jako parametry i wywołująca metodę wysyłającą
     * @param title
     * @param date
     * @param hour
     * @param hall
     * @param ilosc
     * @param name
     * @param lastname
     * @param email
     * @param tel
     * @param price
     * @param id
     * @param pass 
     */
    public static void send(String title, String date, String hour, String hall, String ilosc, String name, String lastname, String email, String tel, String price, String id, String pass)
    {
        String body = "Dziękujemy za dokonanie rezerwacji. Oto dane Twojego zamówienia:\n"
                + "Tytuł: " + title + "\n"
                + "Data: " + date + "\n"
                + "Godzina: " + hour + "\n"
                + "Sala: " + hall + "\n"
                + "Ilość miejsc: " + ilosc + "\n"
                + "Imię: " + name + "\n"
                + "Nazwisko: " + lastname + "\n"
                + "Adres e-mail: " + email + "\n"
                + "Nr. telefonu: " + tel + "\n"
                + "Cena: " + price + "\n"
                + "Hasło: " + pass + "\n"
                + "Życzymy udango seansu";
        sendMail(USER_NAME, PASSWORD, email, "Potwierdzenie rezerwacji nr: " + id, body);
    }
    
    /**
     * Metoda wysyłająca email
     * @param from
     * @param pass
     * @param to
     * @param subject
     * @param body 
     */
    public static void sendMail(String from, String pass, String to, String subject, String body)
    {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try 
        {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae)
        {
            JOptionPane.showMessageDialog(null, ae.toString());
        }
        catch (MessagingException me)
        {
            JOptionPane.showMessageDialog(null, me.toString());
        }
    }
}



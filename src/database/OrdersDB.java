package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Szymon
 */
public class OrdersDB {

    private Connection connect = null;
    private Statement statement = null;

    /**
     * Metoda otwiera połączenie z bazą
     */
    public void open() {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://ran.nazwa.pl:3307/ran_2", "ran_2", "Szymon_M95");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    /**
     * Metoda zamykająca połączenie z bazą danych
     */
    public void close() {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    /**
     * Metoda dodająca zamówienie do bazy zamówień
     *
     * @param term
     * @param name
     * @param surname
     * @param mail
     * @param phone
     * @param password
     */
    public void addOrder(int term, String name, String surname, String mail, int phone, String password) {
        try {
            if (connect != null) {
                statement = connect.createStatement();
                statement.executeUpdate("INSERT INTO Orders (term, name, surname, mail, telephone, password) VALUES ("
                        + "'" + term + "',"
                        + "'" + name + "',"
                        + "'" + surname + "',"
                        + "'" + mail + "',"
                        + "'" + phone + "',"
                        + "'" + password + "')");
            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    /**
     * Metoda zwracająca id ostatniego zamówienia
     *
     * @return id ostatniego zamowienia
     */
    public int getId() {
        int id = 0;
        ResultSet temp;
        try {
            if (connect != null) {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT max(id) FROM Orders");
                temp.next();
                id = temp.getInt(1);
            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return id;
    }

    /**
     * Metoda sprawdzająca czy poprawność zgodność hasła z nr rezerwacji
     *
     * @param id
     * @param pass
     * @return true/false w zaleznosci od prowadzonych akceptacji hasloa i numeru rezerwacji
     */
    public boolean check(int id, String pass) {
        String temp_pass, date;
        int term;
        ResultSet temp, termid, data;
        try {
            if (connect != null) {
                statement = connect.createStatement();
                termid = statement.executeQuery("SELECT term FROM Orders WHERE id=" + id);
                if (termid.next()) {
                    term = termid.getInt("term");
                    data = statement.executeQuery("SELECT date, hour FROM Terms WHERE id=" + term);
                    data.next();
                    date = data.getString("date") + " " + data.getString("hour");
                    Date d2 = parseDate(date, "dd.MM.yy HH:mm");
                    Date d1 = new Date();
                    if (d2.before(d1)) {
                        return false;
                    }
                }
                temp = statement.executeQuery("SELECT password FROM Orders WHERE id=" + id);
                if (temp.next()) {
                    temp_pass = temp.getString("password");
                    if (temp_pass.equals(pass)) {
                        return true;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return false;
    }

    private Date parseDate(String date, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}

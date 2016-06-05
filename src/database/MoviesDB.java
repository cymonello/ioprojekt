package database;

import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Szymon
 */
public class MoviesDB {

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
     * Zwraca cały ResultSet ze wszystkimi danymi owybranym filmie
     *
     * @param id
     * @return ResultSet
     */
    public ResultSet getMovie(int id) {
        ResultSet movie = null;
        try {
            if (connect != null) {
                statement = connect.createStatement();
                movie = statement.executeQuery("SELECT * FROM Movies WHERE id=" + id);
            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return movie;
    }

    /**
     * Zwraca String tytuł
     *
     * @param id
     * @return tytuł filmu o danym id
     */
    public String getTitle(int id) {
        String title = null;
        ResultSet temp;
        try {
            if (connect != null) {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT title FROM Movies WHERE id=" + id);
                temp.next();
                title = temp.getString("title");
            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return title;
    }

    /**
     * Zwraca Date datę premiery filmu
     *
     * @param id
     * @return opis filmu
     */
    public String getDescription(int id) {
        String desc = null;
        ResultSet temp;
        try {
            if (connect != null) {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT description FROM Movies WHERE id=" + id);
                temp.next();
                desc = temp.getString("description");
            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return desc;
    }

    /**
     * Metoda zwracająca max id z bazy filmów czyli ilość filmów w bazie
     *
     * @return ilość filmów w bazie - najwieksze ID
     */
    public int getMaxId() {
        int id = 0;
        ResultSet temp;
        try {
            if (connect != null) {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT max(id) FROM Movies");
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
     * Metoda zwracająca info o filmie + zdjęcie w tablicy Object 0 - obrazek 1
     * - tytuł 2 - gatunek 3 - długość 4 - język 5 - wiek 6 - reżyser 7 -
     * aktorzy 8 - kraj 9 - rok prod 10 - ocena 11 - opis
     *
     * @param id
     * @return zbiór informacji o filmie
     */
    public Object[] getMovieInfo(int id) {
        ResultSet r = null;
        Object[] mov = new Object[12];
        try {
            if (connect != null) {
                statement = connect.createStatement();
                r = statement.executeQuery("SELECT title, genre, duration, lang, age, director, cast, country, year, note, description, image FROM Movies WHERE id=" + id);
                r.next();
                byte[] image = r.getBytes("image");
                Image img = Toolkit.getDefaultToolkit().createImage(image);
                ImageIcon icon = new ImageIcon(img);
                mov[0] = icon;
                mov[1] = r.getString("title");
                mov[2] = r.getString("genre");
                mov[3] = r.getInt("duration");
                mov[4] = r.getString("lang");
                mov[5] = r.getString("age");
                mov[6] = r.getString("director");
                mov[7] = r.getString("cast");
                mov[8] = r.getString("country");
                mov[9] = r.getInt("year");
                mov[10] = r.getDouble("note");
                mov[11] = r.getString("description");

            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return mov;
    }

    /**
     * Metoda zwracająca ocenę filmu
     *
     * @param id
     * @return ocena danego filmu
     */
    public double getNote(int id) {
        double note = 0;
        ResultSet temp;
        try {
            if (connect != null) {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT note_count, note_sum FROM Movies WHERE id=" + id);
                temp.next();
                int count = temp.getInt("note_count");
                int sum = temp.getInt("note_sum");
                note = (double) sum / (double) count;
            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return note;
    }

    /**
     * MEtoda dodająca ocenę filmu
     *
     * @param id
     * @param note
     */
    public void addNote(int id, int note) {
        ResultSet temp;
        try {
            if (connect != null) {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT note_count, note_sum FROM Movies WHERE id=" + id);
                temp.next();
                int count = temp.getInt("note_count") + 1;
                int sum = temp.getInt("note_sum") + note;
                statement.executeUpdate("UPDATE Movies SET note_count='" + count + "', note_sum='" + sum + "' WHERE id='" + id + "';");
            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

}

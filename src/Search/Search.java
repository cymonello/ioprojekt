/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author robert
 */
public class Search {

    private Connection connect = null;
    private Statement statement = null;

    public void open() {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://ran.nazwa.pl:3307/ran_2", "ran_2", "Szymon_M95");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    public void close() {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    public Integer[] byTitle(String s) {
        ResultSet movies;
        try {
            if (connect != null) {
                statement = connect.createStatement();
                movies = statement.executeQuery("SELECT *  FROM Movies WHERE title LIKE '%" + s + "%'");
                movies.last();
                int size = movies.getRow();
                Integer[] TabMovies = new Integer[size];
                movies.beforeFirst();
                int i = 0;
                while (movies.next()) {

                    TabMovies[i++] = movies.getInt("id");
                }
                return TabMovies;
            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return null;
    }

    public Integer[] byGenre(String s) {
        ResultSet movies;
        try {
            if (connect != null) {
                statement = connect.createStatement();
                movies = statement.executeQuery("SELECT *  FROM Movies WHERE genre='" + s + "'");
                movies.last();
                int size = movies.getRow();
                Integer[] TabMovies = new Integer[size];
                movies.beforeFirst();
                int i = 0;
                while (movies.next()) {

                    TabMovies[i++] = movies.getInt("id");
                }
                return TabMovies;
            } else {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return null;
    }
}

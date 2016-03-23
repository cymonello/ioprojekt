/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author Szymon
 */
public class TermsDB
{
    private Connection connect = null;
    private Statement statement = null;
    public void open()
    {
        try
        {
            connect = DriverManager.getConnection("jdbc:mysql://ran.nazwa.pl:3307/ran_2", "ran_2", "Szymon_M95");
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    public void close()
    {
        try
        {
            if(connect != null)
                connect.close();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    /**
     * Metoda getTermsInDay:
     * Parametrem jest string date - czyli data wybrana z tabelki w reprtuarze
     * Metoda zwraca ResultSet (czyli taką jakby tabelę) wypełnioną terminami dla tego dnia który był parametrem
     * Aby uzupełnić repertuar zwróconym obiektem ResultSet trzeba skorzystać z funkcjonalności klasy ResultSet (np metoda next() do przechodzenia po wierszach tablei, coś jak foreach)
     * Korzystając z metod RasultSet trzeba wybrać dla każdego wiersza kolumny hour, movie, hall
     * A następnie wywołaść metodę getMovieInfo(int id) gdzie id to będzie movie z ResultSet
     * @param date
     * @return
     */

    public ResultSet getTermsInDay(String date)
    {
        ResultSet termsInDay = null;
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                termsInDay = statement.executeQuery("SELECT * FROM Terms WHERE date=" + date);  
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return termsInDay;
    }
    
    /**
     * Metoda getMovieInfo:
     * Jako parametr przymuje id wg którego wybierze odpowiedni film
     * Zwraca tablicę stringów, której pola to odpowiednie informacje o filmie które trzeba umiejscowić w tabeli
     * @param id
     * @return
     */
    public String[] getMovieInfo(int id)
    {
        String info[] = new String[5];
        ResultSet movie = null;
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                movie = statement.executeQuery("SELECT title, age, info, lang, duration FROM Movies WHERE id=" + id);
                movie.next();
                info[0] = movie.getString("title");
                info[1] = movie.getString("age");
                info[2] = movie.getString("info");
                info[3] = movie.getString("lang");
                info[4] = movie.getString("duration");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
                info = null;
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return info;
    }

    /**
     * Zwraca tytuł filmu z konkretnego terminu
     * @param date
     * @param time
     * @return
     */
    public String getTitle(String date, String time)
    {
        String title = null;
        ResultSet temp;
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT movie FROM Terms WHERE date='" + date + "' AND hour='" + time + "'"); 
                temp.next();
                int i = temp.getInt("movie");
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT title FROM Movies WHERE id=" + i);
                temp.next();
                title = temp.getString("title");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return title;
    }
    
}
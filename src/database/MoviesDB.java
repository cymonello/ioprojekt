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
import java.sql.Date;
import java.sql.Time;
import javax.swing.JOptionPane;

/**
 *
 * @author Szymon
 */
public class MoviesDB
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
     * Zwraca cały ResultSet ze wszystkimi danymi owybranym filmie
     * @param id
     * @return
     */
    public ResultSet getMovie(int id)
    {
        ResultSet movie = null;
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                movie = statement.executeQuery("SELECT * FROM Movies WHERE id=" + id);  
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return movie;
    }

    /**
     * Zwraca String tytuł
     * @param id
     * @return
     */
    public String getTitle(int id)
    {
        String title = null;
        ResultSet temp;
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT title FROM Movies WHERE id=" + id); 
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

    /**
     * Zwraca Date datę premiery filmu
     * @param id
     * @return
     */
    public String getDescription(int id)
    {
        String desc = null;
        ResultSet temp;
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT description FROM Movies WHERE id=" + id); 
                temp.next();
                desc = temp.getString("description");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return desc;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Blob;
import javax.swing.ImageIcon;
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
    
    
    // Metoda zwracająca max id z bazy filmów czyli ilość filmów w bazie
    public int getMaxId()
    {
        int id = 0;
        ResultSet temp;
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT max(id) FROM Movies"); 
                temp.next();
                id = temp.getInt(1);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return id;
    }
    
    // Metoda zwracająca info o filmie + zdjęcie w tablicy Object
    
    public Object[] getMovieInfo(int id)
    {
        ResultSet r = null;
        Object[] mov = new Object[12];
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                r = statement.executeQuery("SELECT title, genre, duration, lang, age, director, cast, country, year, note, description, image FROM Movies WHERE id="+id); 
                r.next();
                byte [] image = r.getBytes("image");
                Image img = Toolkit.getDefaultToolkit().createImage(image);
                ImageIcon icon = new ImageIcon(img);
                mov[0] = icon;
                mov[1] = r.getInt("title");
                mov[2] = r.getInt("genre");
                mov[3] = r.getInt("duration");
                mov[4] = r.getInt("lang");
                mov[5] = r.getInt("age");
                mov[6] = r.getInt("director");
                mov[7] = r.getInt("cast");
                mov[8] = r.getInt("country");
                mov[9] = r.getInt("year");
                mov[10] = r.getInt("note");
                mov[11] = r.getInt("description");
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return mov;
    }
    
}

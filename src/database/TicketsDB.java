/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Szymon
 */
public class TicketsDB
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
    
    public void addTicket(int order, String date, String hour, int hall, int row, int seat, int type)
    {
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                statement.executeUpdate("INSERT INTO Tickets (orderid, date, hour, hall, row, seat, type) VALUES ("
                        + "'" + order + "',"
                        + "'" + date + "',"
                        + "'" + hour + "',"
                        + "'" + hall + "',"
                        + "'" + row + "',"
                        + "'" + seat + "',"
                        + "'" + type + "')");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    
    public int[][] checkHall(String date, String hour, int hall)
    {
        int[][] sala = new int[10][20];
        ResultSet term = null;
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                term = statement.executeQuery("SELECT row, seat FORM Tickets WHERE hall='" + hall + "' AND date='" + date + "' AND hour='" + hour + "';");
                if(term.next())
                {
                    while(term.next())
                    {
                        sala[term.getInt("row")][term.getInt("seat")] = 1;
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą biletow");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return sala;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Szymon
 */
public class OrdersDB
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
     * !!!!!!!!!!!!!!!!!!! KEY w tickets zamienić na auto żeby się sam dodawał przy wstawianiu!!!!!!!!!!!!!!!!!!!!!!!!
     * @param id
     * @param term
     * @param name
     * @param surname
     * @param mail
     * @param phone
     * @param password
     */
    public void addOrder(int term, String name, String surname, String mail, int phone, String password)
    {
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                statement.executeUpdate("INSERT INTO Orders (term, name, surname, mail, telephone, password) VALUES ("
                        + "'" + term + "',"
                        + "'" + name + "',"
                        + "'" + surname + "',"
                        + "'" + mail + "',"
                        + "'" + phone + "',"
                        + "'" + password + "')");
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
}

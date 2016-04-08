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
public class HallDB
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
    
    public int [][] getHall(int id)
    {
        int[][] hall = new int[10][20];
        ResultSet temp;
        try
        {
            if(connect != null)
            {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT row, col, status FROM Hall" + id); 
                temp.next();
                for(int i=0; i<10; i++)
                {
                    for(int j=0;j<20; j++)
                    {
                        hall[i][j] = temp.getInt("status");
                        temp.next();
                    }
                }
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą Sali");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return hall;
    }
    /*
    public void updateHall(int id, int[][] sala)
    {
        try
        {
            if(connect != null)
            {
                for(int i=0; i<10; i++)
                {
                    for(int j=0;j<20; j++)
                    {
                        statement = connect.createStatement();
                        statement.executeUpdate("UPDATE Hall" + id + " SET status='" + sala[i][j] + "'WHERE row='" + i + "' AND col='" + j + "' ;");
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą Sali");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    */
}

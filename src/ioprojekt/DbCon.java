/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioprojekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Szymon
 */
public class DbCon
{
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public void readDataBase() throws Exception
    {
        try
        {
            connect = DriverManager.getConnection("jdbc:mysql://eu-cdbr-azure-west-d.cloudapp.net:3306/cinema?"
                    + "user=b63c5dc3f86bbe&password=6f80e1c2");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from cinema.test_data");
            writeResultSet(resultSet);
        }
        catch(Exception e)
        {
            System.out.println("ERROR");
            throw e;
        }
        finally
        {
            close();
        }
    }
    
    private void writeResultSet(ResultSet set) throws SQLException
    {
        while(set.next())
        {
            String id = set.getString("id");
            String nazwa = set.getString("nazwa");
            String typ = set.getString("typ");
            System.out.println("Id: " + id);
            System.out.println("Name: " + nazwa);
            System.out.println("Type: " + typ);
        }
    }
    private void writePreparedStatement()
    {
        //TEST
    }
    private void close()
    {
        try
        {
            if(connect != null)
                connect.close();
        }
        catch(Exception e) {}
    }
}

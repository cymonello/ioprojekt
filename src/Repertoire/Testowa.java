package Repertoire;

import java.sql.SQLException;

/**
 * Created on 23.03.16.
 */
public class Testowa {
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Repertoire repertuar = new Repertoire("1");

        } catch(ClassNotFoundException e)
        {
            e.getMessage();
        }
    }
}

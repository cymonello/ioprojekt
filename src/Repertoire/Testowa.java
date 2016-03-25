package Repertoire;

import java.sql.SQLException;

/**
 * Created by deleviretta on 23.03.16.
 */
public class Testowa {
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Repertoire repertuar;
            String date = new String("23.03.16");
            repertuar = new Repertoire(date);
        } catch(ClassNotFoundException e)
        {
            e.getMessage();
        }
    }
}

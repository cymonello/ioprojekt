package Repertoire;

import database.MoviesDB;
import database.TermsDB;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by deleviretta on 23.03.16.
 */
public class Repertoire {
    String dateOfMovie;
    ResultSet movies;
    String[][] repertoire;
    TermsDB tdb;

    public Repertoire(String dateOfMovies) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            e.getMessage();
        }
        finally {
            tdb = new TermsDB();
            tdb.open();
            this.dateOfMovie = dateOfMovies;
            movies = tdb.getTermsInDay(dateOfMovies);
            tdb.close();
        }
    }
    /*
    public String gettingID() throws SQLException {

        tdb.open();
        String indexID = movies.getString("id");
        tdb.close();
        return indexID;
    }
    */
}

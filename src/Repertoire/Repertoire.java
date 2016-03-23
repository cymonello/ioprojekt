package Repertoire;

import database.MoviesDB;

/**
 * Created by deleviretta on 23.03.16.
 */
public class Repertoire {
    public void Movie(){
        MoviesDB mdb = new MoviesDB();
        mdb.open();

        mdb.close();
    }
    String dateOfMovies;

    public Repertoire(String dateOfMovies) {
        this.dateOfMovies = dateOfMovies;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Booking.Hall;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Szymon
 */
public class TicketsDB
{

    private Connection connect = null;
    private Statement statement = null;
    private static int[][] stareMiejsca;
    private static int ilosc;
    private String date, hour;
    private int hall;
    /**
     * Metoda otwiera połączenie z bazą
     */
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

    /**
     * Metoda zamykająca połączenie z bazą danych
     */
    public void close()
    {
        try
        {
            if (connect != null)
            {
                connect.close();
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    /**
     * Metoda dodająca bilet do bazy biletów
     *
     * @param order
     * @param date
     * @param hour
     * @param hall
     * @param row
     * @param seat
     * @param type
     */
    public void addTicket(int order, String date, String hour, int hall, int row, int seat, int type)
    {
        try
        {
            if (connect != null)
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
            } else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą filmów");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    /**
     * MEtoda przeglądająca bazę biletów by sprawdzić które miejca na sali są
     * już zajęte
     *
     * @param date
     * @param hour
     * @param hall
     * @return zwraca tablicę int[][] symbolizującą salę (0 lub 1 - zajętość
     * miejsc)
     */
    public int[][] checkHall(String date, String hour, int hall)
    {
        int[][] sala = new int[10][20];
        ResultSet term = null;
        try
        {
            if (connect != null)
            {
                statement = connect.createStatement();
                term = statement.executeQuery("SELECT row, seat FROM Tickets WHERE hall='" + hall + "' AND date='" + date + "' AND hour='" + hour + "';");
                while (term.next())
                {
                    sala[term.getInt("row")][term.getInt("seat")] = 1;
                }
                for (int i = 0; i < 10; i++)
                {
                    for (int j = 9; j < 11; j++)
                    {
                        sala[i][j] = -1;
                    }
                }
            } else
            {
                JOptionPane.showMessageDialog(null, "Błąd! Brak połączenia z bazą biletow");
            }
        } catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return sala;
    }

    /**
     * Metoda zwracająca tablicę z elementami potrzebymi do edycji zamówieinia
     * Data, godzina, sala, ilość miejsc oraz tablica reprezentująca salę z
     * zaznaczonymi przez użytkownika miejsami do edycji
     *
     * @param id id erdytowanego zamówienia
     * @return informacje - tablica obiektów z informacjami
     */
    public Object[] orderToEdit(int id)
    {
        ResultSet temp = null;
        Integer count = 0;
        Object[] informacje = new Object[5];
        int[][] sala = null;
        stareMiejsca = new int[10][20];
        try
        {
            if (connect != null)
            {
                statement = connect.createStatement();
                temp = statement.executeQuery("SELECT date, hour, hall, row, seat, type FROM Tickets WHERE orderid='" + id + "';");
                temp.next();
                informacje[0] = temp.getString("date");
                date = temp.getString("date");
                informacje[1] = temp.getString("hour");
                hour = temp.getString("hour");
                informacje[2] = String.valueOf(temp.getInt("hall"));
                hall = temp.getInt("hall");
                sala = checkHall(temp.getString("date"), temp.getString("hour"), temp.getInt("hall"));
                temp.beforeFirst();
                while (temp.next())
                {
                    sala[temp.getInt("row")][temp.getInt("seat")] = 2;
                    count++;
                }
                for (int i = 0; i < 10; i++)
                {
                    for (int j = 9; j < 11; j++)
                    {
                        sala[i][j] = -1;
                    }
                }
                informacje[3] = count.toString();
                informacje[4] = sala;
                for (int i = 0; i < 10; i++)
                {
                    for (int j = 0; j < 20; j++)
                    {
                        stareMiejsca[i][j] = sala[i][j];
                    }
                }
                ilosc = count;
            }
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return informacje;
    }

    /**
     * Metoda uaktualniająca miejsca po edycji rezerwacji
     *
     * @param id id rezerwacji
     * @param sala tablica reprezentująca salę z nowo wybranymi miejscami
     * @return
     */
    public boolean saveEdited(int id, int[][] sala)
    {
        try
        {
            int[] rows = new int[ilosc];
            int[] seats = new int[ilosc];
            int k = 0;
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 20; j++)
                {
                    if (stareMiejsca[i][j] == 2)
                    {
                        rows[k] = i;
                        seats[k] = j;
                        k++;
                    }
                }
            }
            k = 0;
            if (connect != null)
            {
                int[][] sala_do_spr = checkHall(date, hour, hall);
                for (int i = 0; i < 10; i++)
                {
                    for (int j = 0; j < 20; j++)
                    {
                        if (sala[i][j] == 2)
                        {
                            if (sala_do_spr[i][j] != 1)
                            {
                                statement = connect.createStatement();
                                statement.executeUpdate("UPDATE Tickets SET row='" + i + "', seat='" + j + "' WHERE orderid='" + id + "' AND row='" + rows[k] + "' AND seat='" + seats[k] + "';");
                                k++;
                            }
                            else 
                                return false;
                        }
                    }
                }
            }
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return true;
    }

    /**
     * Metoda usuwająca bilety należące do zamówienia oraz sam rekord zamówienia
     * z odpowiednich tabeli w bazie danych
     *
     * @param id - id usuwanego zamówienia
     */
    public void deleteOrder(int id)
    {
        try
        {
            if (connect != null)
            {
                statement = connect.createStatement();
                statement.executeUpdate("DELETE FROM Tickets WHERE orderid=" + id + ";");
                statement.executeUpdate("DELETE FROM Orders WHERE id=" + id + ";");
            }
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
}

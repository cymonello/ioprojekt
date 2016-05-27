package Windows;

/**
 * Klasa posiadająca statyczną metodę do sprawdzania poprawności danych
 * wprowadzanych przez urzytkownika w dostępnych polach
 *
 * @author Bartłomiej
 */
public class PoprawnoscDanych {

    /**
     * Meotda sprawdzająca poprawność danych wprowadzonych przez urzystkownika
     * na etapie rezerwacji.
     *
     * @param name - imię - może zawierać jedynie znaki a-z / A-Z - nie jest
     * konieczne aby pierwszalitera była wielka
     * @param lastname - nazwisko - może zawierać jedynie znaki a-z / A-Z - nie
     * jest konieczne aby pierwszalitera była wielka
     * @param email - adres e-mail - sprawdzane jest czy adres zawiera ciąg
     * znaków po którym następuje znak '@' oraz inny ciąg znaków po po '@' za
     * którym wystąpki znak '.' oraz kolejny ciąg znaków, ciągi takich znaków
     * muszą być minimum 1-znakowe
     * @param numer - numer teleronu - dowolna dziewięciocyfrowa liczba
     * nieujemna różna od 0
     * @return true jeżeli wszystkie powyższe warunki są spełnione, przeciwnie
     * false
     */
    public static boolean poprawnosc(String name, String lastname, String email, String numer) {
        String replaceAll1 = name.replaceAll("[a-z]+", "").replaceAll("[A-Z]+", "").replaceAll(" ", "");
        String replaceAll2 = lastname.replaceAll("[a-z]+", "").replaceAll("[A-Z]+", "").replaceAll(" ", "");
        String replaceAll3 = email.replaceAll(" ", "");

        if (replaceAll1.length() > 0 || name.length() == 0
                || replaceAll2.length() > 0 || lastname.length() == 0
                || !email.equals(replaceAll3) || email.indexOf('@') <= 0 || email.indexOf('@') > email.indexOf('.', email.indexOf('@') + 2) || email.indexOf('.', email.indexOf('@') + 2) == email.length() - 1) {
            return false;
        }

        int tel_num = 0;
        try {
            if (!numer.replaceAll(" ", "").equals(numer) || numer.length() != 9) {
                return false;
            }
            tel_num = Integer.parseInt(numer);
            if (tel_num <= 0) {
                return false;
            }
        } catch (Exception exc) {
            return false;
        }
        return true;
    }
}

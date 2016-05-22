/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

/**
 *
 * @author Bartłomiej
 */
public class PoprawnoscDanych {

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

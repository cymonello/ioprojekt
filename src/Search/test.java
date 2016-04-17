/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

//import BaseClasses.Film;

import java.util.HashMap;

/**
 *
 * @author robert
 */
public class test {
    public static void main(String[] args) {
        Dates date = new Dates(1);
        HashMap<String, Integer[]> hoursPerDay = date.getMapHoursOfMovie();
        String[] dates = date.getDatesOfMovie();
        for (int i = 0; i < dates.length; i++) {
            System.out.println(dates[i]);
            Integer[] hours = hoursPerDay.get(dates[i]);
            for (int j = 0; j < hours.length; j++) {
                System.out.print(hours[j]);
            }
            System.out.println();
        }
        System.out.println(date.gettingTermsIDForFilm(19,"23.04.16"));
    }


    
    
//    
//    static public void main(String[] argv){
//    Search tmp = new Search();
//    tmp.open();
//    Film[] temp=tmp.byTitle("a");
//    for(int i=0;i<temp.length;i++)
//    {
//        if(temp[i]!=null)
//            temp[i].Print();
//        
//    }
//    temp=tmp.byGenre("Dramat");
//    if(temp!=null)
//    for(int i=0;i<temp.length;i++)
//    {
//        if(temp[i]!=null)
//            temp[i].Print();
//        
//    }
//    tmp.close();
//}
}

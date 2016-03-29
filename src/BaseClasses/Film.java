/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseClasses;

import java.awt.Image;

/**
 *
 * @author robert
 */
public class Film{
    private final String title;
    private final String genre;
    private final String note;
    private final String description;
    private final int id;
    private Image img =null; 
    public Film(String title,String genre,String note,String description,int id,Image img){
       this.title=title;
       this.genre=genre;
       this.note=note;
       this.description=description;
       this.id=id;
       this.img=img;
    }
    
    public void Print()
    {
        System.out.println(title+", "+genre);
        System.out.println(description);
        System.out.println();
    }
}

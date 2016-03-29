/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import BaseClasses.Film;

/**
 *
 * @author robert
 */
public class test {
    
    
    
    
    
    static public void main(String[] argv){
    Search tmp = new Search();
    tmp.open();
    Film[] temp=tmp.byTitle("a");
    for(int i=0;i<temp.length;i++)
    {
        if(temp[i]!=null)
            temp[i].Print();
        
    }
    temp=tmp.byGenre("Dramat");
    if(temp!=null)
    for(int i=0;i<temp.length;i++)
    {
        if(temp[i]!=null)
            temp[i].Print();
        
    }
    tmp.close();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Foreground.Blackwind.Blackwind;

/**
 *
 * @author Connor
 */
public class Display {
    String[] text;

    public Display() {
        text = new String[4];
    }
    public void setDisplay(String text1){
        text[0] = text1;
        text[1]="";
        text[2]="";
        text[3]="";
    }
    public void setDisplay(String text1, String text2, String text3, String text4){
        text[0]=text1;
        text[1]=text2;
        text[2]=text3;
        text[3]=text4;
    }
    public void printText(){
        for(int i=0;i<4;i++){
            System.out.println(text[i]);
        }
    }
    
    public static void main(String[] args) {
        Display d = new Display();
        Event e = new Event();
        e.addSegment(new SegmentText("This is a test","","",""));
        e.addSegment(new SegmentText("This is also a test","so we can see multiple lines","",""));
        
        Blackwind b = new Blackwind();
        //d.printText();
        e.activateNextSegment(d, b);
        d.printText();
        e.activateNextSegment(d, b);
        d.printText();
        e.activateNextSegment(d, b);
    }
    
}

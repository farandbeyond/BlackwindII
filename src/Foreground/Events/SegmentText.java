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
public class SegmentText extends EventSegment{
    String[] text;
    public SegmentText(String text1, String text2, String text3, String text4) {
        super(false);
        text=new String[4];
        text[0]=text1;
        text[1]=text2;
        text[2]=text3;
        text[3]=text4;
    }

    @Override
    public void activate(Display d, Blackwind b) {
        d.setDisplay(text[0], text[1], text[2], text[3]);
    }
    
}

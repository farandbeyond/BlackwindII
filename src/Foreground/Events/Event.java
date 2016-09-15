/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Events;

import Foreground.Blackwind.Blackwind;
import java.util.ArrayList;

/**
 *
 * @author Connor
 */
public class Event {
    ArrayList<EventSegment> segments;
    int currentIndex;
    public Event() {
        segments = new ArrayList<>();
        currentIndex = 0;
    }
    public void activateNextSegment(Display d, Blackwind b){
        try{
            if(!segments.get(currentIndex).getEventEnd())
                segments.get(currentIndex).activate(d, b);
            currentIndex++;
        }catch(IndexOutOfBoundsException e){
            System.out.println("Event over");
            //e.printStackTrace();
        }
    }
    public void addSegment(EventSegment e){
        segments.add(e);
    }
    public void findSegment(Class c){
        while(true){
            if(segments.get(currentIndex).getClass()==c)
                return;
            currentIndex++;
        }
    }
    
    public EventSegment getSegment(int index){
        return segments.get(index);
    }
    
}

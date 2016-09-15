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
public abstract class EventSegment {
    private final boolean endsEvent;
    EventSegment(boolean endsEvent){
        this.endsEvent = endsEvent;
    }
    public boolean getEventEnd(){return endsEvent;}
    public abstract void activate(Display d,Blackwind b);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground;

import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public abstract class HandlerMenu extends JPanel{
    boolean change;
    boolean confirmEvent;
    boolean cancelEvent;
    public HandlerMenu(){
        change = true;
        confirmEvent = false;
        cancelEvent = false;
    }
    public abstract void upEvent();
    public abstract void downEvent();
    public abstract void rightEvent();
    public abstract void leftEvent();
    public void confirmEvent(){confirmEvent = true;change = true;}
    public void cancelEvent(){cancelEvent = true;change = true;}
    public boolean change(){return change;}
    public boolean getConfirmEvent(){return confirmEvent;}
    public boolean getCancelEvent(){return cancelEvent;}
    public void resetEvents(){confirmEvent = false;cancelEvent = false;}
    @Override
    public void repaint(){
        //This will be removed once Blackwind comes into play. until then, enjoy the Thread.sleep(10);
        try{
            Thread.sleep(10);
        }catch(InterruptedException e){
            
        }
        if(change){
            super.repaint();
            painted();
        }
    }
    public void changed(){change = true;}
    public void painted(){change = false;}
}

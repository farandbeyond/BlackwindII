/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Connor
 */
public class Joystick implements KeyListener{
    HandlerMenu menu;
    public Joystick(HandlerMenu m){
        menu = m;
    }
    public void sendMenu(HandlerMenu m){
        menu = m;
    }
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getExtendedKeyCode()){
            case KeyEvent.VK_W:menu.upEvent();break;
            case KeyEvent.VK_A:menu.leftEvent();break;
            case KeyEvent.VK_S:menu.downEvent();break;
            case KeyEvent.VK_D:menu.rightEvent();break;
            case KeyEvent.VK_O:menu.confirmEvent();break;
            case KeyEvent.VK_P:menu.cancelEvent();break;
            case KeyEvent.VK_ENTER:
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
}

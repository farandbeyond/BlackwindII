/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Connor
 */
public class MOptions extends SelectorMenu{
    public static final int
            INVENTORY=0,
            STATUS=1,
            SPELLS=2,
            EQUIPMENT=3,
            SWAPMEMBERS=4,
            SAVE=5;
    public static final int
            maxOptions=6,
            distFromLeft = 40,
            distFromTop = 50;
    private String[] optionsDisplayed;
    public MOptions(int x, int y, int width, int height, Color c) {
        super(maxOptions, x, y, width, height,70,50,50,c);
        optionsDisplayed = new String[maxOptions];
        loadMainMenuOptions();
    }
    public void paint(Graphics g){
        super.paint(g);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        for(int i=0;i<6;i++){
            g.drawString(optionsDisplayed[i], getDistLeft()+myX(), getDistTop()+myY()+getDistBetween()*i);
        }
        
    }
    public void loadMainMenuOptions(){
        optionsDisplayed[0]="Inventory";
        optionsDisplayed[1]="Status";
        optionsDisplayed[2]="Spells";
        optionsDisplayed[3]="Equipment";
        optionsDisplayed[4]="Swap Members";
        optionsDisplayed[5]="Save";
        setSelectorMaxPos(5);
    }
    public void loadInventoryMenuOptions(){
        optionsDisplayed[0]="Use";
        optionsDisplayed[1]="Swap";
        optionsDisplayed[2]="Examine";
        optionsDisplayed[3]="Drop";
        optionsDisplayed[4]="";
        optionsDisplayed[5]="";
        setSelectorMaxPos(3);
    }
    public void loadStatusMenuOptions(){
        optionsDisplayed[0]="Exit";
        optionsDisplayed[1]="";
        optionsDisplayed[2]="";
        optionsDisplayed[3]="";
        optionsDisplayed[4]="";
        optionsDisplayed[5]="";
        setSelectorMaxPos(0);
    }
    public void loadSpellsMenuOptions(){
        optionsDisplayed[0]="Cast";
        optionsDisplayed[1]="Description";
        optionsDisplayed[2]="Swap";
        optionsDisplayed[3]="";
        optionsDisplayed[4]="";
        optionsDisplayed[5]="";
        setSelectorMaxPos(2);
    }
    public void loadEquipmentMenuOptions(){
        optionsDisplayed[0]="Equip";
        optionsDisplayed[1]="UnEquip";
        optionsDisplayed[2]="Description";
        optionsDisplayed[3]="";
        optionsDisplayed[4]="";
        optionsDisplayed[5]="";
        setSelectorMaxPos(2);
    }

    @Override
    public void upEvent() {
        super.upEvent();
        confirmMenuPosition();
    }

    @Override
    public void downEvent() {
        super.downEvent();
        confirmMenuPosition();
    }
    
    
    
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        
        MOptions o = new MOptions(400,0,240,480,Color.red);
        frame.add(o);
        
    }
    
}

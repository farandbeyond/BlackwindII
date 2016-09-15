/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Entities.BattleEntity;
import Background.Entities.Element;
import Background.Entities.EntityLoader;
import Background.Entities.PartyMember;
import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author Connor
 */
public class MStatus extends SelectorMenu{
    PartyMember m;
    public MStatus(){
        super(0,0,0,400,480,0,0,0,Color.magenta);
        m=null;
    }
    public MStatus(PartyMember m){
        super(0,0,0,400,480,0,0,0,Color.magenta);
        this.m = m;
    }
    public void paint(Graphics g){
        super.paint(g);
        g.drawString(m.getName(),15,55);
        g.drawString(String.format("Element: %s",Element.getElementName(m.getElement())),140,55);
        g.drawString(m.isDead()?"Currently dead":"Currently alive", 15, 80);
        g.drawString("Level "+m.getLevel(),15,105);
        g.drawString("Exp until next level: "+m.getExpToLevel(),15,125);
        g.drawString(String.format("%d/%d HP",m.getStat(BattleEntity.HP).getStat(),m.getStat(BattleEntity.HP).getMax()),15,170);
        g.drawString(String.format("%d/%d MP",m.getStat(BattleEntity.MP).getStat(),m.getStat(BattleEntity.MP).getMax()),215,170);
        for(int i=2;i<7;i++){
            String s1 = "  ";
            String s2 = "  ";
            if(m.getStat(i).getBase()>=10){
                s1=" ";
            }else if(m.getStat(i).getBase()>=100){
                s1="";
            }
            if(m.getStat(i).getStat()>=10){
                s2=" ";
            }else if(m.getStat(i).getStat()>=100){
                s2="";
            }
            g.drawString(String.format("%s:%s%d : %s%d",m.getStat(i).getShortName(),s1,m.getStat(i).getBase(),s2,m.getStat(i).getStat()), 15, 160+20*i);//maxing at 300
        }
        try{
            g.drawString("Weapon: "+m.getWeapon().getName(), 15, 320);
        }catch(BattleEntity.EntityNullError e){
            g.drawString("Weapon: ------", 15, 320);
        }
        for(int i=1;i<4;i++){
            try{
                g.drawString("Armor:  "+m.getArmor(i).getName(), 15, 320+20*i);
            }catch(BattleEntity.EntityNullError e){
                g.drawString("Armor:  ------", 15, 320+20*i);
            }
        }
        
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        MStatus m = new MStatus();
        //MItemStatus m = new MItemStatus(Items.Load(Items.MAGICCANE, 3));
        frame.add(m);
        //Thread.sleep(1000);
        //frame.repaint();
    }
}

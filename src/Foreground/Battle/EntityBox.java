/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Battle;

import Background.Entities.BattleEntity;
import Background.Entities.EntityLoader;
import Background.Entities.PartyMember;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class EntityBox extends JPanel{
    StatBar hpBar, mpBar;
    BattleEntity b;
    int myX, myY, myWidth, myHeight;
    int hpX, hpY, mpX, mpY;
    Color currentColor;
    boolean targeted,acting;

    public EntityBox(BattleEntity b, int myX, int myY, int myWidth, int myHeight, int hpX, int mpX, int bothY) {
        this.hpBar = new StatBar(b,true,BattleEntity.HP,hpX,bothY,myWidth/2);
        this.mpBar = new StatBar(b,true,BattleEntity.MP,mpX,bothY,myWidth/2);
        this.b = b;
        this.myX = myX;
        this.myY = myY;
        this.myWidth = myWidth;
        this.myHeight = myHeight;
        this.hpX = hpX;
        this.hpY = bothY;
        this.mpX = mpX;
        this.mpY = bothY;
        this.currentColor = Color.green;
        this.targeted = false;
        this.acting = false;
    }
    
    public EntityBox(BattleEntity b, int myX, int myY, int myWidth, int myHeight) {
        hpX = myX+1;
        mpX = myX+1;
        hpY = myY+30;
        mpY = myY+40;
        this.hpBar = new StatBar(b,BattleEntity.HP,hpX,hpY,myWidth);
        this.mpBar = new StatBar(b,BattleEntity.MP,mpX,mpY,myWidth);
        targeted=false;
        acting=false;
        this.b = b;
        this.myX = myX;
        this.myY = myY;
        this.myWidth = myWidth;
        this.myHeight = myHeight;
        updateColor();
    }
    public EntityBox(int myX, int myY, int myWidth, int myHeight) {
        hpX = myX;
        mpX = myX;
        hpY = myY+30;
        mpY = myY+40;
        this.hpBar = new StatBar(null,BattleEntity.HP,hpX,hpY,myWidth+1);
        this.mpBar = new StatBar(null,BattleEntity.MP,mpX,mpY,myWidth+1);
        targeted=false;
        acting=false;
        this.b = null;
        this.myX = myX;
        this.myY = myY;
        this.myWidth = myWidth;
        this.myHeight = myHeight;
        updateColor();
    }
    
    public void updateColor(){
        if(b==null)
            currentColor = Color.gray;
        else if(targeted)
            currentColor = Color.orange;
        else if(acting)
            currentColor = Color.GREEN;
        else if(b.isDead())
            currentColor = Color.red;
        else
            currentColor = Color.cyan;
    }
    public void updateEntity(BattleEntity b){
        this.hpBar = new StatBar(b,true,BattleEntity.HP,hpX,hpY,myWidth/2);
        this.mpBar = new StatBar(b,true,BattleEntity.MP,mpX,mpY,myWidth/2);
        this.b = b;
    }
    
    public void paint(Graphics g) {
        updateColor();
        g.setColor(currentColor);
        g.fillRect(myX, myY, myWidth, myHeight);
        g.setColor(Color.black);
        g.drawRect(myX, myY, myWidth, myHeight);
        hpBar.paint(g);
        mpBar.paint(g);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.setColor(Color.black);
        if(b!=null)
            g.drawString(b.getName(), myX+2, myY+12);
    }
    
    public boolean getActing(){return acting;}
    public boolean getTargeted(){return targeted;}
    
    public void target(){targeted = true;}
    public void acting(){acting = true;}
    
    public void setTargeted(boolean targeted){this.targeted = targeted;}
    public void setActing(boolean acting){this.acting = acting;}
    
    public static void main(String[] args){
        
        PartyMember m = EntityLoader.loadPartyMember(0);
        m.printhpmp();
        m.damage(15);
        
        EntityBox b = new EntityBox(m,0,0,50,50);
        
        JFrame frame = new JFrame("Blackwind Dev",null){
            public void paintComponent(Graphics g){
                b.paint(g);
            }
        };
                
                //new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        frame.setPreferredSize(new Dimension(640,480));
        frame.pack();
        
        
        frame.add(b);
        while(true){
            b.repaint();
        }
    }
}

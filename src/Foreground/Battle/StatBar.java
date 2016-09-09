/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Battle;

import Background.Entities.BattleEntity;
import Background.Entities.BattleEntity.Stat;
import Background.Entities.EntityLoader;
import Foreground.TestDisplay;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class StatBar extends JComponent{
    BattleEntity e;
    int statID;
    Color c;
    int myX, myY, myWidth, myHeight;
    boolean showMax;
    public StatBar(BattleEntity e, int statID, int x, int y, int width){
        this.setPreferredSize(new Dimension(480,480));
        this.e = e;
        myX=x;
        myY=y;
        myWidth = width;
        myHeight = 8;
        switch(statID){
            case BattleEntity.HP : c=Color.RED;break;
            case BattleEntity.MP : c=Color.BLUE;break;
        }
        this.statID = statID;
        showMax = false;
    }
    public StatBar(BattleEntity e, boolean showMax, int statID, int x, int y, int width){
        this.setPreferredSize(new Dimension(480,480));
        this.e = e;
        myX=x;
        myY=y;
        myWidth = width;
        myHeight = 8;
        switch(statID){
            case BattleEntity.HP : c=Color.RED;break;
            case BattleEntity.MP : c=Color.BLUE;break;
        }
        this.statID = statID;
        this.showMax = showMax;
    }
    public void paint(Graphics g){
        //g.setFont(new Font("Monospaced", Font.BOLD, 20));
        //g.drawString(e.getName(),myX+15, myY+15);
        //this is the test: HP bar
        g.setColor(Color.black);
        g.fillRect(myX, myY, myWidth, 10);
        
        try{
            g.setColor(c);
            g.fillRect(myX+1, myY+1, 
                    (myWidth-2) *(statID==BattleEntity.HP?e.getHpPercentile():e.getMpPercentile())/100
                    , 8);

            g.setColor(Color.white);
            //USING FONT SIZE 12:
            //all characters are 6 pixels wide, with a 2 pixel gap between
            //centering the hp stat is not overly difficult while the size remains the same
            g.setFont(new Font("Monospaced", Font.BOLD, 12));
            int xOffset;
            if(!showMax){
                xOffset=4*(e.getStat(statID).getStat()+"").length();
                g.drawString(e.getStat(statID).getStat()+"", myX-2+(myWidth/2)-xOffset, myY+myHeight+1);
            }else{
                xOffset=4*(e.getStat(statID).getStat()+"/"+e.getStat(statID).getMax()).length();
                g.drawString(e.getStat(statID).getStat()+"/"+e.getStat(statID).getMax(), myX-2+(myWidth/2)-xOffset, myY+myHeight+1); 
            }
        }catch(NullPointerException e){
            g.setColor(Color.gray);
            g.fillRect(myX+1, myY+1, 
                    myWidth-2
                    , 8);
            g.setColor(Color.white);
            g.drawString("-", myX-2+(myWidth/2), myY+myHeight+1);
        }
        //System.out.println((e.getStat(0).getStat()+"").length());
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        
        StatBar h = new StatBar(EntityLoader.loadPartyMember(0),0,25,25,50);
        StatBar m = new StatBar(EntityLoader.loadPartyMember(0),1,100,35,50);
        
        JPanel p = new JPanel(){
            public void paintComponent(Graphics g){
                for(Component m:this.getComponents())
                    m.paint(g);
            }
        };
        
        p.setVisible(true);
        p.setPreferredSize(new Dimension(640, 480));
        
        
        frame.add(p);
        p.add(h);
        p.add(m);
        frame.pack();
        while(true){
            frame.repaint();
        }
    }
    
}

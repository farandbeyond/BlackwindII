/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground;

import Background.Entities.BattleEntity;
import Background.Entities.EntityLoader;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class TestDisplay extends JPanel{
    BattleEntity e;
    int myX, myY, myWidth, myHeight;
    public TestDisplay(BattleEntity e){
        this.e=e;
        myX=50;
        myY=50;
        myWidth = 50;
        myHeight = 50;
        this.setPreferredSize(new Dimension(640,480));
    }
    public void paint(Graphics g){
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString(e.getName(),myX+15, myY+15);
        //this is the test: HP bar
        g.setColor(Color.black);
        g.fillRect(myX+5, myY+40, myWidth-10, 10);
        g.setColor(Color.red);
        g.fillRect(myX+6, myY+41, 
                (myWidth-12) * e.getHpPercentile()/100
                , 8);
        
        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 12));
        
        g.drawString(e.getStat(0).getStat()+"", myX+15, myY+49);
    }
    public void loop() throws InterruptedException{
        while(true){
            repaint();
            Thread.sleep(1000);
            e.damage(1);
            System.out.println(e.getStat(BattleEntity.HP).getStat()+"HP");
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        
        TestDisplay t = new TestDisplay(EntityLoader.loadPartyMember(0));
        frame.add(t);
        frame.pack();
        //t.loop();
    }
}

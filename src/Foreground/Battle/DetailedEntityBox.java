/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Battle;

import Background.Entities.BattleEntity;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Connor
 */
public class DetailedEntityBox extends EntityBox{

    public DetailedEntityBox(BattleEntity b, int myX, int myY, int myWidth, int myHeight) {
        super(b, myX, myY, myWidth, myHeight, myX, myX+myWidth/2, myY);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(currentColor);
        g.fillRect(myX, myY, myWidth, myHeight);
        g.setColor(Color.black);
        g.drawRect(myX, myY, myWidth, myHeight);
        hpBar.paint(g);
        mpBar.paint(g);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.setColor(Color.black);
        g.drawString(b.getName(), myX+2, myY+30);
    }
    
    
    public void setActing(BattleEntity b){
        super.updateEntity(b);
    }
}

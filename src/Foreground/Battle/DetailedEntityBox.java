/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Battle;

import Background.Effects.Buff;
import Background.Effects.Debuff;
import Background.Effects.Effect;
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
        for(Effect e:b.getEffectList()){
            if(e.getClass()==Buff.class){
                Buff ef = (Buff)e;
                g.drawImage(Battle.getBuffImage(ef.getStat()), myX+30+30*b.getEffectList().indexOf(e), myY+(b.getEffectList().indexOf(e)%2==0?60:30), this);
                //b.getEffectList().indexOf(e);
            }
            if(e.getClass()==Debuff.class){
                Debuff ef = (Debuff)e;
                g.drawImage(Battle.getDebuffImage(ef.getStat()), myX+30+30*b.getEffectList().indexOf(e), myY+(b.getEffectList().indexOf(e)%2==0?60:30), this);
                //b.getEffectList().indexOf(e);
            }
        }
    }
    
    
    public void setActing(BattleEntity b){
        super.updateEntity(b);
    }
}

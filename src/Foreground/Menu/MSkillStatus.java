/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.BattleActions.*;
import Background.Effects.Buff;
import Background.Effects.Debuff;
import Background.Effects.DoT;
import Background.Entities.Element;
import Background.Entities.BattleEntity;
import Background.Entities.EntityLoader;
import Background.Items.Items;
import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author Connor
 */
public class MSkillStatus extends SelectorMenu{
    BattleAction b;
    MSkillStatus(BattleAction act){
        super(0,0,0,400,480,0,0,0,Color.MAGENTA);
        b=act;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString(String.format("Caster: %s",b.getCaster().getName()),15,15+40);
        g.drawString(b.getName(), 15, 50+40);
        g.drawString(String.format("%d mp",b.getCost()),15,70+40);
        if(b.getClass()==PhysicalAction.class){
            PhysicalAction ba = (PhysicalAction)b;
            g.drawString(String.format("Deals %d-%d %s damage,",ba.getBaseDamage(),ba.getMaxDamage(),Element.getElementName(ba.getElement())), 15, 100+40);
            g.drawString("Physical Action",15,120+40);
            g.drawString(String.format("Damage increased by your %s",BattleEntity.statNames[ba.getDamageStat()]),15,170+40);
            g.drawString(String.format("Damage reduced by Enemy %s",BattleEntity.statNames[ba.getResistStat()]),15,190+40);
        }else if(b.getClass()==HealingSpell.class){
            HealingSpell ba = (HealingSpell)b;
            g.drawString(ba.getRevives()?"Revives target and":"Does not revive target, but", 15, 100+40);
            g.drawString(String.format("heals target for %d-%d HP",ba.getBaseHeal(),ba.getMaxHeal()), 15, 120+40);
            g.drawString("Heal increased by your Intelligence",15,170+40);
        }else if(b.getClass()==DamageSpell.class){
            DamageSpell ba = (DamageSpell)b;
            g.drawString(String.format("Deals %d-%d %s damage,",ba.getBaseDamage(),ba.getMaxDamage(),Element.getElementName(ba.getElement())), 15, 100+40);
            g.drawString("Damaging Spell",15,120+40);
            g.drawString("Damage increased by your Intelligence",15,170+40);
            g.drawString("Damage reduced by Enemy Resistance",15,190+40);
        }else if(b.getClass()==EffectSpell.class){
            EffectSpell ba = (EffectSpell)b;
            g.drawString("Spell applies the following effect:",15,100+40);
            if(ba.getEffect().getClass()==Buff.class){
                Buff e = (Buff)ba.getEffect();
                g.drawString(e.getName(),15,150+40);
                g.drawString("Increases "+BattleEntity.statNames[e.getStat()]+" by "+e.getIncrease()*100+"%", 15, 170+40);
                g.drawString(String.format("Lasts %d turns",e.getDuration()),15,190+40);
            }else if(ba.getEffect().getClass()==Debuff.class){
                Debuff e = (Debuff)ba.getEffect();
                g.drawString(e.getName(),15,150+40);
                g.drawString("Decreases "+BattleEntity.statNames[e.getStat()]+" by "+e.getDecrease()*100+"%", 15, 170+40);
                g.drawString(String.format("Lasts %d turns",e.getDuration()),15,190+40);
            }else if(ba.getEffect().getClass()==DoT.class){
                DoT e = (DoT)ba.getEffect();
                g.drawString(e.getName(),15,150+40);
                g.drawString(String.format("Deals %d %s damage per turn",e.getDamage(),Element.getElementName(e.getElement())), 15, 170+40);
                g.drawString(String.format("Lasts %d turns",e.getDuration()),15,190+40);
            }
            
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Menu.main(args);
        /*
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        MSkillStatus m = new MSkillStatus(BattleActionLoader.loadAction(BattleActionLoader.SHELTER));
        //MItemStatus m = new MItemStatus(Items.Load(Items.MAGICCANE, 3));
        frame.add(m);
        //Thread.sleep(1000);
        //frame.repaint();
                
                */
    }
}

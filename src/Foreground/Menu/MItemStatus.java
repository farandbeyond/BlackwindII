/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Entities.Element;
import Background.Entities.BattleEntity;
import Background.Items.*;
import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author Connor
 */
public class MItemStatus extends SelectorMenu{
    Item i;
    MItemStatus(Item i){
        super(0,0,0,400,480,0,0,0,Color.magenta);
        this.i = i;
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString(i.getName(), 15, 50+40);
        g.drawString("x"+i.getQuantity()+"/"+i.getMaxQuantity(),320,50+40);
        g.drawString(i.getShopValue()+"g", 15, 70+40);
        if(i.getClass()==HealingItem.class){
            HealingItem it = (HealingItem)i;
            g.drawString(it.getRevives()?"Revives the target, and":"Does not revive the target, but", 15, 100+40);
            switch(it.getHealType()){
                case HealingItem.HP:g.drawString(String.format("heals the target for %d hp",it.getHealValue()), 15, 120+40);break;
                case HealingItem.MP:g.drawString(String.format("recovers %d of the target's mp",it.getHealValue()), 15, 120+40);break;
                case HealingItem.HPMP:g.drawString(String.format("refreshes the target for %d hp and mp",it.getHealValue()), 15, 120+40);break;
            }
        }else if(i.getClass()==DamageItem.class){
            DamageItem it = (DamageItem)i;
            g.drawString(String.format("Deals %d %s damage",it.getDamage(),Element.getElementName(it.getElement())), 15, 100+40);
        }else if(i.getClass()==Armor.class){
            Armor it = (Armor)i;
            g.drawString("Equips to an armor slot",15,100+40);
            g.drawString("Increases:",15,150+40);
            g.drawString(String.format("%s by %d and",BattleEntity.statNames[it.getPrimaryStat()],it.getPrimaryValue()),15,170+40);
            g.drawString(String.format("%s by %d when equipped",BattleEntity.statNames[it.getSecondaryStat()],it.getSecondaryValue()),15,190+40);
        }else if(i.getClass()==Weapon.class){
            Weapon it = (Weapon)i;
            g.drawString("Equips to a weapon slot",15,100+40);
            g.drawString(String.format("Deals %d-%d damage",it.getBaseDamage(),it.getBaseDamage()+it.getRollDamage()),15,150+40);
            g.drawString(String.format("Increases %s by %d",BattleEntity.statNames[it.getStatToIncrease()],it.getStatIncrease()),15,170+40);
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        
        MItemStatus m = new MItemStatus(Items.Load(Items.MAGICCANE, 3));
        frame.add(m);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Entities.BattleEntity;
import Background.Entities.Element;
import Background.Entities.PartyMember;
import Background.Items.Equipment;
import Background.Items.Item;
import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Connor
 */
public class MEquipment extends SelectorMenu{
    PartyMember m;
    public MEquipment(){
        super(3,0,0,400,480,150,100,30,Color.magenta);
        //this.m=m;
    }
    public void updateEquipper(PartyMember m){this.m=m;}
    public void paint(Graphics g){
        super.paint(g);
        g.drawString(m.getName(), 140, 60);
        
        g.drawString("Approx Damage Range",70,90);
        try{
            g.drawString(String.format("%d-%d",(m.getWeapon().getBaseDamage())+(m.getStat(BattleEntity.STR).getStat()/2),(m.getWeapon().getMaxDamage())+(m.getStat(BattleEntity.STR).getStat()/2)),140,115);
        }catch(BattleEntity.EntityNullError e){
            g.drawString(String.format("%d-%d",m.getStat(BattleEntity.STR).getStat()/2,m.getStat(BattleEntity.STR).getStat()/2),140,115);
        }
        try{
            g.drawString("Weapon: "+m.getWeapon().getName(),getX()+getDistLeft(),getY()+getDistTop());
        }catch(BattleEntity.EntityNullError e){
            g.drawString("Weapon: ------",getX()+getDistLeft(),getY()+getDistTop());
        }
        for(int i=1;i<4;i++){
            try{
                g.drawString("Armor:  "+m.getArmor(i).getName(),getX()+getDistLeft(),getY()+getDistTop()+getDistBetween()*i);
            }catch(BattleEntity.EntityNullError e){
                g.drawString("Armor:  ------",getX()+getDistLeft(),getY()+getDistTop()+getDistBetween()*i);
            }
        }
        try{
            g.drawString(m.getWeapon().getName(), 15, 300);
            g.drawString(String.format("%s: +%d",m.getStat(m.getWeapon().getStatToIncrease()).getShortName(),m.getWeapon().getStatIncrease()), 15, 320);
            g.drawString("Element: "+Element.getElementName(m.getElement()), 15, 340);
        }catch(BattleEntity.EntityNullError e){
            g.drawString("No Weapon", 15, 300);
            //g.drawString("No Weapon", 15, 320);
            //g.drawString("No Weapon", 15, 340);
        }
        
        try{
            g.drawString(m.getArmor(1).getName(), 215, 300);
            g.drawString(String.format("%s: +%d",m.getStat(m.getArmor(1).getPrimaryStat()).getShortName(),m.getArmor(1).getPrimaryValue()), 215, 320);
            g.drawString(String.format("%s: +%d",m.getStat(m.getArmor(1).getSecondaryStat()).getShortName(),m.getArmor(1).getSecondaryValue()), 215, 340);
        }catch(BattleEntity.EntityNullError e){
            g.drawString("No Slot 1 Armor", 215, 300);
            //g.drawString("No Slot 1 Armor", 215, 320);
            //g.drawString("No Slot 1 Armor", 215, 340);
        }
        
        try{
            g.drawString(m.getArmor(2).getName(), 15, 380);
            g.drawString(String.format("%s: +%d",m.getStat(m.getArmor(1).getPrimaryStat()).getShortName(),m.getArmor(1).getPrimaryValue()), 15, 400);
            g.drawString(String.format("%s: +%d",m.getStat(m.getArmor(1).getSecondaryStat()).getShortName(),m.getArmor(1).getSecondaryValue()), 15, 420);
        }catch(BattleEntity.EntityNullError e){
            g.drawString("No Slot 2 Armor", 15, 380);
            //g.drawString("No Slot 2 Armor", 15, 400);
            //g.drawString("No Slot 2 Armor", 15, 420);
        }
        
        try{
            g.drawString(m.getArmor(3).getName(), 215, 380);
            g.drawString(String.format("%s: +%d",m.getStat(m.getArmor(1).getPrimaryStat()).getShortName(),m.getArmor(1).getPrimaryValue()), 215, 400);
            g.drawString(String.format("%s: +%d",m.getStat(m.getArmor(1).getSecondaryStat()).getShortName(),m.getArmor(1).getSecondaryValue()), 215, 420);
        }catch(BattleEntity.EntityNullError e){
            g.drawString("No Slot 3 Armor", 215, 380);
            //g.drawString("No Slot 3 Armor", 215, 400);
            //g.drawString("No Slot 3 Armor", 215, 420);
        }
        //g.drawString(m.getWeapon().getName(), 15, 300);
    }
    public void equip(Item i){
        m.equip(i, getSelectorPosition());
    }
    public Equipment getSelectedEquipment(){
        if(getSelectorPosition()==0){
            return m.getWeapon();
        }else
            return m.getArmor(getSelectorPosition());
    }
    
    @Override
    public void upEvent() {
        super.upEvent(); //To change body of generated methods, choose Tools | Templates.
        confirmMenuPosition();
    }

    @Override
    public void downEvent() {
        super.downEvent(); //To change body of generated methods, choose Tools | Templates.
        confirmMenuPosition();
    }
    
    public static void main(String[] args) {
        Menu.main(args);
    }
}

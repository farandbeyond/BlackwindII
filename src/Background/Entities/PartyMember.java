/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Entities;

import Background.Items.*;
import Background.Items.Item.ItemInvalidType;
import java.util.Random;

/**
 *
 * @author Connor
 */
public class PartyMember extends BattleEntity{
    int level;
    int totalExp;
    int expToLevel;
    Equipment[] equipment;
    public PartyMember(String name, String hp, String mp, String str, String dex, String vit, String ine, String res, String element, String levelData, String isDead, String actions, String equipment) {
        super(name, hp, mp, str, dex, vit, ine, res, element, isDead, actions);
        this.equipment = new Equipment[4];
        level = Integer.parseInt(levelData.split("-")[0]);
        totalExp = Integer.parseInt(levelData.split("-")[1]);
        expToLevel = Integer.parseInt(levelData.split("-")[2]);
        for(int i=0;i<4;i++){
            if(!equipment.split("-")[i+1].equals("null"))
                equip((Equipment)Items.Load(Integer.parseInt(equipment.split("-")[i+1]), 1),i);
        }
    }

    public PartyMember(String name, int baseHP, double hpGrowth, int baseMP, double mpGrowth, int baseStr, double strGrowth, int baseDex, double dexGrowth, int baseVit, double vitGrowth, int baseInt, double intGrowth, int baseRes, double resGrowth, int element, int level, int totalExp){
        super(name, baseHP, hpGrowth, baseMP, mpGrowth, baseStr, strGrowth, baseDex, dexGrowth, baseVit, vitGrowth, baseInt, intGrowth, baseRes, resGrowth, element);
        equipment = new Equipment[4];
        this.level = level;
        this.totalExp = totalExp;
        findXpToLevel();
    }
    public void findXpToLevel(){
        expToLevel=0;
        for(int i=1;i<level+1;i++){
            for(int w=1;w<i+1;w++){
                expToLevel+=100*w;
            }
        }
    }
    //equipment controlling
    public void equip(Item i, int slot){
        try{
            Equipment e = (Equipment)i;
            if(slot>3||slot<0)
                throw new EntityBadIndex(String.format("Equipment slot %d doesnt exist",slot));
            if(e.getClass()==Weapon.class){
                Weapon w =(Weapon) e;
                if(slot!=0){
                    throw new Item.ItemInvalidType(String.format("Cannot equip weapon %s to an armor slot: %d",w.getName(),slot));
                }
                e.equip(this);
            }
            if(e.getClass()==Armor.class){
                Armor a = (Armor)e;
                if(slot==0){
                    throw new Item.ItemInvalidType(String.format("Cannot equip armor %s to the weapon slot", a.getName()));
                }
                e.equip(this);
            }
            equipment[slot] = e;
        }catch(ClassCastException e){
            throw new Item.ItemInvalidType(String.format("Item %s is not a form of equipment",i.getName()));
        }
    }
    public Equipment unequip(int slot){
        if(equipment[slot]==null){
            throw new EntityNullError("No weapon equipped");
        }else{
            if(slot==0){
                Weapon weap = ((Weapon)equipment[0]);
                weap.unequip();
                equipment[0]=null;
                return weap;
            }else{
                Armor arm = ((Armor)equipment[slot]);
                arm.unequip();
                equipment[slot]=null;
                return arm;
            }
        }
    }

    @Override
    public int getElement() {
        if(equipment[0]==null)
            return super.getElement();
        return ((Weapon)equipment[0]).getElement();
    }
    
    //level controlling
    public String gainExp(int xpGained){
        totalExp+=xpGained;
        if(totalExp>=expToLevel){
            levelUp();
            findXpToLevel();
            return getName()+" levelled up to level "+level;
        }
        return getName()+" gained "+xpGained+" exp";
    }
    public void levelUp(){
        try{
            BattleEntity.rand.setSeed(System.currentTimeMillis());
            Thread.sleep(12);
            level++;
            for(int i=0;i<7;i++){
                super.getStat(i).levelUp();
            }
        }catch(InterruptedException e){
            System.out.println(e);
        }
    }
    //gets
    public int getLevel(){return level;}
    public int getExp(){return totalExp;}
    public int getExpToLevel(){return expToLevel;}
    public Weapon getWeapon(){
        try{
            equipment[0].getId();
            return (Weapon)equipment[0];
        }catch(NullPointerException e){
            throw new EntityNullError("No weapon equipped");
        }
    }
    public Armor getArmor(int slot){
        if(slot>3||slot<1){
            throw new EntityBadIndex(String.format("No armor at slot %d",slot));
        }
        try{
            equipment[slot].getId();
            return (Armor)equipment[slot];
        }catch(NullPointerException e){
            throw new EntityNullError(String.format("Armor in slot %d is null",slot));
        }        
    }
    public Equipment getEquipment(int slot){return equipment[slot];}
    
    public static void main(String[] args){
        System.out.println("Test 01");
        System.out.println("build");
        PartyMember wilson = new PartyMember("Wilson",20,2.5,10,1.25,10,1.9,5,0.5,10,1.9,3,0.7,8,1.7,0,1,0);
        System.out.println("Test 01");
        System.out.println("check level");
        System.out.println(wilson.getLevel());
        System.out.println(wilson.getExpToLevel());
        System.out.println(wilson.getExp());
        System.out.println("Test 01");
        System.out.println("level up");
        wilson.printAllStats();
        wilson.gainExp(100);
        wilson.printAllStats();
        System.out.println(wilson.getExp());
        System.out.println(wilson.getExpToLevel());
    }
}

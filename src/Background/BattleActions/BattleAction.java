
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.Entities.BattleEntity;
import Background.Entities.Element;
import Background.Entities.PartyMember;
import java.util.Random;

/**
 *
 * @author Connor
 */
public abstract class BattleAction {
    public static Random rand = new Random();
    private int skillID;
    private BattleEntity caster;
    private String name,description;
    private int element;
    public abstract String execute(BattleEntity target);
    public abstract int getCost();
    public int getElement(){return element;}
    public abstract boolean targetsAllies();
    public abstract boolean canExecuteOn(BattleEntity target);
    public boolean canExecute(){
        return caster.canCast(this);
    }
    
    public abstract int getInflictedDamage(BattleEntity target,int damage);
    
    public int elementHandler(BattleEntity target,int damage){
        if(Element.handler(element, target.getElement())<0)
            damage*=Element.handler(element, target.getElement());
        else{
            damage*=Element.handler(element, target.getElement());
            if(damage<=0)
                damage = 1;
        }
        return damage;
    }
    public void useMp(){
        getCaster().useMp(getCost());
    }
    
    public BattleAction(int skillID,BattleEntity caster,String name, String description, int element){
        this.skillID = skillID;
        this.name=name;
        this.description=description;
        this.caster=caster;
        this.element = element;
    }
    //gets
    public BattleEntity getCaster(){return caster;}
    public int getCasterStat(int statID){return caster.getStat(statID).getStat();}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public int getID(){return skillID;}
    public int getWeaponDamage(int damage){
        if(getCaster().getClass()==PartyMember.class){
            try{
                damage+=((PartyMember)getCaster()).getWeapon().getDamage();
            }catch(BattleEntity.EntityNullError e){
                
            }
            
        }
        return damage;
    }
    public void setCaster(BattleEntity caster){this.caster = caster;}
    public BattleAction(BattleEntity caster){
        this.caster = caster;
    }
    public String toString(){
        return String.format("%s, cast by %s", name,caster.getName());
    }
}

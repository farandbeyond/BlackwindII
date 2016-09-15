
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
    //overridden by each subclass. this method is called when the caster acts in battle. (a damage ability will deal damage, a buff skill will give a buff, etc
    //the target argument is the BattleEntity targeted by the skill.
    public abstract String execute(BattleEntity target);
    public abstract int getCost();
    //overridden by each subclass, tells wether the ability is helpful or harmful. return true if it helps/heals, and false if it damages or debuffs
    public abstract boolean targetsAllies();
    //checks to see if the ability would have an effect on the target. (eg. attacking a dead target would have no effect)
    public abstract boolean canExecuteOn(BattleEntity target);
    //gets the numerical value of the damage/heal from using this ability on the target.
    public abstract int getInflictedDamage(BattleEntity target,int damage);
    
    public BattleAction(BattleEntity caster){
        this.caster = caster;
    }
    public BattleAction(int skillID,BattleEntity caster,String name, String description, int element){
        this.skillID = skillID;
        this.name=name;
        this.description=description;
        this.caster=caster;
        this.element = element;
    }
    //this returns the damage after being multiplied by the Element.handler function.
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
    //causes the caster to lose mp equal to the abilty cost.
    public void useMp(){
        getCaster().useMp(getCost());
    }
    //checks if the caster can use this skill. returns true if the caster has enough MP to execute
    public boolean canExecute(){
        return caster.canCast(this);
    }
    //gets
    public int getID(){return skillID;}
    public BattleEntity getCaster(){return caster;}
    public int getCasterStat(int statID){return caster.getStat(statID).getStat();}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public int getElement(){return element;}
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
    //not essential to this program, was used in an older version
    public String toString(){
        return String.format("%s, cast by %s", name,caster.getName());
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.Entities.BattleEntity;
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
    public abstract String execute(BattleEntity target);
    public abstract int getCost();
    public abstract int getElement();
    public abstract boolean targetsAllies();
    public abstract boolean canExecute(BattleEntity target);
    public BattleAction(int skillID,BattleEntity caster,String name, String description){
        this.skillID = skillID;
        this.name=name;
        this.description=description;
        this.caster=caster;
    }
    //gets
    public BattleEntity getCaster(){return caster;}
    public int getCasterStat(int statID){return caster.getStat(statID).getStat();}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public int getID(){return skillID;}
    public void setCaster(BattleEntity caster){this.caster = caster;}
    public BattleAction(BattleEntity caster){
        this.caster = caster;
    }
    public String toString(){
        return String.format("%s, cast by %s", name,caster.getName());
    }
}

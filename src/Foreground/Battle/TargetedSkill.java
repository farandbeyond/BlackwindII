/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Battle;

import Background.BattleActions.BattleAction;
import Background.Entities.BattleEntity;

/**
 *
 * @author Connor
 */
public class TargetedSkill {
    BattleAction b;
    BattleEntity target;
    public TargetedSkill(){}
    public TargetedSkill(BattleAction action, BattleEntity t){
        target = t;
        b = action;
    }
    public String execute(){
        if(b.canExecuteOn(target))
            return b.execute(target);
        else
            return String.format("%s didn't act",b.getCaster().getName());
    }
    public boolean isCastBy(BattleEntity e){
        return b.getCaster()==e;
    }
    public int getCasterDex(){return b.getCasterStat(BattleEntity.DEX);}
    public BattleAction getSkill(){return b;}
    public void setAction(BattleAction b) {
        this.b = b;
    }

    public void setTarget(BattleEntity target) {
        this.target = target;
    }
    public BattleEntity getCaster(){return b.getCaster();}
    
}

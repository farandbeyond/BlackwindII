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
public class TargettedSkill {
    BattleAction b;
    BattleEntity target;
    public TargettedSkill(BattleAction action, BattleEntity t){
        target = t;
        b = action;
    }
    public String execute(){
        if(b.canExecute(target))
            return b.execute(target);
        else
            return String.format("%s didn't act",b.getCaster().getName());
    }
}

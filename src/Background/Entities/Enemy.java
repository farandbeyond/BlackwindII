/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Entities;

import Background.BattleActions.BattleAction;
import Foreground.EnemyAI;

/**
 *
 * @author Connor
 */
public class Enemy extends BattleEntity{
    //BattleAction listOfActions
    int xpGiven;
    int goldGiven;
    EnemyAI ai;
    //Item itemDropped;
    public Enemy(String name, int baseHP, int baseMP, int baseStr, int baseDex, int baseVit, int baseInt, int baseRes, int element, int xpGiven, int goldGiven) {
        super(name, baseHP, 1.0, baseMP, 1.0, baseStr, 1.0, baseDex, 1.0, baseVit, 1.0, baseInt, 1.0, baseRes, 1.0, element);
        this.xpGiven = xpGiven;
        this.goldGiven = goldGiven;
    }
    public void giveAI(EnemyAI ai){
        ai.setEnemy(this);
        this.ai = ai;
    }

    
    public BattleAction getSkill() {
        return ai.getAction();
    }
    
    
}

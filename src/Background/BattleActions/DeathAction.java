/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.Entities.BattleEntity;
import Background.Entities.Element;

/**
 *
 * @author Connor
 */
public class DeathAction extends BattleAction{

    public DeathAction(BattleEntity caster) {
        super(0,caster, "Death", "The action a dead unit takes",Element.NEUTRAL);
    }

    @Override
    public String execute(BattleEntity target) {
        return String.format("%s came back from the dead",getCaster());
    }

    @Override
    public boolean canExecuteOn(BattleEntity target) {
        return !getCaster().isDead();
    }

    @Override
    public int getInflictedDamage(BattleEntity target, int damage) {
        return 0;
    }
    

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public int getElement() {
        return 0;
    }

    @Override
    public boolean targetsAllies() {
        return true;
    }
    
}

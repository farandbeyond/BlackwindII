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

    public DeathAction(int id,BattleEntity caster, String name, String description) {
        super(id,caster, name, description,Element.NEUTRAL);
    }

    @Override
    public String execute(BattleEntity target) {
        return String.format("%s came back from the dead",getCaster());
    }

    @Override
    public boolean canExecute(BattleEntity target) {
        return true;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.Entities.BattleEntity;

/**
 *
 * @author Connor
 */
public abstract class Spell extends BattleAction{
    private int cost;
    public Spell(int id,BattleEntity e, String name, String description,int cost,int element){
        super(id,e,name, description,element);
        this.cost=cost;
    }
    //overridden by its subclasses, cast is the "execute" funtion of spells. there is no essential difference at this moment, except thematically.
    public abstract String cast(BattleEntity target);
    public abstract boolean targetsAllies();
    
    @Override
    public abstract boolean canExecuteOn(BattleEntity target);
    
    @Override
    public String execute(BattleEntity target) {
        return cast(target);
    }
    //gets
    public int getCost(){return cost;}
}

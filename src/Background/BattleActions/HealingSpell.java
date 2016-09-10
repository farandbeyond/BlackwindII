/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.Entities.*;
import java.util.Random;

/**
 *
 * @author Connor
 */
public class HealingSpell extends Spell{
    private final int baseHeal, rollHeal;
    private final boolean revives;
    //Random rand;
    public HealingSpell(int id,BattleEntity caster,String name, String description, int baseHeal, int rollHeal,boolean revives, int cost){
        super(id,caster,name,description,cost,Element.NEUTRAL);
        this.baseHeal=baseHeal;
        this.rollHeal=rollHeal;
        this.revives=revives;
        //rand = new Random();
    }
    public HealingSpell(int id,String name, String description,int baseHeal, int rollHeal,boolean revives, int cost){
        super(id,null,name,description,cost,Element.NEUTRAL);
        this.baseHeal=baseHeal;
        this.rollHeal=rollHeal;
        this.revives=revives;
        //rand = new Random();
    }

    @Override
    public boolean canExecute(BattleEntity target) {
        if(!target.isDead()&&!revives&&getCaster().canCast(this)&&target.isDamaged())
            return true;
        if(revives&&target.isDead()&&getCaster().canCast(this))
            return true;
        return false;
    }
    
    @Override
    public String cast(BattleEntity target) {
        int heal = 0;
        rand.setSeed(System.currentTimeMillis());
        useMp();
        heal = getInflictedDamage(target,heal);
        if(revives){
            target.raise(heal);
            return String.format("%s raised %s for %d with %s", getCaster().getName(),target.getName(),heal,getName());
        }
        else{
            target.heal(heal);
            return String.format("%s healed %s for %d with %s", getCaster().getName(),target.getName(),heal,getName());
        }
    }

    @Override
    public int getInflictedDamage(BattleEntity target, int heal) {
        heal+=getCaster().getStat(BattleEntity.INT).getStat()/3;
        heal+=baseHeal+rand.nextInt(rollHeal);
        return heal;
    }
    
    
    //gets
    public int getBaseHeal(){return baseHeal;}
    public int getRollHeal(){return rollHeal;}
    public int getMaxHeal(){return baseHeal+rollHeal;}
    public boolean getRevives(){return revives;}
    public boolean targetsAllies(){return true;}
}

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
public class DamageSpell extends Spell{
    private final int baseDamage, rollDamage;
    Random rand;
    public DamageSpell(int id,BattleEntity caster,String name, String description, int baseDamage, int rollDamage, int element, int cost){
        super(id,caster,name,description,cost,element);
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        //this.element=element;
        rand = new Random();
    }
    public DamageSpell(int id,String name, String description,int baseDamage, int rollDamage, int element, int cost){
        super(id,null,name,description,cost,element);
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        //this.element=element;
        rand = new Random();
    }
    @Override
    public String cast(BattleEntity target) {
        int damage = 0;
        rand.setSeed(System.currentTimeMillis());
        useMp();
        damage = getInflictedDamage(target,damage);
        elementHandler(target,damage);
        target.damage(damage);
        return String.format("%s dealt %d damage to %s with %s", getCaster().getName(),damage, target.getName(), getName());
    }

    @Override
    public int getInflictedDamage(BattleEntity target,int damage) {
        damage+=getCaster().getStat(BattleEntity.INT).getStat()/2;
        //System.out.println("Stat buffed Damage:"+damage);
        damage+=baseDamage+rand.nextInt(rollDamage);
        //System.out.println("Skill buffed Damage:"+damage);
        damage-=target.getStat(BattleEntity.RES).getStat();
        //System.out.println("Enemy Resistance Debuffed Damage:"+damage);
        return damage;
    }

    @Override
    public boolean canExecuteOn(BattleEntity target) {
        if(!target.isDead()&&getCaster().canCast(this))
            return true;
        return false;
    }
    
    //gets
    public int getBaseDamage(){return baseDamage;}
    public int getRollDamage(){return rollDamage;}
    public int getMaxDamage(){return baseDamage+rollDamage;}
    //public int getElement(){return element;}
    public boolean targetsAllies(){return false;}
    
}

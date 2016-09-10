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
public class PhysicalAction extends BattleAction{
    private int baseDamage, rollDamage;
    private Random rand;
    private int element;
    private int damageStat, resistStat;
    
    public PhysicalAction(int id,BattleEntity caster,String name, String description,int baseDamage, int rollDamage, int damageStat, int resistStat, int element){
        super(id,caster,name,description,element);
        this.damageStat=damageStat;
        this.resistStat=resistStat;
        this.baseDamage=baseDamage;
        this.rollDamage = rollDamage;
        this.element=element;
        rand = new Random();
    }
    public PhysicalAction(int id,String name, String description,int baseDamage, int rollDamage, int damageStat, int resistStat, int element){
        super(id,null,name,description,element);
        this.damageStat=damageStat;
        this.resistStat=resistStat;
        this.baseDamage=baseDamage;
        this.rollDamage = rollDamage;
        this.element=element;
        rand = new Random();
    }

    @Override
    public boolean canExecute(BattleEntity target) {
        if(!target.isDead())
            return true;
        return false;
    }
    
    public String execute(BattleEntity target){
        rand.setSeed(System.currentTimeMillis());
        int damage = 0;
        //System.out.println("Starting Damage:"+damage);
        damage = getWeaponDamage(damage);
        damage = getInflictedDamage(target,damage);
        damage = elementHandler(target, damage);
        //System.out.println("Element Damage Multiplier:"+damage);
        target.damage(damage);
        return String.format("%s dealt %d damage to %s with %s", getCaster().getName(),damage, target.getName(), getName());
    }

    @Override
    public int getInflictedDamage(BattleEntity target, int damage) {
        //System.out.println("Weapon Damage:"+damage);
        damage+=baseDamage;
        //System.out.println("Skill Base Damage:"+damage);
        damage+=rand.nextInt(rollDamage);
        //System.out.println("Skill Random Damage:"+damage);
        damage+=getCaster().getStat(damageStat).getStat()/2;
        //System.out.println("Casting Stat Damage:"+damage);
        damage-=target.getStat(resistStat).getStat()/2;
        //System.out.println("Enemy Resistance Reduction:"+damage);
        return damage;
    }
    
    //gets
    public int getCost(){return 0;}
    public int getBaseDamage(){return baseDamage;}
    public int getRollDamage(){return rollDamage;}
    public int getMaxDamage(){return baseDamage+rollDamage;}
    public int getElement(){return element;}
    public int getDamageStat(){return damageStat;}
    public int getResistStat(){return resistStat;}
    public boolean targetsAllies(){return false;}
}
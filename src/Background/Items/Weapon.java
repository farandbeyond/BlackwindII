/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.Entities.BattleEntity;

/**
 *
 * @author Connor
 */
public class Weapon extends Equipment{
    int baseDamage;
    int rollDamage;
    int statToIncrease;
    int increase;
    int element;

    public Weapon(int id, String name, String description,int baseDamage, int rollDamage, int statToIncrease, int increase,int element,int quantity, int maxQuantity, int shopValue) {
        super(id, name, description, quantity, maxQuantity, shopValue);
        this.baseDamage = baseDamage;
        this.rollDamage = rollDamage;
        this.statToIncrease = statToIncrease;
        this.increase = increase;
        this.element = element;
    }
    
    @Override
    public String use(BattleEntity target) {
        //return "No Effect";
        throw new ItemCannotBeUsed("Weapons have no usable effect");
    }
    @Override
    public boolean canUse(BattleEntity target){
        return false;
    }

    @Override
    public void equip(BattleEntity target) {
        setEquipper(target);
        getEquipper().increaseStat(statToIncrease,increase);
    }

    @Override
    public void unequip() {
        getEquipper().decreaseStat(statToIncrease, increase);
        setEquipper(null);
    }
    
    public int getElement(){return element;}
    public int getDamage(){
        return baseDamage + rand.nextInt(rollDamage);
    }
    public int getBaseDamage(){return baseDamage;}
    public int getRollDamage(){return rollDamage;}
    public int getMaxDamage(){return baseDamage+rollDamage;}
    public int getStatToIncrease(){return statToIncrease;}
    public int getStatIncrease(){return increase;}
    
}

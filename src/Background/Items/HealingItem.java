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
public class HealingItem extends Item{
    public static final int HP=0, MP=1, HPMP=2;
    int healValue;
    int healType;
    boolean revives;

    public HealingItem(int id, String name, String description, int healValue, int healType, boolean revives, int quantity, int maxQuantity, int shopValue) {
        super(id, name, description, quantity, maxQuantity, shopValue);
        this.healValue = healValue;
        this.healType = healType;
        this.revives = revives;
    }
    @Override
    public boolean canUseOn(BattleEntity target){
        if(revives){
            if(target.isDead())
                return true;
            return false;
        }else{
            if(target.isDead())
                return false;
            if(target.isDamaged()&&healType!=MP)
                return true;
            else if(target.isBelowMaxMana()&&healType!=HP)
                return true;
            else
                return false;
        }
    }
    public boolean canUse(){return true;}
    @Override
    public String use(BattleEntity target) {
        if(getQuantity()==0)
            throw new ItemCannotBeUsed("Using item "+getName()+" at Quantity 0");
        reduceQuantity(1);
        switch(healType){
            case HPMP: target.heal(healValue);target.regainMp(healValue);return String.format("%s refreshed for %d hp and %d mp", target.getName(),healValue,healValue);
            case HP:
                if(revives){
                    target.raise(healValue);return String.format("%s raised for %d hp", target.getName(),healValue);
                }
                target.heal(healValue);
                return String.format("%s healed for %d health", target.getName(),healValue);
            case MP:
                target.regainMp(healValue);
                return String.format("%s refreshed for %d mp", target.getName(),healValue);
        }
        throw new ItemInvalidType("Healing item invalid healtype: "+healType);
    }
    public int getHealValue(){return healValue;}
    public int getHealType(){return healType;}
    public boolean getRevives(){return revives;}
    
}

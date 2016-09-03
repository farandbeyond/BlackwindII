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
public class Armor extends Equipment{
    private int primaryStat,secondaryStat;
    private int primaryValue,secondaryValue;
    public Armor(int id, String name, String description,int primaryStat, int primaryIncrease, int secondaryStat, int secondaryIncrease, int quantity, int maxQuantity, int shopValue){
        super(id,name,description,quantity,maxQuantity,shopValue);
        this.primaryStat=primaryStat;
        this.primaryValue=primaryIncrease;
        this.secondaryStat=secondaryStat;
        this.secondaryValue=secondaryIncrease;
    }
    @Override
    public boolean canUse(BattleEntity target){
        return false;
    }
    @Override
    public void equip(BattleEntity target) {
        this.setEquipper(target);
        getEquipper().increaseStat(primaryStat, primaryValue);
        getEquipper().increaseStat(secondaryStat, secondaryValue);
    }
    @Override
    public void unequip() {
        getEquipper().decreaseStat(primaryStat, primaryValue);
        getEquipper().decreaseStat(secondaryStat, secondaryValue);
        this.setEquipper(null);
    }
    @Override
    public String use(BattleEntity target) {
        return "No use";
    }
    //gets
    public int getPrimaryStat(){return primaryStat;}
    public int getSecondaryStat(){return secondaryStat;}
    public int getPrimaryValue(){return primaryValue;}
    public int getSecondaryValue(){return secondaryValue;}
    
}

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
public abstract class Equipment extends Item{
    BattleEntity equipper;
    public Equipment(int id, String name, String description, int quantity, int maxQuantity, int shopValue) {
        super(id, name, description, quantity, maxQuantity, shopValue);
    }
    @Override
    public abstract boolean canUseOn(BattleEntity target);
    @Override
    public abstract String use(BattleEntity target);
    public abstract void equip(BattleEntity target);
    public abstract void unequip();
    
    
    public void setEquipper(BattleEntity e){
        equipper = e;
    }
    public BattleEntity getEquipper(){return equipper;}
}

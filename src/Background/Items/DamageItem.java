/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.Entities.Element;
import Background.Entities.BattleEntity;

/**
 *
 * @author Connor
 */
public class DamageItem extends Item{
    int damage;
    int element;

    public DamageItem(int id, String name, String description, int damage, int element, int quantity, int maxQuantity, int shopValue) {
        super(id, name, description, quantity, maxQuantity, shopValue);
        this.damage = damage;
        this.element = element;
    }
    @Override
    public boolean canUseOn(BattleEntity target){
        if(!target.isDead())
            return true;
        return false;
    }
    public boolean canUse(){return true;}
    @Override
    public String use(BattleEntity target) {
        if(getQuantity()==0)
            throw new ItemCannotBeUsed("Item "+getName()+" used at 0 quantity");
        reduceQuantity(1);
        int totaldamage = damage;
        totaldamage*=Element.handler(element, target.getElement());
        if(Element.handler(element, target.getElement())>0)
            if(totaldamage<=0)
                totaldamage = 1;
        target.damage(totaldamage);
        return String.format("%d %s damage dealt to %s",totaldamage, Element.getElementName(element), target.getName());
    }
    public int getDamage(){return damage;}
    public int getElement(){return element;}
    
}

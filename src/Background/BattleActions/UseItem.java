/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.Entities.BattleEntity;
import Background.Items.HealingItem;
import Background.Items.Item;

/**
 *
 * @author Connor
 */
public class UseItem extends BattleAction{

    Item item;
    public UseItem(int id,BattleEntity caster, Item i){
        super(id,caster,i.getName(),"Uses the item");
        item = i;
    }

    @Override
    public boolean canExecute(BattleEntity target) {
        if(item.getQuantity()>0&&!target.isDead())
            return true;
        if(item.getClass()==HealingItem.class)
            if(((HealingItem)item).getRevives()&&item.getQuantity()>0)
                return true;
        return false;
    }
    
    @Override
    public String execute(BattleEntity target) {return item.use(target);}
    @Override
    public int getCost() {return 0;}
    @Override
    public int getElement() {return 0;}
    public boolean targetsAllies(){
        if(item.getId()>=0&&item.getId()<=99){
            return true;
        }else{
            return false;
        }
    }
}

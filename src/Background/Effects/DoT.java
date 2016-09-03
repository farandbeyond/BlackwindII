/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Effects;

import Background.Entities.Element;
import Background.Entities.BattleEntity;

/**
 *
 * @author Connor
 */
public class DoT extends Effect{
    int dotDamage;
    int element;
    public DoT(int dotDamage, int element, String name, String source, int duration) {
        super(name, source, duration);
        this.dotDamage = dotDamage;
        this.element = element;
    }
    
    @Override
    public void assign(BattleEntity e) {
        
    }

    @Override
    public void remove(BattleEntity e) {
    }

    @Override
    public void onTick(BattleEntity e) {
        int damage = dotDamage;
        damage*=Element.handler(element, e.getElement());
        e.damage(damage);
        setDuration(getDuration()-1);
        if(getDuration()<0)
           setDuration(0);
    }
    public int getElement(){return element;}
    public int getDamage(){return dotDamage;}
    
}

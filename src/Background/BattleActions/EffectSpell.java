/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.Effects.*;
import Background.Entities.*;


/**
 *
 * @author Connor
 */
public class EffectSpell extends Spell{
    private Effect effect;
    
    public EffectSpell(int id,BattleEntity caster,String name, String description,int cost, Effect effect){
        super(id,caster,name,description,cost);
        this.effect=effect;
    }
    public EffectSpell(int id,String name, String description,int cost, Effect effect){
        super(id,null,name,description,cost);
        this.effect=effect;
    }
    @Override
    public String cast(BattleEntity target) {
        getCaster().useMp(getCost());
        if(effect.getClass()==Buff.class){
            target.giveEffect(Effect.loadStatAltering(Effect.BUFF,effect.getName(),getName(),effect.getDuration(),((Buff)effect).getStat(),((Buff)effect).getIncrease()));
            return String.format("%s bolstered %s's %s",getCaster().getName(),target.getName(),getCaster().getStat(((Buff)effect).getStat()).getShortName());
        }else if(effect.getClass()==Debuff.class){
            target.giveEffect(Effect.loadStatAltering(Effect.DEBUFF, effect.getName(), getName(), effect.getDuration(), ((Debuff)effect).getStat(), ((Debuff)effect).getDecrease()));
            return String.format("%s reduced %s's %s",getCaster().getName(),target.getName(),getCaster().getStat(((Debuff)effect).getStat()).getShortName());
        }
        return "something went wrong";
    }
    //gets
    public Effect getEffect(){return effect;}
    public int getElement(){return Element.NEUTRAL;}
    public boolean targetsAllies(){
        if(effect.getClass()==Buff.class){
            return true;
        }else{
            return false;        
        }
    }
}

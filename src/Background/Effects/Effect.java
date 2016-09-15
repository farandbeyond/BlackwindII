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
public abstract class Effect {
    private String name, source;
    private int duration;
    public Effect(String name, String source, int duration){
        this.name=name;
        this.source=source;
        this.duration=duration;
    }
    //abstracts
    //this is called when an effect is given to a target. (a buff will increase a stat, etc.)
    public abstract void assign(BattleEntity e);
    //this is called when an effect is removed from a target, either by duration 0, or end of battle. (a stat will return to normal, etc)
    public abstract void remove(BattleEntity e);
    //this is called at the end of each combat round in battle. not all effects have an on tick effect (a DoT will damage on tick, but a buff wil do nothing but reduce duration)
    public abstract void onTick(BattleEntity e);
    //public abstract int getType();
    //gets
    public String getName(){return name;}
    public String getSource(){return source;}
    public int getDuration(){return duration;}
    public void setDuration(int duration){this.duration=duration;}
    
    public static final int BUFF=0, DEBUFF=1;
    //this funtions as the "loader" class, as seen in BattleActionLoader, and ItemLoader
    public static Effect loadStatAltering(int type, String name,String source, int duration, int stat, double percIncrease){
        switch(type){
            case BUFF:
                return new Buff(name,source, duration, stat, percIncrease);
            case DEBUFF:
                return new Debuff(name, source, duration, stat, percIncrease);
        }
        return null;
    }
    public static Effect loadDamageOverTime(int damage, String name, String source, int duration, int element){
        return new DoT(damage,element,name,source,duration);
    }
}

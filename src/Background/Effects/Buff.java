/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Effects;

import Background.Entities.BattleEntity;

/**
 *
 * @author Connor
 */
public class Buff extends Effect{
    private int stat;
    private double increase;
    private int savedIncrease;
    
    public Buff(String name, String source, int duration, int stat, double increase){
        super(name,source,duration);
        this.stat=stat;
        this.increase=increase;
        savedIncrease=0;
    }
    @Override
    public void assign(BattleEntity e) {
        savedIncrease = (int)(e.getStat(stat).getBase()*increase);
        //System.out.println(savedIncrease);
        e.increaseStat(stat,savedIncrease);
    }
    @Override
    public void remove(BattleEntity e) {
        e.decreaseStat(stat, savedIncrease);
        //e.removeEffect(this);
    }
    @Override
    public void onTick(BattleEntity e) {
       setDuration(getDuration()-1);
       if(getDuration()<0)
           setDuration(0);
    }
    //gets
    public int getStat(){return stat;}
    public double getIncrease(){return increase;}
    public int getSavedIncrease(){
        if(savedIncrease!=0)
            return savedIncrease;
        return 0;
    }
}

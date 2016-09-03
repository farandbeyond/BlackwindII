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
public class Debuff extends Effect{
    private int stat;
    private double decrease;
    private int savedDecrease;
    public Debuff(String name, String source, int duration, int stat, double decrease){
        super(name,source,duration);
        this.stat=stat;
        this.decrease=decrease;
    }
    @Override
    public void assign(BattleEntity e) {
        savedDecrease = (int)(e.getStat(stat).getBase()*decrease);
        //System.out.println(savedDecrease);
        e.decreaseStat(stat,savedDecrease);
    }
    @Override
    public void remove(BattleEntity e) {
        e.increaseStat(stat, savedDecrease);
        //e.removeEffect(this);
    }
    @Override
    public void onTick(BattleEntity e) {
       setDuration(getDuration()-1);
       if(getDuration()<0)
           setDuration(0);
       //if(duration==0){
       //    remove(e);
       //}
    }
    //gets
    public int getStat(){return stat;}
    public double getDecrease(){return decrease;}
    public int getSavedDecrease(){
        if(savedDecrease!=0)
            return savedDecrease;
        return 0;
    }
}

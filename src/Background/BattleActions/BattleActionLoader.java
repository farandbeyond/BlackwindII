/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.Entities.*;
import Background.Effects.*;
import Background.Items.Item;

/**
 *
 * @author Connor
 */
public class BattleActionLoader {
    public static final int 
            FIRE=1, WATER=2, EARTH=3,WIND=4, LIGHT=5, DARK =6,
            CURE=100,RAISE=101,
            ATTACK=200, SLICE=201,
            BRAVERY=300,SHELTER=301, FRAILTY=302, SEAR=303,
            RAISEGUARD = 400, ENRAGE=401;

    public static BattleAction loadAction(int actionID){
        switch(actionID){
            case FIRE       :return new DamageSpell(actionID,"Kasai","deals 5-10 Fire damage",5,5,Element.FIRE,5);
            case WATER      :return new DamageSpell(actionID,"Mizu","deals 5-10 Water damage",5,5,Element.WATER,5);
            case EARTH      :return new DamageSpell(actionID,"Setchi","deals 5-10 Earth damage",5,5,Element.EARTH,5);
            case WIND       :return new DamageSpell(actionID,"Kaze","deals 5-10 Air damage",5,5,Element.AIR,5);
            case LIGHT      :return new DamageSpell(actionID,"Hikari","deals 5-10 Light damage",5,5,Element.LIGHT,8);
            case DARK       :return new DamageSpell(actionID,"Yami","deals 5-10 Dark damage",5,5,Element.DARK,8);
            case CURE       :return new HealingSpell(actionID,"Cure","Heals an ally for 10-20hp",10,10,false,5);
            case RAISE      :return new HealingSpell(actionID,"Raise","Revives an ally with 5hp",5,0,true,20);
            case ATTACK     :return new PhysicalAction(actionID,"Attack","The basic attack action everyone has",1,3,BattleEntity.STR,BattleEntity.VIT,Element.NEUTRAL);    
            case SLICE      :return new PhysicalAction(actionID,"Slice","A quick slash dealing 10-15dmg",10,5,BattleEntity.STR,BattleEntity.VIT,Element.NEUTRAL);
            case BRAVERY    :return new EffectSpell(actionID,"Bravery","25% Str buff",10,Effect.loadStatAltering(Effect.BUFF, "Bravery", "Bravery", 5, BattleEntity.STR, .25));
            case SHELTER    :return new EffectSpell(actionID,"Shelter","25% Vit buff",10,Effect.loadStatAltering(Effect.BUFF, "Shelter", "Shelter", 5, BattleEntity.VIT, .25));
            case FRAILTY    :return new EffectSpell(actionID,"Frailty","25% Vit debuff",10,Effect.loadStatAltering(Effect.DEBUFF, "Frailty", "Frailty", 5, BattleEntity.VIT, .25));
            case SEAR       :return new EffectSpell(actionID,"Sear","Deals 3 Fire damage per turn",10,Effect.loadDamageOverTime(3, "Sear", "Sear", 5, Element.FIRE));
//enemyExclusive
            case RAISEGUARD :return new SelfBuff(actionID,"Raise Guard","Defense increases",0,Effect.loadStatAltering(Effect.BUFF, "Raise Guard", "Self", 5, BattleEntity.VIT, .50));
            case ENRAGE     :return new SelfBuff(actionID,"Enrage"     ,"Strength increases",0,Effect.loadStatAltering(Effect.BUFF, "Enrage", "Pure Goblinoid Rage", 3, BattleEntity.STR, .25));
        }
        return null;
    }
    
    public static BattleAction loadAttack(BattleEntity caster){
        return new PhysicalAction(0,caster,"Attack","The basic attack action everyone has",1,3,BattleEntity.STR,BattleEntity.VIT,Element.NEUTRAL);
    }
    public static BattleAction noAction(BattleEntity caster){
        return new DeathAction(0,caster,"Dead","Caster was dead at round start");
    }
    public static BattleAction loadAttack(BattleEntity caster, int newBase, int newRoll){
        return new PhysicalAction(ATTACK,caster,"Attack","The basic attack action everyone has",newBase,newRoll,BattleEntity.STR,BattleEntity.VIT,Element.NEUTRAL);
    }
    public static BattleAction loadItemAction(BattleEntity caster, Item i){
        return new UseItem(0,caster,i);
    }
}

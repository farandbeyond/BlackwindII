/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.BattleActions;

import Background.Entities.*;
import Background.Effects.*;


/**
 *
 * @author Connor
 */
public class BattleActionTester {
    public static void main(String[] args){
        text("----------Test01----------");
        text("Creation of battle actions");
        BattleEntity tester = EntityLoader.loadPartyMember(0);
        BattleEntity target = EntityLoader.loadPartyMember(1);
        BattleAction[] actions = new BattleAction[10];
        actions[0]=new DamageSpell(0,tester,"Fireball","deals 15-20 firedamage",15,5,Element.FIRE,12);
        actions[1]=new HealingSpell(0,tester,"Cure","heals 10-15 hp",10,5,false,10);
        actions[2]=new PhysicalAction(0,tester,"Slice","A quick slash dealing 10-15dmg",10,5,BattleEntity.STR,BattleEntity.VIT,Element.NEUTRAL);
        actions[3]=new EffectSpell(0,tester,"Bravery","25% Str buff",0,Effect.loadStatAltering(Effect.BUFF,"Bravery",tester.getName(), BattleEntity.STR,5,0.25));
        text("----------Test02----------");
        text("Using battleActions on the target");
        tester.printAllStats();
        tester.printhpmp();
        System.out.println("-Fireball: Not Enough MP");
        if(tester.canCast((Spell)actions[0]))
            actions[0].execute(target);
        target.printhpmp();
        System.out.println("-Cure: Enough MP");
        if(tester.canCast((Spell)actions[1]))
            actions[1].execute(target);
        target.printhpmp();
        System.out.println("-Slice: No cost");
        //if(tester.canCast((Spell)actions[2]))
        //this throws "ClassCastException" for future reference
            actions[2].execute(target);
        target.printhpmp();
        System.out.println("-Bravery: Enough MP");
        if(tester.canCast((Spell)actions[3]))
            actions[3].execute(target);
        target.printhpmp();
        System.out.println("-");
        tester.printAllStats();
        tester.printhpmp();
        text("------");
        target.printAllStats();
        target.printhpmp();
        
        
    }
    public static void text(String text){
        System.out.println(text);
    }
}

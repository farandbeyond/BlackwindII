/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Effects;

import Background.Entities.BattleEntity;
import Background.Entities.EntityLoader;

/**
 *
 * @author Connor
 */
public class EffectTester {
    public static void main(String[] args) {
        test("init");
        BattleEntity e = EntityLoader.loadPartyMember(0);
        e.printAllStats();
        e.giveEffect(Effect.loadStatAltering(Effect.BUFF, "Attack up", "magic", 3, BattleEntity.STR, 0.50));
        test("check effect");
        e.printAllStats();
        System.out.println(e.getEffect(0).getName());
        System.out.println(e.getEffect(0).getDuration());
        System.out.println(((Buff)e.getEffect(0)).getSavedIncrease());
        test("now debuffs");
        e.giveEffect(Effect.loadStatAltering(Effect.DEBUFF, "defense down", "magic", 2, BattleEntity.VIT, 0.25));
        System.out.println(e.getEffect(1).getName());
        System.out.println(e.getEffect(1).getDuration());
        System.out.println(((Debuff)e.getEffect(1)).getSavedDecrease());
        e.printAllStats();
        test("now tick the effects down");
        e.tickAllEffects();
        e.tickAllEffects();
        System.out.println(e.getEffect(0).getName());
        System.out.println(e.getEffect(0).getDuration());
        //System.out.println(e.getEffect(1).getName());
        //System.out.println(e.getEffect(1).getDuration());     
        e.printAllStats();
        test("add a duplicate effect");
        e.giveEffect(Effect.loadStatAltering(Effect.BUFF, "Attack up", "magic", 3, BattleEntity.STR, 0.50));
        System.out.println(e.getEffect(0).getName());
        System.out.println(e.getEffect(0).getDuration());
        test("DoT effect");
        e.giveEffect(new DoT(5,0,"Poison","magic",2));
        e.printhpmp();
        e.tickAllEffects();
        e.printhpmp();
        e.tickAllEffects();
        e.printhpmp();
        e.tickAllEffects();
        e.printhpmp();
    }
    public static void test(String testName){
        test++;
        System.out.println(String.format("Test %d",test));
        System.out.println(testName);
        System.out.println("-----------------------------");
    }
    static int test = 0;
}

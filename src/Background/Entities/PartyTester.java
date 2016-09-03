/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Entities;

/**
 *
 * @author Connor
 */
public class PartyTester {
    static int test = 0;
    public static void main(String[] args) {
        test("setup");
        Party p = new Party(4);
        test("add members");
        p.addMember(EntityLoader.loadPartyMember(0));
        p.addMember(EntityLoader.loadPartyMember(1));
        p.addMember(EntityLoader.loadPartyMember(2));
        p.addMember(EntityLoader.loadPartyMember(3));
        for(PartyMember m:p.members){
            System.out.println(m.getName());
            m.printhpmp();
        }
        test("try errors");
        try{
            p.addMember(EntityLoader.loadPartyMember(0));
        }catch(Party.PartyFull e){
            System.out.println(e);
        }
        p.removeMember(0);
        try{
            p.getMember(3);
        }catch(Party.PartyNoMember e){
            System.out.println(e);
        }
        try{
            p.getMember(5);
        }catch(Party.PartyBadIndex e){
            System.out.println(e);
        }
        test("Try stuff with included members");
        System.out.println(p.getMembersStat(0, BattleEntity.STR).getStat());
        
    }
    public static void test(String testName){
        test++;
        System.out.println(String.format("Test %d",test));
        System.out.println(testName);
        System.out.println("-----------------------------");
    }
}

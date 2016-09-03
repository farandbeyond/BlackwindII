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
public class EntityLoader {
    public static BattleEntity loadEntity(int id){
        switch(id){
            //                              name       hp      mp       str     dex    vit    int    res    element       level
            case 0: return new PartyMember("Wilson",  20,2.5, 10,1.25, 10,1.9, 5,0.5,  10,1.9, 3,0.3,  8,1.7,Element.NEUTRAL,1,0);
            case 1: return new PartyMember("Huntress",15,1.9, 15,1.5,  12,1.2, 10,1.5, 6,1.2,  4,0.7,  5,0.6,Element.NEUTRAL,1,0);
            case 2: return new PartyMember("Ander",   16,1.5, 20,2.55, 2,0.6,  7,0.9,  4,1.1,  12,1.3, 9,1.1,Element.NEUTRAL,1,0);
            case 3: return new PartyMember("Matilda", 16,1.5, 20,2.55, 7,0.6,  8,1.5,  4,0.9,  12,1.2, 14,1.7,Element.NEUTRAL,1,0);
        }
        System.out.println("Invalid id");
        return null;
    }
    public static PartyMember loadPartyMember(int id){
        return (PartyMember)loadEntity(id);
    }
    
}

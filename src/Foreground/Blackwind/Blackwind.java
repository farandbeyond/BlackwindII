/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Blackwind;

import Background.Entities.Party;
import Background.Items.Inventory;
import Foreground.Battle.Battle;
import Foreground.Menu.Menu;

/**
 *
 * @author Connor
 */
public class Blackwind {
    Party party;
    Inventory inventory;
    int gold;
    
    static Menu menu;
    static Battle battle;
    public Blackwind(){
        party = new Party(4);
        inventory = new Inventory(15);
        menu = new Menu(party,inventory);
        battle = new Battle();
    }

    public Party getParty() {return party;}
    public Inventory getInventory() {return inventory;}
    public int getGold() {return gold;}
    
    
   
}

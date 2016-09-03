/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.Entities.Element;
import Background.Entities.BattleEntity;
import Background.Entities.EntityLoader;
import Background.Entities.PartyMember;
import Background.Items.Item.ItemCannotBeUsed;

/**
 *
 * @author Connor
 */
public class ItemTester {
    public static void main(String[] args) {
        System.out.println("test 01");
        System.out.println("build");
        PartyMember wilson = EntityLoader.loadPartyMember(0);
        System.out.println("test 02");
        System.out.println("healing item");
        Item potion = new HealingItem(0,"Potion","Heals target for 10hp",10,HealingItem.HP,false,1,20,150);
        wilson.damage(19);
        wilson.printhpmp();
        potion.use(wilson);
        wilson.printhpmp();
        System.out.println(potion.getQuantity());
        try{
            potion.use(wilson);
        }catch(ItemCannotBeUsed e){
            System.out.println(e);
        }
        System.out.println("test 03");
        System.out.println("damage item");
        Item firebomb = new DamageItem(1,"fire bomb","Deals 5 fire damage",5,Element.FIRE,2,20,200);
        wilson.printhpmp();
        firebomb.use(wilson);
        wilson.printhpmp();
        System.out.println("test 04");
        System.out.println("Weapon");
        Item sword = new Weapon(2,"sword","deals 4-6 damage, increases dex by 3",4,2,BattleEntity.DEX,3,0,1,5,300);
        wilson.printAllStats();
        wilson.equip((Equipment)sword, 0);
        wilson.printAllStats();
        wilson.unequip(0);
        wilson.printAllStats();
        System.out.println("test 05");
        System.out.println("Armor");
        Item armor = new Armor(3,"armor","Increases con by 2 and dex by 1",1,5,BattleEntity.VIT,2,BattleEntity.DEX,1,250);
        wilson.equip(armor, 1);
        wilson.printAllStats();
        wilson.unequip(1);
        wilson.printAllStats();
        System.out.println("test 06");
        System.out.println("inventory");
        System.out.println("----------------------------");
        Inventory inv = new Inventory(4);
        System.out.println("test 07");
        System.out.println("testing inventory error throws");
        inv.add(Items.Load(Items.POTION, 4));
        inv.add(Items.Load(Items.COTTONSHIRT, 4));
        inv.add(Items.Load(Items.MAGICCANE, 4));
        inv.add(Items.Load(Items.FIREBOMB, 4));
        try{
            inv.add(Items.Load(Items.LIGHTBOMB, 4));
        }catch(Inventory.InventoryTooFull e){
            System.out.println(e);
        }
        inv.printAllItems();
        try{
            inv.add(Items.Load(Items.POTION, 99));
        }catch(Inventory.InventoryTooFull e){
            System.out.println(e);
        }
        inv.add(Items.Load(Items.POTION, 94));
        
        inv.add(Items.Load(Items.POTION, 1));
        inv.printAllItems();
        inv.drop(inv.getLocationOf(Items.POTION), 10);
        inv.printAllItems();
        inv.useItem(inv.getLocationOf(Items.POTION), wilson);
        inv.printAllItems();
        inv.drop(0, 87);
        inv.printAllItems();
        inv.useItem(0, wilson);
        inv.printAllItems();
    }
}

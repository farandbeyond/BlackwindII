/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.Entities.Element;
import Background.Entities.BattleEntity;

/**
 *
 * @author Connor
 */
public class Items {
    public static final int 
            POTION=0, ETHER=1, ASH=2, REJUV=3,
            FIREBOMB=100,WINDBOMB=101,WATERBOMB=102,EARTHBOMB=103,LIGHTBOMB=104,DARKBOMB=105,
            WOODSWORD=200,MAGICCANE=201,
            COTTONSHIRT=300;
    public static Item Load(int id, int quantity){
        switch(id){
            //healing item:               id,    name,             description, healamount,healtype,revives,quantity,maxquantity,shopvalue
            case 0:return new HealingItem(id,"Potion","Heals 20hp to a target", 20,  HealingItem.HP,  false,quantity,99,50);
            case 1:return new HealingItem(id,"Ether", "Heals 20mp to a target", 20,  HealingItem.MP,  false,quantity,99,100);
            case 2:return new HealingItem(id,"Magic Ash","Revives a target with 10hp",10,HealingItem.HP,true,quantity,99,1000);
            case 3:return new HealingItem(id,"Rejuvi","Rafreshes a target for 15 hp and mp",15,HealingItem.HPMP,false,quantity,99,500);
            //damage item                   id,       name,                       description,damage, element,quantity,maxQuantity, shopvalue
            case 100: return new DamageItem(id,"Fire Bomb","Deals 20 Fire damage to a target",20,Element.FIRE,quantity,50,250);
            case 101: return new DamageItem(id,"Wind Bomb","Deals 20 Wind damage to a target",20,Element.AIR,quantity,50,250);
            case 102: return new DamageItem(id,"Water Bomb","Deals 20 Water damage to a target",20,Element.WATER,quantity,50,250);
            case 103: return new DamageItem(id,"Earth Bomb","Deals 20 Earth damage to a target",20,Element.EARTH,quantity,50,250);
            case 104: return new DamageItem(id,"Light Bomb","Deals 20 Light damage to a target",20,Element.LIGHT,quantity,50,250);
            case 105: return new DamageItem(id,"Dark Bomb","Deals 20 Dark damage to a target",20,Element.DARK,quantity,50,250);
            //weapons                   id,        name,      description,basedamage,rolldamage,stat,increase,element,quantity,maxquantity,shopvalue
            case 200: return new Weapon(id,"Wood Sword","Barely more than a stick",2,3,   BattleEntity.DEX, 0,Element.NEUTRAL,quantity,10,10);
            case 201: return new Weapon(id,"Magic Cane","Actually a stick",1,1,BattleEntity.INT,1,Element.EARTH,quantity,10,10);
            //armor                    id,          name,           description, stat1,increase,stat2,increase   maxQ,shopvalue
            case 300: return new Armor(id,"Cotton Shirt","Smells like sweat...",BattleEntity.VIT,1,BattleEntity.DEX,1,quantity,10,10);
        }
        return null;
    }
}

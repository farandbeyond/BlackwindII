/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.Entities.BattleEntity;
import java.util.Random;

/**
 *
 * @author Connor
 */
public abstract class Item {
    public static Random rand = new Random();
    int id;
    int quantity;
    int maxQuantity;
    int shopValue;
    String name;
    String description;
    public Item(int id, String name, String description, int quantity, int maxQuantity, int shopValue) {
        this.id = id;
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
        this.shopValue = shopValue;
        this.name = name;
        this.description = description;
    }
    public abstract String use(BattleEntity target);
    public abstract boolean canUse(BattleEntity target);
    //quantity adjusters/checkers
    public void reduceQuantity(int i){
        quantity-=i;
        if(quantity<=0)
            quantity = 0;
    }
    public void restock(int quantity){
        if(canStack(quantity)){
            this.quantity+=quantity;return;
        }
        this.quantity = maxQuantity;
        System.out.printf("Stacked to max. %d items wasted%n",this.quantity+quantity-maxQuantity);
    }
    public boolean canStack(int quantity){
        if(this.quantity+quantity<=maxQuantity)
            return true;
        return false;
    }
    //gets
    public int getId(){return id;}
    public int getQuantity(){return quantity;}
    public int getMaxQuantity(){return maxQuantity;}
    public int getShopValue(){return shopValue;}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public Item getOne(){
        reduceQuantity(1);
        return Items.Load(id, 1);
    }
    //sets
    private void setQuantity(int newQuantity){
        quantity = newQuantity;
    }
    public static class ItemInvalidType extends RuntimeException{
        public ItemInvalidType(String err){
            super(err);
        }
    }
    class ItemCannotBeUsed extends RuntimeException{
        ItemCannotBeUsed(String err){
            super(err);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Items;

import Background.Entities.BattleEntity;
import java.util.ArrayList;

/**
 *
 * @author Connor
 */
public class Inventory {
    ArrayList<Item> itemlist;
    int maxSize;
    public Inventory(int size){
        maxSize = size;
        itemlist = new ArrayList<>();
    }
    //content altering
    public void add(Item it){
        if(it == null){
            return;
        }
        System.out.println(itemlist.size());
        for(Item i:itemlist){
            if(i.getId()==it.getId()){
                System.out.println(i.canStack(it.getQuantity())+""+it.getQuantity());
                if(i.canStack(it.getQuantity())){
                    i.restock(it.getQuantity());
                    return;
                }
                throw new InventoryTooFull(String.format("Item %s contained within inventory, but not enough quantity to stack",it.getName()));
            }
        }
        if(itemlist.size()<maxSize){
            itemlist.add(it);
        }else
            throw new InventoryTooFull(String.format("Inventory at capacity: Cannot add %s",it.getName()));
    }
    public void drop(int slot){
        try{
            drop(slot,itemlist.get(slot).getQuantity());
        }catch(IndexOutOfBoundsException e){
            throw new InventoryBadIndex(String.format("Bad index dropping item at slot %d",slot));
        }
    }
    public void drop(int slot, int quantity){
        try{
            itemlist.get(slot).reduceQuantity(quantity);
            if(itemlist.get(slot).getQuantity()==0){
                System.out.printf("Item %s removed from inventory, quantity 0%n", itemlist.get(slot).getName());
                itemlist.remove(slot);
            }
        }catch(IndexOutOfBoundsException e){
            throw new InventoryBadIndex(String.format("Bad index dropping item at slot %d",slot));
        }
    }
    public void swap(int slot1, int slot2){
        Item temp = getItem(slot1);
        itemlist.set(slot1, getItem(slot2));
        itemlist.set(slot2, temp);
    }
    public void useItem(int slot, BattleEntity target){
        getItem(slot).use(target);
        if(itemlist.get(slot).getQuantity()==0)
            itemlist.remove(slot);
    }
    
    public boolean canAdd(Item i){
        if(i==null){
            return false;
        }
        if(contains(i.getId())&&getItem(getLocationOf(i.getId())).canStack(i.getQuantity()))
            return true;
        if(itemlist.size()<maxSize)
            return true;
        return false;
    }
    public void updateInventory(){
        for(int i=itemlist.size()-1;i>=0;i--){
            if(itemlist.get(i).getQuantity()==0){
                itemlist.remove(i);
            }
        }
    }
    public boolean contains(int itemID){
        for(Item i:itemlist){
            if(i.getId()==itemID)
                return true;
        }
        return false;
    }
    public int getLocationOf(int itemID){
        for(int i=0;i<itemlist.size()-1;i++){
            if(itemlist.get(i).getId()==itemID)
                return i;
        }
        throw new InventoryBadIndex(String.format("No item of id %s is contained within inventory",itemID));
    }
    //gets
    public Item getItem(int i){
        try{
            return itemlist.get(i);
        }catch(IndexOutOfBoundsException e){
            throw new InventoryBadIndex(String.format("Index %d is not within inventory",i));
        }
    }
    public ArrayList<Item> getItemList(){return itemlist;}
    public int getMaxSize(){return maxSize;}
    public int getCurrentSize(){
        int items=0;
        for(Item item:itemlist){
            items++;
        }
        return items;
    }
    //prints for testing
    public void printAllItems(){
        System.out.println("----------");
        for(Item i:itemlist){
            System.out.printf("%s:\t %d/%d. %s%n", i.getName(),i.getQuantity(), i.getMaxQuantity(),i.getDescription());
        }
    }
    
    public class InventoryBadIndex extends RuntimeException{
        InventoryBadIndex(String err){
            super(err);
        }
    }
    public class InventoryTooFull extends RuntimeException{
        InventoryTooFull(String err){
            super(err);
        }
    }
}

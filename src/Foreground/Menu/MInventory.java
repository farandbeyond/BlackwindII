/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Entities.BattleEntity;
import Background.Items.*;
import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Connor
 */
public class MInventory extends SelectorMenu{
    Inventory inv;
    Item filter;
    public MInventory(Inventory inv, int x, int y, int width, int height, Color c) {
        super(inv.getCurrentSize()<9?inv.getCurrentSize()-1:9, x, y, width, height, 60,40,40, c);
        filter = null;
        setMaxOffsetInv(inv.getItemList());
        this.inv = inv;
    }
    public Item getSelectedItem(){return inv.getItem(getSelectorPosition()+getCurrOffset());}
    public void useSelectedItem(BattleEntity target){inv.useItem(getSelectorPosition()+getCurrOffset(), target);}
    public Inventory getInv(){return inv;}
    public void filter(Item i){
        filter = i;
    }
    public void clearFilter(){filter = null;}
    public boolean checkFilter(Item i){
        if(i.getClass()==filter.getClass())
            return true;
        return false;
    }
    public void updateInventory(Inventory inv){
        this.inv = inv;
        setSelectorMaxPos(inv.getCurrentSize()<9?inv.getCurrentSize()-1:9);
        setMaxOffsetInv(inv.getItemList());
    }
    public void updateInventory(){
        setSelectorMaxPos(inv.getCurrentSize()<9?inv.getCurrentSize()-1:9);
        setMaxOffsetInv(inv.getItemList());
    }
    @Override
    public void upEvent(){
        super.upEvent();
        confirmOffsetMenuPosition();
    }
    @Override
    public void downEvent(){
        super.downEvent();
        confirmOffsetMenuPosition();
    }
    @Override
    public void paint(Graphics g){
        
        super.paint(g);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString(String.format("Capacity: %d/%d", inv.getCurrentSize(),inv.getMaxSize()), 125, 455);
        for(int i=0;i<10;i++){
            try{
                g.setColor(Color.black);
                if(filter!=null){
                    if(inv.getItem(i+getCurrOffset()).getClass()!=filter.getClass())
                        g.setColor(Color.gray);
                }
                g.drawString(inv.getItem(i+getCurrOffset()).getName(), getDistLeft(), getDistTop()+myX()+getDistBetween()*i);
                g.drawString("x"+inv.getItem(i+getCurrOffset()).getQuantity(), getDistLeft()+300, getDistTop()+myX()+getDistBetween()*i);
            }catch(Inventory.InventoryBadIndex e){
                g.drawString("---", getDistLeft(), getDistTop()+myX()+getDistBetween()*i);
                g.drawString("x--", getDistLeft()+300, getDistTop()+myX()+getDistBetween()*i);
            }
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground;

import Background.BattleActions.BattleAction;
import Background.Items.Item;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class SelectorMenu extends JPanel{
    int selectorPosition;
    int selectorMaxPos;
    int selectorMinPos;
    int distFromTop;
    int distFromLeft;
    int distBetweenItems;
    private boolean selectorVisible;
    private int currOffset;
    private int maxOffset;
    
    
    int myWidth, myHeight, myX, myY;
    Color color;
    
    public SelectorMenu(int maxPos, int x, int y, int width, int height, int distanceFromTop, int distanceFromLeft, int distanceBetweenItems, Color c){
        selectorPosition = 0;
        selectorMaxPos = maxPos;
        selectorMinPos = 0;
        currOffset = 0;
        distFromTop = distanceFromTop;
        distFromLeft = distanceFromLeft;
        distBetweenItems = distanceBetweenItems;
        myX = x;
        myY = y;
        myWidth = width;
        myHeight = height;
        color = c;
    }
    public SelectorMenu(int minPos, int maxPos, int x, int y, int width, int height, int distanceFromTop, int distanceFromLeft, int distanceBetweenItems, Color c){
        selectorPosition = 0;
        selectorMaxPos = maxPos;
        selectorMinPos = minPos;
        currOffset = 0;
        distFromTop = distanceFromTop;
        distFromLeft = distanceFromLeft;
        distBetweenItems = distanceBetweenItems;
        myX = x;
        myY = y;
        myWidth = width;
        myHeight = height;
        color = c;
    }
    
    public void paint(Graphics g){
        g.setColor(color);
        g.fillRect(myX, myY, myWidth, myHeight);
        g.setColor(Color.black);
        g.drawRect(myX, myY, myWidth, myHeight);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        if(isSelectorVisible()){
            g.drawString(">",myX+distFromLeft-40,myY+distFromTop+distBetweenItems*selectorPosition);
        }
    }
    
    public void updateSelectorPosition(int newPos){
        selectorPosition = newPos;
    }
    public void confirmMenuPosition(){
        if(selectorPosition>selectorMaxPos){
            selectorPosition = selectorMinPos;
        }
        if(selectorPosition<selectorMinPos){
            selectorPosition = selectorMaxPos;
        }
    }
    public void confirmOffsetMenuPosition(){
        if(currOffset<maxOffset && selectorPosition==selectorMaxPos-2){
            currOffset++;
            selectorPosition--;
            System.out.println("Condition 1");
        }else if(currOffset>selectorMinPos && selectorPosition==2){
            currOffset--;
            selectorPosition++;
            System.out.println("Condition 2");
        }else if(selectorPosition<selectorMinPos){
            
            currOffset = maxOffset;
            selectorPosition = selectorMaxPos;
            System.out.println("Condition 3");
        }else if(selectorPosition>selectorMaxPos){
            currOffset = 0;
            selectorPosition = 0;
            System.out.println("Condition 4");
        }                    
    }
    public void setMaxOffsetInv(ArrayList<Item> displayedList){
        maxOffset = 0;
        try{
            int i = selectorMaxPos+1;
            while(true){
                displayedList.get(i);
                i++;
                maxOffset++;
            }
        }catch(IndexOutOfBoundsException e){
            System.out.printf("Max offset is %d, from a container size %d\n",maxOffset,displayedList.size());
        }
    }
    public void setMaxOffsetAct(ArrayList<BattleAction> displayedList){
        maxOffset = 0;
        try{
            int i = selectorMaxPos+1;
            while(true){
                displayedList.get(i);
                i++;
                maxOffset++;
            }
        }catch(IndexOutOfBoundsException e){
            System.out.printf("Max offset is %d, from a container size %d\n",maxOffset,displayedList.size());
        }
    }
    public void setMaxOffset(Object[] displayedList){
        maxOffset = 0;
        try{
            int i = selectorMaxPos+1;
            while(true){
                displayedList[i].getClass();
                i++;
                maxOffset++;
            }
        }catch(IndexOutOfBoundsException e){
            System.out.printf("Max offset is %d, from a container size %d\n",maxOffset,displayedList.length);
        }
    }
    public void toggleSelectorVisible(){
        if(selectorVisible){
            selectorVisible=false;
            return;
        }
        selectorVisible=true;
    }
    public void setSelectorVisible(){
        selectorVisible = true;
    }
    public void setSelectorInvisible(){selectorVisible = false;}
    
    public void upEvent(){
        selectorPosition--;
    }
    public void downEvent(){
        selectorPosition++;
    }
    public void rightEvent(){
        
    }
    public void leftEvent(){
        
    }
    
    //gets
    public int getSelectorMaxPosition(){return selectorMaxPos;}
    public int getSelectorPosition(){return selectorPosition;}
    public boolean isSelectorVisible(){return selectorVisible;}
    public int getCurrOffset(){return currOffset;}
    public int getMaxOffset(){return maxOffset;}
    public int myX(){return myX;}
    public int myY(){return myY;}
    public int myWidth(){return myWidth;}
    public int myHeight(){return myHeight;}
    public int getDistLeft(){return distFromLeft;}
    public int getDistTop(){return distFromTop;}
    public int getDistBetween(){return distBetweenItems;}
    //sets
    public void setSelectorMaxPos(int newMax){
        selectorMaxPos = newMax;
    }
}

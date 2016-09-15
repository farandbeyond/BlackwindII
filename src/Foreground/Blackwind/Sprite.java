/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Blackwind;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author Connor
 */
public class Sprite {
    //sprite dictionary
    public static final int COLUMNS = 10, ROWS = 5;
    public static final int numberOfSprites = 4;
    private static String[] spriteNames;
    private static BufferedImage[] spriteSheets;
    
    public static String getSpriteName(int index){return spriteNames[index];}    
    
    public static void startUp(){
        System.out.println("Loading Sprites");
        spriteNames = new String[numberOfSprites];
        spriteSheets = new BufferedImage[numberOfSprites];
        addSprite(0,"Test Dummy","testDummy");
        addSprite(1,"Male Villager","villager1");
        addSprite(2,"Female Villager","villager2");
        addSprite(3,"Slutty Villager","villager3");
        System.out.println("Sprites Loaded Successfully");
    }
    public static void addSprite(int id, String name, String fileName){
        try{
            spriteSheets[id]=ImageIO.read(new File(String.format("images/spriteSheets/%s.png",fileName)));
            spriteNames[id]=name;
        }catch(IOException e){
            System.out.printf("Error occured reading file %s\n",fileName);
        }
    }
    
    public static final int ANIMFRAMES=4, FRAMEDUR = 4;
    public static final int STILL=0,DOWN=1, UP=2, RIGHT=3, LEFT=4;
    private int spriteID;
    private String spriteName;
    //private String eventName;
    
    private boolean moving;
    private int facingDirection;
    private int mapX, mapY, globalX, globalY;
    
    private int animationCycle, cycleDelay;
    public Sprite(){
        spriteID = 1;
        spriteName = "Player";
        moving = false;
        facingDirection = DOWN;
        mapX = 5;
        mapY = 5;
        animationCycle = 0;
        cycleDelay = 0;
        setGlobals();
    }
    private void setGlobals(){
        globalX = mapX*Tile.tileSize;
        globalY = mapY*Tile.tileSize;
    }

    public void setMovingDirection(int direction){
        facingDirection = direction;
        moving = true;
        switch(direction){
            case UP:mapY--;break;
            case DOWN:mapY++;break;
            case LEFT:mapX--;break;
            case RIGHT:mapX++;break;
        }
    }
    public void animate(){
        if(!moving)
            return;
        if(animationCycle==0&&cycleDelay==0){
            animationCycle++;
            cycleDelay++;
        }else if(animationCycle==ANIMFRAMES&&cycleDelay==FRAMEDUR-1){
            animationCycle=1;
            cycleDelay=0;
            return;
        }else if(cycleDelay==FRAMEDUR-1){
            cycleDelay = 0;
            animationCycle++;
        }
        cycleDelay++;
        System.out.println(animationCycle+cycleDelay*FRAMEDUR);
    }
    
    public BufferedImage getSpriteImage(){
        return spriteSheets[spriteID].getSubimage(
            1+(1+Tile.tileSize)*animationCycle, 
            1+(1+Tile.tileSize)*facingDirection, 
            Tile.tileSize, 
            Tile.tileSize);
    }
    public int getID() {return spriteID;}
    public String getName() {return spriteName;}
    public boolean getMoving(){return moving;}
    public int getFacingDirection(){return facingDirection;}
    public int getMapX(){return mapX;}
    public int getMapY(){return mapY;}
    public int getGlobalX(){return globalX;}
    public int getGlobalY(){return globalY;}
    
    public void setID(int newID){spriteID = newID;}
    public void setName(String newName){spriteName = newName;}
    public void setMapX(int x){mapX = x;}
    public void setMapY(int y){mapY = y;}
    
    
    public static void main(String[] args) {
        Sprite.startUp();
        Sprite s = new Sprite();
        JFrame frame = new JFrame("Blackwind Dev",null){

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.fillRect(50, 50, 32, 32);
                g.drawImage(s.getSpriteImage(), 50, 50, this);
            }
            
        };
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        
        while(true){
            try{
                if(!s.getMoving())
                    s.setMovingDirection(s.getFacingDirection());
                s.animate();
                Thread.sleep(100);
                frame.repaint();
            }catch(InterruptedException e){
                
            }
        }
        
        //frame.addMouseListener(p);
        //frame.add(p);
    }
}

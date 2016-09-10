/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Battle;

import Background.BattleActions.BattleActionLoader;
import Background.Effects.Effect;
import Background.Entities.BattleEntity;
import Background.Entities.EntityLoader;
import Background.Entities.Party;
import Background.Entities.Set;
import Background.Items.Inventory;
import Background.Items.Items;
import Foreground.HandlerMenu;
import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class Battle extends HandlerMenu{
    public static int numberOfEffectImages = 14;
    public static int numberOfBuffs = 7;
    private static BufferedImage[] effectImages;
    public static void startUp(){
        effectImages = new BufferedImage[numberOfEffectImages];
        System.out.println("Tile Startup Commenced");
        try{
            loadImage(BattleEntity.HP,"BuffArrowHP");
            loadImage(BattleEntity.MP,"BuffArrowMP");
            loadImage(BattleEntity.STR,"BuffArrowSTR");
            loadImage(BattleEntity.DEX,"BuffArrowDEX");
            loadImage(BattleEntity.VIT,"BuffArrowVIT");
            loadImage(BattleEntity.INT,"BuffArrowINT");
            loadImage(BattleEntity.RES,"BuffArrowRES");
            
            loadImage(BattleEntity.HP+numberOfBuffs,"DebuffArrowHP");
            loadImage(BattleEntity.MP+numberOfBuffs,"DebuffArrowMP");
            loadImage(BattleEntity.STR+numberOfBuffs,"DebuffArrowSTR");
            loadImage(BattleEntity.DEX+numberOfBuffs,"DebuffArrowDEX");
            loadImage(BattleEntity.VIT+numberOfBuffs,"DebuffArrowVIT");
            loadImage(BattleEntity.INT+numberOfBuffs,"DebuffArrowINT");
            loadImage(BattleEntity.RES+numberOfBuffs,"DebuffArrowRES");
            System.out.println("Battle Images Finished Loading Sucessfully");
        }catch(Exception e){
            System.out.println("Error Ocurred");
            e.printStackTrace();
        }
        
    }
    public static void loadImage(int id,String fileName) throws IOException{
        effectImages[id] = ImageIO.read(new File(String.format("images/battle/%s.png",fileName)));
        //System.out.println(String.format("images/tiles/%s.png, sucessfully loaded",fileName));
    }
    public static BufferedImage getBuffImage(int statID){return effectImages[statID];}
    public static BufferedImage getDebuffImage(int statID){return effectImages[statID+numberOfBuffs];}
    private final static int boxWidth = 150;
    Party p;
    Set s;
    Inventory inv;
    EntityBox[] partyBoxes;
    EntityBox[] enemyBoxes;
    Menu m;
    DetailedEntityBox b;
    public Battle(Party p, Set s, Inventory inv) {
        this.setPreferredSize(new Dimension(640,480));
        
        this.p = p;
        this.s = s;
        this.inv = inv;
        partyBoxes = new EntityBox[4];
        enemyBoxes = new EntityBox[3];
        m = new Menu();
        b = new DetailedEntityBox(p.getMember(0),boxWidth+1,480-200,318,199);
        for(int i=0;i<4;i++){
            try{
                partyBoxes[i]=new EntityBox(p.getMember(i),0,280+50*i,boxWidth,50);
            }catch(Party.PartyNoMember e){
                partyBoxes[i]=new EntityBox(0,280+50*i,boxWidth,50);
            }
        }
        for(int i=0;i<3;i++){
            try{
                enemyBoxes[i]=new EntityBox(s.getEnemy(i),0,50*i,boxWidth,50);
            }catch(Set.SetNoEnemy e){
                enemyBoxes[i]=new EntityBox(0,50*i,boxWidth,50);
            }
        }
    }

    public void loop(){
        while(true)
            repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 640, 480);
        g.setColor(Color.black);
        g.drawRect(0, 0, 639, 479);
        for(EntityBox b:partyBoxes)
            if(b!=null)
                b.paint(g);
        for(EntityBox b:enemyBoxes)
            if(b!=null)
                b.paint(g);
        m.paint(g);
        b.paint(g);
    }

    public class Menu extends SelectorMenu{
        Menu(){
            //30*8 = 240
            super(8,470,200,169,279,20,40,35,new Color(255,0,255));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g); 
            for(int i=0;i<8;i++){
                g.drawString("Option "+i,myX()+getDistLeft(),myY()+getDistTop()+getDistBetween()*i);
            }
        }
        
    }
    
    
    @Override
    public void upEvent() {
    }
    @Override
    public void downEvent() {
    }
    @Override
    public void rightEvent() {
    }
    @Override
    public void leftEvent() {
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        
        //set entitybox to static for this tester to work
        //EntityBox james = new EntityBox(EntityLoader.loadPartyMember(0),0,0,120,150);
        // frame.add(james);
        Battle.startUp();
        Party p = new Party(4);
        p.addMember(EntityLoader.loadPartyMember(0));
        p.addMember(EntityLoader.loadPartyMember(1));
        p.getMember(0).equip(Items.Load(Items.MAGICCANE, 1), 0);
        p.getMember(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.FRAILTY));
        p.getMember(0).getSkill(0).execute(p.getMember(0));
        //p.restAllMembers();
        //p.getMember(0).getSkill(0).execute(p.getMember(0));
        //p.restAllMembers();
        //p.getMember(0).getSkill(0).execute(p.getMember(0));
        p.getMember(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.FIRE));
        p.getMember(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.BRAVERY));
        p.getMember(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.CURE));
        p.getMember(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.WATER));
        p.getMember(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.FRAILTY));
        p.getMember(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.SEAR));
        Inventory i = new Inventory(9);
        i.add(Items.Load(0, 3));
        i.add(Items.Load(1, 3));
        i.add(Items.Load(2, 3));
        //i.add(Items.Load(3, 3));
        i.add(Items.Load(Items.COTTONSHIRT, 5));
        //i.add(Items.Load(Items.MAGICCANE, 6));
        i.add(Items.Load(102, 7));
        i.add(Items.Load(103, 8));
        i.add(Items.Load(104, 9));
        i.add(Items.Load(105, 10));
        i.add(Items.Load(200, 3));
        
        Set s = new Set(3);
        s.addEnemy(EntityLoader.loadEnemy(100));
        
        Battle b = new Battle(p,s,i);
        frame.add(b);
        frame.pack();
        //simpleEntityBox b = new simpleEntityBox(EntityLoader.loadPartyMember(0),0,0,50,50);
        //b.setPreferredSize(new Dimension(640,480));
        //frame.add(b);
        //frame.pack();
        b.loop();
        
    }
    
}
/*
    This is old code. if i cant make the new layout work, i can revert to this
    static int partyBoxWidth = 120;
    static int partyBoxHeight = 150;
    static int enemyBoxWidth = 200;
    static int enemyBoxHeight = 150;
    Party p;
    Set s;
    Inventory inv;
    EntityBox[] partyBoxes;
    EntityBox[] enemyBoxes;
    public Battle(Party p, Set s, Inventory inv){
        this.setVisible(true);
        this.setPreferredSize(new Dimension(640,480));
        this.p = p;
        this.s=s;
        this.inv = inv;
        partyBoxes = new EntityBox[4];
        enemyBoxes = new EntityBox[3];
        for(int i=0;i<4;i++){
            try{
                partyBoxes[i] = new EntityBox(p.getMember(i),0+i*partyBoxWidth,480-partyBoxHeight,partyBoxWidth,partyBoxHeight);
            }catch(Party.PartyNoMember e){
                partyBoxes[i] = new EntityBox(0+i*partyBoxWidth,480-partyBoxHeight,partyBoxWidth,partyBoxHeight);
            }
        }
        for(int i=0;i<3;i++){
            try{
                enemyBoxes[i] = new EntityBox(s.getEnemy(i),0+i*enemyBoxWidth,0,enemyBoxWidth,enemyBoxHeight);
            }catch(Set.SetNoEnemy e){
                enemyBoxes[i] = new EntityBox(0+i*enemyBoxWidth,0,enemyBoxWidth,enemyBoxHeight);
            }
        }
        changed();
    }
    @Override
    public void repaint(){
        if(change())
            super.repaint();
    }

 
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(EntityBox b:partyBoxes)
            b.paint(g);
        for(EntityBox b:enemyBoxes)
            b.paint(g);
        //g.fillRect(0,0,50,50);
    }

    public void loop() throws InterruptedException{
        while(true){
            repaint();
            Thread.sleep(100);
            System.out.println("Looping");
        }
    }
    
    
    public class EntityBox extends JPanel{
        int myX, myY, myWidth, myHeight;
        boolean targeted,acting;
        BattleEntity displayed;
        Color currentColor;
        public EntityBox(BattleEntity displayed, int myX, int myY, int myWidth, int myHeight) {
            this.myX = myX;
            this.myY = myY;
            this.myWidth = myWidth;
            this.myHeight = myHeight;
            this.displayed = displayed;
            targeted = false;
            acting = false;
            updateColor();
        }
        public EntityBox(int myX, int myY, int myWidth, int myHeight) {
            this.myX = myX;
            this.myY = myY;
            this.myWidth = myWidth;
            this.myHeight = myHeight;
            this.displayed = null;
            targeted = false;
            acting = false;
            updateColor();
        }
        public void updateColor(){
            if(displayed==null)
                currentColor = Color.gray;
            else if(targeted)
                currentColor = Color.orange;
            else if(acting)
                currentColor = Color.GREEN;
            else if(displayed.isDead())
                currentColor = Color.red;
            else
                currentColor = Color.cyan;
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(currentColor);
            g.fillRect(myX, myY, myWidth, myHeight);
            g.setColor(Color.black);
            g.drawRect(myX, myY, myWidth, myHeight);
            g.setFont(new Font("Monospaced", Font.BOLD, 20));
            try{
                g.drawString(displayed.getName(),myX+15,myY+50);
                g.drawString(String.format("%d/%dHP",displayed.getStat(BattleEntity.HP).getStat(),displayed.getStat(BattleEntity.HP).getMax()),myX+15,myY+80);
                g.drawString(String.format("%d/%dMP",displayed.getStat(BattleEntity.MP).getStat(),displayed.getStat(BattleEntity.MP).getMax()),myX+15,myY+110);
            }catch(NullPointerException e){
                g.drawString("-----",myX+15,myY+50);
                g.drawString("--/--HP",myX+15,myY+80);
                g.drawString("--/--MP",myX+15,myY+110);
            }
        }
        
        
    }
    */
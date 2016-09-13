/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Battle;

import Background.BattleActions.BattleAction;
import Background.BattleActions.BattleActionLoader;
import Background.BattleActions.DeathAction;
import Background.BattleActions.UseItem;
import Background.Effects.Effect;
import Background.Entities.BattleEntity;
import Background.Entities.Enemy;
import Background.Entities.EntityLoader;
import Background.Entities.Party;
import Background.Entities.PartyMember;
import Background.Entities.Set;
import Background.Items.Inventory;
import Background.Items.Items;
import Foreground.HandlerMenu;
import Foreground.Joystick;
import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    //mechanics
    Party p;
    Set s;
    Inventory inv;
    //display
    EntityBox[] partyBoxes;
    EntityBox[] enemyBoxes;
    Menu m;
    targetSelector tar;
    DetailedEntityBox b;
    //controllers
    boolean selectingTarget, selectingAllies;
    ArrayList<TargetedSkill> skills;
    boolean battleOver;
    String assistText;
    public Battle(Party p, Set s, Inventory inv) {
        this.setPreferredSize(new Dimension(640,480));
        
        this.p = p;
        this.s = s;
        this.inv = inv;
        partyBoxes = new EntityBox[4];
        enemyBoxes = new EntityBox[3];
        skills = new ArrayList<>();
        m = new Menu(p.getMember(0));
        tar = new targetSelector();
        b = new DetailedEntityBox(p.getMember(0),boxWidth+1,480-200,248,199);
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
        battleOver = false;
        selectingTarget = false;
        selectingAllies = false;
        setAssistText("");
    }

    public void loop(){
        checkForBattleOver();
        while(!battleOver){
            repaint();
            getPartyActions();
            getEnemyActions();
            sortByDex();
            executeAllActions();
            checkForBattleOver();
        }
        endBattle();
    }
    public void checkForBattleOver(){
        if(!s.someoneIsAlive()){
            battleOver = true;
            return;
        }
        if(!p.someoneIsAlive()){
            battleOver = true;
            return;
        }
        if(p.getCurrentPartySize()==0||s.getCurrentSetSize()==0){
            battleOver = true;
        }
    }
    public void sortByDex(){
        //Step 1: Assume position 0 is the highest dex before sorting
        //Step 2: Any caster below position 0 with higher dex replaces Position 0
        //Step 3: If no caster is faster than the selected caster, Add their skill to the bottom of the list and remove the old one
        //Step 4: repeat until all skills are sorted
        //for this: Size returns max of 7, when index is max of 6. scales down linearly
        for(int i=0;i<skills.size();i++){
            int fastestIndex = 0;
            for(int x=0;x<skills.size()-i;x++){
                if(skills.get(x).getCasterDex()>skills.get(fastestIndex).getCasterDex())
                    fastestIndex = x;
            }
            //System.out.println(skills.get(fastestIndex).getCasterDex());
            skills.add(skills.get(fastestIndex));
            skills.remove(fastestIndex);
        }
        //for(TargetedSkill s:skills)
                //System.out.printf("Skill %s by Caster %s, with %d dex\n",s.getSkill().getName(),s.getCaster().getName(),s.getCasterDex());
    }
    public void getPartyActions(){
        int index = 0;
        while(index<p.getCurrentPartySize()){
            if(!p.getMember(index).isDead()){
                resetEvents();
                b.setActing(p.getMember(index));
                partyBoxes[index].setActing(true);
                m.loadNewMember(p.getMember(index));
                TargetedSkill t = selectAction();
                partyBoxes[index].setActing(false);
                if(!getCancelEvent()){
                    skills.add(t);
                    index++;
                }else{
                    if(index>0){
                        index--;
                        skills.remove(index);
                    }
                    
                }
            }else{   
                
                if(getCancelEvent()){
                    if(index>0){
                        index--;
                        skills.remove(index);
                    }
                }else{
                    //System.out.println("Actor is Dead");
                    skills.add(new TargetedSkill(new DeathAction(p.getMember(index)),p.getMember(index)));
                    index++;
                }
            }
        }
    }
    public void getEnemyActions(){
        for(Enemy e:s.getEnemyList()){
            if(e!=null){
                BattleAction bat = e.getAiSkill(s, p);
                BattleEntity target = e.getAiTarget(p, s, bat);
                skills.add(new TargetedSkill(bat,target));
            }
        }
    }
    public void executeAllActions(){
        for(TargetedSkill t:skills){
            setAssistText(t.execute());
            repaint();
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                
            }
        }
        skills=new ArrayList<>();
    }
    
    public void endBattle(){
        int expGained = 0;
        int goldGained = 0;
        for(Enemy e:s.getEnemyList()){
            if(e!=null){
                expGained+=e.getXpGiven();
                goldGained+=e.getGoldGiven();
            }
        }
        for(PartyMember p:p.getMemberList()){
            if(p!=null){
                setAssistText(p.gainExp(expGained));
                repaint();
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    
                }
            }
        }
        System.out.println(goldGained+"G earned");
    }
    //selecting actions
    public TargetedSkill selectAction(){
        TargetedSkill actionToReturn = new TargetedSkill();
        m.loadMenuOptions();
        while(!getCancelEvent()&&!m.turnOver){
            resetEvents();
            repaint();
            if(getConfirmEvent()){
                switch(m.getSelectorPosition()){
                    case 0:selectTarget(actionToReturn,BattleActionLoader.loadAttack(m.loaded));break;
                    case 1:selectSkill(actionToReturn);resetEvents();break;
                    case 2:selectItem(actionToReturn);resetEvents();break;
                    case 3:System.exit(0);
                }
            }
        }
        return actionToReturn;
    }
    public void selectItem(TargetedSkill actionToReturn){
        m.loadItemNames();
        while(!getCancelEvent()&&!m.turnOver){
            resetEvents();
            repaint();
            if(getConfirmEvent()){
                if(inv.getItem(m.getSelectorPosition()+m.getCurrOffset()).canUse())
                    selectTarget(actionToReturn,new UseItem(m.getMember(),inv.getItem(m.getSelectorPosition()+m.getCurrOffset())));
            }
        }
        resetEvents();
    }
    public void selectSkill(TargetedSkill actionToReturn){
        m.loadSkillNames();
        while(!getCancelEvent()&&!m.turnOver){
            resetEvents();
            repaint();
            if(getConfirmEvent()){
                if(m.getMember().getSkill(m.getSelectorPosition()+m.getCurrOffset()).canExecute())
                    selectTarget(actionToReturn,m.getMember().getSkill(m.getSelectorPosition()+m.getCurrOffset()));
            }
        }
        resetEvents();
    }
    public void selectTarget(TargetedSkill t,BattleAction skill){
        selectingTarget = true;
        setAssistText("Using "+skill.getName()+" on who?");
        t.setAction(skill);
        if(skill.targetsAllies()){
            selectingAllies = true;
            tar.setSelectorMaxPos(p.getCurrentPartySize()-1);
            tar.setSelectorInvisible();
            tar.confirmMenuPosition();
            while(!getCancelEvent()&&!m.turnOver){
                resetEvents();
                repaint();
                partyBoxes[tar.getSelectorPosition()].target();
                if(getConfirmEvent()){
                    t.setTarget(p.getMember(tar.getSelectorPosition()));
                    m.turnOver = true;
                }
            }
        }else{
            selectingAllies = false;
            tar.setSelectorMaxPos(s.getCurrentSetSize()-1);
            tar.setSelectorInvisible();
            tar.confirmMenuPosition();
            while(!getCancelEvent()&&!m.turnOver){
                resetEvents();
                repaint();
                //System.out.println(tar.getSelectorPosition());
                enemyBoxes[tar.getSelectorPosition()].target();
                if(getConfirmEvent()){
                    t.setTarget(s.getEnemy(tar.getSelectorPosition()));
                    m.turnOver = true;
                }
            }
        }
        selectingTarget = false;
        resetEvents();
    }
    
    public void setAssistText(String text){
        assistText = text;
        changed();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 640, 480);
        g.setColor(Color.black);
        g.drawRect(0, 0, 639, 479);
        
        for(EntityBox box:partyBoxes)
            if(box!=null){
                box.setTargeted(false);
                box.paint(g);
            }
        for(EntityBox box:enemyBoxes)
            if(box!=null){
                box.setTargeted(false);
                box.paint(g);
            }
        if(selectingTarget)
            if(selectingAllies){
                partyBoxes[tar.getSelectorPosition()].target();
                partyBoxes[tar.getSelectorPosition()].paint(g);
            }else{
                enemyBoxes[tar.getSelectorPosition()].target();
                enemyBoxes[tar.getSelectorPosition()].paint(g);
            }
        m.paint(g);
        b.paint(g);
        g.setColor(Color.black);
        if(assistText.length()<50)
            g.drawString(assistText, boxWidth+15, 50);
        else{
            int splitIndex = assistText.indexOf(" ", 35);
            g.drawString(assistText.substring(0, splitIndex), boxWidth+15, 50);
            g.drawString(assistText.substring(splitIndex+1), boxWidth+15, 80);
        }
    }
    
    public class Menu extends SelectorMenu{
        private static final int MAIN=0,ITEMS=1,SKILLS=2;
        PartyMember loaded;
        String[] options;
        int loadedMenu;
        boolean turnOver;
        Menu(PartyMember p){
            //30*8 = 240
            super(8,400,200,239,279,30,40,33,new Color(255,0,255));
            loaded=p;
            turnOver = false;
            options = new String[8];
            loadMenuOptions();
            setSelectorVisible();
        }
        public void updateSkillNames(){
            for(int i=0;i<8;i++){
                try{
                    options[i]=loaded.getSkill(i+getCurrOffset()).getName()+" "+loaded.getSkill(i+getCurrOffset()).getCost()+"mp";
                }catch(BattleEntity.EntityBadIndex e){
                    options[i]="----- --mp";
                }
            }
        }
        public void updateItemNames(){
            for(int i=0;i<8;i++){
                try{
                    options[i]=inv.getItem(i+getCurrOffset()).getName()+" x"+inv.getItem(i+getCurrOffset()).getQuantity();
                }catch(Inventory.InventoryBadIndex e){
                    options[i]="----- x--";
                }
            }
        }
        
        public void loadItemNames(){
            int selectorMaxPos = -1;
            for(int i=0;i<8;i++){
                try{
                    options[i]=inv.getItem(i+getCurrOffset()).getName()+" x"+inv.getItem(i+getCurrOffset()).getQuantity();
                    selectorMaxPos++;
                }catch(Inventory.InventoryBadIndex e){
                    options[i]="----- x--";
                    if(selectorMaxPos==-1)
                        selectorMaxPos = 0;
                }
            }
            setSelectorMaxPos(selectorMaxPos);
            setMaxOffsetInv(inv.getItemList());
            
            loadedMenu = ITEMS;
        }
        public void loadSkillNames(){
            int selectorMaxPos = -1;
            for(int i=0;i<8;i++){
                try{
                    options[i]=loaded.getSkill(i+getCurrOffset()).getName()+" "+loaded.getSkill(i+getCurrOffset()).getCost()+"mp";
                    selectorMaxPos++;
                }catch(BattleEntity.EntityBadIndex e){
                    options[i]="----- --mp";
                    if(selectorMaxPos==-1)
                        selectorMaxPos = 0;
                }
            }
            setSelectorMaxPos(selectorMaxPos);
            setMaxOffsetAct(loaded.getSkillList());
            loadedMenu = SKILLS;
        }
        public void loadMenuOptions(){
            options[0]="Attack";
            options[1]="Skills";
            options[2]="Item";
            options[3]="Flee";
            options[4]="";
            options[5]="";
            options[6]="";
            options[7]="";
            setSelectorMaxPos(3);
            loadedMenu = MAIN;
        }
        public void loadNewMember(PartyMember p){loaded = p;turnOver = false;}
        
        public PartyMember getMember(){return loaded;}

        @Override
        public void paint(Graphics g) {
            super.paint(g); 
            switch(loadedMenu){
                case ITEMS:updateItemNames();break;
                case SKILLS:updateSkillNames();break;
            }
            for(int i=0;i<8;i++){
                switch(loadedMenu){
                    case ITEMS:
                        if(!options[i].equalsIgnoreCase("----- x--"))
                            if(!inv.getItem(i+getCurrOffset()).canUse())
                                g.setColor(Color.gray);
                            else
                                g.setColor(Color.black);
                        break;
                    case SKILLS:
                        if(!options[i].equalsIgnoreCase("----- --mp")){
                            if(!loaded.getSkill(i+getCurrOffset()).canExecute())
                                g.setColor(Color.gray);
                            else
                                g.setColor(Color.black);
                        }
                    break;
                }
                g.drawString(options[i],myX()+getDistLeft(),myY()+getDistTop()+getDistBetween()*i);
            }
        }
        
        
        
        @Override
        public void upEvent() {
            super.upEvent(); //To change body of generated methods, choose Tools | Templates.
            if(loadedMenu==MAIN)
                confirmMenuPosition();
            else
                confirmOffsetMenuPosition();
            
        }
        @Override
        public void downEvent() {
            super.downEvent(); //To change body of generated methods, choose Tools | Templates.
            if(loadedMenu==MAIN)
                confirmMenuPosition();
            else
                confirmOffsetMenuPosition();
            
        }
        
    }
    public class targetSelector extends SelectorMenu{
        public targetSelector(){
            super(0,0,0,0,0,0,0,0,Color.BLACK);
        }

        @Override
        public void upEvent() {
            super.upEvent(); //To change body of generated methods, choose Tools | Templates.
            confirmMenuPosition();
        }

        @Override
        public void downEvent() {
            super.downEvent(); //To change body of generated methods, choose Tools | Templates.
            confirmMenuPosition();
        }
        
    }
    
    @Override
    public void upEvent() {
        if(selectingTarget)
            tar.upEvent();
        else
            m.upEvent();
        changed();
    }
    @Override
    public void downEvent() {
        if(selectingTarget)
            tar.downEvent();
        else
            m.downEvent();
        changed();
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
        //p.addMember(EntityLoader.loadPartyMember(2));
        p.addMember(EntityLoader.loadPartyMember(3));
        p.getMember(0).equip(Items.Load(Items.MAGICCANE, 1), 0);
        
        //p.damageMember(30, 1);
        //p.getMember(0).addSkill(BattleActionLoader.loadAction(BattleActionLoader.FRAILTY));
        //p.getMember(0).getSkill(0).execute(p.getMember(0));
        p.getMember(2).equip(Items.Load(Items.WOODSWORD, 1), 0);
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
        s.addEnemy(EntityLoader.loadEnemyWithSkills(100));
        //s.addEnemy(EntityLoader.loadEnemyWithSkills(100));
        //s.addEnemy(EntityLoader.loadEnemyWithSkills(100));
        
        Battle b = new Battle(p,s,i);
        
        Joystick j = new Joystick(b);
        frame.add(b);
        frame.addKeyListener(j);
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.BattleActions.BattleAction;
import Background.BattleActions.BattleActionLoader;
import Background.BattleActions.HealingSpell;
import Background.Entities.BattleEntity;
import Background.Entities.EntityLoader;
import Background.Entities.Party;
import Background.Items.HealingItem;
import Background.Items.Inventory;
import Background.Items.Items;
import Foreground.HandlerMenu;
import Foreground.Joystick;
import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author Connor
 */
public class Menu extends HandlerMenu{

    static MOptions options;
    static MInventory inventory;
    static MParty party;
    static MSkills skills;
    static MEquipment equipment;
    
    String assistText;
    boolean optionsLoaded;
    SelectorMenu loadedMenu;
    public Menu(Party p, Inventory i){
        super();
        this.setBounds(0, 0, 640, 480);
        
        assistText = "";
        optionsLoaded = true;
        options = new MOptions(400,0,240,480,Color.RED);
        party = new MParty(p,0,0,400,480,Color.YELLOW);
        inventory = new MInventory(i,0,0,400,480,Color.CYAN);
        skills = new MSkills(p.getMember(0),0,0,400,480,Color.green);
        equipment = new MEquipment(p.getMember(0));
        loadedMenu = party;
        //loadedMenu.toggleSelectorVisible();
    }
    public void updateMenu(Party p, Inventory inv){
        party.p = p;
        inventory.inv = inv;
        
    }
    //opens the menu to the initial screen. you select from the 6 basic options here
    public void loop() throws InterruptedException{
        //loadedMenu.setSelectorVisible();
        changed();
        options.setSelectorVisible();
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                System.out.println("Confirm");
                switch(options.getSelectorPosition()){
                    case 0:itemsLoop();break;
                    case 1:statusLoop();break;
                    case 2:skillsLoop();break;
                    case 3:equipmentLoop();break;
                    case 4:swapMembers();break;
                    case 5:
                }
            }
        }
    }
    //opens the inventory menu, allowing you to select an item from the list
    public void itemsLoop() throws InterruptedException{
        loadMenu(inventory);
        inventory.filter(Items.Load(Items.POTION, 0));
        options.loadInventoryMenuOptions();
        options.setSelectorInvisible();
        resetEvents();
        optionsLoaded = false;
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                //System.out.println(inventory.getSelectedItem().getName());
                itemOptionsLoop();
            }
        }
        loadMenu(party);
        inventory.clearFilter();
        resetEvents();
        options.loadMainMenuOptions();
        options.setSelectorVisible();
        optionsLoaded = true;
        party.toggleSelectorVisible();
        setAssistText("");
    }
    //opens the options menu from the inventory, the selector position of the inventory panel is preserved, for future reference
    public void itemOptionsLoop() throws InterruptedException{
        resetEvents();
        optionsLoaded = true;
        options.setSelectorVisible();
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                switch(options.getSelectorPosition()){
                    case 0:
                        System.out.println("use");
                        useItemLoop();
                        break;
                    case 1:
                        System.out.println("swap");
                        swapItemLoop();
                        break;
                    case 2:
                        System.out.println("description");
                        itemStatusLoop();
                        break;
                    case 3:
                        System.out.println("drop");
                        itemDropLoop();
                        break;
                }
            }
        }
        resetEvents();
        optionsLoaded = false;
        options.setSelectorInvisible();
        
    }
    //goes to the party menu, and allows the selection of a party member, so that an item can be used on them
    public void useItemLoop() throws InterruptedException{
        if(inventory.getSelectedItem().getClass()!=HealingItem.class)
            return;
        resetEvents();
        optionsLoaded = false;
        options.setSelectorInvisible();
        loadMenu(party);
        party.setSelectorVisible();
        while(!getCancelEvent()){
            //System.out.println(party.getSelectorPosition());
            setAssistText(String.format("Use %s x%d on whom?", inventory.getSelectedItem().getName(),inventory.getSelectedItem().getQuantity()));
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                if(!inventory.getSelectedItem().canUse(party.getSelectedMember())){
                    setAssistText(String.format("%s x%d cant be used on %s", inventory.getSelectedItem().getName(),inventory.getSelectedItem().getQuantity(),party.getSelectedMember().getName()));
                    Thread.sleep(50);
                }else{
                    inventory.useSelectedItem(party.getSelectedMember());
                    resetEvents();
                    this.changed();
                }
            }
        }
        resetEvents();
        optionsLoaded = true;
        options.setSelectorVisible();
        loadMenu(inventory);
        party.setSelectorInvisible();
        setAssistText("");
    }
    //stays in the inventory menu, and allows you to swap the selected item with another
    public void swapItemLoop() throws InterruptedException{
        optionsLoaded = false;
        setAssistText(String.format("Swap %s with what?", inventory.getSelectedItem().getName(),inventory.getSelectedItem().getQuantity()));
        int save = inventory.getSelectorPosition()+inventory.getCurrOffset();
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                inventory.getInv().swap(save, inventory.getSelectorPosition()+inventory.getCurrOffset());
                cancelEvent();
            }
        }
        resetEvents();
        optionsLoaded = true;
        setAssistText("");
    }
    //opens the item status menu class, for the selected item
    public void itemStatusLoop() throws InterruptedException{
        loadedMenu = new MItemStatus(inventory.getSelectedItem());
        changed();
        options.loadStatusMenuOptions();
        options.updateSelectorPosition(0);
        resetEvents();
        while(!getCancelEvent()&&!getConfirmEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
        }
        resetEvents();
        options.loadInventoryMenuOptions();
        options.updateSelectorPosition(2);
        loadedMenu = inventory;
    }
    //allows you to drop items
    public void itemDropLoop() throws InterruptedException{
        setAssistText(String.format("Drop %s x%d?",inventory.getSelectedItem().getName(),inventory.getSelectedItem().getQuantity()));
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                setAssistText(String.format("Dropped %s x%d",inventory.getSelectedItem().getName(),inventory.getSelectedItem().getQuantity()));
                inventory.getInv().drop(inventory.getSelectorPosition());
                inventory.updateInventory();
                cancelEvent();
                return;
            }
        }
        cancelEvent();
        setAssistText("");
        options.updateSelectorPosition(0);
    }
    
    //allows you to select a party member to view
    public void statusLoop() throws InterruptedException{
        optionsLoaded = false;
        party.setSelectorVisible();
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                statusViewLoop();
            }
        }
        resetEvents();
        party.setSelectorInvisible();
        optionsLoaded = true;
    }
    //
    public void statusViewLoop() throws InterruptedException{
        loadMenu(new MStatus(party.getSelectedMember()));
        changed();
        resetEvents();
        options.loadStatusMenuOptions();
        options.updateSelectorPosition(0);
        while(!getCancelEvent() && !getConfirmEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
        }
        options.updateSelectorPosition(1);
        options.loadMainMenuOptions();
        resetEvents();
        loadMenu(party);
    }
    //prepares to open the spells menu, by first selecting a member whos skills you will see
    
    public void skillsLoop()throws InterruptedException{
        optionsLoaded = false;
        party.setSelectorVisible();
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                viewSkillsMenu();
            }
        }
        party.setSelectorInvisible();
        optionsLoaded = true;
        resetEvents();
        
    }
    //opens the spells menu, allowing you to see the skills of a party member
    public void viewSkillsMenu() throws InterruptedException{
        skills.loadNewMember(party.getSelectedMember());
        options.loadSpellsMenuOptions();
        options.setSelectorInvisible();
        loadMenu(skills);
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                skillOptionsLoop();
            }
        }
        options.loadMainMenuOptions();
        options.setSelectorVisible();
        loadMenu(party);
        resetEvents();
        setAssistText("");
    }
    //opens the skill options, aloowing you to choose description, cast or swap on your skills
    public void skillOptionsLoop() throws InterruptedException{
        optionsLoaded=true;
        options.setSelectorVisible();
        options.updateSelectorPosition(0);
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                switch(options.getSelectorPosition()){
                    case 0:skillCastLoop();break;
                    case 1:skillDescriptionLoop();break;
                    case 2:skillSwapLoop();break;
                }
            }
        }
        optionsLoaded=false;
        options.setSelectorInvisible();
        options.updateSelectorPosition(2);
        resetEvents();
    }
    //allows you to select a party member to cast a spell on. will return immediatly if the spell cannot be cast
    public void skillCastLoop() throws InterruptedException{
        if(skills.getSelectedAction().getClass()!=HealingSpell.class){
            setAssistText("Skill cannot be cast");
            return;
        }
        loadMenu(party);
        optionsLoaded=false;
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                setAssistText(skills.getSelectedAction().execute(party.getSelectedMember()));
            }
        }
        resetEvents();
        loadMenu(skills);
        optionsLoaded=false;
    }
    //opens the skill description status menu
    public void skillDescriptionLoop() throws InterruptedException{
        loadedMenu = new MSkillStatus(skills.getSelectedAction());
        options.loadStatusMenuOptions();
        options.updateSelectorPosition(0);
        resetEvents();
        changed();
        while(!getCancelEvent()&&!getConfirmEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
        }
        options.loadSpellsMenuOptions();
        options.updateSelectorPosition(1);
        resetEvents();
        loadMenu(skills);
    }
    //allows you to select another skill, in order to swap positions with the one selected by viewSkillsMenu
    public void skillSwapLoop() throws InterruptedException{
        optionsLoaded = false;
        int swapPosition = skills.getSelectorPosition();
        setAssistText("What will swap with "+skills.getSelectedAction().getName()+"?");
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                BattleAction b = skills.getSkillsList().get(swapPosition);
                skills.getSkillsList().set(swapPosition, skills.getSelectedAction());
                skills.getSkillsList().set(skills.getSelectorPosition(),b);
                setAssistText("Swapped");
            }
        }
        resetEvents();
        optionsLoaded = true;
    }
    
    //
    public void equipmentLoop() throws InterruptedException{
        optionsLoaded = false;
        party.setSelectorVisible();
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                equipment.updateEquipper(party.getSelectedMember());
                equipMenuLoop();
            }
        }
        party.setSelectorInvisible();
        optionsLoaded = true;
        resetEvents();
    }
    //
    public void equipMenuLoop() throws InterruptedException{
        loadMenu(equipment);
        equipment.setSelectorVisible();
        options.loadEquipmentMenuOptions();
        options.updateSelectorPosition(0);
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                equipmentOptionsLoop();
            }
        }
        options.loadMainMenuOptions();
        options.updateSelectorPosition(3);
        equipment.setSelectorInvisible();
        resetEvents();
        loadMenu(party);
    }
    //
    public void equipmentOptionsLoop() throws InterruptedException{
        optionsLoaded=true;
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                switch(options.getSelectorPosition()){
                    case 0:equipLoop();break;
                    case 1:
                        try{
                            unequip();
                        }catch(BattleEntity.EntityNullError e){
                            
                        }
                        break;
                    case 2:
                        try{
                            equipmentDescriptionLoop();
                        }catch(BattleEntity.EntityNullError e){
                            
                        }
                }
            }
        }
        optionsLoaded=false;
        resetEvents();
    }
    //
    public void equipLoop() throws InterruptedException{
        loadMenu(inventory);
        inventory.filter(Items.Load(equipment.getSelectorPosition()==0?Items.MAGICCANE:Items.COTTONSHIRT,0));
        optionsLoaded = false;
        while(!getCancelEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                if(inventory.checkFilter(inventory.getSelectedItem())){
                    try{
                        if(inventory.getInv().canAdd(party.getSelectedMember().getWeapon())){
                            inventory.getInv().add(party.getSelectedMember().unequip(equipment.getSelectorPosition()));
                            equipment.equip(inventory.getSelectedItem().getOne());
                            cancelEvent();
                        }else{
                            setAssistText("Cant unequip weapon: inv full");
                        }
                    }catch(BattleEntity.EntityNullError e){
                        equipment.equip(inventory.getSelectedItem().getOne());
                        cancelEvent();
                    }
                    
                }
            }
        }
        //optionsLoaded = true;
        inventory.clearFilter();
        loadMenu(equipment);
        //resetEvents();
    }
    //
    public void unequip() throws InterruptedException{
        if(inventory.getInv().canAdd(equipment.getSelectedEquipment())){
            inventory.getInv().add(party.getSelectedMember().unequip(equipment.getSelectorPosition()));
            cancelEvent();
        }else{
            setAssistText("Can't Unequip: Inventory Full");
            cancelEvent();
        }
    }
    //
    public void equipmentDescriptionLoop() throws InterruptedException{
        loadMenu(new MItemStatus(equipment.getSelectedEquipment()));
        resetEvents();
        while(!getCancelEvent() && !getConfirmEvent()){
            resetEvents();
            Thread.sleep(10);
            repaint();
        }
        loadMenu(equipment);
    }
    //opens the party menu from the main menu. allows you to swap the position of 2 members
    public void swapMembers() throws InterruptedException{
        optionsLoaded = false;
        party.setSelectorVisible();
        boolean ret = false;
        assistText = "Who will swap?";
        while(!getCancelEvent()&&!ret){
            resetEvents();
            Thread.sleep(10);
            repaint();
            if(getConfirmEvent()){
                int index1 = loadedMenu.getSelectorPosition();
                assistText = "Who will they swap with?";
                while(!getCancelEvent()&&!ret){
                    resetEvents();
                    Thread.sleep(10);
                    repaint();
                    if(getConfirmEvent()){
                        assistText = "Swapped";
                        party.getParty().swapMembers(index1, loadedMenu.getSelectorPosition());
                        ret = true;
                    }
                }
            }
        }
        party.setSelectorInvisible();
        optionsLoaded = true;
    }
    
    public void setAssistText(String text){
        assistText = text;
    }
    
    public void repaint(){
        if(change()){
            painted();
            //System.out.println("Painting");
            super.repaint();
        }
    }
    public void paintComponents(Graphics g){
        paint(g);
    }
    public void paint(Graphics g){
        options.paint(g);
        loadedMenu.paint(g);
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 640, 35);
        g.setColor(Color.black);
        g.drawRect(0,0,639,34);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString(assistText, 10, 15);
    }
    @Override
    public void upEvent() {
        if(optionsLoaded)
            options.upEvent();
        else
            loadedMenu.upEvent();
        changed();
    }
    @Override
    public void downEvent() {
        if(optionsLoaded)
            options.downEvent();
        else
            loadedMenu.downEvent();
        changed();
    }
    private void loadMenu(SelectorMenu m){
        loadedMenu.setSelectorInvisible();
        loadedMenu = m;
        loadedMenu.setSelectorVisible();
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        
        
        
        Party p = new Party(4);
        p.addMember(EntityLoader.loadPartyMember(0));
        p.addMember(EntityLoader.loadPartyMember(1));
        p.getMember(0).equip(Items.Load(Items.MAGICCANE, 1), 0);
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
        
        
        Menu m = new Menu(p,i);
        m.setPreferredSize(new Dimension(640,480));
        
        frame.add(m);
        frame.pack();
        m.setLocation(0, 0);
        frame.pack();
        Joystick j = new Joystick(m);
        frame.addKeyListener(j);
        try{
            m.loop();
        }catch(InterruptedException e){
            System.out.println("Ended loop:");
            e.printStackTrace();
        }
    }

    @Override
    public void rightEvent() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void leftEvent() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

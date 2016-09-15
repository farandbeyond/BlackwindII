/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.BattleActions.BattleAction;
import Background.BattleActions.HealingSpell;
import Background.Entities.BattleEntity.EntityBadIndex;
import Background.Entities.PartyMember;
import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Connor
 */
public class MSkills extends SelectorMenu{
    PartyMember loadedMember;
    public MSkills(int x, int y, int width, int height, Color c) {
        super(9, x, y, width, height,60,40,40, c);
        //loadNewMember(e);
        //setMaxOffsetAct(e.getSkillList());
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        for(int i=0;i<10;i++){
            try{
                if(loadedMember.getSkill(i).getClass()!=HealingSpell.class)
                    g.setColor(Color.gray);
                else
                    g.setColor(Color.black);
                g.drawString(loadedMember.getSkill(i).getName(), myX()+getDistLeft(), myY()+getDistTop()+getDistBetween()*i);
                g.drawString(loadedMember.getSkill(i).getCost()+"mp", myX()+getDistLeft()+300, myY()+getDistTop()+getDistBetween()*i);
            }catch(EntityBadIndex e){
                g.setColor(Color.black);
                g.drawString("-----", myX()+getDistLeft(), myY()+getDistTop()+getDistBetween()*i);
                g.drawString("--mp", myX()+getDistLeft()+300, myY()+getDistTop()+getDistBetween()*i);
            }
        }
        g.drawString("Grey skills cannot be cast",20,440);
    }
    public void loadNewMember(PartyMember e){
        loadedMember = e;
        super.setSelectorMaxPos(e.getSkillList().size()>9?9:e.getSkillList().size()-1);
    }
    public BattleAction getSelectedAction(){return loadedMember.getSkill(getSelectorPosition()+getCurrOffset());}
    public ArrayList<BattleAction> getSkillsList(){return loadedMember.getSkillList();}
    @Override
    public void upEvent() {
        super.upEvent();
        confirmOffsetMenuPosition();
    }

    @Override
    public void downEvent() {
        super.downEvent(); //To change body of generated methods, choose Tools | Templates.
        confirmOffsetMenuPosition();
    }
    
}

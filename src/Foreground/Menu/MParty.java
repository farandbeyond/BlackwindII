/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground.Menu;

import Background.Entities.BattleEntity;
import Background.Entities.EntityLoader;
import Background.Entities.Party;
import Background.Entities.PartyMember;
import Foreground.SelectorMenu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author Connor
 */
public class MParty extends SelectorMenu{
    Party p;
    public MParty(Party p, int x, int y, int width, int height, Color c) {
        super(p.getCurrentPartySize()-1, x, y, width, height,60,40,100, c);
        this.p = p;
    }
    public void paint(Graphics g){
        super.paint(g);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        for(int i=0;i<4;i++){
            try{
                g.drawString(p.getMember(i).getName(), getDistLeft()+myX(), getDistTop()+myY()+getDistBetween()*i);
                g.drawString(String.format("%d/%d HP", p.getMembersStat(i, BattleEntity.HP).getStat(),p.getMembersStat(i, BattleEntity.HP).getMax()), getDistLeft()+myX(), getDistTop()+25+myY()+getDistBetween()*i);
                g.drawString(String.format("%d/%d MP", p.getMembersStat(i, BattleEntity.MP).getStat(),p.getMembersStat(i, BattleEntity.MP).getMax()), getDistLeft()+myX(), getDistTop()+50+myY()+getDistBetween()*i);
            }catch(Party.PartyNoMember e){
                g.drawString("-----", getDistLeft()+myX(), getDistTop()+myY()+getDistBetween()*i);
                g.drawString("--/-- HP", getDistLeft()+myX(), getDistTop()+25+myY()+getDistBetween()*i);
                g.drawString("--/-- MP", getDistLeft()+myX(), getDistTop()+50+myY()+getDistBetween()*i);
            }
        }
        
                
    }
    
    public PartyMember getSelectedMember(){return p.getMember(getSelectorPosition());}
    public Party getParty(){return p;}
    public void updateParty(Party p){
        this.p=p;
        setSelectorMaxPos(p.getCurrentPartySize()-1);
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
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blackwind Dev",null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(3); //exit on close
        frame.setSize(640, 480);
        
        Party p = new Party(4);
        p.addMember(EntityLoader.loadPartyMember(0));
        MParty m = new MParty(p,0,0,400,480,Color.YELLOW);
        
        frame.add(m);
    }
}

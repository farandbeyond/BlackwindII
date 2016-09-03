/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground;

import Background.BattleActions.*;
import Background.Entities.Enemy;
import Background.Entities.EntityLoader;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Connor
 */
public class EnemyAI {
    public static final int DAMAGEFOCUS=0, ANYFOCUS=1, HEALFOCUS=2, BUFFFOCUS=3, SPELLFOCUS=4;
    static Random rand = new Random();
    Enemy e;
    int type;
    ArrayList<BattleAction> weightedSkillList;
    public EnemyAI(int type){
        this.type = type;
        rand.setSeed(System.currentTimeMillis());
    }
    
    public BattleAction getSkill(ArrayList<BattleAction> actions){
        for(BattleAction e:actions){
            //System.out.println(e.getName());
        }
        return actions.get(rand.nextInt(actions.size()));
    }
    public ArrayList<BattleAction> weighSkills(ArrayList<BattleAction> actions){
        ArrayList<BattleAction> actionsClone = (ArrayList<BattleAction>)actions.clone();
        //System.out.println(actionsClone.size());
        for(BattleAction e:actions){
            //System.out.println(e.getName());
            switch(type){
                case DAMAGEFOCUS: //uses damage MUCH more than any other skill
                    if(e.getClass()==PhysicalAction.class)
                        for(int i=0;i<10;i++)
                            actionsClone.add(e);
                    if(e.getClass()==DamageSpell.class)
                        for(int i=0;i<3;i++)
                            actionsClone.add(e);
                    break;
                case HEALFOCUS:
                    if(e.getClass()==HealingSpell.class)
                        for(int i=0;i<10;i++)
                            actionsClone.add(e);
                    if(e.getClass()==DamageSpell.class)
                        for(int i=0;i<2;i++)
                            actionsClone.add(e);
                    break;
                case BUFFFOCUS:
                    if(e.getClass()==EffectSpell.class)
                        for(int i=0;i<10;i++)
                            actionsClone.add(e);
                    break;
                case SPELLFOCUS:
                    if(e.getClass()==DamageSpell.class)
                        for(int i=0;i<10;i++)
                            actionsClone.add(e);
                    if(e.getClass()==HealingSpell.class)
                        for(int i=0;i<4;i++)
                            actionsClone.add(e);
                    if(e.getClass()==EffectSpell.class)
                        for(int i=0;i<5;i++)
                            actionsClone.add(e);
                    break;
                case ANYFOCUS: //uses anything equally
                    break;
            }
        }
        weightedSkillList = actionsClone;
        return actionsClone;
    }
    public BattleAction getAction(){
        if(weightedSkillList!=null)
            return getSkill(weightedSkillList);
        else
            return getSkill(weighSkills(e.getSkillList()));
    }
    
    public void setEnemy(Enemy e){this.e=e;}
    
    public static void main(String[] args) {
        Enemy e0 = EntityLoader.loadEnemy(100);
        e0.addSkill(BattleActionLoader.loadAction(BattleActionLoader.RAISEGUARD));
        e0.addSkill(BattleActionLoader.loadAction(BattleActionLoader.CURE));
        e0.addSkill(BattleActionLoader.loadAction(BattleActionLoader.DARK));
        e0.addSkill(BattleActionLoader.loadAction(BattleActionLoader.FRAILTY));
        e0.addSkill(BattleActionLoader.loadAction(BattleActionLoader.BRAVERY));
        e0.addSkill(BattleActionLoader.loadAction(BattleActionLoader.SLICE));
        
        Enemy e1 = EntityLoader.loadEnemy(100);
        e1.addSkill(BattleActionLoader.loadAction(BattleActionLoader.RAISEGUARD));
        e1.addSkill(BattleActionLoader.loadAction(BattleActionLoader.CURE));
        e1.addSkill(BattleActionLoader.loadAction(BattleActionLoader.DARK));
        e1.addSkill(BattleActionLoader.loadAction(BattleActionLoader.FRAILTY));
        e1.addSkill(BattleActionLoader.loadAction(BattleActionLoader.BRAVERY));
        e1.addSkill(BattleActionLoader.loadAction(BattleActionLoader.SLICE));
        
        Enemy e2 = EntityLoader.loadEnemy(100);
        e2.addSkill(BattleActionLoader.loadAction(BattleActionLoader.RAISEGUARD));
        e2.addSkill(BattleActionLoader.loadAction(BattleActionLoader.CURE));
        e2.addSkill(BattleActionLoader.loadAction(BattleActionLoader.DARK));
        e2.addSkill(BattleActionLoader.loadAction(BattleActionLoader.FRAILTY));
        e2.addSkill(BattleActionLoader.loadAction(BattleActionLoader.BRAVERY));
        e2.addSkill(BattleActionLoader.loadAction(BattleActionLoader.SLICE));
        
        Enemy e3 = EntityLoader.loadEnemy(100);
        e3.addSkill(BattleActionLoader.loadAction(BattleActionLoader.RAISEGUARD));
        e3.addSkill(BattleActionLoader.loadAction(BattleActionLoader.CURE));
        e3.addSkill(BattleActionLoader.loadAction(BattleActionLoader.DARK));
        e3.addSkill(BattleActionLoader.loadAction(BattleActionLoader.FRAILTY));
        e3.addSkill(BattleActionLoader.loadAction(BattleActionLoader.BRAVERY));
        e3.addSkill(BattleActionLoader.loadAction(BattleActionLoader.SLICE));
        
        Enemy e4 = EntityLoader.loadEnemy(100);
        e4.addSkill(BattleActionLoader.loadAction(BattleActionLoader.RAISEGUARD));
        e4.addSkill(BattleActionLoader.loadAction(BattleActionLoader.CURE));
        e4.addSkill(BattleActionLoader.loadAction(BattleActionLoader.DARK));
        e4.addSkill(BattleActionLoader.loadAction(BattleActionLoader.FRAILTY));
        e4.addSkill(BattleActionLoader.loadAction(BattleActionLoader.BRAVERY));
        e4.addSkill(BattleActionLoader.loadAction(BattleActionLoader.SLICE));
        
        e0.giveAI(new EnemyAI(EnemyAI.ANYFOCUS));
        e1.giveAI(new EnemyAI(EnemyAI.BUFFFOCUS));
        e2.giveAI(new EnemyAI(EnemyAI.DAMAGEFOCUS));
        e3.giveAI(new EnemyAI(EnemyAI.HEALFOCUS));
        e4.giveAI(new EnemyAI(EnemyAI.SPELLFOCUS));
        
        BattleAction[] actions = new BattleAction[5];
        System.out.println("\nEnemy 0\n");
        actions[0]=e0.getSkill();
        System.out.println("\nEnemy 1\n");
        actions[1]=e1.getSkill();
        System.out.println("\nEnemy 2\n");
        actions[2]=e2.getSkill();
        System.out.println("\nEnemy 3\n");
        actions[3]=e3.getSkill();
        System.out.println("\nEnemy 4\n");
        actions[4]=e4.getSkill();
        System.out.println("\nActions Selected\n");
        for(BattleAction b:actions){
            System.out.println(b.getName());
        }
        System.out.println("\nSpamm Enemy 0\n");
        System.out.println(e4.getSkill().getName());
        System.out.println(e4.getSkill().getName());
        System.out.println(e4.getSkill().getName());
        System.out.println(e4.getSkill().getName());
        System.out.println(e4.getSkill().getName());
        System.out.println(e4.getSkill().getName());
        System.out.println(e4.getSkill().getName());
        System.out.println(e4.getSkill().getName());
        System.out.println(e4.getSkill().getName());
    }
}

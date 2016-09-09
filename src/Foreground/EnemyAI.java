/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Foreground;

import Background.BattleActions.*;
import Background.Entities.*;
import Background.Items.Items;
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
    public ArrayList<BattleAction> weighSkills(ArrayList<BattleAction> actions, Set s, Party p){
        ArrayList<BattleAction> actionsClone = (ArrayList<BattleAction>)actions.clone();
        //System.out.println(actionsClone.size());
        for(BattleAction e:actions){
            //System.out.println(e.getName());
            switch(type){
                case DAMAGEFOCUS: //uses damage MUCH more than any other skill
                    if(e.getClass()==PhysicalAction.class)
                        setWeight(actionsClone,e,10);
                    if(e.getClass()==DamageSpell.class)
                        setWeight(actionsClone,e,3);
                    break;
                case HEALFOCUS:
                    if(e.getClass()==HealingSpell.class&&s.anyoneIsDamaged())
                        setWeight(actionsClone,e,10);
                    if(e.getClass()==DamageSpell.class)
                        setWeight(actionsClone,e,2);
                    break;
                case BUFFFOCUS:
                    if(e.getClass()==EffectSpell.class)
                        setWeight(actionsClone,e,10);
                    break;
                case SPELLFOCUS:
                    if(e.getClass()==DamageSpell.class)
                        setWeight(actionsClone,e,10);
                    if(e.getClass()==HealingSpell.class&&s.anyoneIsDamaged())
                        setWeight(actionsClone,e,4);
                    if(e.getClass()==EffectSpell.class)
                        setWeight(actionsClone,e,5);
                    break;
                case ANYFOCUS: //uses anything equally
                    break;
            }
        }
        weightedSkillList = actionsClone;
        return actionsClone;
    }
    private void setWeight(ArrayList<BattleAction> list, BattleAction skill, int weight){ 
        for(int i=0;i<weight;i++)
            list.add(skill);
    }
    public BattleAction getAction(Set s, Party p){
        if(weightedSkillList!=null)
            return getSkill(weightedSkillList);
        else
            return getSkill(weighSkills(e.getSkillList(),s,p));
    }
    
    public BattleEntity getTarget(BattleAction b, Set s, Party p){
        ArrayList<BattleEntity> possibleTargets = new ArrayList<>();
        //System.out.println(b.getName());
        //System.out.println(b.getClass().toGenericString());
        if(b.getClass()==SelfBuff.class||b.getClass()==DeathAction.class){
            return e;
        }else if(b.targetsAllies()){
            //System.out.println("Target Allies");
            if(b.getClass()==HealingSpell.class){
                for(Enemy en:s.getEnemyList()){
                    if(en.isDamaged()&&!en.isDead())
                        possibleTargets.add(en);
                }
            }else if(b.getClass()==EffectSpell.class){
                for(Enemy en:s.getEnemyList())
                    if(!en.isDead())
                        possibleTargets.add(en);
            }
        }else{
            //System.out.println("Target Enemies");
            if(b.getClass()==DamageSpell.class||b.getClass()==PhysicalAction.class){
                //System.out.println("Damaging ability found");
                for(PartyMember en:p.getMemberList()){
                    System.out.println(1+(en.getMissingHpPercentile()/20)*en.getElementModifier(b.getElement()));
                    //System.out.println();
                    //System.out.printf("%s: %d/%d\n", en.getName(),en.getStat(BattleEntity.HP).getStat(),en.getStat(BattleEntity.HP).getMax());
                    for(int i=0;i<1+(en.getMissingHpPercentile()/20)*en.getElementModifier(b.getElement());i++){
                        System.out.println(en.getName()+" added "+(i+1)+" times.");
                        if(!en.isDead())
                            possibleTargets.add(en);
                    }
                }
            }else if(b.getClass()==EffectSpell.class){
                for(PartyMember en:p.getMemberList())
                    if(!en.isDead())
                        possibleTargets.add(en);
            }
        }
        for(BattleEntity en:possibleTargets)
            System.out.println(en.getName());
        return possibleTargets.get(rand.nextInt(possibleTargets.size()));
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
        
        Party p = new Party(4);
        Set s = new Set(3);
        s.addEnemy(e4);
        s.addEnemy(e1);
        s.addEnemy(e2);
        p.addMember(EntityLoader.loadPartyMember(0));
        p.addMember(EntityLoader.loadPartyMember(1));
        p.addMember(EntityLoader.loadPartyMember(2));
        p.addMember(EntityLoader.loadPartyMember(3));
        p.getMember(0).equip(Items.Load(Items.MAGICCANE, 1), 0);
        
        BattleAction[] actions = new BattleAction[5];
        System.out.println("\nEnemy 0\n");
        actions[0]=e0.getAiSkill(s,p);
        System.out.println("\nEnemy 1\n");
        actions[1]=e1.getAiSkill(s,p);
        System.out.println("\nEnemy 2\n");
        actions[2]=e2.getAiSkill(s,p);
        System.out.println("\nEnemy 3\n");
        actions[3]=e3.getAiSkill(s,p);
        System.out.println("\nEnemy 4\n");
        actions[4]=e4.getAiSkill(s,p);
        System.out.println("\nActions Selected\n");
        for(BattleAction b:actions){
            System.out.println(b.getName());
        }
        System.out.println("\nSpamm Enemy 0\n");
        System.out.println(e4.getAiSkill(s,p).getName());
        System.out.println(e4.getAiSkill(s,p).getName());
        System.out.println(e4.getAiSkill(s,p).getName());
        System.out.println(e4.getAiSkill(s,p).getName());
        System.out.println(e4.getAiSkill(s,p).getName());
        System.out.println(e4.getAiSkill(s,p).getName());
        System.out.println(e4.getAiSkill(s,p).getName());
        System.out.println(e4.getAiSkill(s,p).getName());
        System.out.println(e4.getAiSkill(s,p).getName());
        

        
        p.damageMember(10, 0);
        s.damageEnemy(10, 0);
        for(int i=0;i<3;i++){
            BattleAction b = s.getEnemy(i).getAiSkill(s,p);
            BattleEntity e = s.getEnemy(i).getAiTarget(p, s, b);
            System.out.printf("%s used %s on %s\n",s.getEnemy(i).getName(),b.getName(),e.getName());
        }
        
        
    }
}

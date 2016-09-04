/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Entities;

import Background.BattleActions.BattleAction;
import Background.BattleActions.Spell;
import Background.Effects.Effect;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Connor
 */
public class BattleEntity {
    public static int HP=0,MP=1,STR=2, DEX=3, VIT=4, INT=5,RES=6;
    public static String[] statNames = {"HP","MP","Strength","Dexterity","Vitality","Intelligence","Resistance"};
    public static Random rand = new Random();
    String name;
    Stat[] stats;
    int element;
    ArrayList<Effect> effects;
    ArrayList<BattleAction> skills;
    boolean isDead;
    //used when loading an entity from a file
    public BattleEntity(String name,String hp, String mp, String str, String dex, String vit, String ine, String res, String element, String isDead, String actions){
        this.name = name;
        effects = new ArrayList<>();
        stats = new Stat[7];
        //skills = new ArrayList<>();
        //effects = new ArrayList<>();
        //this.equipment = new Equipment[4];
        stats[HP]=new Stat(hp);
        stats[MP]=new Stat(mp);
        stats[STR]=new Stat(str);
        stats[DEX]=new Stat(dex);
        stats[VIT]=new Stat(vit);
        stats[INT]=new Stat(ine);
        stats[RES]=new Stat(res);
        this.element = Integer.parseInt(element);
        this.isDead = isDead.equals("true")?true:false;
        skills = new ArrayList<>();
        //for(int i=1;i<actions.split("-").length;i++){
        //    addSkill(BattleActionLoader.loadAction(Integer.parseInt(actions.split("-")[i])));
        //}

    }
    //used when first creating an entity
    BattleEntity(String name, int baseHP, double hpGrowth,int baseMP, double mpGrowth, int baseStr, double strGrowth, int baseDex, double dexGrowth,int baseVit, double vitGrowth, int baseInt, double intGrowth, int baseRes, double resGrowth, int element){
        effects = new ArrayList<>();
        stats = new Stat[7];
        this.name= name;
        stats[HP] = new Stat(baseHP, hpGrowth,"HP");
        stats[MP] = new Stat(baseMP, mpGrowth,"MP");
        stats[STR] = new Stat(baseStr, strGrowth,"Strength");
        stats[DEX] = new Stat(baseDex, dexGrowth,"Dexterity");
        stats[VIT] = new Stat(baseVit, vitGrowth,"Vitality");
        stats[INT] = new Stat(baseInt, intGrowth,"Intelligence");
        stats[RES] = new Stat(baseRes, resGrowth,"Resistance");
        isDead = false;
        this.element = element;
        skills = new ArrayList<>();
    }
    //hp altering
    public void damage(int damage){
        stats[HP].damage(damage);
        if(stats[HP].getStat()<=0){
            isDead = true;
            stats[HP].setZero();
        }
    }
    public void heal(int heal){
        if(!isDead){
            stats[HP].heal(heal);
        }
    }
    public void raise(int heal){
        isDead = false;
        heal(heal);
    }
    public void rest(){
        raise(stats[HP].getMax());
        regainMp(stats[MP].getMax());
    }
    //mp altering
    public boolean canCast(Spell s){
        if(s.getCost()<=stats[MP].getStat())
            return true;
        return false;
    }
    public void useMp(int mpuse){
        stats[MP].damage(mpuse);
    }
    public void regainMp(int mpHeal){
        stats[MP].heal(mpHeal);
    }
    //stat altering
    public void increaseStat(int statID, int increase){
        stats[statID].buff(increase);
    }
    public void decreaseStat(int statID, int decrease){
        stats[statID].debuff(decrease);
    }
    //effect altering
    public void giveEffect(Effect e){
        for(Effect f:effects){
            if(f.getName().equals(e.getName())){
                f.setDuration(e.getDuration());
                return;
            }
        }
        effects.add(e);
        e.assign(this);
    }
    public void removeEffect(int e){
        effects.get(e).remove(this);
        effects.remove(e);
    }
    public void tickAllEffects(){
        for(int i=effects.size()-1;i>=0;i--){
            effects.get(i).onTick(this);
            System.out.println(effects.get(i).getName());
            if(effects.get(i).getDuration()==0){
                removeEffect(i);
            }
        }
    }
    //skill altering
    public void addSkill(BattleAction e){
        skills.add(e);
        e.setCaster(this);
    }
    //gets
    public BattleAction getSkill(int index){
        try{
            return skills.get(index);
        }catch(IndexOutOfBoundsException e){
            throw new EntityBadIndex("Bad Index getting a skill");
        }
    }
    public ArrayList<BattleAction> getSkillList(){
        return skills;
    }
    public Effect getEffect(int index){
        try{
            return effects.get(index);
        }catch(IndexOutOfBoundsException e){
            throw new EntityBadIndex(String.format("Bad effect index: %d",index));
        }
    }
    public Stat getStat(int statID){
        try{
            return stats[statID];
        }catch(IndexOutOfBoundsException e){
            throw new EntityBadIndex(String.format("Bad Index found accessing stat: %d", statID));
        }
    }
    public String getName(){return name;}
    public int getElement(){return element;}
    public boolean isDead(){return isDead;}
    public boolean isDamaged(){
        if(getStat(HP).getStat()==getStat(HP).getMax())
            return false;
        return true;
    }
    public boolean isBelowMaxMana(){
        if(getStat(MP).getStat()==getStat(MP).getMax())
            return false;
        return true;
    }
    public double getElementModifier(int attackingElement){
        return Element.handler(attackingElement, element);
    }
    public int getHpPercentile(){
        int pc = getStat(HP).getStat()*100/getStat(HP).getMax();
        return pc;
    }
    public int getMissingHpPercentile(){
        return 100-getHpPercentile();
    }
    //prints for testing
    public void printhpmp(){
        System.out.printf("%d/%d hp\n", stats[HP].getStat(),stats[HP].getMax());
        System.out.printf("%d/%d mp\n", stats[MP].getStat(),stats[MP].getMax());
    }
    public void printAllStats(){
        for(Stat s:stats){
            System.out.printf("%s:%d/%d%n",s.getShortName(), s.getStat(),s.getMax());
        }
    }
    public class Stat{
        //basestat is the natural stat. modified base functions a the "max", mostly used for stats like HP. currentStat is this, but changes constantly (currentHP)
        //growth is the natural growth upon level up, and overflow holds any excess. (eg a growth of 2.6 would increase base by 2 and overflow by .6)
        //overflow is used upon further levelling to increase base
        int baseStat;
        int modifiedBase;
        int currentStat;
        double growth;
        double overflow;
        String name;
        //used when first creating a stat
        Stat(int base, double growth, String name){
            baseStat = base;
            currentStat = base;
            this.growth = growth;
            overflow = 0;
            modifiedBase = base;
            this.name = name;
        }
        //used when loading a stat
        Stat(int base, int current, double growth, double overflow, String name){
            baseStat=base;
            modifiedBase = base;
            currentStat = current;
            this.growth = growth;
            this.overflow = overflow;
            this.name = name;
        }
        public void levelUp(){
            int change = 0;
            overflow+=growth;
            
            overflow+=rand.nextInt(2);
            while(overflow>=1.0){
                overflow--;
                baseStat++;
                modifiedBase++;
                currentStat++;
                change++;
            }
            System.out.printf("%s increased by %d\n",name,change);
        }
        //stat altering
        public void buff(int increase){
            currentStat+=increase;
            modifiedBase+=increase;
        }
        public void debuff(int decrease){
            currentStat-=decrease;
            modifiedBase-=decrease;
            if(currentStat<=0)
                currentStat=1;
        }
        public void damage(int decrease){
            currentStat-=decrease;
        }
        public void heal(int increase){
            currentStat+=increase;
            if(currentStat>modifiedBase)
                currentStat = modifiedBase;
        }
        //gets
        public int getBase(){return baseStat;}
        public int getMax(){return modifiedBase;}
        public int getStat(){return currentStat;}
        public int getStatModifier(){return modifiedBase-baseStat;}
        public double getGrowth(){return growth;}
        public double getOverflow(){return overflow;}
        public String getName(){return name;}
        public String getShortName(){return name.length()<3?name:name.subSequence(0, 3).toString().toUpperCase();}
        //sets
        public void setBase(int base){
            baseStat = base;
        }
        public void setGrowth(double newGrowth){
            growth = newGrowth;
        }
        public void setZero(){
            currentStat = 0;
        }
        //saving/loading
        public String save(){
            //                  current/max (unmodified)
            return String.format("%s-%d/%d-%f-%f", name, currentStat, baseStat, growth, overflow);
        }
        public Stat(String statData){
            String[] data = statData.split("-");
            name = data[0];
            growth = Double.parseDouble(data[2]);
            overflow = Double.parseDouble(data[3]);
            baseStat = Integer.parseInt(data[1].split("/")[1]);
            currentStat = Integer.parseInt(data[1].split("/")[0]);
            modifiedBase = baseStat;
        }
    }
    public class EntityBadIndex extends RuntimeException{
        EntityBadIndex(String err){
            super(err);
        }
    }
    public class EntityNullError extends RuntimeException{
        EntityNullError(String err){
            super(err);
        }
    }
    public static void main(String[] args) {
        System.out.println("Test 01");
        System.out.println("build");
        BattleEntity wilson = new BattleEntity("Wilson",20,2.5,10,1.25,10,1.9,5,0.5,10,1.9,3,0.7,8,1.7,0);
        System.out.println("Test 02");
        System.out.println("catch the exception");
        try{
            wilson.getStat(-1);
            System.out.println("Accessed stat");
        }catch(EntityBadIndex e){
            System.out.println(e);
        }
        System.out.println("Test 03");
        System.out.println("Stat stuff");
        for(int i=0;i<7;i++){
            System.out.println(wilson.getStat(i).getBase());
        }
        Stat vit = wilson.getStat(STR);
        System.out.println(vit.getName());
        System.out.println(vit.getShortName());
        System.out.println(vit.save());
        System.out.println("Test 04");
        System.out.println("heal and damage entity");
        wilson.printhpmp();
        wilson.damage(10);
        wilson.printhpmp();
        wilson.heal(5);
        wilson.printhpmp();
        wilson.heal(15);
        wilson.printhpmp();
        wilson.damage(35);
        wilson.printhpmp();
        wilson.heal(5);
        wilson.printhpmp();
        wilson.raise(5);
        wilson.printhpmp();
        wilson.heal(10);
        wilson.printhpmp();
        //wilson.heal(10);
        
        System.out.println(wilson.getMissingHpPercentile());
        System.out.println(wilson.getHpPercentile());
    }
}

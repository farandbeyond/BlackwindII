/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Entities;

import Background.Entities.BattleEntity.Stat;
/**
 *
 * @author Connor
 */
public class Set {
    Enemy[] members;
    int maxSetSize;
    public Set(int setSize){
        members = new Enemy[setSize];
        this.maxSetSize=setSize;
    }
    public void addEnemy(Enemy p){
        if(p==null)
            return;
        for(int i=0;i<maxSetSize;i++){
            if(members[i]==null){
                members[i]=p;
                return;
            }
        }
        throw new SetFull(String.format("Could not add %s to set: set is full",p.getName()));            
    }
    public Enemy removeEnemy(int slot){
        if(members[slot]!=null){
            Enemy p = members[slot];
            members[slot]=null;
            sortSet();
            return p;
        }
        throw new SetNoEnemy(String.format("No set member found at slot %d",slot));
    }
    public void swapEnemys(int i, int i2){
        if(getEnemy(i)!=null&&getEnemy(i2)!=null){
            Enemy p = members[i];
            members[i] = members[i2];
            members[i2] = p;
        }
    }
    
    public void damageEnemy(int damage, int slot){
        getEnemy(slot).damage(damage);
    }
    public void healEnemy(int heal, int slot){
        getEnemy(slot).heal(heal);
    }
    public void useEnemyMp(int mp, int slot){
        getEnemy(slot).useMp(mp);
    }
    public void raiseEnemy(int heal, int slot){
        getEnemy(slot).raise(heal);
    }
    public void sortSet(){
        for(int i=0;i<maxSetSize;i++){
            if(members[i]==null){
                try{
                    if(members[i+1]!=null){
                        members[i] = members[i+1];
                        members[i+1] = null;
                    }
                }catch(IndexOutOfBoundsException e){
                    members[i]=null;
                }
            }
        }
    }
    
    public Enemy getEnemy(int slot){
        if(slot<0||slot>maxSetSize){
            throw new SetBadIndex(String.format("Invalid index for getting member: %d",slot));
        }
        if(members[slot]==null)
            throw new SetNoEnemy(String.format("No set member at valid index %d",slot));
        return members[slot];
    }
    public Enemy[] getEnemyList(){return members;}
    public BattleEntity.Stat getEnemysStat(int member, int StatID){
        return getEnemy(member).getStat(StatID);
    }
    public String getHpMaxHp(int member){
        return String.format("%d/%d", getEnemy(member).getStat(BattleEntity.HP).getStat(),getEnemy(member).getStat(BattleEntity.HP).getMax());
    }
    public String getMpMaxMp(int member){
        return String.format("%d/%d", getEnemy(member).getStat(BattleEntity.MP).getStat(),getEnemy(member).getStat(BattleEntity.MP).getMax());
    }
    public int getMaxSetSize(){return maxSetSize;}
    public int getCurrentSetSize(){
        int i=0;
        for(BattleEntity member:members){
            try{
                member.getName();
                i++;
            }catch(NullPointerException e){
                return i;
            }
        }
        return i;
    }
    public boolean anyoneIsDamaged(){
        for(Enemy m:members)
            if(m.isDamaged())
                return true;
        return false;
    }
    public boolean someoneIsAlive(){
        for(int i=0;i<members.length;i++){
            if(members[i]!=null)
                if(!members[i].isDead())
                    return true;
        }
        return false;
    }
    
    public class SetFull extends RuntimeException{
        SetFull(String err){
            super(err);
        }
    }
    public class SetNoEnemy extends RuntimeException{
        SetNoEnemy(String err){
            super(err);
        }
    }
    public class SetBadIndex extends RuntimeException{
        SetBadIndex(String err){
            super(err);
        }
    }
}



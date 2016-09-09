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
public class Party {
    PartyMember[] members;
    int maxPartySize;
    public Party(int partySize){
        members = new PartyMember[partySize];
        this.maxPartySize=partySize;
    }
    public void addMember(PartyMember p){
        if(p==null)
            return;
        for(int i=0;i<maxPartySize;i++){
            if(members[i]==null){
                members[i]=p;
                return;
            }
        }
        throw new PartyFull(String.format("Could not add %s to party: party is full",p.getName()));            
    }
    public PartyMember removeMember(int slot){
        if(members[slot]!=null){
            PartyMember p = members[slot];
            members[slot]=null;
            sortParty();
            return p;
        }
        throw new PartyNoMember(String.format("No party member found at slot %d",slot));
    }
    public void swapMembers(int i, int i2){
        if(getMember(i)!=null&&getMember(i2)!=null){
            PartyMember p = members[i];
            members[i] = members[i2];
            members[i2] = p;
        }
    }
    
    public void damageMember(int damage, int slot){
        getMember(slot).damage(damage);
    }
    public void healMember(int heal, int slot){
        getMember(slot).heal(heal);
    }
    public void giveExp(int exp, int slot){
        getMember(slot).gainExp(exp);
    }
    public void restAllMembers(){
        for(PartyMember p:members)
            p.rest();
    }
    public void useMemberMp(int mp, int slot){
        getMember(slot).useMp(mp);
    }
    public void raiseMember(int heal, int slot){
        getMember(slot).raise(heal);
    }
    
    public void sortParty(){
        for(int i=0;i<maxPartySize;i++){
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
    public PartyMember getMember(int slot){
        if(slot<0||slot>maxPartySize){
            throw new PartyBadIndex(String.format("Invalid index for getting member: %d",slot));
        }
        if(members[slot]==null)
            throw new PartyNoMember(String.format("No party member at valid index %d",slot));
        return members[slot];
    }
    public PartyMember[] getMemberList(){return members;}
    public Stat getMembersStat(int member, int StatID){
        return getMember(member).getStat(StatID);
    }
    public String getHpMaxHp(int member){
        return String.format("%d/%d", getMember(member).getStat(BattleEntity.HP).getStat(),getMember(member).getStat(BattleEntity.HP).getMax());
    }
    public String getMpMaxMp(int member){
        return String.format("%d/%d", getMember(member).getStat(BattleEntity.MP).getStat(),getMember(member).getStat(BattleEntity.MP).getMax());
    }
    public int getMaxPartySize(){return maxPartySize;}
    public int getCurrentPartySize(){
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
        for(PartyMember m:members)
            if(m.isDamaged())
                return true;
        return false;
    }
    
    public class PartyFull extends RuntimeException{
        PartyFull(String err){
            super(err);
        }
    }
    public class PartyNoMember extends RuntimeException{
        PartyNoMember(String err){
            super(err);
        }
    }
    public class PartyBadIndex extends RuntimeException{
        PartyBadIndex(String err){
            super(err);
        }
    }
}

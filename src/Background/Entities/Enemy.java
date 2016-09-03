/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background.Entities;

/**
 *
 * @author Connor
 */
public class Enemy extends BattleEntity{
    //BattleAction listOfActions
    int xpGiven;
    int goldGiven;
    //Item itemDropped;
    public Enemy(String name, int baseHP, double hpGrowth, int baseMP, double mpGrowth, int baseStr, double strGrowth, int baseDex, double dexGrowth, int baseVit, double vitGrowth, int baseInt, double intGrowth, int baseRes, double resGrowth, int element, int xpGiven, int goldGiven) {
        super(name, baseHP, hpGrowth, baseMP, mpGrowth, baseStr, strGrowth, baseDex, dexGrowth, baseVit, vitGrowth, baseInt, intGrowth, baseRes, resGrowth, element);
        this.xpGiven = xpGiven;
        this.goldGiven = goldGiven;
    }
    
}

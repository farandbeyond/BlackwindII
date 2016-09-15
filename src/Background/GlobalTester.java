/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background;
import Background.Entities.*;
import Background.Items.ItemTester;
/**
 *
 * @author Connor
 */

//this class is outdated. no point in keeping it around other than to preserve file structure
public class GlobalTester {
    public static void main(String[] args){
        BattleEntity.main(args);
        PartyMember.main(args);
        ItemTester.main(args);
        PartyTester.main(args);
    }
}

package lai;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import lai.content.*;
import lai.content.blocks.*;

public class LaiMod extends Mod{

    public LaiMod(){

    }

    @Override
    public void loadContent(){
        LaiStatus.load();
        LaiItems.load();
        LaiLiquids.load();
        new LaiSchematics().load();
        LaiUnits.load();
        //Blocks
        LaiBlocksCrafting.load();
        LaiBlocksLiquids.load();
        LaiBlocksTurrets.load();
        LaiBlocks.load();
        //t.g
        LaiPlanets.load();
        LaiSectors.load();  
        //TechTree
        MathurakTechTree.load();        
    }   
    @Override
    public void init(){
        LaiTeams.load();
    }

}

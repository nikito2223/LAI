package lai;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import lai.type.LoadAnnoProcessor;
import lai.content.*;
import lai.content.blocks.*;
import lai.ui.*;

public class LaiMod extends Mod{

    public LaiMod(){
        Events.on(EventType.ClientLoadEvent.class, e -> {
            clientLoaded();
        });
        Events.on(EventType.ContentInitEvent.class, e -> {
            LoadAnnoProcessor.begin("lai");
        });
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
        LaiEnvironmentBlocks.load();
        LaiBlocks.load();
        //t.g
        LaiPlanets.load();
        LaiSectors.load();  
        //TechTree
        MathexisTechTree.load();        
    }   
    @Override
    public void init(){
        LaiTeams.load();
    }


    public void clientLoaded() {
        UIHandler.init();
    }
}

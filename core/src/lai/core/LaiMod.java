package lai.core;

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

import lai.type.*;
import lai.graphics.*;

public class LaiMod extends Mod{
    
    public LaiMod(){
        Events.on(EventType.ClientLoadEvent.class, e -> {
            UIHandler.init();      
        });
        Events.on(EventType.ContentInitEvent.class, e -> {
            LoadAnnoProcessor.begin("lai");
        });

        LaiVars.core = this;
    }

    @Override
    public void loadContent() {
        LAIShaders.init();
        CLayer.init();


        if(DebugLaiMode.isDebuging == false){
            defaultLoad();
        }else {
            DebugLaiMode.starter();
        }
    }

    private void defaultLoad(){
        LaiSectors.load();
        LaiItems.load();
        LaiLiquids.load();
        new LaiSchematics().load();
        LaiUnits.load();
        //Blocks
        LaiBlocksCrafting.load();
        LaiBlocksLiquids.load();
        LaiBlocksTurrets.load();
        LaiEnvironmentBlocks.load();
        LaiBlocksDistribution.load();
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
}

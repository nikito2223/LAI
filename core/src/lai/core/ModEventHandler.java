package lai.core;

import lai.ui.dialogs.CircularResearchDialog;
import lai.ui.CoreLiquidDisplay;
import arc.Events;
import arc.util.Log;
import arc.util.Reflect;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.ui.fragments.MenuFragment;
import mindustry.world.meta.Env;

import java.lang.reflect.Field;
import java.util.Map;


public class ModEventHandler {
    public static CircularResearchDialog techDialog;
    public static void load(){
        techDialog = new CircularResearchDialog();
    }
}
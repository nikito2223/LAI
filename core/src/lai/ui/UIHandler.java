package lai.ui;

import arc.Core;
import arc.graphics.Color;
import arc.input.KeyCode;
import arc.math.Mathf;
import arc.scene.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.mod.Mods;
import mindustry.ui.*;
import mindustry.ui.dialogs.PlanetDialog;
import lai.ui.dialogs.DisnablePlanetDialog;
import arc.scene.ui.layout.WidgetGroup;

import lai.ui.*;
import lai.ui.fragments.*;

import static arc.Core.*;
import static mindustry.Vars.*;
import static lai.ui.dialogs.DisnablePlanetDialog.*;

public class UIHandler {

    public static LaiHudFragment laihudfrag;
    public static LaiMenuFragment modmenufrag;
    public static DisnablePlanetDialog dpd;

	public static void init() {
		modifyEditorUI();
        modifyCoreTable();
        dpd.init();

/*        Core.app.post(() -> {
            modmenufrag = new LaiMenuFragment();
            //Vars.ui.menufrag = modmenufrag; // заменяем стандартное меню
            modmenufrag.build(Vars.ui.menuGroup); // строим меню
        });
*/
	}

    static void modifyCoreTable(){
        Group parent = ui.hudGroup; 

        laihudfrag = new LaiHudFragment();
        laihudfrag.build(parent);
    }

	static void modifyEditorUI() {
        ui.editor.shown(() -> {
            // java sucks
            Element teambuttons = ui.editor.getChildren().get(0);
            teambuttons = ((Group)teambuttons).getChildren().get(0);
            teambuttons = ((Group)teambuttons).getChildren().get(0);

            ((Table)teambuttons).row();

            for (int i = 69; i <= 69; i++) {
                Team team = Team.get(i);

                ImageButton button = new ImageButton(Tex.whiteui, Styles.clearNoneTogglei);
                button.margin(4f);
                button.getImageCell().grow();
                button.getStyle().imageUpColor = team.color;
                button.clicked(() -> editor.drawTeam = team);
                button.update(() -> button.setChecked(editor.drawTeam == team));

                ((Table)teambuttons).add(button);
            }
        });
    }


}
package lai.ui.fragments;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.ImageButton.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ui.fragments.*;
import mindustry.core.GameState.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.*;
import mindustry.net.Packets.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.world.meta.*;
import mindustry.core.*;
import static mindustry.Vars.*;
import static mindustry.gen.Tex.*;


import lai.ui.*;

public class LaiHudFragment {
	public CoreLiquidDisplay coreLiquid = new CoreLiquidDisplay();
	private final float pauseHeight = 36f; // стандартная высота ванильной паузы


	public void build(Group parent){

		Events.on(WorldLoadEvent.class, e -> {
		    Core.settings.put("core-show-items", true);
		    coreLiquid.clear();
		});
		Events.on(ResetEvent.class, e -> {
		    ui.hudfrag.coreItems.resetUsed();  // ванильный reset
		    ui.hudfrag.coreItems.clear();      // очистка UI
		    coreLiquid.clear();     // очистка жидкости, чтобы не оставалась со старой карты
		});


		parent.fill(t -> {
		    t.top();
		
		    if(Core.settings.getBool("macnotch")){
		        t.margin(macNotchHeight);
		    }

		    t.visible(() -> ui.hudfrag.shown);

			t.name = "coreinfo";

			t.collapser(v -> v.add().height(pauseHeight), 
    		() -> state.isPaused() && !netServer.isWaitingForPlayers()).row();

		
		    Boolp showItems = () -> Core.settings.getBool("core-show-items", true);
		    Boolp showLiquids = () -> !Core.settings.getBool("core-show-items", true);

		    t.toBack();

		    t.table(main -> {
		        main.top().left();
		
		        main.table(view -> {
		            view.top().center();
		
		            view.update(() -> {
		                view.clearChildren();
		
		                if(showItems.get()){
		                    view.add(ui.hudfrag.coreItems).top().left();
		                }else{
		                    view.add(coreLiquid).top().left();
		                }
		            });
		        }).growX().padBottom(10f);
		
		        main.row();
		
		        main.table(btns -> {
		            btns.defaults().height(48f).width(48f).pad(4f);
		
		            btns.button(b -> {
		                b.image(Items.copper.uiIcon).size(24f).padRight(4f);
		            }, Styles.clearTogglei, () -> {
		                Core.settings.put("core-show-items", true);
		            }).update(b -> b.setChecked(showItems.get()));
		
		            btns.button(b -> {
		                b.image(Liquids.water.uiIcon).size(24f).padRight(4f);
		            }, Styles.clearTogglei, () -> {
		                Core.settings.put("core-show-items", false);
		            }).update(b -> b.setChecked(showLiquids.get()));
		
		        }).bottom();

		    })
		    .growX()
		    .row();


		});

	}
}
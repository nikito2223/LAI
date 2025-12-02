package lai.ui;

import arc.*;
import arc.Graphics.*;
import arc.Input.*;
import arc.assets.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.input.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.TextField.*;
import arc.scene.ui.Tooltip.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.scene.ui.layout.*;
import arc.struct.ObjectSet;
import mindustry.Vars;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock.CoreBuild;
import mindustry.type.Liquid;

import mindustry.core.UI;
import mindustry.ui.*;

import mindustry.world.blocks.storage.CoreBlock.CoreBuild;
import lai.world.blocks.storage.CoreBlockLiquid.CoreBlockLiquidBuild;


import static mindustry.Vars.*;

public class CoreLiquidDisplay extends Table{
    private final ObjectSet<Liquid> usedLiquids = new ObjectSet<>();
    private CoreBlockLiquidBuild core;
    private float timer = 0f;

    public CoreLiquidDisplay(){
        rebuild();
    }

    public void resetUsed(){
        usedLiquids.clear();
        background(null);
    }

    String formatLiquid(float amount){
        if(amount < 1f){
            return (int)(amount * 1000) + " mL";        // 0.5 → 500 mL
        }else if(amount < 1000f){
            return Strings.autoFixed(amount, 1) + " L";  // 20 → 20.0 L
        }else if(amount < 1_000_000f){
            return Strings.autoFixed(amount / 1000f, 1) + " kL"; // 2500 → 2.5 kL
        }else{
            return Strings.autoFixed(amount / 1_000_000f, 2) + " ML"; // мега-литры
        }
    }


    @Override
    public void act(float delta){
        super.act(delta);
    
        timer += delta;
        if(timer >= 0.2f){ // обновлять 5 раз/сек хватит
            timer = 0f;
            rebuild();
        }
    }

    void rebuild(){
        clear();

        Seq<Liquid> liquids = new Seq<>();

        // собираем все жидкости из всех ядер команды
        for(CoreBuild c : player.team().cores()){
            if(c instanceof CoreBlockLiquidBuild lc){
                for(Liquid liq : Vars.content.liquids()){
                    if(lc.liquids.get(liq) > 0 && !liquids.contains(liq)){
                        liquids.add(liq);
                    }
                }
            }
        }


        if(liquids.isEmpty()) return;

        background(Styles.black6);
        margin(4);

        int i = 0;
        for(Liquid liq : liquids){
            // иконка жидкости
            image(liq.uiIcon).size(iconSmall).padRight(3)
                .tooltip(t -> {
                    t.background(Styles.black6).margin(4f);
                    t.add(liq.localizedName).row();
                    // суммарное количество жидкости во всех ядрах
                    float sum = 0;
                    for(CoreBuild c : player.team().cores()){
                        if(c instanceof CoreBlockLiquidBuild lc){
                            sum += lc.liquids.get(liq);
                        }
                    }
                    t.add(formatLiquid(sum)).left();
                });

            // суммарное количество жидкости во всех ядрах
            label(() -> {
                float sum = 0;
                for(CoreBuild c : player.team().cores()){
                    if(c instanceof CoreBlockLiquidBuild lc){
                        sum += lc.liquids.get(liq);
                    }
                }
                return formatLiquid(sum);
            }).padRight(3).minWidth(52f).left();

            if(++i % 4 == 0) row();
        }
    }
}

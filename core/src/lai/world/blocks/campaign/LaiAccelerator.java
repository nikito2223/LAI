package lai.world.blocks.campaign;

import arc.*;
import arc.Graphics.*;
import arc.Graphics.Cursor.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import lai.content.*;
import mindustry.world.blocks.campaign.Accelerator;

import static mindustry.Vars.*;

public class LaiAccelerator extends Block{
    public TextureRegion arrowRegion;
    //public static Texture arrow;
    public static boolean planetUpdate = false;
    //TODO dynamic
    public Block launching = LaiBlocks.coreCaser;
    public int[] capacities = {};

    public LaiAccelerator(String name){
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        itemCapacity = 8000;
        configurable = true;
        arrowRegion = Core.atlas.find(name + "-arrow");
    }

    @Override
    public void init(){
        itemCapacity = 0;
        capacities = new int[content.items().size];
        for(ItemStack stack : launching.requirements){
            capacities[stack.item.id] = stack.amount;
            itemCapacity += stack.amount;
        }
        consumeItems(launching.requirements);
        super.init();
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    @SuppressWarnings("unused")
    public class AcceleratorBuild extends Building{
        public float heat, statusLerp;

        @Override
        public void updateTile(){
            super.updateTile();
            heat = Mathf.lerpDelta(heat, efficiency, 0.07f);
            statusLerp = Mathf.lerpDelta(statusLerp, power.status, 0.07f);
        }

        @Override
        public void draw(){
            super.draw();

            for(int l = 0; l < 4; l++){
                
                 Draw.color(Tmp.c1.set(Pal.darkMetal).lerp(team.color, statusLerp), Pal.darkMetal, Mathf.absin(Time.time + l*50f, 10f, 1f));

                for(int i = 0; i < 4; i++){
                    
                }
            }

            if(heat < 0.0001f) return;

            float rad = size * tilesize / 2f * 0.74f;
            float scl = 1f;

            Draw.z(Layer.bullet - 0.0001f);
            Lines.stroke(1.75f * heat, Pal.accent);
            Lines.square(x, y, rad * 1.22f, 45f);

            Lines.stroke(3f * heat, Pal.accent);
            Lines.square(x, y, rad, Time.time / scl);
            Lines.square(x, y, rad, -Time.time / scl);

            Draw.color(team.color);
            Draw.alpha(Mathf.clamp(heat * 3f));

            for(int i = 0; i < 4; i++){
                float rot = i*90f + 45f + (-Time.time /3f)%360f;
                float length = 26f * heat;
                Draw.rect(arrowRegion, x + Angles.trnsx(rot, length), y + Angles.trnsy(rot, length), rot + 180f);
            }

            Draw.reset();
        }

        @Override
        public Cursor getCursor(){
            return !state.isCampaign() || efficiency <= 0f ? SystemCursor.arrow : super.getCursor();
        }

        @Override
        public void buildConfiguration(Table table){
            deselect();

            if(!state.isCampaign() || efficiency <= 0f) return;

            if(planetUpdate == true) {
                ui.planet.showPlanetLaunch(state.rules.sector, sector -> {
                    //TODO cutscene, etc...

                    //TODO should consume resources based on destination schem
                    consume();

                    universe.clearLoadoutInfo();
                    universe.updateLoadout(sector.planet.generator.defaultLoadout.findCore(), sector.planet.generator.defaultLoadout);
                });

                Events.fire(Trigger.acceleratorUse);
            }
        }

        @Override
        public int getMaximumAccepted(Item item){
            return capacities[item.id];
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return items.get(item) < getMaximumAccepted(item);
        }
    }
}

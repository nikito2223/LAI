package lai.world.blocks.production;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.world.blocks.production.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.meta.*;

import lai.world.meta.*;


public class AtmosphericExtractor extends GenericCrafter {
	public Seq<LiquidPlan> plans = new Seq<>(3);

	public AtmosphericExtractor(String name){
		super(name);
		update = true;
        solid = true;
        hasPower = true;
        hasLiquids = true;
        hasItems = false;
		configurable = true;
		outputsLiquid = true;
		saveConfig = true;
		liquidCapacity = 150f;
        envEnabled = Env.any;

        clearOnDoubleTap = true;
        outputsPayload = true;

        config(Integer.class, (AtmosphericExtractorBuild tile, Integer index) -> {
            if (index >= 0 && index < plans.size) {
                tile.selectedPlan = plans.get(index);
            }
        });

	}

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(Stat.liquidCapacity);

        stats.add(Stat.output, table -> {
            table.row();

            for(var plan : plans){
                table.table(Styles.grayPanel, t -> {

                    if(plan.liquid.unlockedNow()){
                        t.image(plan.liquid.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit).with(i -> LaiValues.withTooltip(i, plan.liquid));
                        t.table(info -> {
                            info.add(plan.liquid.localizedName).left();
                            info.row();
                            info.add(Strings.autoFixed(plan.time / 60f, 1) + " " + Core.bundle.get("unit.seconds")).color(Color.lightGray);
                        }).left();
                    }else{
                        t.image(Icon.lock).color(Pal.darkerGray).size(40);
                    }
                }).growX().pad(5);
                table.row();
            }
        });
    }
	@Override
	public void setBars() {
	    super.setBars();
	
	   //Прогресс добычи газа
        addBar("progress", (AtmosphericExtractorBuild entity) -> new Bar(
            () -> Core.bundle.format("bar.progress", (int)(entity.progress / entity.selectedPlan.time * 100)),
            () -> entity.selectedPlan.liquid.color, // Цвет = цвет выбранного газа
            () -> Mathf.clamp(entity.progress / entity.selectedPlan.time) // Ограничение от 0 до 1
        ));

        
		
    	// Заполненность бака с газом
    	addBar("liquid", (AtmosphericExtractorBuild entity) -> new Bar(
    	    () -> (int) (entity.liquids.get(entity.selectedPlan.liquid) / liquidCapacity * 100) + "%",
    	    () -> entity.selectedPlan.liquid.color, // Цвет = цвет газа
    	    () -> Mathf.clamp(entity.liquids.get(entity.selectedPlan.liquid) / liquidCapacity) // Заполнение от 0 до 1
    	));
	}

	public static class LiquidPlan{
        public Liquid liquid;
        public float amount;
        public float time;

        public LiquidPlan(Liquid liquid, float amount, float time){
            this.liquid = liquid;
            this.amount = amount;
            this.time = time;
        }
    }

	public class AtmosphericExtractorBuild extends GenericCrafterBuild {
		public LiquidPlan selectedPlan = plans.first(); // Стандартный газ
        public float progress = 0f; // Прогресс добычи


        @Override
        public void updateTile() {
    		if (selectedPlan == null || liquids == null) return;
		
    		// Обновляем прогресс
    		if (liquids.get(selectedPlan.liquid) < liquidCapacity) {
    		    progress += edelta() * selectedPlan.time;
    		}
		
    		// Добыча завершена
    		if (progress >= selectedPlan.time) {
    		    progress = 0f;
    		    liquids.add(selectedPlan.liquid, selectedPlan.amount);
    		}
		
    		// !!! Важно: Сливаем жидкость В ЛЮБОМ СЛУЧАЕ, даже если прогресс не идёт
            
    		dumpLiquid(selectedPlan.liquid);
        }

		@Override
        public void buildConfiguration(Table table) {
            table.clear(); // Очистка интерфейса перед отрисовкой

            for (int i = 0; i < plans.size; i++) {
                int index = i;
                LiquidPlan plan = plans.get(i);

                ImageButton button = new ImageButton(new TextureRegionDrawable(plan.liquid.uiIcon));
                button.clicked(() -> configure(index));

                table.add(button).size(50f); // Размещаем кнопки в один ряд
            }

        }

        @Override
        public Object config() {
            return plans.indexOf(selectedPlan); // Сохраняем индекс газа
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            int index = read.i();
            if (index >= 0 && index < plans.size) {
                selectedPlan = plans.get(index);
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(plans.indexOf(selectedPlan)); // Записываем индекс
        }
	}
}
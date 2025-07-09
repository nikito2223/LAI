package lai.world.blocks.units;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.io.*;
import arc.scene.ui.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;

import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

import mindustry.Vars;
import mindustry.gen.*;
import mindustry.type.UnitType;
import mindustry.content.*;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BlockGroup;
import arc.struct.Seq;
import arc.func.Boolf;
import arc.math.geom.Vec2;
import arc.graphics.g2d.Lines;
import mindustry.ui.*;
import arc.scene.ui.layout.Table;
import arc.scene.ui.Button;
import mindustry.input.*;

import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

import lai.graphics.*;
import lai.world.*;
import lai.content.*;
import static lai.content.LaiFx.*;

import static mindustry.Vars.*;

public class Replicator extends LaiBlock {
    public float buildTime = 60f * 8f;
    public float productionTime = 300f; // Время производства дрона
    public float distance = 25f;
    public UnitType droneType = LaiUnits.scanningDrone;
    public Seq<AmmunitionPlan> AmmunitionPlans = new Seq<>(3);

    public TextureRegion ammunition;
    public TextureRegion top;

    public Replicator(String name) {
        super(name);
        update = true;
        solid = true;
        hasPower = true;
        configurable = true;
        consumesPower = true;
        AmmunitionPlans = Seq.with(
            new AmmunitionPlan(Color.blue, 25f, 22f),
            new AmmunitionPlan(Color.red, 45f, 62f)
        );

    }

    @Override
    public void init() {
        super.init();
        ammunition = Core.atlas.find(name + "-ammunition");
        top = Core.atlas.find(name + "-top");
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return super.canPlaceOn(tile, team, rotation) && Units.canCreate(team, droneType);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        if(!Units.canCreate(Vars.player.team(), droneType)){
            drawPlaceText(Core.bundle.get("bar.cargounitcap"), x, y, valid);
        }
    }

    @Override
    public void setBars(){
        super.setBars();
    
        addBar("units", (ReplicatorBuild e) ->
            new Bar(
            () ->
            Core.bundle.format("bar.unitcap",
                Fonts.getUnicodeStr(droneType.name),
                e.team.data().countType(droneType),
                droneType.useUnitCap ? Units.getStringCap(e.team) : "∞"
            ),
            () -> Pal.power,
            () -> droneType.useUnitCap ? (float)e.team.data().countType(droneType) / Units.getCap(e.team) : 1f
        ));
    }

    public static class AmmunitionPlan {
        public Color color;
        public float reload = 0f;
        public float damage = 15f;

        public AmmunitionPlan(Color color, float damage, float reload){
            this.color = color;
            this.damage = damage;
            this.reload = reload;
        }

        AmmunitionPlan(){}
    }

    public class ReplicatorBuild extends Building {
        private Seq<Unit> drones = new Seq<>();
        public AmmunitionPlan selectedPlan = AmmunitionPlans.first(); // Стандартный газ
        // state = 1 - следовать за игроком; state = 2 - преследовать врагов
        private int state = 1;
        private float productionProgress, buildProgress;
        public float cooldown = 30f; // текущий кулдаун
        float scanAngle = 0f;
        float scanRadius = 60f; // радиус сканирования
        public float warmup;
        float pulseCooldown = 70f; // Кулдаун между волнами
        float lastPulse = 0f;

        @Override
        public void updateTile() {
            // Производство дрона, если их меньше двух
            if(drones.size < 2) {
                buildProgress += edelta() / buildTime;
                productionProgress += edelta();
                if(productionProgress >= buildTime) {
                    spawnDrone();
                    productionProgress = 0f;
                }
            }

            if (cooldown > 0f) {
                cooldown -= Time.delta;
            }

            warmup = Mathf.approachDelta(warmup, efficiency, 1f / 60f);
            lastPulse += edelta();
            // Улучшенное перемещение дронов
            for(Unit drone : drones) {
                if(tile.build != null && tile.build.health > 0) {
                    if(drone != null && !drone.dead) {
                        Vec2 target = new Vec2();

                        switch (state) {

                            case 1:                                      
                                // Следование за игроком, если он существует
                                if(Vars.player != null && Vars.player.unit() != null) {
                                    target.set(Vars.player.unit().x, Vars.player.unit().y);
                                } else {
                                    target.set(x, y);
                                }
                                break;
                            case 2:                                      
                                // Поиск ближайшего врага, если таковой есть
                                Unit enemy = Units.closestEnemy(team, x, y, 9999f, u -> true);
                                if(enemy != null) {
                                    target.set(enemy.x, enemy.y);
                                } else {
                                    // Если врагов нет, возвращаемся к блоку
                                    target.set(x, y);
                                }
                                break;
                                
                        }                       
                        // Вычисляем вектор направления к цели
                        Vec2 direction = new Vec2(target).sub(drone.x, drone.y);
                        float distanceToTarget = direction.len();

                        if(distanceToTarget > distance){ // <-- проверка на минимальную дистанцию
                            float speed = drone.type.speed;
                            direction.nor().scl(speed * edelta());
                            drone.moveAt(direction);
                        }
                    }
                } 
            }
            Unit target = Units.closestEnemy(team, x, y, scanRadius, u -> u.type != null && !u.dead);
            if (selectedPlan == null) return;

            if(target != null && lastPulse >= pulseCooldown && cooldown <= 0f){
                // Запускаем волну
                giveAnShock();
                lastPulse = 0f;
                cooldown = selectedPlan.reload;
            }  
        }

        @Override
        public void onRemoved(){
            killAllDrones();
            super.onRemoved();
        }

        void giveAnShock(){
            LaiFx.pulseEffect.at(x, y, selectedPlan.color); // Рисуем эффект волны
        
            Units.nearbyEnemies(team, x, y, scanRadius, enemy -> {
                if(enemy.dst(x, y) <= scanRadius){
                    enemy.damage(selectedPlan.damage);
                }
            });
        }

        private void killAllDrones(){
            for(Unit drone : drones){
                if(drone != null && !drone.dead){
                    drone.kill();
                }
            }
            drones.clear();
        }

        @Override
        public void draw(){
            super.draw();
            
            scanAngle += Time.delta * 1.5f;
            float angleRad = scanAngle * Mathf.degRad;
            for(Unit unit : drones){
                if(unit == null){
                    Draw.draw(Layer.blockOver, () -> {
                        //TODO make sure it looks proper
                        Drawf.construct(this, droneType.fullIcon, 0f, buildProgress, warmup, productionProgress);
                    });
                } 
            }

            if(enabled && power.status > 0) {
                // Рисуем полупрозрачный круг
                Draw.z(Layer.effect);
                Drawf.circles(x,y, scanRadius, team.color);
            
                // Рисуем сканирующую линию
                Lines.stroke(1.5f);
                float lineX = x + Mathf.cos(angleRad) * scanRadius;
                float lineY = y + Mathf.sin(angleRad) * scanRadius;
                Drawf.line(team.color, x, y, lineX, lineY);
            }
            Draw.rect(top, x, y);
            //ammunition
            Draw.color(selectedPlan.color);
            Draw.rect(ammunition, x, y);

            Draw.reset(); // сбрасываем настройки цвета и толщины
        }

        // Создание дрона с задержкой
        private void spawnDrone() {
            Unit drone = droneType.spawn(team, x, y);
            if(drone != null) {
                drones.add(drone);
            }
        }

        @Override
        public void buildConfiguration(Table table) {
            table.row();
        
            if (droneType != null) {
        
                table.button(b -> {
                    b.image(Icon.units).size(30);
                }, Styles.clearTogglei, () -> {
                    state = 1;
                }).size(50).padRight(10).update(b -> {
                    b.setChecked(state == 1);
                });

                table.button(b -> {
                    b.image(Icon.warning).size(30);
                }, Styles.clearTogglei, () -> {
                    state = 2;
                }).size(50).padRight(10).update(b -> {
                    b.setChecked(state == 2);
                });
    
            }
            table.row();

            for (int i = 0; i < AmmunitionPlans.size; i++) {
                int index = i;
                AmmunitionPlan plan = AmmunitionPlans.get(i);
            
                // Toggle-кнопка со стандартным стилем
                ImageButton button = new ImageButton(Styles.clearTogglei);

                //button.image(Icon.warning).size(30);

                Image colorBox = new Image(Tex.whiteui); // белая текстура
                colorBox.setColor(plan.color);           // красим в нужный цвет
                button.add(colorBox).size(20f);          // размер квадратика
            
                // Логика выбора
                button.clicked(() -> {
                    selectedPlan = plan;
                    configure(index);
                });
            
                // Обновление состояния (выделение)
                button.update(() -> {
                    button.setChecked(selectedPlan == plan);
                });
            
                // Добавляем кнопку на таблицу с отступом
                table.add(button).size(50).padRight(10);
            }

        }

        @Override
        public Object config() {
            return AmmunitionPlans.indexOf(selectedPlan); // Сохраняем индекс газа
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(state);
            write.i(AmmunitionPlans.indexOf(selectedPlan));
        }
        
        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            state = read.i();
            int index = read.i();
            if (index >= 0 && index < AmmunitionPlans.size) {
                selectedPlan = AmmunitionPlans.get(index);
            }
        }

    }
}

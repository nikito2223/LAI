package lai.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.graphics.Color;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.weapons.*;
import mindustry.content.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.graphics.Pal;
import lai.graphics.LaiPal;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

import mindustry.content.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;

import lai.type.unit.*;
import lai.type.*;
import lai.content.*;

import static mindustry.Vars.*;

public class LaiUnits{

    public static UnitType stars, arom;

    public static UnitType scanningDrone;


    public static UnitType exarch, archon, doom;
        
    public static void load(){
        stars = new MathexisUnitType("stars") {{
            coreUnitDock = true;
            controller = u -> new BuilderAI(true, 500f);
            isEnemy = false;
            envDisabled = 0; 
            targetPriority = -2;
            lowAltitude = false;
            mineWalls = true;
            mineFloor = false;
            mineHardnessScaling = false;
            flying = true;
            mineSpeed = 3f;
            mineTier = 2;
            buildSpeed = 1.2f;
            drag = 0.08f;
            speed = 4.6f;
            rotateSpeed = 5f;
            accel = 0.09f;
            itemCapacity = 20;
            health = 210f;
            armor = 2f;
            hitSize = 9f;
            engineSize = 0;
            payloadCapacity = 2f * 2f * tilesize * tilesize;
            pickupUnits = false;
            vulnerableWithPayloads = true;
            constructor = UnitEntity::create;
            fogRadius = 0f;
            targetable = false;
            hittable = false;

            setEnginesMirror(
                new UnitEngine(5.25f, 0f, 2.2f, 45f)
            );

            weapons.add(new RepairBeamWeapon(){{
                widthSinMag = 0.11f;
                reload = 20f;
                x = 0f;
                y = 6.5f;
                rotate = false;
                shootY = 0f;
                beamWidth = 0.7f;
                repairSpeed = 3.1f;
                fractionRepairSpeed = 0.06f;
                aimDst = 0f;
                shootCone = 15f;
                mirror = false;
                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;
                laserColor = Pal.accent;
                healColor = Pal.accent;
                bullet = new BulletType(){{
                    maxRange = 60f;
                }};
            }});
        }};

        arom = new MathexisUnitType("arom") {{
            coreUnitDock = true;
            controller = u -> new BuilderAI(true, 500f);
            isEnemy = false;
            envDisabled = 0; 
            targetPriority = -2;
            lowAltitude = false;
            mineWalls = true;
            mineFloor = false;
            mineHardnessScaling = false;
            flying = true;
            mineSpeed = 6f;
            mineTier = 3;
            buildSpeed = 1.2f;
            drag = 0.08f;
            speed = 5.6f;
            rotateSpeed = 7f;
            accel = 0.09f;
            itemCapacity = 60;
            health = 300f;
            armor = 2f;
            hitSize = 9f;
            engineSize = 0;
            payloadCapacity = 2f * 2f * tilesize * tilesize;
            pickupUnits = false;
            vulnerableWithPayloads = true;
            constructor = UnitEntity::create;
            fogRadius = 0f;
            targetable = false;
            hittable = false;

            setEnginesMirror(
                new UnitEngine(5.25f, 0f, 2.2f, 45f),
                new UnitEngine(0f, -4.25f, 2.2f, 0f)
            );

            weapons.add(new RepairBeamWeapon(){{
                widthSinMag = 0.11f;
                reload = 20f;
                x = 0f;
                y = 6.5f;
                rotate = false;
                shootY = 0f;
                beamWidth = 0.7f;
                repairSpeed = 3.1f;
                fractionRepairSpeed = 0.06f;
                aimDst = 0f;
                shootCone = 15f;
                mirror = false;
                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;
                laserColor = Pal.accent;
                healColor = Pal.accent;
                bullet = new BulletType(){{
                    maxRange = 60f;
                }};
            }});
        }};

        scanningDrone = new MathexisUnitType("scanning-drone") {{
            coreUnitDock = false;
            isEnemy = false;
            envDisabled = 0; 
            targetPriority = -2;
            lowAltitude = false;
            mineWalls = false;
            mineFloor = false;
            mineHardnessScaling = false;
            flying = true;
            drag = 0.08f;
            speed = 5.6f;
            rotateSpeed = 7f;
            accel = 0.09f;
            health = 300f;
            itemCapacity = 0;
            hitSize = 9f;
            engineSize = 0;
            payloadCapacity = 2f * 2f * tilesize * tilesize;
            pickupUnits = false;
            vulnerableWithPayloads = true;
            constructor = UnitEntity::create;
        
            fogRadius = 15f;
            targetable = false;
            hittable = false;
        
            // 🚫 ключевые параметры
            playerControllable = false;
            logicControllable = false;

            envEnabled = Env.any;       // включён во всех средах
        
            setEnginesMirror(
                new UnitEngine(5.25f, 0f, 2.2f, 45f),
                new UnitEngine(0f, -4.25f, 2.2f, 0f)
            );
        }};


        exarch = new MathexisUnitType("exarch"){{
            constructor = LegsUnit::create;
            health = 480;
            speed = 0.6f;
            hitSize = 12f;

            mineWalls = true;

            legCount = 4;
            legLength = 15f;
            legExtension = -2f;
            legBaseOffset = 3f;
            legMoveSpace = 1.4f;
            legPairOffset = 3;
            legLengthScl = 0.96f;
            legForwardScl = 1.1f;
            shadowElevation = 0.1f;
            targetAir = false;
            lockLegBase = true;
            legContinuousMove = true;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legGroupSize = 3;
            rippleScale = 0.2f;

            allowLegStep = true;
            hovering = true;
            legPhysicsLayer = false;
            mineTier = 2;

            groundLayer = Layer.legUnit - 1f;
            researchCostMultiplier = 0f;
            
            weapons.add(
                // Левая боковая пушка — быстрый заряд
                new Weapon("lai-exarch-arc"){{
                    x = -5f;
                    y = 0f;
                    reload = 20f;
                    shootSound = Sounds.shoot;
        
                    bullet = new LaserBoltBulletType(4f, 10){{
                        width = 5f;
                        height = 7f;
                        lifetime = 25f;
                        healPercent = 5.5f;
                        collidesTeam = true;
                        shootEffect = Fx.shootSmall;
                        backColor = Pal.heal;
                        frontColor = Color.white;
                        collidesAir = true;
                    }};
                }},
                // Правая боковая пушка — быстрый заряд
                new Weapon("lai-exarch-arc"){{
                    x = 5f;
                    y = 0f;
                    reload = 20f;
                    shootSound = Sounds.shoot;
        
                    bullet = new LaserBoltBulletType(4f, 10){{
                        width = 5f;
                        height = 7f;
                        lifetime = 25f;
                        healPercent = 5.5f;
                        collidesTeam = true;
                        shootEffect = Fx.shootSmall;
                        backColor = Pal.heal;
                        frontColor = Color.white;
                        collidesAir = true;
                    }};
                }}
            );
        }};
        archon = new MathexisUnitType("archon"){{
            constructor = LegsUnit::create;
            health = 880;
            speed = 0.95f;
            hitSize = 14f;
            
            mineWalls = true;

            legCount = 5;
            legLength = 28f;        // вместо 20f — натурально удлинённые ноги
            legLengthScl = 1.05f;   // немного увеличивает длину без перебора
            legForwardScl = 1.15f;  // делает шаг более "вытянутым"
            
            legExtension = -2.5f;   // можно оставить без изменений
            legBaseOffset = 4.2f;   // чуть шире — чтобы длинные ноги не пересекались
            legMoveSpace = 1.7f;    // легкое увеличение шага
            legPairOffset = 3;
            
            lockLegBase = true;
            legContinuousMove = true;
            
            legMaxLength = 1.25f;   // даёт длинной ноге нормально тянуться
            legMinLength = 0.2f;
            
            legGroupSize = 3;
            rippleScale = 0.2f;
            hovering = true;

            shadowElevation = 0.2f;
            groundLayer = Layer.legUnit - 1f;
            mineTier = 2;
            targetAir = true;
            targetGround = true;
            buildSpeed = 0.3f;
            ammoType = new PowerAmmoType(0.5f);

            weapons.add(
                new Weapon("lai-archon-laser"){{
                    x = 4f;          // положительное значение — создаст вторую пушку автоматически
                    y = -3f;
                    mirror = true;   // включаем автоматическое зеркалирование
                    rotate = false;
            
                    reload = 40f;
                    recoil = 0.8f;
                    shootSound = Sounds.laser;
            
                    bullet = new BasicBulletType(5f, 20){{
                        damage = 20;
                        speed = 5f;
                        lifetime = 45f;
                        width = height = 12f;
            
                        status = LaiStatus.сorrosiveFlux;
                        frontColor = Color.white;
                        backColor = trailColor = Color.valueOf("8cffd9"); // необычный цвет
                        trailWidth = 2.5f;
                        trailLength = 14;
            
                        hitEffect = Fx.hitLaser;
                        despawnEffect = Fx.smokeCloud;
                        smokeEffect = Fx.none;
            
                        collidesAir = true;
                        collidesGround = true;
            
                        // убраны молнии и фрагменты
                    }};
                }}
            );

            
        }};
        doom = new MathexisUnitType("doom"){{
            constructor = LegsUnit::create;
            health = 2000;                           
            speed = 0.85f;
            hitSize = 26f;

            legCount = 6;
            legMoveSpace = 2f;
            legPairOffset = 3;
            legLength = 30f;
            legExtension = -15;
            legBaseOffset = 10f;
            stepShake = 1f;
            legLengthScl = 0.96f;
            rippleScale = 2f;
            legSpeed = 0.2f;
        
            legSplashDamage = 32;
            legSplashRange = 30;
            drownTimeMultiplier = 2f;

            hovering = true;
            shadowElevation = 0.65f;
        
            groundLayer = Layer.legUnit;
            targetAir = true;
            targetGround = true;

            weapons.add(new Weapon("lai-doom-laser"){{
                shake = 4f;
                shootY = 0f;
                x = 0;
                y = -8f;

                alternate = false;

                shoot.firstShotDelay = Fx.greenLaserChargeSmall.lifetime - 1f;
                parentizeEffects = true;

                reload = 200f;
                chargeSound = Sounds.lasercharge2;
                shootSound = Sounds.beam;
                continuous = true;
                cooldownTime = 340f;
                smoothReloadSpeed = 0.15f;


                bullet = new ContinuousLaserBulletType(){{
                    damage = 22f;
                    length = 130f;
                    hitEffect = Fx.hitMeltHeal;
                    keepVelocity = false;
                    pierceCap = 1; 
                    drawSize = 420f;
                    lifetime = 180f;
                    despawnEffect = Fx.smokeCloud;
                    smokeEffect = Fx.none;

                    width = 10f;   

                    chargeEffect = LaiFx.pulseEffect;
        
                    incendChance = 0.1f;
                    incendSpread = 5f;
                    incendAmount = 1;
        
                    colors = new Color[]{LaiPal.spiderKink.cpy().a(.2f), LaiPal.spiderKink.cpy().a(.5f), LaiPal.spiderKink.cpy().mul(1.2f), Color.white};
                }};

                parts.add(
                    new RegionPart("-side"){{
                        under = true;
                        layerOffset = 0.01f;
                        mirror = true;
                        outlineColor = Pal.darkOutline;
                        progress = PartProgress.recoil;
                        moveX = 1.75f; moveY = -1.25f; moveRot = -10;
                    }}
                );

                shootStatus = StatusEffects.slow;
                shootStatusDuration = bullet.lifetime + shoot.firstShotDelay;
            }});        

            weapons.add(
                new Weapon("lai-archon-laser"){{
                    x = 6f;
                    y = 9f;
                    rotate = true;
                    reload = 10f;


                    bullet = new SapBulletType(){{
                        sapStrength = 0.85f;
                        length = 65f;
                        damage = 35;
                        status = LaiStatus.сorrosiveFlux;
                        shootEffect = Fx.shootSmall;
                        hitColor = Color.valueOf("d8639a");
                        despawnEffect = Fx.none;
                        width = 0.55f;
                        lifetime = 30f;
                        knockback = -1f;
                    }};
                    shootSound = Sounds.sap;
                }}
            );
        }};


    }
}
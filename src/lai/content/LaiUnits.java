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


    public static UnitType exarch, archon, doom, excalibur;
        
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


        exarch = new MathexisUnitType("exarch") {{
            constructor = LegsUnit::create;
            health = 160; // Низкий HP для T1
            speed = 1.5f; // Быстрое передвижение
            hitSize = 10f;
        
            mineWalls = true;
        
            legCount = 4;
            legLength = 12f;
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
            mineTier = 1;
        
            groundLayer = Layer.legUnit - 1f;
            researchCostMultiplier = 0f;
        
            weapons.add(
                // Левая боковая пушка — быстрый точечный урон
                new Weapon("lai-exarch-beam") {{
                    x = 0f;
                    y = -5f;

                    mirror = false;

                    reload = 40; // Скорострельность
                    shootSound = Sounds.shoot;
                    
                    shoot.shots = 2;
                    shoot.shotDelay = 3f;

                    bullet = new BasicBulletType(5f, 10) {{ // Точечный урон 12
                        width = height = 12f;
                        lifetime = 15f;
                        collidesAir = true;
                        shootEffect = Fx.shootSmall;
                        hitColor = backColor = trailColor = LaiPal.litiumAmmoBack;                       
                        frontColor = LaiPal.litiumAmmoFront;
                        trailWidth = 1.5f;
                        trailLength = 7;

                        status = StatusEffects.burning;
                        statusDuration = 30f;

                        hitEffect = Fx.hitLaser;
                        despawnEffect = Fx.smokeCloud;
                        smokeEffect = Fx.none;
                    }};
                }}
            );
        }};

        archon = new MathexisUnitType("archon"){{
            constructor = LegsUnit::create;
        
            // Параметры по таблице T2
            health = 220;
            speed = 1.2f;
            hitSize = 14f;
            
            targetAir = true;
            targetGround = true;
        
            mineWalls = true;
            mineTier = 2;
        
            // Ноги — как у тебя, только чуть сокращено под уровень Т2
            legCount = 5;
            legLength = 24f;
            legLengthScl = 1.02f; 
            legForwardScl = 1.1f;
            legExtension = -2.3f; 
            legBaseOffset = 4f; 
            legMoveSpace = 1.65f;  
            legPairOffset = 3;
            lockLegBase = true;
            legContinuousMove = true;
            legMaxLength = 1.2f;
            legMinLength = 0.2f;
            legGroupSize = 3;
            rippleScale = 0.25f;
            hovering = true;
        
            shadowElevation = 0.2f;
            groundLayer = Layer.legUnit - 1f;
        
            // === ПУШКА T2: Flame Spitter ===
            weapons.add(new Weapon("exarch-flame-spitter"){{
                x = 5f;
                y = -2f;
                mirror = true;
                rotate = false;
        
                reload = 22f;       // faster T2 fire-rate
                recoil = 0.5f;
                inaccuracy = 6f; 
                // Т2 стреляет 3 огненными снарядами
                shoot.shots = 3;
                shoot.shotDelay = 2.5f;
        
                bullet = new BasicBulletType(4f, 8){{
                    // 8 точечный урон
                    damage = 8;    
        
                    speed = 4f;
                    lifetime = 28f;
        
                    width = 9f;
                    height = 10f;
        
                    // === Сплэш T2 ===
                    splashDamage = 15;        // 15 сплэш урон
                    splashDamageRadius = 12f; // радиус ≈ 1 tile
        
                    // Поджог
                    status = StatusEffects.burning;
                    statusDuration = 70f;
        
                    frontColor = LaiPal.litiumAmmoFront;
                    hitColor = backColor = trailColor = LaiPal.litiumAmmoBack;
        
                    trailWidth = 2.5f;
                    trailLength = 13;
        
                    hitEffect = Fx.fireHit;
                    despawnEffect = Fx.fireSmoke;
                    smokeEffect = Fx.none;
        
                    collidesAir = true;
                    collidesGround = true;
                }};
            }});
        }};

        doom = new MathexisUnitType("doom"){{
            constructor = LegsUnit::create;
            health = 7000;                           
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
                shootY = 3f;
                x = 0;
                y = -3f;

                alternate = false;

                shoot.firstShotDelay = Fx.greenLaserChargeSmall.lifetime - 1f;
                parentizeEffects = true;

                reload = 200f;
                chargeSound = Sounds.chargeVela;
                shootSound = Sounds.shootBreach;
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
        }};
        excalibur = new MathexisUnitType("excalibur"){{
            constructor = LegsUnit::create;
            health = 23000;                           
            speed = 0.55f;
            hitSize = 28f;
            armor = 25f;


            legCount = 6;
            legMoveSpace = 2f;
            legPairOffset = 3;
            legLength = 31f;
            legExtension = -20;
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
            legSplashDamage = 32;
            legSplashRange = 30;
        
            groundLayer = Layer.legUnit;
            targetAir = true;
            targetGround = true;
            ammoType = new ItemAmmoType(LaiItems.natrium);

            weapons.add(
                new Weapon("lai-excalibur-cannon"){{
                    x = 0f;   // смещение по X относительно юнита
                    y = -8f;  // смещение по Y
            
                    rotate = true;  // оружие вращается вместе с юнитом
                    mirror = false; // зеркальное отображение не нужно
            
                    reload = 40f;
                    recoil = 3f; 
                    shake = 1f;  

                    range = 240f;

                    shoot = new ShootSpread(7, 2f);

                    bullet = new BasicBulletType(8f, 65){{
                        knockback = 4f;
                        width = 25f;
                        hitSize = 7f;
                        height = 20f;
                        shootEffect = Fx.shootBigColor;
                        smokeEffect = Fx.shootSmokeSquareSparse;
                        ammoMultiplier = 1;
                        hitColor = backColor = trailColor = LaiPal.litiumAmmoBack;
                        trailWidth = 6f;
                        trailLength = 3;
                        hitEffect = despawnEffect = Fx.hitSquaresColor;
                        buildingDamageMultiplier = 0.2f;
                    }};
                }}
            );
        }};

    }
}
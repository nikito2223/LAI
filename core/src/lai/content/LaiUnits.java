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

import static mindustry.Vars.*;

public class LaiUnits{

    public static UnitType arom;

    public static UnitType scanningDrone;


    public static UnitType exarch, archon;
        
    public static void load(){
        arom = new UnitType("arom") {{
            coreUnitDock = true;
            controller = u -> new BuilderAI(true, 500f);
            isEnemy = false;
            envDisabled = 0; 
            targetPriority = -2;
            lowAltitude = false;
            mineWalls = true;
            mineFloor = true;
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
        scanningDrone = new UnitType("scanning-drone") {{
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

            setEnginesMirror(
                new UnitEngine(5.25f, 0f, 2.2f, 45f),
                new UnitEngine(0f, -4.25f, 2.2f, 0f)
            );
        }};

        exarch = new ErekirUnitType("exarch"){{
            constructor = LegsUnit::create;
            health = 480;
            speed = 0.6f;
            hitSize = 12f;

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
            mineTier = 2;

            groundLayer = Layer.legUnit - 1f;
            researchCostMultiplier = 0f;

            ammoType = new ItemAmmoType(LaiItems.lithium);
        
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
        archon = new ErekirUnitType("archon"){{
            constructor = LegsUnit::create;
            defaultCommand = UnitCommand.rebuildCommand;
            health = 880;
            speed = 0.95f;
            hitSize = 14f;
            
            mineWalls = true;

            legCount = 8;
            legLength = 16f;
            legExtension = -2.5f;
            legBaseOffset = 3.5f;
            legMoveSpace = 1.6f;
            legPairOffset = 3;
            legLengthScl = 1f;
            legForwardScl = 1.1f;

            lockLegBase = true;
            legContinuousMove = true;
            legMaxLength = 1.1f;
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

            // Центральный лазер
            weapons.add(new Weapon("lai-archon-laser"){{
                x = 0f;
                y = 6f;
                shootY = 4f;
                reload = 45f;
                shootSound = Sounds.laser;
                mirror = false;
                rotate = false;
            
                bullet = new LaserBulletType(){{
                    length = 80f;
                    damage = 35f;
                    lifetime = 20f;
                    width = 4f;
                    colors = new Color[]{Color.valueOf("4eff60"), Color.valueOf("6aff73"), Color.white};
                    status = StatusEffects.corroded;
                    statusDuration = 60f;
                    drawSize = 90f;
                }};
            }});
            
            // Левая боковая пушка
            weapons.add(new Weapon("lai-exarch-arc"){{
                x = -7.5f;
                y = 1.5f;
                reload = 25f;
                shootSound = Sounds.pew;
                mirror = false;
            
                bullet = new BasicBulletType(3f, 10f){{
                    width = 5f;
                    height = 8f;
                    lifetime = 25f;
                    backColor = Color.valueOf("86ff6a");
                    frontColor = Color.valueOf("4eff60");
                    status = StatusEffects.corroded;
                    statusDuration = 60f;
                    shootEffect = Fx.shootSmall;
                    hitEffect = Fx.hitMeltdown;
                    despawnEffect = Fx.none;
                    pierce = true;
                    pierceBuilding = true;
                    pierceCap = 2;
                }};
            }});
            
            // Правая боковая пушка
            weapons.add(new Weapon("lai-exarch-arc"){{
                x = 7.5f;
                y = 1.5f;
                reload = 25f;
                shootSound = Sounds.pew;
                mirror = false;
            
                bullet = new BasicBulletType(3f, 10f){{
                    width = 5f;
                    height = 8f;
                    lifetime = 25f;
                    backColor = Color.valueOf("86ff6a");
                    frontColor = Color.valueOf("4eff60");
                    status = StatusEffects.corroded;
                    statusDuration = 60f;
                    shootEffect = Fx.shootSmall;
                    hitEffect = Fx.hitMeltdown;
                    despawnEffect = Fx.none;
                    pierce = true;
                    pierceBuilding = true;
                    pierceCap = 2;
                }};
            }});
        }};

    }
}
package lai.content.blocks;

import arc.graphics.*;
import arc.struct.*;



import lai.content.LaiBlocks;
import lai.world.blocks.campaign.*;
import mindustry.entities.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.RegionPart;

import mindustry.content.*;

import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.gen.*;
import mindustry.graphics.*; 
import mindustry.world.consumers.ConsumeLiquid;

import lai.content.*;
import lai.audio.*;
import lai.graphics.*; 

//My Import! aaaaaaa adf
import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

public class LaiBlocksTurrets {

	public static Block
    destroyers, foremdow, shower, dugasteret, tesla, acidGun, frezeeningIncinerator, ballista;
    //t1

    //t2

    //t3

    //t4 

	public static void load() {

        ballista = new ItemTurret("ballista"){{
            requirements(Category.turret, with(Items.silicon, 30, lithium, 30));

            Effect sfe = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);

            ammo(
                lithium, new BasicBulletType(4.5f, 75){{
                    width = 24f;
                    hitSize = 7f;
                    height = 20f;
                    shootEffect = sfe;
                    smokeEffect = Fx.shootBigSmoke;
                    ammoMultiplier = 1;
                    pierceCap = 2;
                    pierce = true;
                    pierceBuilding = true;
                    hitColor = backColor = trailColor = LaiPal.litShot;
                    frontColor = Color.white;
                    trailWidth = 2.1f;
                    trailLength = 10;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    buildingDamageMultiplier = 0.3f;
                }}
            );
            coolantMultiplier = 6f;
            reload = 60f;
            range = 130f;
            scaledHealth = 280;
            outlineColor = Pal.darkOutline;
            shake = 3f;
            shootY = -2;
            size = 2;
            shootCone = 3f;
            targetUnderBlocks = false;
            rotateSpeed = 0.9f;
            researchCostMultiplier = 0.05f;

            limitRange(5f);
        }};

        destroyers = new ItemTurret("destroyers"){{
            requirements(Category.turret, with(Items.silicon, 900, lithium, 300, Items.graphite, 250));
            ammo(
                Items.graphite, new BasicBulletType(7.5f, 50){{
                    hitSize = 5f;
                    width = 15f;
                    height = 30f;
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 4;
                    reloadMultiplier = 2.7f;
                    knockback = 0.3f;
                }},
                lithium, new BasicBulletType(8.5f, 50){{
                    hitSize =  7f;
                    width = 15f;
                    height = 30f;
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 5;
                    hitEffect = Fx.blastExplosion;
                    status = StatusEffects.burning;
                    reloadMultiplier = 3.7f;
                    knockback = 1.3f;
                }}
            );

            shoot = new ShootSpread(3, 5f);

            reload = 10f;
            recoilTime = reload * 1f;
            ammoUseEffect = Fx.casing3;
            range = 230f;
            shootY = 2f;
            drawer = new DrawTurret("base-");
            inaccuracy = 1f;
            shootCone = 35f;
            scaledHealth = 200;
            shootSound = Sounds.shootSnap;
            recoil = 5f;
            size = 3;
            coolant = consumeCoolant(0.1f);
            limitRange(0f);
        }};
        foremdow = new ItemTurret("foremdow"){{
            requirements(Category.turret, with(lithium, 40, Items.graphite, 25));
            ammo(
                 Items.graphite, new BasicBulletType(7.5f, 50){{
                    hitSize = 2.8f;
                    width = 15f;
                    height = 21f;
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 4;
                    reloadMultiplier = 2.7f;
                    knockback = 0.3f;
                }},
                Items.silicon, new ArtilleryBulletType(3f, 20){{
                    knockback = 0.8f;
                    lifetime = 10f;
                    width = height = 11f;
                    collidesTiles = false;
                    splashDamageRadius = 25f * 0.75f;
                    splashDamage = 33f;
                    collidesAir = true;
                    ammoMultiplier = 6;
                    reloadMultiplier = 1.2f;
                    ammoMultiplier = 3f;
                    homingPower = 0.08f;
                    homingRange = 50f;
                }},
                lithium, new BasicBulletType(8.5f, 50){{
                    width = 25f;
                    hitSize = 7f;
                    height = 20f;
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 5;
                    hitEffect = despawnEffect = Fx.hitSquaresColor;
                    reloadMultiplier = 3.7f;
                    knockback = 1.3f;
                }},
                platinum, new ArtilleryBulletType(3f, 45){{
                    hitEffect = Fx.plasticExplosion;
                    knockback = 1f;
                    lifetime = 80f;
                    width = height = 13f;
                    collidesTiles = false;
                    splashDamageRadius = 35f * 0.75f;
                    splashDamage = 45f;
                    status = LaiStatus.platinum;
                    fragBullet = new BasicBulletType(2.5f, 10, "bullet"){{
                        width = 10f;
                        height = 12f;
                        shrinkY = 1f;
                        lifetime = 15f;
                        backColor = Pal.plastaniumBack;
                        frontColor = Pal.plastaniumFront;
                        despawnEffect = Fx.none;
                        collidesAir = false;
                    }};

                    fragBullets = 10;
                    backColor = Pal.plastaniumBack;
                    frontColor = Pal.plastaniumFront;
                }}
            );
            reload = 40f;
            recoil = 2f;
            range = 160;
            shootY = 5;
            size = 2;
            shoot.shotDelay = 5f;
            shoot.shots = 2;
            rotateSpeed = 15f;
            shake = 1;
            inaccuracy = 17f;
            shootCone = 10f;
            ammoPerShot = 1;
            coolantMultiplier = 20;
            health = 260;
            drawer = new DrawTurret("base-");
            shootSound = LaiSounds.pule;
            coolant = consumeCoolant(0.1f);
            limitRange(4f);
        }};
        shower = new ItemTurret("shower"){{
            requirements(Category.turret, with(lithium, 100, platinum, 50, Items.graphite, 30, vanadium, 10));
            ammo(
                iron, new BasicBulletType(8.2f, 70) {{ // Чуть уменьшил скорость, добавил урон
                    width = height = 14; // Чуть меньше
                    shrinkY = 0.3f;
                    backSprite = "missile-large-back";
                    sprite = "missile-large";
                    velocityRnd = 0.1f;
                    shootEffect = Fx.shootBig2;
                    smokeEffect = Fx.shootSmokeDisperse;
                    frontColor = Color.white;
                    backColor = trailColor = hitColor = Color.gray;
                    trailChance = 0.4f;
                    ammoMultiplier = 3f;
                    lifetime = 35f;
                    rotationOffset = 90f;
                    trailRotation = true;
                    trailEffect = Fx.disperseTrail;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                }},
                
                lithium, new BasicBulletType(10f, 50) {{ // Литий быстрее, но слабее
                    width = height = 12; // Самый маленький
                    shrinkY = 0.25f;
                    backSprite = "missile-large-back";
                    sprite = "missile-large";
                    velocityRnd = 0.15f; // Больше случайности
                    shootEffect = Fx.shootBig2;
                    smokeEffect = Fx.shootSmokeDisperse;
                    frontColor = Color.white;
                    backColor = trailColor = hitColor = Color.blue;
                    trailChance = 0.5f;
                    ammoMultiplier = 4f; // Больше боеприпасов за единицу ресурса
                    lifetime = 37;
                    rotationOffset = 90f;
                    trailRotation = true;
                    trailEffect = Fx.disperseTrail;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                }},
                
                uranium, new BasicBulletType(7.2f, 140) {{ // Уран мощный, но медленный
                    width = height = 20; // Самый крупный снаряд
                    shrinkY = 0.4f;
                    backSprite = "missile-large-back";
                    sprite = "missile-large";
                    velocityRnd = 0.05f; // Почти без случайности
                    shootEffect = Fx.shootBig2;
                    smokeEffect = Fx.shootSmokeDisperse;
                    frontColor = Color.white;
                    backColor = trailColor = hitColor = Color.green;
                    trailChance = 0.7f;
                    ammoMultiplier = 2f; // Дорогие боеприпасы
                    lifetime = 40f;
                    rotationOffset = 90f;
                    trailRotation = true;
                    trailEffect = Fx.disperseTrail;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    splashDamage = 50; // Дополнительный урон по площади
                    splashDamageRadius = 30f;
                }}
            );
            drawer = new DrawTurret("base-"){{
                parts.add(new RegionPart("-mid"){{
                    under = true;
                    moveY = -1.5f;
                    progress = PartProgress.recoil;
                    heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                    heatColor = Color.sky.cpy().a(0.9f);
                }},
                new RegionPart("-blade"){{
                    heatProgress = PartProgress.warmup;
                    heatColor = Color.sky.cpy().a(0.9f);
                    mirror = true;
                    under = true;
                    moveY = 1f;
                    moveX = 1.5f;
                    moveRot = 8;
                }});
            }};

            shoot = new ShootAlternate(){{
                spread = 4.7f;
                shots = 4;
                barrels = 4;
            }};
            rotateSpeed = 5f;
            shootCone = 30f;
            shootSound = Sounds.shootBig;
            reload = 9f;
            inaccuracy = 2f; 
            recoil = 2f;
            shootWarmupSpeed = 0.08f;

            outlineColor = Pal.darkOutline;
            targetUnderBlocks = false;

            scaledHealth = 480f;
            range = 190f;
            size = 4;

            coolant = consume(new ConsumeLiquid(freshwater, 20f / 60f));
            coolantMultiplier = 2.5f;

            limitRange(5f);
            //flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
            researchCost = with(lithium, 300, platinum, 400, Items.graphite, 600, vanadium, 100);
        }};
        dugasteret = new ItemTurret("dugasteret"){{
            requirements(Category.turret, with(lithium, 250, Items.silicon, 800, Items.graphite, 400, platinum, 90, vanadium, 150));

            ammo(
            lithium, new BasicBulletType(8f, 65){{
                knockback = 4f;
                width = 25f;
                hitSize = 7f;
                height = 20f;
                shootEffect = Fx.shootBigColor;
                smokeEffect = Fx.shootSmokeSquareSparse;
                ammoMultiplier = 1;
                hitColor = backColor = trailColor = Color.valueOf("48aef4");
                trailWidth = 6f;
                trailLength = 3;
                hitEffect = despawnEffect = Fx.hitSquaresColor;
                buildingDamageMultiplier = 0.2f;
            }},
            rhodium, new BasicBulletType(8f, 65){{
                knockback = 6f;
                width = 27f;
                hitSize = 8f;
                height = 20f;
                shootEffect = Fx.shootBigColor;
                smokeEffect = Fx.shootSmokeSquareSparse;
                ammoMultiplier = 1;
                hitColor = backColor = trailColor = Color.valueOf("69d491");
                frontColor = LaiPal.rhodium;
                trailWidth = 6f;
                trailLength = 3;
                hitEffect = despawnEffect = Fx.hitSquaresColor;
                buildingDamageMultiplier = 3.2f;
                status = LaiStatus.rhodium;
            }},
            platinum, new BasicBulletType(8f, 55){{
                hitEffect = despawnEffect = LaiFx.platinum;
                    knockback = 3f;
                    lifetime = 80f;
                    width = height = 1f;
                    collidesTiles = false;
                    splashDamageRadius = 35f * 0.75f;
                    splashDamage = 45f;
                    status = LaiStatus.platinum;
                    fragBullet = new BasicBulletType(2.5f, 10){{
                        width = 10f;
                        height = 12f;
                        shrinkY = 2f;
                        backColor = LaiPal.platinumBack;
                        frontColor = LaiPal.platinumFront;
                        despawnEffect = Fx.none;
                        collidesAir = true;
                    }};
                    backColor = LaiPal.platinumBack;
                    frontColor = LaiPal.platinumFront;
            }}
            );

            shoot = new ShootSpread(17, 4f);

            coolantMultiplier = 6f;

            inaccuracy = 0.2f;
            velocityRnd = 0.17f;
            shake = 1f;
            ammoPerShot = 3;
            maxAmmo = 30;
            consumeAmmoOnce = true;
            targetUnderBlocks = false;

            shootSound = Sounds.shootAltLong;

            drawer = new DrawTurret("base-"){{
                parts.add(new RegionPart("-front"){{
                    progress = PartProgress.warmup;
                    moveRot = -10f;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.recoil, 0f, -4f, -5f));
                    heatColor = Color.red;
                }});
            }};
            shootY = 5f;
            outlineColor = Pal.darkOutline;
            size = 3;
            envEnabled |= Env.space;
            reload = 30f;
            recoil = 2f;
            range = 165f;
            shootCone = 40f;
            scaledHealth = 210;
            rotateSpeed = 3f;

            coolant = consume(new ConsumeLiquid(freshwater, 15f / 60f));
            limitRange();
        }};
        tesla = new PowerTurret("Tesla"){{
            requirements(Category.turret, with(lithium, 60, platinum, 30, Items.silicon, 20));
            shootType = new LightningBulletType(){{
                damage = 65;
                lightningLength = 25;
                collidesAir = false;
                ammoMultiplier = 1f;

                //for visual stats only.
                buildingDamageMultiplier = 1.25f;

                lightningType = new BulletType(0.0001f, 0f){{
                    lifetime = Fx.lightning.lifetime;
                    hitEffect = LaiFx.platinum;
                    despawnEffect = Fx.none;
                    status = LaiStatus.platinum;
                    statusDuration = 10f;
                    hittable = false;
                    lightColor = Color.white;
                    collidesAir = false;
                    buildingDamageMultiplier = 0.25f;
                }};
            }};
            reload = 45f;
            shootCone = 40f;
            rotateSpeed = 8f;
            targetAir = false;
            range = 150f;
            shootEffect = LaiFx.platinum;
            heatColor = Color.red;
            drawer = new DrawTurret("base-"){{

            }};
            recoil = 3f;
            size = 2;
            health = 260;
            shootSound = Sounds.spark;
            consumePower(3.3f);
            coolant = consumeCoolant(0.1f);
        }};
        acidGun = new LiquidTurret("acid-gun"){{
            requirements(Category.turret, with(lithium, 1200, Items.silicon, 800, Items.graphite, 400, platinum, 500, vanadium, 1050));

            ammo(
            acid, new LiquidBulletType(acid){{
                    lifetime = 49f;
                    speed = 8f;
                    knockback = 1.3f;
                    puddleSize = 8f;
                    orbSize = 4f;
                    puddleSize = 8f;
                    ammoMultiplier = 0.4f;
                    statusDuration = 60f * 4f;
                    status = LaiStatus.platinum;
                    damage = 2.01f;
                    drag = 0.001f;
                    layer = Layer.bullet - 2f;
                    fragBullet = new BasicBulletType(5.5f, 7, "bullet"){{
                        width = 15f;
                        height = 7f;
                        shrinkY = 8f;
                        lifetime = 15f;
                        despawnEffect = Fx.none;
                        collidesAir = false;
                    }};

                }}
            );
            size = 4;
            shootY = 5f;
            reload = 2f;
            shoot.shots = 3;
            velocityRnd = 0.1f;
            inaccuracy = 4f;
            recoil = 3f;
            shootCone = 45f;
            liquidCapacity = 60f;
            shootEffect = Fx.shootLiquid;
            range = 260f;
            scaledHealth = 2500;
            drawer = new DrawTurret("base-"){{

            }};
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);          
        }};
        frezeeningIncinerator = new LaserTurret("frezeening_incinerator"){{
            requirements(Category.turret, with(lithium, 1200, Items.silicon, 800, Items.graphite, 400, platinum, 500, vanadium, 1050));
            shootEffect = LaiFx.shootBigSmokFreze;
            shootCone = 40f;
            recoil = 4f;
            size = 2;
            shake = 8f;
            range = 95f;
            reload = 40f;
            firingMoveFract = 0.5f;
            shootDuration = 230f;
            shootSound = Sounds.laserbig;
            loopSound = Sounds.beam;
            loopSoundVolume = 2f;

            shootType = new ContinuousLaserBulletType(48){{
                length = 96f;
                hitEffect = LaiFx.hitfrezeeningIncinerator;
                hitColor = Color.valueOf("26beff");
                status = StatusEffects.freezing;
                drawSize = 420f;
                //timescaleDamage = true;
                despawnEffect = Fx.none;

                incendChance = 0.4f;
                incendSpread = 5f;
                incendAmount = 1;
                ammoMultiplier = 1f;
            }};

            drawer = new DrawTurret("base-"){{ }};

            scaledHealth = 200;
            coolant = consumeCoolant(0.5f);
            consumePower(67f);
        }};

	}
}
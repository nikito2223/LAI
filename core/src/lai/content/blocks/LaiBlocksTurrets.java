package lai.content.blocks;

import arc.graphics.*;
import arc.struct.*;

import lai.content.LaiBlocks;
import lai.world.blocks.campaign.*;
 
import mindustry.entities.pattern.ShootSpread;
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

import lai.world.blocks.defense.turrets.powerTurrets.*;


public class LaiBlocksTurrets {

	public static Block
    destroyers, foremdow, shower, dugasteret, tesla, acidGun, frezeeningIncinerator;

	public static void load() {
        destroyers = new ItemTurret("destroyers"){{
            requirements(Category.turret, with(Items.silicon, 900, lithium, 300, Items.graphite, 250));
            ammo(
                Items.graphite, new BasicBulletType(7.5f, 50){{
                    hitSize = 5f;
                    width = 15f;
                    height = 21f;
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 4;
                    reloadMultiplier = 2.7f;
                    knockback = 0.3f;
                }},
                lithium, new BasicBulletType(8.5f, 50){{
                    hitSize =  7f;
                    width = 15f;
                    height = 40f;
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 5;
                    hitEffect = Fx.blastExplosion;
                    status = StatusEffects.burning;
                    reloadMultiplier = 3.7f;
                    knockback = 1.3f;
                }}
            );
            reload = 1f;
            recoilTime = reload * 1f;
            ammoUseEffect = Fx.casing3;
            range = 230f;
            shootY = 2f;
            drawer = new DrawTurret("base-");
            inaccuracy = 1f;
            shootCone = 35f;
            scaledHealth = 200;
            shootSound = Sounds.shootSnap;
            recoil = 1f;
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
            reload = 3f;
            recoil = 2f;
            range = 130f;
            size = 2;
            shoot.shotDelay = 5f;
            shoot.shots = 2;
            rotateSpeed = 15f;
            inaccuracy = 17f;
            shootCone = 10f;
            health = 260;
            drawer = new DrawTurret("base-");
            shootSound = LaiSounds.pule;
            coolant = consumeCoolant(0.1f);
            limitRange(0f);
        }};
        shower = new LiquidTurret("shower"){{
            requirements(Category.turret, with(lithium, 100, platinum, 50, Items.graphite, 30, vanadium, 10));
            ammo(
                distilledwater, new LiquidBulletType(distilledwater){{
                    lifetime = 49f;
                    speed = 8f;
                    knockback = 1.3f;
                    puddleSize = 8f;
                    orbSize = 4f;
                    puddleSize = 8f;
                    ammoMultiplier = 0.4f;
                    statusDuration = 60f * 4f;
                    damage = 0.3f;
                    drag = 0.001f;
                    layer = Layer.bullet - 2f;
                }},
                fueloli, new LiquidBulletType(fueloli){{
                    lifetime = 49f;
                    speed = 8f;
                    knockback = 1.3f;
                    puddleSize = 8f;
                    puddleSize = 8f;
                    orbSize = 4f;
                    drag = 0.001f;
                    ammoMultiplier = 0.4f;
                    statusDuration = 60f * 4f;
                    damage = 0.2f;
                    layer = Layer.bullet - 2f;
                }},
            lava, new LiquidBulletType(lava){{
                    lifetime = 49f;
                    speed = 4f;
                    knockback = 1.3f;
                    puddleSize = 8f;
                    puddleSize = 8f;
                    orbSize = 6f;
                    drag = 0.001f;
                    ammoMultiplier = 0.4f;
                    statusDuration = 60f * 4f;
                    damage = 2.5f;
                    layer = Layer.bullet - 2f;
                }}
            );
            size = 4;
            reload = 2f;
            shoot.shots = 3;
            velocityRnd = 0.1f;
            inaccuracy = 4f;
            recoil = 3f;
            shootCone = 45f;
            liquidCapacity = 60f;
            shootEffect = Fx.shootLiquid;
            range = 210f;
            scaledHealth = 2500;
            drawer = new DrawTurret("base-"){{

            }};
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
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

            coolant = consume(new ConsumeLiquid(distilledwater, 15f / 60f));
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
        frezeeningIncinerator = new FreezingLaserTurret("frezeening_incinerator"){{
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
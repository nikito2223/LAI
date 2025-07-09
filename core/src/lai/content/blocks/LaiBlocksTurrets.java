package lai.content.blocks;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import arc.func.*;
import arc.math.*;
import arc.math.geom.*;

import lai.content.LaiBlocks;
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

import lai.world.blocks.defense.turrets.*;

//My Import! aaaaaaa adf
import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

public class LaiBlocksTurrets {

	public static Block
    //t1
    ballista,
    //t2
    titaniumSlinger, dugasteret, tesla,
    //t3
    ilasruk, shower,
    //t4 
    acidGun, frezeeningIncinerator, glacier;
    //t5

	public static void load() {

        ballista = new ItemTurret("ballista"){{
            requirements(Category.turret, with(Items.silicon, 30, lithium, 30));

            Effect sfe = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);

            ammo(
                lithium, new BasicBulletType(4.5f, 21f){{
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
            reload = 40f;
            range = 130f;
            scaledHealth = 280;
            outlineColor = Pal.darkOutline;
            shake = 3f;
            shootY = -2;
            drawer = new DrawTurret("base-");
            size = 2;
            shootCone = 3f;
            targetUnderBlocks = false;
            rotateSpeed = 0.9f;
            researchCostMultiplier = 0.05f;
            coolant = consume(new ConsumeLiquid(freshwater, 15f / 60f));
            limitRange(5f);
        }};

        ilasruk = new ItemTurret("ilasruk"){{
            requirements(Category.turret, with(Items.silicon, 900, lithium, 300, Items.graphite, 250));
        
            ammo(
                Items.graphite, new BasicBulletType(5.5f, 30){{
                    hitSize = 4f;
                    width = 10f;
                    height = 22f;
                    lifetime = 230f / speed;
                
                    shootEffect = Fx.shootBig;
                    hitEffect = Fx.sparkShoot;
                    despawnEffect = Fx.shootSmall;
                
                    trailColor = Pal.bulletYellowBack;
                    backColor = Items.graphite.color;
                    frontColor = Color.white;
                
                    trailLength = 10;
                    trailWidth = 2.2f;
                    lightColor = Items.graphite.color;
                    lightOpacity = 0.5f;
                
                    ammoMultiplier = 4;
                    reloadMultiplier = 1.5f;
                    knockback = 0.2f;
                
                    buildingDamageMultiplier = 0.4f;
                }},
                lithium, new BasicBulletType(7.5f, 55){{
                    hitSize = 6f;
                    width = 14f;
                    height = 28f;
                    lifetime = 230f / speed;
                
                    shootEffect = Fx.shootBigColor;
                    despawnEffect = Fx.explosion;
                
                    trailColor = Pal.lancerLaser;
                    backColor = lithium.color;
                    frontColor = Color.orange;
                
                    trailLength = 15;
                    trailWidth = 3.2f;
                    lightColor = Color.orange;
                    lightOpacity = 0.7f;
                
                    status = StatusEffects.burning;
                    ammoMultiplier = 3;
                    reloadMultiplier = 2.2f;
                    knockback = 0.7f;
                
                    buildingDamageMultiplier = 0.7f;
                }}

            );
        
            shoot = new ShootAlternate(){{
                shots = 1;
            }};
        
            reload = 35f;
            recoilTime = 20f;
            ammoUseEffect = Fx.casing3;
            range = 230f;
            shootY = 3f;
        
            drawer = new DrawTurret("base-"){{
                parts.addAll(
                    new RegionPart("-mid"){{
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
                    }}
                );
            }};
        
            inaccuracy = 0.4f;
            shootCone = 10f;
            scaledHealth = 220;
            shootSound = Sounds.shootBig;
            recoil = 3f;
            size = 3;
            coolant = consumeCoolant(0.1f);
            limitRange(0f);
        }};

        titaniumSlinger = new ItemTurret("titanium-slinger"){{
            requirements(Category.turret, with(lithium, 40, Items.graphite, 25));
        
            ammo(
                Items.graphite, new BasicBulletType(7.5f, 40){{
                    hitSize = 2.8f;
                    width = 13f;
                    height = 20f;
                    shootEffect = Fx.shootBig;
                    trailColor = Items.graphite.color;
                    frontColor = Color.white;
                    backColor = Items.graphite.color;
                    lightColor = Items.graphite.color;
                    lightOpacity = 0.4f;
                    trailLength = 12;
                    trailWidth = 2.5f;
                    despawnEffect = Fx.hitBulletSmall;
                    ammoMultiplier = 4;
                    reloadMultiplier = 2.5f;
                    knockback = 0.25f;
                    buildingDamageMultiplier = 0.5f;
                }},
                Items.silicon, new ArtilleryBulletType(3f, 20){{
                    knockback = 0.8f;
                    lifetime = 40f;
                    width = height = 11f;
                    collidesTiles = false;
                    splashDamageRadius = 18.75f;
                    splashDamage = 33f;
                    collidesAir = true;
                    ammoMultiplier = 3f;
                    reloadMultiplier = 1.2f;
                    homingPower = 0.08f;
                    homingRange = 50f;
                    frontColor = Items.silicon.color;
                    backColor = Items.silicon.color;
                    trailColor = Items.silicon.color;
                    lightColor = Items.silicon.color;
                    lightOpacity = 0.5f;
                    despawnEffect = Fx.smokeCloud;
                    buildingDamageMultiplier = 0.3f;
                }},
                lithium, new BasicBulletType(6.5f, 50){{
                    width = 25f;
                    height = 20f;
                    hitSize = 7f;
                    shootEffect = Fx.shootBig;
                    trailColor = lithium.color;
                    backColor = lithium.color;
                    frontColor = Color.white;
                    lightColor = lithium.color;
                    lightOpacity = 0.6f;
                    trailLength = 16;
                    trailWidth = 3f;
                    hitEffect = Fx.hitSquaresColor;
                    despawnEffect = Fx.hitSquaresColor;
                    ammoMultiplier = 2;
                    reloadMultiplier = 3.2f;
                    knockback = 1f;
                    buildingDamageMultiplier = 0.6f;
                }},
                platinum, new ArtilleryBulletType(3f, 45){{
                    hitEffect = Fx.plasticExplosion;
                    knockback = 1f;
                    lifetime = 80f;
                    width = height = 13f;
                    collidesTiles = false;
                    splashDamageRadius = 26.25f;
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
                    trailColor = Pal.plastaniumBack;
                    lightColor = Pal.plastaniumFront;
                    lightOpacity = 0.7f;
                    despawnEffect = Fx.plasticExplosion;
                    buildingDamageMultiplier = 0.75f;
                }}
            );
        
            reload = 45f;
            recoil = 1.8f;
            range = 160f;
            shootY = 5f;
            size = 2;
            shoot = new ShootPattern(){{
                shots = 2;
                shotDelay = 5f;
            }};
            rotateSpeed = 12f;
            shake = 0.8f;
            inaccuracy = 13f;
            shootCone = 10f;
            ammoPerShot = 1;
            coolantMultiplier = 1.4f;
            health = 260;
            shootSound = LaiSounds.pule;
            coolant = consumeCoolant(0.1f);
            drawer = new DrawTurret("base-");
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
        glacier = new PowerTurret("glacier"){{
            requirements(Category.turret, with(Items.silicon, 80, lithium, 30));
            size = 2;
            health = 250;
            range = 200f;
            reload = 90f;
            shootCone = 5f;
            rotateSpeed = 5f;
            coolantMultiplier = 0.6f;
            consumePower(3.5f); // потребляет 3.5 энергии
        
            shootType = new BasicBulletType(6f, 40){{
                lifetime = 200f / speed;
                width = 10f;
                height = 14f;
                backColor = Pal.techBlue;
                frontColor = Color.white;
                shrinkX = shrinkY = 0f;
                trailEffect = Fx.none;
                trailLength = 8;
                trailWidth = 2.5f;
                trailColor = Pal.techBlue;

        
                pierce = false;
            }};
        }};
	}
}
package lai.content;

import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.graphics.g3d.PlanetGrid.*; 

import arc.graphics.*;
import arc.math.Rand;
import arc.math.geom.Mat3D;
import arc.struct.Seq;
import arc.util.Tmp;    
import lai.maps.gen.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.graphics.g3d.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.content.*;
import mindustry.content.Planets; 

import lai.content.blocks.*;

import static lai.content.LaiBlocks.*; 
import static mindustry.content.Planets.*;
import static mindustry.content.Blocks.*;
import static lai.content.LaiItems.*;
import mindustry.game.*;
import mindustry.maps.planet.*;
import static mindustry.Vars.*;

public class LaiPlanets{

    public static Planet
        /* planets */ mathexis,
		/*star*/ mathelios, sapuke,
        /*Asteroids*/ mathires;

    public static void load() {
        

        mathelios = new Planet("mathelios", sapuke, 2f, 3){{
            bloom = true;
			drawOrbit = false;
            accessible = true;
			lightColor = Color.valueOf("61b3f1");
            orbitRadius = 1000;
			hasAtmosphere = true;

            alwaysUnlocked = false;
            startSector = 0;

            generator = new TemaPlanetGenerator();
            iconColor = Color.valueOf("4e4bde");

            meshLoader = () -> new SunMesh(
                    this, 6,
                    5, 0.3, 1.7, 1.2, 1,
                    1.1f,
                    Color.valueOf("4e4bde"),
                    Color.valueOf("1a17e3"),
                    Color.valueOf("2f2bff"),
                    Color.valueOf("2f2bff")
			);

        }};
        sapuke = new Planet("sapuke", mathelios, 2f, 0){{
            bloom = true;
            solarSystem = mathelios;
            drawOrbit = false;
            visible = false;
            accessible = false;
            lightColor = Color.valueOf("1c911a");
            hasAtmosphere = true;
            orbitRadius = 50f;
            meshLoader = () -> new SunMesh(
                    this, 9,
                    5, 0.3, 1.7, 1.2, 1,
                    1.1f,
                    Color.valueOf("fce46d"),
                    Color.valueOf("e8d576"),
                    Color.valueOf("d4b208"),
                    Color.valueOf("fff0a6")
					);

        }};
        mathexis = new Planet("mathexis", mathelios, 1f, 2) {{
            generator = new MathexisPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 7);
            cloudMeshLoader = () -> new MultiMesh(
                    
                    new HexSkyMesh(this, 3, 0.2f, 0.23f, 5, Color.valueOf("828282").a(0.65f), 3, 0.25f, 1.22f, 0.45f),
                    new HexSkyMesh(this, 2, 0.3f, 0.32f, 6, Color.valueOf("7a7a7a").a(0.55f), 4, 0.35f, 1.35f, 0.45f),
                    new HexSkyMesh(this, 2, 0.3f, 0.32f, 5, Color.valueOf("b9baba").a(0.35f), 5, 0.45f, 1.35f, 0.35f),
                    new HexSkyMesh(this, 7, 0.1f, 0.28f, 8, Color.valueOf("7a7a7a").a(0.25f), 2, 0.55f, 1.13f, 0.25f),
                    new HexSkyMesh(this, 4, 0.4f, 0.18f, 7, Color.valueOf("b9baba").a(0.56f), 1, 0.55f, 1.43f, 0.29f)
                );
            launchCapacityMultiplier = 0.5f;
            sectorSeed = 2;
            allowWaves = true;
            defaultEnv = Env.underwater | Env.terrestrial;
            allowWaveSimulation = true;
            allowSectorInvasion = true;
            allowLaunchSchematics = true;
            enemyCoreSpawnReplace = true;
            orbitRadius = 40f;
            allowLaunchLoadout = false;
            defaultCore = LaiBlocks.coreCaser;
            solarSystem = mathelios;
            orbitSpacing = 2f;
            landCloudColor = Color.valueOf("41b9ec");
            tidalLock = true;
            rotateTime = 1440f; // Время вращения вокруг оси
            orbitOffset = 2440f;
            lightSrcTo = 0.8f;
            lightDstFrom = 0.2f;
             //TODO disallowed for now
            allowLaunchToNumbered = false;
            icon = "\uEB8B";
            //TODO SHOULD there be lighting?
            updateLighting = false;
            totalRadius += 5.6f;
            //doesn't play well with configs
            prebuildBase = true;
            ruleSetter = r -> {
                r.waveTeam = LaiTeams.xenoSyndicate;
                r.placeRangeCheck = false;
                r.showSpawns = true;
                r.fog = true;
                r.staticFog = true;
                r.lighting = false;
                r.coreDestroyClear = true;
                r.onlyDepositCore = true;
            };

            iconColor = Color.valueOf("4d6aff");
            atmosphereColor = Color.valueOf("002b64");
            atmosphereRadIn = 0.01f;
            atmosphereRadOut = 0.3f;
            startSector = 15;
            alwaysUnlocked = true;

            unlockedOnLand.add(LaiBlocks.coreCaser);
        }};


        mathires = newAsteroid("mathires", mathelios, LaiEnvironmentBlocks.darkgreenStoneWall, LaiEnvironmentBlocks.darkBlueSandWall, 0.4f, 12, 2f, gen -> {
            gen.min = 25;
            gen.max = 35;
            gen.carbonChance = 0.6f;
            gen.iceChance = 0f;
            gen.berylChance = 0.1f;
        });

                //vanilla planets 
        serpulo.hiddenItems.addAll(mathexisItems).removeAll(Items.serpuloItems);
        erekir.hiddenItems.addAll(mathexisItems).removeAll(Items.erekirItems);

        // mod planets
        mathexis.hiddenItems.addAll(Vars.content.items()).removeAll(mathexisItems);

    }
    public static Planet newAsteroid(String name, Planet parent, Block base, Block tint, float tintThresh, int pieces, float scale, Cons<AsteroidGenerator> cgen){
            return new Planet(name, parent, 0.12f){{
                hasAtmosphere = false;
                updateLighting = false;
                sectors.add(new Sector(this, Ptile.empty));
                camRadius = 0.68f * scale;
                minZoom = 0.6f;
                drawOrbit = false;
                accessible = true;
                clipRadius = 2f;
                defaultEnv = Env.space;
                icon = "commandRally";
                generator = new AsteroidGenerator();
                cgen.get((AsteroidGenerator)generator);
    
                meshLoader = () -> {
                    iconColor = tint.mapColor;
                    Color tinted = tint.mapColor.cpy().a(1f - tint.mapColor.a);
                    Seq<GenericMesh> meshes = new Seq<>();
                    Color color = base.mapColor;
                    Rand rand = new Rand(id + 2);
    
                    meshes.add(new NoiseMesh(
                        this, 0, 2, radius, 2, 0.55f, 0.45f, 14f,
                        color, tinted, 3, 0.6f, 0.38f, tintThresh
                    ));
    
                    for(int j = 0; j < pieces; j++){
                        meshes.add(new MatMesh(
                            new NoiseMesh(this, j + 1, 1, 0.022f + rand.random(0.039f) * scale, 2, 0.6f, 0.38f, 20f,
                            color, tinted, 3, 0.6f, 0.38f, tintThresh),
                            new Mat3D().setToTranslation(Tmp.v31.setToRandomDirection(rand).setLength(rand.random(0.44f, 1.4f) * scale)))
                        );
                    }
    
                    return new MultiMesh(meshes.toArray(GenericMesh.class));
                };
        }};
    }
}

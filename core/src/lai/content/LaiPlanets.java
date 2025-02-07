package lai.content;

import arc.graphics.*;
import lai.maps.gen.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.graphics.g3d.*;
import mindustry.graphics.*;
import mindustry.type.*;

import static mindustry.content.Planets.*;
import static lai.content.LaiItems.*;


public class LaiPlanets{

    public static Planet
        /* planets */ mathurak,
		/*star*/ sakeru, sapuke;

    public static void load() {
        

        sakeru = new Planet("sakeru", sapuke, 2f, 3){{
            bloom = true;
			drawOrbit = false;
            accessible = true;
			lightColor = Color.valueOf("61b3f1");
            orbitRadius = 1000;
			hasAtmosphere = true;

            alwaysUnlocked = true;
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
        sapuke = new Planet("sapuke", sakeru, 2f, 0){{
            bloom = true;
            solarSystem = sakeru;
            drawOrbit = false;
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
        mathurak = new Planet("mathurak", sakeru, 1f, 2) {{
            generator = new MathurakPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 5);
            defaultCore = LaiBlocks.coreCaser;
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
            allowWaveSimulation = true;
            allowSectorInvasion = true;
            allowLaunchSchematics = true;
            enemyCoreSpawnReplace = true;
            orbitRadius = 80f;
            allowLaunchLoadout = false;
            solarSystem = sakeru;
            landCloudColor = Color.valueOf("41b9ec");

            rotateTime = 1440f; // Время вращения вокруг оси
            orbitOffset = 2440f;
            //doesn't play well with configs
            prebuildBase = true;
            ruleSetter = r -> {
                r.waveTeam = LaiTeams.sievers;
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


        /*Asteroid = new makAsteroid("Asteroid", sakeru, 1f, 3) {{
            
        }};*/

        //vanilla planets 
        serpulo.hiddenItems.addAll(mathurakItems).removeAll(Items.serpuloItems);
        erekir.hiddenItems.addAll(mathurakItems).removeAll(Items.erekirItems);

        // mod planets
        mathurak.hiddenItems.addAll(Vars.content.items()).removeAll(mathurakItems);

    }
}

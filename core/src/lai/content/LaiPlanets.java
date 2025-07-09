package lai.content;


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

import lai.graphics.g3d.wobj.*;

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
        

        mathelios = new Planet("mathelios", sapuke, 2f, 3) {{
            bloom = true;
            drawOrbit = false;
            accessible = true;
        
            lightColor = Color.valueOf("ffcce5"); // нежный свет сакуры
            iconColor = Color.valueOf("ff99cc");  // иконка — розово-цветущая
        
            orbitRadius = 1000;
            hasAtmosphere = false;
            alwaysUnlocked = false;
        
            meshLoader = () -> new SunMesh(
                this, 7, 5, 0.6, 1.5,
                1.4, 1.3f, 1.2f,
                Color.valueOf("ffb7d5"), // внешний — розово-сиреневый
                Color.valueOf("ffcce5"), // внутреннее — нежно-розовый
                Color.valueOf("fff0f5"), // лучи — почти белые, с розовым оттенком
                Color.valueOf("ffffff")  // центр — белый
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
        mathexis = new Planet("mathexis", mathelios, 1f, 2) {

            final Seq<Obj> obj = new Seq<>();
            {

                generator = new MathexisPlanetGenerator();
            
                // Сетка поверхности планеты
                meshLoader = () -> new HexMesh(this, 7);
            
                // Эффектные облака, отражающие свечение пульсара
                cloudMeshLoader = () -> new MultiMesh(
                    // Грозовые облака — преобладают, плотные и насыщенные
                    new HexSkyMesh(this, 6, 0.4f, 0.25f, 8, Color.valueOf("4a6eff").a(0.65f), 3, 0.35f, 1.4f, 0.45f),
                    new HexSkyMesh(this, 5, 0.38f, 0.22f, 7, Color.valueOf("3b5ccc").a(0.7f), 4, 0.4f, 1.3f, 0.4f),
                    new HexSkyMesh(this, 5, 0.36f, 0.24f, 6, Color.valueOf("597fff").a(0.6f), 5, 0.45f, 1.5f, 0.38f),
                    
                    // Обычные облака — немного, для фона и атмосферы
                    new HexSkyMesh(this, 4, 0.25f, 0.3f, 5, Color.valueOf("82ffd4").a(0.25f), 2, 0.3f, 1.1f, 0.2f),
                    new HexSkyMesh(this, 3, 0.22f, 0.28f, 4, Color.valueOf("a4ffe8").a(0.2f), 1, 0.25f, 1.05f, 0.15f)
                );
    
            
                // Параметры орбиты и движения
                orbitRadius = 40f;
                orbitSpacing = 2f;
                orbitOffset = 2440f;
                rotateTime = 1440f;
                tidalLock = true;
            
                // Свет от пульсара не критичен
                lightSrcTo = 0.8f;
                lightDstFrom = 0.2f;
                updateLighting = true;
            
                // Визуальные эффекты
                iconColor = Color.valueOf("4d6aff");
                atmosphereColor = Color.valueOf("002b64");
                atmosphereRadIn = 0.01f;
                atmosphereRadOut = 0.3f;
                landCloudColor = Color.valueOf("41b9ec");
            
                // Планетарные особенности 
                launchCapacityMultiplier = 0.5f;
                allowWaves = true;
                allowWaveSimulation = true;
                allowSectorInvasion = true;
                allowLaunchSchematics = true;
                allowLaunchLoadout = false;
                allowLaunchToNumbered = false;
                allowSectorInvasion = true;
                enemyCoreSpawnReplace = true;
                sectorSeed = 2;
            
                // Логика и ядро
                solarSystem = mathelios;
                defaultCore = LaiBlocks.coreCaser;
                totalRadius += 5.6f;
                prebuildBase = true;
            
                // Кампания
                startSector = 15;
                alwaysUnlocked = true;
                unlockedOnLand.add(LaiBlocks.coreCaser);
                defaultAttributes.set(Attribute.water, 0.8f);
            
                // Окружение
                defaultEnv = Env.underwater | Env.terrestrial;
            
                // Правила
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
            
                // Кампания (повторно, если не сработает в ruleSetter)
                campaignRuleDefaults.fog = true;
                campaignRuleDefaults.showSpawns = true;
    
                //obj.add(ObjParser.loadObj("squid/squid"));
            }

            public Vec3 lightDir = new Vec3(1, 1, 1).nor();
            public Color ambientColor = Color.white.cpy();
            public Vec3 camDir = new Vec3();
//            public Vec3 camPos = new Vec3();

            int ta = -1;

            Vec3[] rs = new Vec3[]{
                    new Vec3(0,0, 0),
                    new Vec3(0, Mathf.PI, 0),
            };

            Vec3[] ps = new Vec3[]{
                    new Vec3(position).add(0f, -0.5f, 2f),
                    new Vec3(position).add(0f, -0.5f, -2f),
            };

            Vec3[] scl = new  Vec3[]{
                    new Vec3(1f, 1f, 1f),
                    new Vec3(0.8f, 0.8f, 0.8f),
            };

            float r = 0;
            @Override
            public void draw(PlanetParams params, Mat3D projection, Mat3D transform) {
                super.draw(params, projection, transform);
                r += Time.delta;
                r %= 360f;
                Vars.renderer.planets.cam.near = 0.1f;
//                t[0] = cloth;
//                t[1] = cloth;
//                t[2] = hair;
//                t[3] = face;
//                t[4] = cloth;
//                t[5] = body;
//                t[6] = cloth;
//                t[7] = cloth;
//                t[7] = cloth;

//                SShaders.g3d.bind();
//                SShaders.g3d.setUniformMatrix4("u_proj", projection.val);
//                SShaders.g3d.setUniformMatrix4("u_trans", transform.val);
                camDir.set(Vars.renderer.planets.cam.direction).rotate(Vec3.Y, getRotation());

//                SLog.dInfo(camDir);
                for (int i = 0; i < obj.size; i++) {
                    Obj o = obj.get(i);
                    ps[i].x = Mathf.cosDeg(r + 180 * i) * 2f;
                    ps[i].z = Mathf.sinDeg(r + 180 * i) * 2f;
                    rs[i].y = Mathf.degRad * (r + 270f + i*180f);
                    o.render(ps[i], rs[i], projection, transform, Vars.renderer.planets.cam, lightDir, scl[i]);
                }
//                SShaders.g3d.setUniformf("u_lightdir", lightDir);
//                SShaders.g3d.setUniformf("u_ambientColor", ambientColor.r, ambientColor.g, ambientColor.b);
//                SShaders.g3d.setUniformf("u_camdir", camDir);
//                SShaders.g3d.setUniformf("u_rotation", 20f);
//                SShaders.g3d.setUniformf("u_campos", Vars.renderer.planets.cam.position);
//                SShaders.g3d.setUniformf("u_pos", 2, 0, 4, 0);
//
//                SShaders.g3d.setUniformf("u_color", 0.4f, 0.4f, 0.4f, 1);


//                texture.bind();
//                am.render(SShaders.g3d, Gl.triangles);

//                if (ms != null) {
//                    for (int i = 0; i < ms.size; i++) {
//                        t[i].bind();
//                        ms.get(i).render(SShaders.g3d, Gl.triangles);
//                    }
//                }
           }
        };



        mathires = newAsteroid("mathires", mathelios, LaiEnvironmentBlocks.darkgreenStoneWall, LaiEnvironmentBlocks.darkBlueSandWall, 0.4f, 12, 2f, gen -> {
            gen.min = 25;
            gen.max = 35;
            gen.carbonChance = 0.6f;
            gen.iceChance = 0f;
            gen.berylChance = 0.1f;
        });

                //vanilla planets 

        // mod planets

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

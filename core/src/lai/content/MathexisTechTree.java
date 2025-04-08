package lai.content;

import arc.struct.Seq;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.bullet.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.world.blocks.defense.turrets.*;

import static mindustry.Vars.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.SectorPresets.*;
import static mindustry.content.TechTree.*;

import mindustry.content.*;

import static lai.content.LaiBlocks.*;
import static lai.content.blocks.LaiBlocksCrafting.*;
import static lai.content.blocks.LaiBlocksTurrets .*;
import static lai.content.blocks.LaiBlocksLiquids.*;
import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;

public class MathexisTechTree {
    static IntSet balanced = new IntSet();

    static void rebalanceBullet(BulletType bullet){
        if(balanced.add(bullet.id)){
            bullet.damage *= 0.75f;
        }
    }

    //TODO remove this
    public static void rebalance(){
        for(var unit : content.units().select(u -> u instanceof ErekirUnitType)){
            for(var weapon : unit.weapons){
                rebalanceBullet(weapon.bullet);
            }
        }

        for(var block : content.blocks()){
            if(block instanceof Turret turret && Structs.contains(block.requirements, i -> !Items.serpuloItems.contains(i.item))){
                if(turret instanceof ItemTurret item){
                    for(var bullet : item.ammoTypes.values()){
                        rebalanceBullet(bullet);
                    }
                }else if(turret instanceof ContinuousLiquidTurret cont){
                    for(var bullet : cont.ammoTypes.values()){
                        rebalanceBullet(bullet);
                    }
                }else if(turret instanceof ContinuousTurret cont){
                    rebalanceBullet(cont.shootType);
                }
            }
        }
    }

    public static void load() {
        rebalance();

        //TODO might be unnecessary with no asteroids
        Seq<Objective> mathexisSector = Seq.with(new OnPlanet(LaiPlanets.mathexis));

        var costMultipliers = new ObjectFloatMap<Item>();
        for(var item : content.items()) costMultipliers.put(item, 0.9f);
        LaiPlanets.mathexis.techTree = nodeRoot("@planet.lai-mathexis.name", coreCaser, true, () -> {
            context().researchCostMultipliers = costMultipliers;
            node(lithiumDuct, mathexisSector, () -> {
                node(lithiumRouter, () -> {
    	   	     node(lithiumJunction, () -> {
    	   	         	node(lithiumBridgeItem, () -> {
    	   	         		node(launchomt, () -> {
                            });
    	   	          });
    	   	    });
               });
            });         
            node(crusherdrill, () -> { 
                node(lithiumPump, () -> {
                    node(lithiumConduit, () -> {
                        node(lithiumconduitrouter, () -> {
                            node(lithiumBridgeLiquid, () -> {   
                        });
                    });
                });
            });
            node(coalpress, () -> {
                node(graphitepress, () -> {
                    node(siliconarcburners, () -> {
                            
                    });
                });
                node(vanadiaSmelter, () -> {
                    node(steelFactory, () -> {});
                });
            });         
            node(lithiumBattery,Seq.with(new SectorComplete(LaiSectors.lemans)), () -> {
                    node(powerTower,Seq.with(new SectorComplete(LaiSectors.lemans)), () -> {
                    });
                });
            });
            node(foremdow, () -> {
                node(dugasteret, () -> {
                    node(destroyers, () -> {
                        node(tesla, () -> {
                        });
                    });
                    node(shower, () -> {
                        node(acidGun, () -> {
                        
                        });
                    });
                });
            });
            nodeProduce(LaiLiquids.freshwater, () -> {
                nodeProduce(oil, () -> {

                });
                nodeProduce(oxygen, () -> {

                });
                node(hydrogen, () -> {
                });
                node(carbondioxide, () -> {
                });
                node(waterRadioction, () -> {
                    node(symbiote, () -> {
                    
                    });
                });
            });
            nodeProduce(lithium, () -> {
                nodeProduce(platinum, () -> {
                });
                nodeProduce(coal, () -> {
                    nodeProduce(silicon, () -> {
                    });
                    nodeProduce(graphite, () -> {
                    });
                    nodeProduce(iron, () -> {
                        nodeProduce(steel, () -> {
                        });
                    });
                });
                nodeProduce(lead, () -> {
                });
                nodeProduce(rhodium, () -> {
                });
                nodeProduce(uranium, () -> {
                    nodeProduce(enrichmentUranium, () -> {
                    });
                });
            });
        });
    }
}

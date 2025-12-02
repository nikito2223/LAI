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

import lai.content.blocks.*;

import static lai.content.LaiBlocks.*;
import static lai.content.blocks.LaiBlocksCrafting.*;
import static lai.content.blocks.LaiBlocksTurrets .*;
import static lai.content.blocks.LaiBlocksLiquids.*;
import static lai.content.blocks.LaiBlocksUnits.*;
import static lai.content.blocks.LaiBlocksDistribution.*;
import static lai.content.LaiItems.*;
import static lai.content.LaiLiquids.*;
import static lai.content.LaiUnits.*;
import static lai.content.LaiPlanets.*;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;

public class MathexisTechTree {

    public static void load() {
        LaiPlanets.mathexis.techTree = nodeRoot("@planet.lai-mathexis.name", coreCaser, () -> {         
            node(lithiumDuct, () -> {
                node(lithiumRouter, () -> {
    	   	     node(lithiumJunction, () -> {
    	   	         	node(lithiumBridgeItem, () -> {
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

            node(lithiumBattery, () -> {
                    node(powerTower, () -> {
                    });
                });
            });
            nodeProduce(sparkFactory, () -> {
                nodeProduce(exarch, () -> {
                    nodeProduce(archon, () -> {

                    });
                });
                nodeProduce(sparkReconstructor, () -> {

                });
            });
            node(titaniumSlinger, () -> {
                node(dugasteret, () -> {
                    node(ilasruk, () -> {
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
                node(Liquids.slag, () -> {
                    node(Liquids.cryofluid, () -> {
                        node(helium, () -> {
                            node(heliophanus, () -> {
                            });
                        });
                        node(neon, () -> {
                        });
                    });
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

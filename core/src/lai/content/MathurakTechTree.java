package lai.content;

import arc.struct.Seq;
import mindustry.game.Objectives.*;
import arc.struct.*;

import static lai.content.LaiBlocks.*;
import static lai.content.blocks.LaiBlocksCrafting.*;
import static lai.content.blocks.LaiBlocksTurrets .*;
import static lai.content.blocks.LaiBlocksLiquids.*;
import static lai.content.LaiItems.*;
import static mindustry.content.Items.*;
import static mindustry.content.TechTree.*;

public class MathurakTechTree {
    static IntSet balanced = new IntSet();

    public static void load() {

        LaiPlanets.mathurak.techTree = nodeRoot("@planet.lai-mathurak.name", coreCaser, () -> {
		
            node(lithiumDuct, () -> {
                node(lithiumRouter, () -> {
					node(lithiumJunction, () -> {
							node(lithiumBridgeItem, () -> {
								node(launchomt,Seq.with(new SectorComplete(LaiSectors.lemans)), () -> {
									node(interplanetary,Seq.with(new SectorComplete(LaiSectors.lemans)), () -> {
								});
                            });
						});
					});
				});
            });
            
            node(crusherdrill, () -> { 
                node(lithiumPump, () -> {
                 node(lithiumconduit, () -> {
                    node(lithiumconduitrouter, () -> {
                        node(lithiumBridgeLiquid, () -> {   
                });
           });
       });
    });
                node(coalpress, () -> {
                    node(graphitepress, () -> {
                        node(siliconarcburners, Seq.with(
                            new SectorComplete(LaiSectors.lemans),
                            new Research(graphitepress),
                            new Research(LaiItems.lithium)
                        ), () -> {
                            
                    });
                });
            });         
                    node(gannerSolarPanel,Seq.with(new SectorComplete(LaiSectors.lemans)), () -> {
                        node(powerGerm,Seq.with(new SectorComplete(LaiSectors.lemans)), () -> {
                            node(projectormoto,Seq.with(new SectorComplete(LaiSectors.konota)), () -> {
                        
                    });
                });
            });
        });
			
			//node(spiderFactory, Seq.with(new SectorComplete(LaiSectors.lemans)), () -> {
			//	node(FormUnits.genrtor, () -> {	
            //    });
			//	node(FormUnits.herma, () -> { 
            //    });
            //    node(FormUnits.mover, Seq.with(new Research(spiderReconstructor)),  () -> { 
            //    });
            //    node(spiderReconstructor, Seq.with(new SectorComplete(LaiSectors.konota)), () -> {
//
            //    });
			//});
		 
            node(foremdow, () -> {
                node(lithiumWall, () -> {
                node(lithiumWallLarge, () -> {
                    node(lithiumWallHuge, () -> {

                 });
            });
        });
                node(destroyers,Seq.with(new SectorComplete(LaiSectors.konota)), ()-> {});
    });
         node(LaiSectors.start, () -> {
            node(LaiSectors.lemans, () -> {
                node(LaiSectors.konota,Seq.with(
                    new SectorComplete(LaiSectors.lemans),
                    new Research(LaiItems.lithium),
                    new Research(graphitepress),
                    new Research(coalpress),
                    new Research(siliconarcburners)
                    ), () -> {
						node(LaiSectors.imposmor,Seq.with(
						new SectorComplete(LaiSectors.konota),
						new Research(spiderFactory)
						), () -> {
							
					});
                });
            });
        });

        nodeProduce(lithium, () -> {
                node(LaiLiquids.distilledwater, () -> {
                    node(LaiLiquids.fueloli);
            });
                nodeProduce(platinum, () -> 
                    nodeProduce(coal, () -> {
                        nodeProduce(graphite, () -> {
                            nodeProduce(silicon, () -> {});
                        });
                    }));
                });
            });
    }
}

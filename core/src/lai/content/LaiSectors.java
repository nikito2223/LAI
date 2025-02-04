package lai.content;

import mindustry.content.*;
import mindustry.type.*;

public class LaiSectors {
    public static SectorPreset
    /* mathurak */  start, lemans, konota, imposmor;

    public static void load() {
        start = new SectorPreset("Start", LaiPlanets.mathurak, 15){{
            captureWave = 15;
            difficulty = 5;
        }};
        lemans = new SectorPreset("lemans", LaiPlanets.mathurak, 150){{
            captureWave = 15;
            difficulty = 5;
        }};
        konota = new SectorPreset("konota", LaiPlanets.mathurak, 300){{
            difficulty = 4;
        }};
		imposmor = new SectorPreset("imposmor", LaiPlanets.mathurak, 600){{
			difficulty = 1;
			captureWave = 15;
		}};
    }
}
 
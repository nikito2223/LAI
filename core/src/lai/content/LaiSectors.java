package lai.content;

import mindustry.content.*;
import mindustry.type.*;

public class LaiSectors {
    public static SectorPreset
    /* mathexis */  start, lemans, konota, imposmor;

    public static void load() {
        start = new SectorPreset("Start", LaiPlanets.mathexis, 15){{
            captureWave = 15;
            difficulty = 5;
        }};
        lemans = new SectorPreset("lemans", LaiPlanets.mathexis, 150){{
            captureWave = 15;
            difficulty = 5;
        }};
        konota = new SectorPreset("konota", LaiPlanets.mathexis, 300){{
            difficulty = 4;
        }};
		imposmor = new SectorPreset("imposmor", LaiPlanets.mathexis, 600){{
			difficulty = 1;
			captureWave = 15;
		}};
    }
}
 
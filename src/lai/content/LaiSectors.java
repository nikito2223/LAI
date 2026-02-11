package lai.content;

import lai.content.*;
import static lai.content.LaiPlanets.*;
import mindustry.content.*;
import mindustry.type.*;

public class LaiSectors {
    public static SectorPreset tests;

    public static void load() {
        tests = new SectorPreset("tests", mathexis, 15){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 10;
            difficulty = 1;
            overrideLaunchDefaults = true;
            noLighting = true;
            startWaveTimeMultiplier = 3f;
        }};
    }
}
 
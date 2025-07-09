package lai.type;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import lai.type.LoadAnnoProcessor;
import lai.content.*;
import lai.content.blocks.*;
import lai.ui.dialogs.*;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class DebugLaiMode {

	public static boolean isDebuging = true;

	public static void starter(){
		starterProfile();
        Events.on(EventType.ClientLoadEvent.class, e -> {
            planetsActivete();        
        });
	}

    public static void planetsActivete(){
        DisnablePlanetDialog.init();
    }

	public static void starterProfile(){
		Log.info(">>> LaiMod: Старт загрузки контента");
        long globalStart = System.currentTimeMillis();
    
        Map<String, Long> timings = new LinkedHashMap<>();
    
        profile(timings, "LaiStatus", LaiStatus::load);
        profile(timings, "LaiItems", LaiItems::load);
        profile(timings, "LaiLiquids", LaiLiquids::load);
        profile(timings, "LaiSchematics", () -> new LaiSchematics().load());
        profile(timings, "LaiUnits", LaiUnits::load);
    
        // Blocks
        profile(timings, "LaiBlocksCrafting", LaiBlocksCrafting::load);
        profile(timings, "LaiBlocksLiquids", LaiBlocksLiquids::load);
        profile(timings, "LaiBlocksTurrets", LaiBlocksTurrets::load);
        profile(timings, "LaiEnvironmentBlocks", LaiEnvironmentBlocks::load);
        profile(timings, "LaiBlocks", LaiBlocks::load);
        profile(timings, "LaiBlocksDistribution", LaiBlocksDistribution::load);
        profile(timings, "LaiBlocksUnits", LaiBlocksUnits::load);
    
        // Planets & Sectors
        profile(timings, "LaiPlanets", LaiPlanets::load);
        profile(timings, "LaiSectors", LaiSectors::load);
    
        // Tech Tree
        profile(timings, "MathexisTechTree", MathexisTechTree::load);
    
        // Анализ производительности
        long max = timings.values().stream().max(Long::compare).orElse(0L);
        long min = timings.values().stream().min(Long::compare).orElse(0L);
        double avg = timings.values().stream().mapToLong(Long::longValue).average().orElse(0.0);
    
        Log.info(">>> LaiMod: Отчёт о производительности:");
        for (Map.Entry<String, Long> entry : timings.entrySet()) {
            String name = entry.getKey();
            long time = entry.getValue();
            String color;
    
            if (time > max * 1.2) {
                color = "[black]";
            } else if (time > avg * 1.2) {
                color = "[red]";
            } else if (time >= avg * 0.8) {
                color = "[yellow]";
            } else {
                color = "[green]";
            }
    
            Log.info(color + " > " + name + ": " + time + " мс[]");
        }
    
        long globalEnd = System.currentTimeMillis();
        Log.info(">>> LaiMod: Общая загрузка заняла " + (globalEnd - globalStart) + " мс");
	}

	// Метод профилирования
    private static void profile(Map<String, Long> timings, String name, Runnable runnable) {
        long start = System.currentTimeMillis();
        try {
            runnable.run();
        } catch (Exception e) {
            Log.err("Ошибка загрузки: " + name, e);
        }
        long end = System.currentTimeMillis();
        timings.put(name, end - start);
    }
}
package lai.graphics;

import mindustry.graphics.CacheLayer;

import static mindustry.Vars.*;

public class CLayer {
	public static CacheLayer plazme;

	public static void init(){
        CacheLayer.add(
            plazme = new CacheLayer.ShaderLayer(LAIShaders.plazme)
        );
    }
}
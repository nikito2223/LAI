package lai.content;

import mindustry.game.Schematic;
import mindustry.game.Schematics;

public class LaiSchematics {
    public static Schematic coreCaser;

    public void load() {
        coreCaser = Schematics.readBase64("bXNjaAF4nGNgYWBhZmDJS8xNZeBKzi9KjU9OLE4tYuBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWTgy0nM1AWp1YWoZWBgBCEgAQAgIRMM");
    }
}

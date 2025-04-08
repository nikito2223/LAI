package lai.ui.dialogs;

import mindustry.ui.dialogs.PlanetDialog;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.SettingsMenuDialog;
import mindustry.Vars;
import arc.scene.ui.layout.Table;
import arc.scene.ui.TextButton;
import mindustry.ui.dialogs.BaseDialog; // Импортируем BaseDialog

public class DisnablePlanetDialog extends PlanetDialog {
    public static boolean uiEnabled = true; // Флаг для включения/выключения UI

    public static void init() {
		addSettingsOption();
	}

    static void addSettingsOption() {
        Vars.ui.settings.game.checkPref("disablePlanetUI", false, value -> {
            uiEnabled = !value;
        });
    }

    @Override
    public PlanetDialog show() { // Должно возвращать PlanetDialog
        if (uiEnabled) {
            super.show();
        }
        return this; // Возвращаем сам диалог
    }

    public void enableUI() {
        uiEnabled = true;
    }
}
package lai.ui;

import arc.*;
import arc.scene.style.*;
import arc.scene.ui.Button.*;
import arc.scene.ui.ImageButton;
import mindustry.gen.*;

// --- Код взят из мода Aquarion ---
// Источник: https://github.com/Twcash/Aquarion

/*
 * Based on code by twcash
 * Copyright 2024 twcash
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


public class LaiStyles {

    public static ImageButton.ImageButtonStyle technodeFull;

    public static void load() {
        technodeFull = new ImageButton.ImageButtonStyle() {{
                this.up = Core.atlas.drawable("lai-circle-button");
                this.over = Core.atlas.drawable("lai-circle-button-down");
            }};
    }
}
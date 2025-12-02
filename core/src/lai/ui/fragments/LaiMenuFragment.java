package lai.ui.fragments;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.scene.Group;
import arc.scene.ui.layout.Table;
import arc.scene.ui.*;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.gen.Icon;
import mindustry.graphics.g3d.PlanetParams;
import mindustry.graphics.g3d.PlanetRenderer;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.ImageButton.*;
import arc.scene.ui.TextButton.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.core.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;

import mindustry.ui.fragments.MenuFragment.MenuButton;
import mindustry.ui.fragments.MenuFragment;


import arc.scene.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.Seq;
import arc.util.*;

import lai.content.LaiPlanets;

import static mindustry.Vars.content;
import static mindustry.Vars.ui;

public class LaiMenuFragment extends MenuFragment{
    private Table container;
    private PlanetRenderer planetRenderer = new PlanetRenderer();
    private float rotationSpeed = 0.1f; // скорость вращения

    @Override
    public void build(Group parent){
        // Сначала построим стандартное меню
        super.build(parent);

        // Затем добавим наш слой, который отрисует фон (перекроет стандартный фон)
        parent.fill((x, y, w, h) -> {
            Draw.reset();
            drawBackground();
        });
    }



    private void drawBackground(){
        // очистка экрана
        Core.graphics.clear(Color.black);

        // простые звезды
        Draw.color(Color.white);
        for(int i = 0; i < 120; i++){
            float x = Mathf.random(Core.graphics.getWidth());
            float y = Mathf.random(Core.graphics.getHeight());
            Fill.circle(x, y, Mathf.random(0.5f, 2f));
        }
        Draw.color();

        // угол вращения (градусы)
        float angle = Time.time * rotationSpeed * 60f;

        // параметры планеты
        PlanetParams params = new PlanetParams();

        if(content.planets().isEmpty()) return;
        params.planet = LaiPlanets.mathexis; // замени на нужную планету

        float cx = Mathf.cosDeg(angle);
        float cz = Mathf.sinDeg(angle);
        params.camDir = new Vec3(cx, 0f, cz).nor();

        params.zoom = 3f;
        params.drawSkybox = true;
        params.drawUi = false;
        params.viewW = Core.graphics.getWidth();
        params.viewH = Core.graphics.getHeight();
        params.uiAlpha = 0f;

        planetRenderer.render(params);
    }

}

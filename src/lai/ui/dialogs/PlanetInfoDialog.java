package lai.ui.dialogs;

import arc.*;
import arc.graphics.*;
import arc.scene.ui.layout.Table;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.type.*;
import lai.type.LaiPlanet;

public class PlanetInfoDialog extends BaseDialog {

    public PlanetInfoDialog(){
        super("@info");
        addCloseButton();
    }

    /** Загрузить данные планеты */
    public void showFor(Planet planet){
        cont.clear();

        cont.table(t -> {
            // Заголовок
            t.add("[accent]" + planet.localizedName)
                .style(Styles.outlineLabel)
                .padBottom(6)
                .row();

            // Разделительная линия


            // Описание
            t.add(planet.description != null ? planet.description : "[lightgray]<нет описания>")
                .wrap().width(400f).padTop(5).left().row();

            t.row();

            t.row();

            if(planet instanceof LaiPlanet lp){
                t.add("[accent]Газы атмосферы:")
                    .style(Styles.outlineLabel)
                    .padTop(10).left().row();

                if(lp.getAtmosphereGases().isEmpty()){
                    t.add("[lightgray]Нет данных").left().padTop(4);
                } else {
                    t.table(gTable -> {
                        gTable.left();
                        lp.getAtmosphereGases().each(g -> {
                            float percent = g.amount * 100f;
                            percent = (int)percent; // убираем .000000

                            gTable.add(g.liquid.localizedName + ": [accent]" + (int)percent + "%")
                                .left().padBottom(3);
                            gTable.row();
                        });
                    }).padTop(6);
                }
            } else {
                t.add("[gray]Атмосферные данные недоступны.")
                    .padTop(10).left();
            }

        }).pad(12f).growX();

        show();
    }
}

package lai.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

import static mindustry.content.Items.*;

public class LaiItems {
    public static Item lithium, iron, steel, platinum, vanadium, rhodium, uranium, enrichmentUranium;

    public static final Seq<Item> mathexisItems = new Seq<>(), mathexisOnlyItems = new Seq<>();

    public static void load(){
        lithium = new Item("lithium", Color.valueOf("4d59a1")){{
            hardness = 1; // Легко добывается
            cost = 1.8f; // Довольно ценный ресурс
            flammability = 0.5f; // Может воспламеняться
            radioactivity = 0f; // Не радиоактивен
            charge = 0.8f; // Отличный проводник энергии (используется в батареях)
            explosiveness = 0.3f; // Может взрываться при высоких температурах
        }};
        iron = new Item("iron", Color.valueOf("b7c5cc")){{
            hardness = 1;
            explosiveness = 0.4f;
            cost = 1f; 
        }};
        platinum = new Item("platinum", Color.valueOf("d76dd1")){{
            cost = 1.9f;
            hardness = 2;
        }};
		rhodium = new Item("rhodium", Color.valueOf("a0ecbd")){{
            cost = 1.9f;
            hardness = 2;
        }};
        vanadium = new Item("vanadium", Color.valueOf("4b4b4b")){{
            cost = 1.9f;
            hardness = 2;
        }};
        uranium = new Item("uranium", Color.valueOf("77ff00")){{
            explosiveness = 0.2f;
            hardness = 4;
            radioactivity = 1f;
            cost = 1.1f;
            healthScaling = 0.2f;
        }};
        enrichmentUranium = new Item("enrichmentUranium", Color.valueOf("77ff00")){{
            explosiveness = 1.2f;
            radioactivity = 1.5f;
            cost = 2.3f;
            healthScaling = 0.2f;
        }};

        steel = new Item("steel", Color.valueOf("a0a0a0")){{
            hardness = 3; // Прочность при бурении (требуется более мощный бур)
            cost = 2.5f; // Дорогой ресурс (используется для сложных механизмов)
            flammability = 0f; // Сталь не горит
            radioactivity = 0f; // Не радиоактивна
            charge = 0.2f; // Может использоваться для проводников энергии
        }};

        mathexisItems.addAll(
            lithium, iron,steel, 
            platinum, rhodium, 
            vanadium, graphite, 
            silicon, uranium, 
            enrichmentUranium, 
            coal, lead
        );

        mathexisOnlyItems.addAll(mathexisItems).removeAll(serpuloItems);
        mathexisOnlyItems.addAll(mathexisItems).removeAll(erekirItems);
    }
}

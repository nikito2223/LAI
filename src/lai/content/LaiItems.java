package lai.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

import static mindustry.content.Items.*;

public class LaiItems {
    public static Item lithium, iron, steel, platinum,
            vanadium, rhodium, uranium,
            rawKinetic, natrium;

    public static final Seq<Item> mathexisItems = new Seq<>(), mathexisOnlyItems = new Seq<>();

    public static void load(){
        lithium = new Item("lithium", Color.valueOf("4d59a1")){{
            hardness = 1; // Легко добывается (аналог меди)
            cost = 0.8f; // Уменьшено с 1.8f (дороже меди, но дешевле свинца)
            flammability = 0.4f; // Чуть снижено
            radioactivity = 0f;
            charge = 0.9f; // Отличный проводник для батарей
            explosiveness = 0.2f; // Снижено
        }};
        
        iron = new Item("iron", Color.valueOf("b7c5cc")){{
            hardness = 1; // Легко добывается (аналог свинца)
            explosiveness = 0.2f; // Снижено
            cost = 0.7f; // Немного снижено
            healthScaling = 0.1f; // Добавлено
        }};
        
        platinum = new Item("platinum", Color.valueOf("d76dd1")){{
            cost = 1.1f; // Снижено с 1.9f (примерно как титан)
            hardness = 2; // Снижено с 3 (средняя твердость)
            healthScaling = 0.2f; // Добавлено
        }};
        
        rhodium = new Item("rhodium", Color.valueOf("a0ecbd")){{
            cost = 1.3f; // Снижено с 1.9f
            hardness = 3; // Высокая твердость (как титан)
            healthScaling = 0.3f; // Добавлено
            charge = 0.4f; // Добавлено
        }};
        
        vanadium = new Item("vanadium", Color.valueOf("4b4b4b")){{
            cost = 1.4f; // Снижено с 1.9f
            hardness = 3; // Снижено с 3
            healthScaling = 0.6f; // Добавлено (хорошо для брони)
        }};
        
        uranium = new Item("uranium", Color.valueOf("77ff00")){{
            explosiveness = 0.1f; // Снижено
            hardness = 3; // Снижено с 4 (как титан)
            radioactivity = 0.8f; // Снижено
            cost = 1.2f; // Чуть увеличено
            healthScaling = 0.4f; // Добавлено
        }};

        steel = new Item("steel", Color.valueOf("a0a0a0")){{
            cost = 1.5f; // Снижено с 2.5f (примерно как surge alloy)
            flammability = 0f;
            radioactivity = 0f;
            charge = 0.3f; // Увеличено
            healthScaling = 0.3f; // Добавлено
            hardness = 2; // Добавлено
        }};
        
        rawKinetic = new Item("raw-kinetic", Color.valueOf("e7dd73")){{
            hardness = 2; // Снижено с 4
            cost = 0.6f; // Снижено
            charge = 0.3f; // Чуть увеличено
            healthScaling = 0.2f; // Снижено
        }};

        natrium = new Item("natrium", Color.valueOf("bd2525")){{
            hardness = 2; // Снижено с 4
            cost = 0.7f; // Снижено с 0.9f
            charge = 0.2f;
            healthScaling = 0.15f; // Снижено
            flammability = 0.6f; // Добавлено (натрий реагирует с водой)
            explosiveness = 0.4f; // Добавлено
        }};

        mathexisItems.addAll(
            lithium, iron, steel, 
            platinum, rhodium, 
            vanadium, graphite, 
            silicon, uranium, 
            natrium,
            coal, lead, rawKinetic, surgeAlloy
        );

        mathexisOnlyItems.addAll(mathexisItems).removeAll(serpuloItems);
        mathexisOnlyItems.addAll(mathexisItems).removeAll(erekirItems);
    }
}

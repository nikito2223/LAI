package lai.content;

import arc.graphics.*;
import mindustry.type.*;
import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.type.Liquid;
import static mindustry.content.Liquids.*;
import lai.type.LaiLiquid;
import lai.content.*;

public class LaiLiquids {
    public static Liquid 
    symbiote, 
    freshwater, 
    fueloli, 
    oxygen, carbondioxide;
    //Спец Житкость
    public static LaiLiquid 
    //Активные
    waterRadioction,
    //Обычные
    lava, acid,
    //gasses
    helium, neon, heliophanus;

    public static void load() {
        //liquid
        freshwater = new Liquid("fresh-water", Color.valueOf("20B2AA")){{
            heatCapacity = 0.4f;
            effect = StatusEffects.wet;
            boilPoint = 0.7f;
            gasColor = Color.grays(0.9f);
            alwaysUnlocked = true;
        }};
        //LaiLiquid
        waterRadioction = new LaiLiquid("water-radioction", Color.valueOf("6ecd8c")){{
            heatCapacity = 0.4f;
            effect = LaiStatus.radiction;
            boilPoint = 0.7f;
            lightColor = Color.valueOf("6ecd5c").a(1.8f);
            radioactive = 0.4f;
        }};
        symbiote = new CellLiquid("symbiote", Color.valueOf("ffcc35")){{
            heatCapacity = 0.4f;
            temperature = 1.0f;  
            viscosity = 0.5f;    
            flammability = 0.0f; 
            explosiveness = 0.0f;
            capPuddles = false;
            spreadTarget = freshwater;
            moveThroughBlocks = true;
            incinerable = false;
            blockReactive = false;
            canStayOn.addAll(freshwater, waterRadioction, oil, oxygen);
        }};

        lava = new LaiLiquid("lava", Color.valueOf("ff7806")){{
            temperature = 100f;
            damageLiquid = true;
            viscosity = 0.9f;
            effect = StatusEffects.melting;
            lightColor = Color.valueOf("bf5c07").a(0.4f);
        }};
        acid = new LaiLiquid("acid", Color.valueOf("30801a")){{
            temperature = 10.38f;
            damageLiquid = true;
            viscosity = 0.10f;
            lightColor = Color.valueOf("30801a").a(0.4f);
        }};
        //t.g
        fueloli = new Liquid("fueloli", Color.valueOf("121212")){{
            viscosity = 0.2f;
            flammability = 2.2f;
            explosiveness = 3.2f;
            heatCapacity = 1.7f;
            barColor = Color.valueOf("121212");
            effect = StatusEffects.tarred;
            boilPoint = 0.65f;
            canStayOn.add(freshwater);
        }};
        //gasses
		oxygen = new Liquid("oxygen", Color.valueOf("90c4d4")){{
            temperature = 0.8f;
            viscosity = 0.1f;   
            flammability = 0f;  
            explosiveness = 0f;  
            gas = true;         
            coolant = true;
        }};
		carbondioxide = new Liquid("carbondioxide", Color.valueOf("828282")){{
            temperature = 0.7f;
            viscosity = 0.2f;
            flammability = 0f;  
            explosiveness = 0f;  
            gas = true;         
            coolant = false;
        }};
        helium = new LaiLiquid("helium", Color.valueOf("60d0ff"), 0.6f){{
            gradient(Color.valueOf("094469"), Color.valueOf("60d0ff"));
            flammability = 0f;              // не горит, инертен
            explosiveness = 0f;             // полностью безопасен
            heatCapacity = 1.6f;            // хорош для охлаждения, но хуже Heliophanus
            viscosity = 0.3f;               // очень текучий (быстро двигается по трубам)
            temperature = 0.15f;            // низкая температура
            gas = true;                     // это газ (важно!)
            boilPoint = 0.2f;               // закипает при малом нагреве  

            barColor = Color.valueOf("cceeff");
            lightColor = Color.valueOf("d0ffff");
        }};
        neon = new LaiLiquid("neon", Color.valueOf("65d0ff")){{ 
            flammability = 0f;              // инертен, не горит
            explosiveness = 0.05f;          // может быть немного нестабилен в ионизированной форме
            heatCapacity = 1.1f;            // ниже среднего
            viscosity = 0.5f;               // умеренно текучий
            temperature = 0.4f;             // теплый газ, особенно в ионизированной форме
            gas = true;                     // это газ
            boilPoint = 0.1f;               // легко испаряется (почти всегда газ)

            barColor = Color.valueOf("ff73ec");
            lightColor = Color.valueOf("ff99ff");

        }};
        heliophanus = new LaiLiquid("heliophanus", Color.valueOf("56a2d1")){{
            flammability = 0f;                   
            explosiveness = 0.1f;                
            heatCapacity = 2.2f;                 
            viscosity = 0.7f;                    
            temperature = 0.1f;                  
            gas = false;                         
            boilPoint = 0.3f;                    
            alwaysUnlocked = false;              
            effect = StatusEffects.freezing;                   
            barColor = Color.valueOf("a3c9ff");  
            lightColor = Color.valueOf("b8d0ff");
        }};
    }
}

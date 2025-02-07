package lai.content;

import lai.graphics.LaiPal;
import mindustry.type.*;
import mindustry.content.*;
import arc.graphics.*;

public class LaiStatus {

	public static StatusEffect
             coldrains, platinum, rhodium, radiction;

	public static void load(){
        coldrains = new StatusEffect("coldrains") {{
            color = LaiPal.platinumBack;
            speedMultiplier = 0.6f;
            effect = Fx.blastExplosion;
        }};
        platinum = new StatusEffect("platinum") {{
            color = LaiPal.platinumBack;
            speedMultiplier = 0.6f;
            effect = LaiFx.platinum;
        }};
        rhodium = new StatusEffect("rhodium") {{
            color = LaiPal.rhodium;
            speedMultiplier = 0.8f;
            damage = 0.167f;
            effectChance = 0.3f;
            effect = LaiFx.rhodium;
        }};
        radiction = new StatusEffect("status-radiction") {{
            color = LaiPal.radiction;
            speedMultiplier = 0.8f;
            damage = 0.167f;
            effectChance = 0.3f;
            effect = LaiFx.rhodium;
        }};
    }
}
package lai.content;

import lai.graphics.LaiPal;
import mindustry.type.*;
import mindustry.content.*;
import arc.graphics.*;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.type.*;

import static mindustry.content.StatusEffects.*;

public class LaiStatus {

	public static StatusEffect
        coldrains, platinum, rhodium, radiction, сorrosiveFlux;

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
            damage = 0.169f;
            effectChance = 0.3f;
            effect = LaiFx.rhodium;
        }};
        сorrosiveFlux = new StatusEffect("сorrosive-flux"){{
            color = Color.valueOf("ff6f61");
            damage = 0.320f;
            speedMultiplier = 1.8f;

            init(() -> {
                affinity(shocked, (unit, result, time) -> {
                    unit.damage(transitionDamage);
                });
                opposite(burning, melting);
            });          
        }};

    }
}
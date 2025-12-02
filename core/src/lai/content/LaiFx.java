package lai.content;

import arc.math.*;
import arc.graphics.g2d.*;
import mindustry.entities.Effect;
import lai.graphics.LaiPal;
import mindustry.graphics.Drawf;
import arc.graphics.g2d.Lines;
import arc.graphics.Color;
import mindustry.entities.*;

import arc.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.world.blocks.power.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static mindustry.Vars.*;


public class LaiFx {
	public static final Rand rand = new Rand();

	public static Effect 
    platinum = new Effect(60, e -> {
        color(LaiPal.platinumFront);
         e.scaled(8, i -> {
            stroke(5f * i.fout());
            Lines.circle(e.x, e.y, 8f + i.fin() * 26f);
        });
        color(LaiPal.platinumBack);
        stroke(e.fout());

        randLenVectors(e.id + 4, 8, 1f + 25f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
        });

        Drawf.light(e.x, e.y, 50f, LaiPal.platinumBack, 0.8f * e.fout());
    }),
    liquidRing = new Effect(30f, e -> {
        for (int i = 0; i < 10; i++) {
            float angle = e.rotation + i * 36f;
            float x = e.x + Angles.trnsx(angle, e.fin() * 20f);
            float y = e.y + Angles.trnsy(angle, e.fin() * 20f);
            Drawf.liquid(Liquids.water.fullIcon, x, y, 3f, Color.cyan);
        }
    }),

    shootSpider = new Effect(8, e -> {
        color(LaiPal.spiderKink, LaiPal.lightPinkViolet, e.fin());
        float w = 1f + 5 * e.fout();
        Drawf.tri(e.x, e.y, w, 15f * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, w, 3f * e.fout(), e.rotation + 180f);
    }),


    kineticChargeFx = new Effect(40f, e -> {
        Color chargeColor = Color.valueOf("ffbb00"); // Цвет кинетического сплава, можно заменить
    
        Draw.z(Layer.effect);
    
        // Центр энергетического пульса
        Draw.color(chargeColor, Color.white, e.fin());
        Fill.circle(e.x, e.y, 1.5f + 3f * e.fout());
    
        // Основные молнии (по 4 мощные)
        for(int i = 0; i < 4; i++){
            float angle = Mathf.random(360f);
            float len = 12f + Mathf.random(6f);
            float tx = Angles.trnsx(angle, len);
            float ty = Angles.trnsy(angle, len);
    
            Drawf.light(e.x, e.y, e.x + tx, e.y + ty, 24f, chargeColor, 0.8f);
            Draw.color(Color.white);
            Lines.stroke(1.6f - e.fin());
            Lines.line(e.x, e.y, e.x + tx, e.y + ty);
        }
    
        // Входящие искры — будто энергия втекает в блок
        Angles.randLenVectors(e.id + 1, 8, 10f * e.fout(), (x, y) -> {
            Draw.color(chargeColor);
            Fill.circle(e.x + x, e.y + y, 0.8f + e.fout() * 1.2f);
        });
    
        // Волна удара
        Draw.color(chargeColor, Color.white, e.fout());
        Lines.stroke(2f * e.fout());
        Lines.circle(e.x, e.y, 4f + 8f * e.fin());
    }),


    hitfrezeeningIncinerator = new Effect(6, e -> {
        color(LaiPal.freezing);
        stroke(e.fout() * 2f);

        randLenVectors(e.id, 7, e.finpow() * 7f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 2 + 1f);
        });
    }),

    shootBigSmokFreze = new Effect(18f, e -> {
        color(LaiPal.freezingShoot);

        randLenVectors(e.id, 9, e.finpow() * 33f, e.rotation, 26f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2.4f + 0.2f);
        });
    }),

    uraniumReactorExplosion = new Effect(30, 500f, b -> {
        float intensity = 3.8f;
        float baseLifetime = 25f + intensity * 11f;
        b.lifetime = 50f + intensity * 65f;

        color(LaiPal.radiction);
        alpha(0.7f);
        for(int i = 0; i < 4; i++){
            rand.setSeed(b.id*2 + i);
            float lenScl = rand.random(0.4f, 1f);
            int fi = i;
            b.scaled(b.lifetime * lenScl, e -> {
                randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 22f * intensity, (x, y, in, out) -> {
                    float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                    float rad = fout * ((2f + intensity) * 2.35f);

                    Fill.circle(e.x + x, e.y + y, rad);
                    Drawf.light(e.x + x, e.y + y, rad * 2.5f, LaiPal.reactorGreen, 0.5f);
                });
            });
        }}),

    pulseEffect = new Effect(30f, e -> {
        float radius = 80f * e.fin();
        color(e.color, Color.white, e.fin());
        stroke(3f * (1f - e.fin()));
        circle(e.x, e.y, radius);
    }),
    
    rhodium = new Effect(30f, e -> {
        color(LaiPal.rhodiumFront);
         e.scaled(9, i -> {
            stroke(8f * i.fout());
            Lines.circle(e.x, e.y, 9f + i.fin() * 28f);
        });
        color(LaiPal.rhodium);
        stroke(e.fout());

        randLenVectors(e.id + 1, 7, 1f + 35f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 3f);
        });

        Drawf.light(e.x, e.y, 50f, LaiPal.rhodium, 0.9f * e.fout());
    });
}
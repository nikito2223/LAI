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
package lai.content;

import arc.graphics.g2d.*;
import mindustry.entities.Effect;
import lai.graphics.LaiPal;
import mindustry.graphics.Drawf;
import arc.graphics.g2d.Lines;
import arc.graphics.Color;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;
import arc.math.*;


public class LaiFx {
	

	public static Effect 
    platinum = new Effect(30f, e -> {
        color(LaiPal.platinumFront);
         e.scaled(8, i -> {
            stroke(5f * i.fout());
            Lines.circle(e.x, e.y, 8f + i.fin() * 26f);
        });
        color(LaiPal.platinumBack);
        stroke(e.fout());

        randLenVectors(e.id + 1, 8, 1f + 25f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
        });

        Drawf.light(e.x, e.y, 50f, LaiPal.platinumBack, 0.8f * e.fout());
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
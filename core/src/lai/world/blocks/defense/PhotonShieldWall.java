package lai.world.blocks.defense;


import arc.audio.Sound;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.entities.bullet.*;
import mindustry.game.Team;
import mindustry.graphics.*;
import mindustry.world.meta.*;
import mindustry.content.*;
import mindustry.gen.Groups;
import mindustry.gen.Bullet;
import mindustry.entities.bullet.*;
import mindustry.gen.Unit;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;

import mindustry.world.blocks.defense.*;

import static mindustry.Vars.*;

public class PhotonShieldWall extends Wall {

    public Color reflectColor = Color.cyan;
    public Sound reflectSound = Sounds.spark;
    public boolean flashOnReflect = true;

    public PhotonShieldWall(String name) {
        super(name);
        absorbLasers = true;
        chanceDeflect = -1f;
        lightningChance = -1f;
    }

    public class PhotonShieldWallBuild extends WallBuild {

		@Override
		public boolean collision(Bullet bullet){
		    super.collision(bullet);
		
		    hit = 1f;
		
		    if(isLaser(bullet.type) && !(bullet.owner instanceof Building)){
		        reflectLaser(bullet);
		        return false; 
		    }
		
		    return true;
		}

        private boolean isLaser(BulletType type){
            return type instanceof LaserBulletType
                    || type instanceof ContinuousLaserBulletType
                    || type instanceof LightningBulletType;
        }

        private void reflectLaser(Bullet bullet){
            reflectSound.at(x, y, Mathf.random(0.9f, 1.1f));

            Fx.hitLaser.at(x, y, reflectColor);

            float angle;

            if(bullet.owner instanceof Unit u){
                angle = Angles.angle(x, y, u.x, u.y);
            }else{
                angle = bullet.rotation() + 180f;
            }

            BulletType type = bullet.type;
            Bullet reflected = type.create(this, team, x, y, angle);

            reflected.time += 3f;
        }
    }

}
package lai.entities.bullet;

import mindustry.gen.Bullet;

import arc.graphics.Color;
import mindustry.entities.bullet.*;

public class TeamArtilleryBulletType extends ArtilleryBulletType {
	
	public float energyGlowIntensity = 1.5f; // яркость центра
    public float energyEdgeMultiplier = 0.3f; // затемнение краёв

	public TeamArtilleryBulletType(float speed, float damage, String bulletSprite){
		super(speed, damage, bulletSprite);
	}	



	public TeamArtilleryBulletType(float speed, float damage){
        this(speed, damage, "shell");
    }

    public TeamArtilleryBulletType(){
        this(1f, 1f, "shell");
    }

    @Override
    public void init(Bullet b){
        super.init(b);

        // Цвет зависит от команды
        Color teamColor = b.team.color;

        // Тёмные края, яркий центр
        this.backColor = teamColor.cpy().mul(energyEdgeMultiplier); // тёмные края
        this.frontColor = this.lightningColor = teamColor.cpy().mul(energyGlowIntensity); // яркий центр

        // Движение энергии (имитация внутреннего движения)
        b.vel.add((float)Math.sin(b.time * 0.3f) * 0.5f, (float)Math.cos(b.time * 0.3f) * 0.5f);


        // Уменьшение скорости для баланса
        b.vel.x *= 0.95f;
        b.vel.y *= 0.95f;

        // Можно добавить небольшой trailEffect или smokeEffect для визуализации энергии
        // b.trailEffect = Fx.lightningShoot; // если хочешь визуальный след энергии
    }
}
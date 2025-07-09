package lai.world.blocks.power;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.power.Battery;
import mindustry.game.EventType.*;
import static mindustry.game.EventType.*;
import mindustry.graphics.Pal;
import mindustry.ui.*;
import mindustry.world.consumers.*;

import lai.graphics.*;

import static mindustry.world.blocks.power.Battery.*;
 
public class LithiumBattery extends Battery{
    
    public float powerConsume; //Количества энергии

	public LithiumBattery(String name) {
		super(name);
	}

    @Override
    public void setBars(){
    	super.setBars();
    	addBar("powerHealth", (LithiumBatteryBuild entity) -> new Bar("bar.lai-powerHealth", LaiPal.lithiumStrength, () -> entity.powerCapacity));
    }


    public class LithiumBatteryBuild extends BatteryBuild{

        public float powerCapacity = 1f; //Емкость акумы  1 = 100%, 0 = 0%
        
        public float healthPower(){
            return (powerConsume / powerCapacity);
        }

        @Override
        public void updateTile() {     
            float health = healthPower();
        	if(power.status > 0) {
        		powerCapacity -= Time.delta * 1 / powerConsume;
                if(power.status < 1){
                
                }else {
                    
                }

        	}


        }
    }
}
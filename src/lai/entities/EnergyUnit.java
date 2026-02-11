package lai.entities;

import mindustry.gen.Unit;
import mindustry.gen.Unitc;
import mindustry.gen.UnitEntity;

// собственный тип юнита с энергией
public class EnergyUnit extends UnitEntity {
    public float energy = 100f;
    public float maxEnergy = 100f;
    public boolean enabled = true;

    @Override
    public void update() {
        // если нет энергии, юнит не работает
        if(!enabled) return;
        super.update();
    }

    public float energyf(){
        return energy / maxEnergy;
    }

    public void consumeEnergy(float amount){
        energy -= amount;
        if(energy <= 0){
            energy = 0;
            enabled = false;
            vel.setZero(); // останавливаем
        }
    }

    public void recharge(float amount){
        energy = Math.min(maxEnergy, energy + amount);
        if(energy > 0) enabled = true;
    }
}

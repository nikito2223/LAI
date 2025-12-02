package lai.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.geom.Vec2;
import arc.struct.*;
import arc.util.*;
import mindustry.game.Team;
import mindustry.ui.Fonts;

public class LaiTeams {
    public static Team xenoSyndicate;

    public static void load() {
        xenoSyndicate = newTeam(5, "xeno-syndicate", Color.valueOf("865fd9"), "\uEB8C");
    }


    protected static Team newTeam(int id, String name, Color color, String emoji) {
            Team team = Team.get(id);
            team.name = name;
            team.color.set(color);
    
            team.palette[0] = color;
            team.palette[1] = color.cpy().mul(0.75f);
            team.palette[2] = color.cpy().mul(0.5f);
    
            for(int i = 0; i < 3; i++){
                team.palettei[i] = team.palette[i].rgba();
            }
    
            //now put whatever the heck we get for an emoji
            team.emoji = team.emoji;
    
            return team;
        }
}
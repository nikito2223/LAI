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
    
            //attempt to load the team icon
            Seq<Font> fonts = Seq.with(Fonts.def, Fonts.outline);
    
            var ch = 65000 + id;
            Reflect.<ObjectIntMap<String>>get(Fonts.class, "unicodeIcons").put(name, ch);
            var stringIcons = Reflect.<ObjectMap<String, String>>get(Fonts.class, "stringIcons");
            stringIcons.put(name, ((char)ch) + "");
    
            int size = (int)(Fonts.def.getData().lineHeight/Fonts.def.getData().scaleY);
            TextureRegion region = Core.atlas.find("lai-team-" + name);
            Vec2 out = Scaling.fit.apply(region.width, region.height, size, size);
            Font.Glyph glyph = new Font.Glyph(){{
                id = ch;
                srcX = 0;
                srcY = 0;
                width = (int)out.x;
                height = (int)out.y;
                u = region.u;
                v = region.v2;
                u2 = region.u2;
                v2 = region.v;
                xoffset = 0;
                yoffset = -size;
                xadvance = size;
                kerning = null;
                fixedWidth = true;
                page = 0;
            }};
            fonts.each(f -> f.getData().setGlyph(ch, glyph));
    
            //now put whatever the heck we get for an emoji
            team.emoji = stringIcons.get(team.name, team.emoji);
    
            return team;
        }
}
#define HIGHP

uniform sampler2D u_texture;
uniform sampler2D u_noise;

uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;

#define GLOW_COLOR vec3(0.8, 0.3, 1.0)  // яркая плазма
#define CORE_COLOR vec3(0.2, 0.0, 0.4)  // темное ядро

#define NSCALE 30.0

void main(){
    vec2 uv = v_texCoords;
    vec2 pos = uv * u_resolution + u_campos;
    float time = u_time * 0.003;

    // динамическое течение плазмы
    vec2 flow1 = texture2D(u_noise, (pos/NSCALE) + vec2(time, -time*0.5)).rg - 0.5;
    vec2 flow2 = texture2D(u_noise, (pos/NSCALE) + vec2(-time*0.8, time*1.2)).rg - 0.5;

    vec2 distortion = (flow1 + flow2) * 0.1; // более сильное искажение
    vec2 distortedUV = uv + distortion;

    // шум для яркости плазмы
    float noise = texture2D(u_noise, (pos + distortion*200.0)/NSCALE + time*vec2(-0.5,0.7)).r;

    // имитация молний и ярких струй
    float streak = smoothstep(0.5, 0.8, noise) * sin(time*10.0 + noise*10.0);
    streak = clamp(streak, 0.0, 1.0);

    // пульсация только в сильных местах
    float pulse = pow(smoothstep(0.6, 1.0, noise), 2.0) * sin(time*15.0 + noise*20.0) * 0.5 + 0.5;

    vec3 plasma = mix(CORE_COLOR, GLOW_COLOR, streak);
    plasma *= 1.0 + pulse * 1.0; // усиление яркости в струях

    vec4 base = texture2D(u_texture, distortedUV);
    base.rgb = mix(base.rgb, plasma, streak);

    gl_FragColor = base;
}

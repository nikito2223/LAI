#define HIGHP

uniform sampler2D u_texture;
uniform sampler2D u_noise;

uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;

#define GLOW_COLOR vec3(0.8, 0.3, 1.0)  // фиолетовое свечение
#define CORE_COLOR vec3(0.3, 0.0, 0.5)  // тёмное ядро

#define NSCALE 40.0

void main(){
    vec2 uv = v_texCoords;
    vec2 pos = uv * u_resolution + u_campos;
    float time = u_time * 0.0025;

    // Шумовые искажения (без линий/бубликов)
    vec2 flow1 = texture2D(u_noise, (pos / NSCALE) + vec2(time * 1.2, time * -0.8)).rg - 0.5;
    vec2 flow2 = texture2D(u_noise, (pos / NSCALE) + vec2(-time * 0.9, time * 1.1)).rg - 0.5;

    vec2 distortion = (flow1 + flow2) * 0.05;
    vec2 distortedUV = uv + distortion;

    // Шумовое значение для формы
    float noise = texture2D(u_noise, (pos + distortion * 150.0) / NSCALE + time * vec2(-0.4, 0.6)).r;

    // Пульс — делает свечение более ярким, но не убирает его
    float pulse = sin(time * 8.0 + noise * 6.0) * 0.5 + 0.5;
    float enhanced = noise + pulse * 0.3; // ← добавляем пульс, а не заменяем

    float glow = smoothstep(0.35, 0.7, enhanced);
    float core = smoothstep(0.55, 0.8, enhanced);

    vec3 plasma = mix(CORE_COLOR, GLOW_COLOR, glow);
    plasma *= 1.0 + pulse * 0.6; // ← яркость усиливается во время пульсации

    vec4 base = texture2D(u_texture, distortedUV);
    base.rgb = mix(base.rgb, plasma, core);

    gl_FragColor = base;
}

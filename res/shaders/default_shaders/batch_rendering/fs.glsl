#version 330 core

out vec4 color;

in vec4 v_Color;
in vec2 v_textCoord;
in float v_textIndex;

uniform sampler2D u_Textures[8];

uniform vec2 u_LightPosition;
in vec2 v_FragmentPosition;

void main() {
	float multiplier = 10;
	float intensity = multiplier*(1/distance(v_FragmentPosition, u_LightPosition));
	
	color = intensity * texture(u_Textures[int(v_textIndex)], v_textCoord) * v_Color;
}

#version 330 core

out vec4 color;

in vec4 v_Color;
in vec2 v_textCoord;
in float v_textIndex;

uniform sampler2D u_Textures[8];

void main() {
	color = texture(u_Textures[int(v_textIndex)], v_textCoord) * v_Color;
}

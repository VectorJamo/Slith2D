#version 330 core

out vec4 color;

uniform vec4 u_Color;
uniform sampler2D u_TextureUnit;

in vec2 v_TextCoord;

void main() {
	color = texture(u_TextureUnit, v_TextCoord) * u_Color;
}

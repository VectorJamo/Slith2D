#version 330 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec2 textCoord;

uniform mat4 u_Scale;
uniform mat4 u_Translate;
uniform mat4 u_Projection;

out vec2 v_TextCoord;

void main() {
	gl_Position =  u_Projection * u_Translate * u_Scale * vec4(position.xy, 0.0, 1.0);
	v_TextCoord = textCoord;
}
#version 330 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec4 color;
layout (location = 2) in vec2 textCoord;
layout (location = 3) in float textIndex;

uniform mat4 u_Projection;

out vec4 v_Color;
out vec2 v_textCoord;
out float v_textIndex;

void main() {
	gl_Position =  u_Projection * vec4(position.xy, 0.0, 1.0);
	
	v_Color = color;
	v_textCoord = textCoord;
	v_textIndex = textIndex;
}	
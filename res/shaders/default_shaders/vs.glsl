#version 330 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec2 textCoord;

uniform mat4 u_Scale;
uniform mat4 u_Translate;
uniform mat4 u_Rotate;
uniform mat4 u_Projection;

out vec2 v_TextCoord;

void main() {
	vec4 localCoords = u_Rotate * u_Scale * vec4(position.xy, 0.0, 1.0);

	vec2 translationFactor = vec2(u_Scale[0][0]/2, u_Scale[1][1]/2); // Remember, is column major ordering (The first index is the column)

	localCoords.x += translationFactor.x;
	localCoords.y -= translationFactor.y;

	localCoords.y = -1.0 * localCoords.y;
	
	gl_Position =  u_Projection * u_Translate * localCoords;
	v_TextCoord = textCoord;
}
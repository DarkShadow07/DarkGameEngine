#version 400 core

in vec3 position;
in vec2 uvs;

out vec2 outUvs;

uniform mat4 transformation;
uniform mat4 projection;
uniform mat4 view;

void main(void)
{
    gl_Position = projection * view * transformation * vec4(position, 1.0);

    outUvs = uvs;
}
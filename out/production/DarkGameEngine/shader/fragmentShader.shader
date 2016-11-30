#version 400 core

in vec2 outUvs;

out vec4 outColor;

uniform sampler2D sampler;

void main(void)
{
    outColor = texture(sampler, outUvs);
}
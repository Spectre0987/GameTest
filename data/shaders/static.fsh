#version 400 core

in vec2 passedCoords;

layout(location = 0)
out vec4 out_Color;

uniform sampler2D texSampler;

void main(void)
{
    out_Color = texture(texSampler, passedCoords);
}

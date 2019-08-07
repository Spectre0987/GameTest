#version 400 core

layout(location = 0)

in vec3 position;
in vec2 textureCoords;
uniform mat4 transform;
uniform mat4 projection;

out vec2 passedCoords;

void main(void)
{
    gl_Position = transform * vec4(position, 1.0);
    passedCoords = textureCoords;
}

#version 450

layout(location = 0) out vec4 f_color;

layout(location=0) in vec3 in_color;

void main() {
    f_color = vec4(in_color.xyz, 1.0);
}

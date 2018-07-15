package entities;


import input.KeyboardHandler;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Light {

    private Vector3f position;
    private Vector3f color;

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public void move() {
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_I)) {
            position.z -=0.2f;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_L)) {
            position.x +=0.2f;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_J)) {
            position.x -=0.2f;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_K)) {
            position.z +=0.2f;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_U)) {
            position.y -=0.2f;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_O)) {
            position.y +=0.2f;
        }
    }
}

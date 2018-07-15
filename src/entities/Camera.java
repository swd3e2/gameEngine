package entities;

import input.KeyboardHandler;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.awt.event.KeyEvent;

public class Camera {

    private Vector3f position = new Vector3f(0,10,100);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {

    }

    public void move() {
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
            position.z -=0.2f;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_D)) {
            position.x +=0.2f;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_A)) {
            position.x -=0.2f;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_S)) {
            position.z +=0.2f;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_Q)) {
            position.y -=0.2f;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_E)) {
            position.y +=0.2f;
        }

    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

}

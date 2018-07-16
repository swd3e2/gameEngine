package entities;

import input.CursorHandler;
import input.KeyboardHandler;
import input.MouseScrollHandler;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.awt.event.KeyEvent;

public class Camera {

    private float distanceFromPlayer = 50;
    private float angleAroundPalyer = 0;

    private Vector3f position = new Vector3f(300,20,200);
    private float pitch = 20;
    private float yaw;
    private float roll;

    private Player player;

    public Camera(Player player) {
        this.player = player;
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

    public void move()
    {
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPalyer);
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance)
    {
        float theta = player.getRotY() + angleAroundPalyer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance;
    }

    private float calculateHorizontalDistance()
    {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance()
    {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom()
    {
        distanceFromPlayer = (float) MouseScrollHandler.getCameraPos();
    }

    private void calculatePitch()
    {
        if (CursorHandler.isIsMouseMoved() && KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_L)) {
            float pitchChange = (float)CursorHandler.getyPosChange()/ 4;
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer()
    {
        if (CursorHandler.isIsMouseMoved() && KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_O)) {
            float angleChange = (float) CursorHandler.getxPos();
            angleAroundPalyer -= angleChange;
        }
    }

}

package entities;

import RenderEngine.DisplayManager;
import input.KeyboardHandler;
import models.TexturedModel;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import terrains.Terrain;

public class Player extends Entity {

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;
    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private static final float TERRAIN_HEIGHT = 0;

    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(Terrain terrain)
    {
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getDelta(), 0);
        float distance = currentSpeed * DisplayManager.getDelta();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getDelta();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getDelta(), 0);
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight) {
            isInAir = false;
            upwardsSpeed = 0;
            super.getPosition().y = terrainHeight;
        }
    }

    public void checkInputs() {
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_W)) {
            currentSpeed = RUN_SPEED;
        } else if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_S)) {
            currentSpeed = -RUN_SPEED;
        } else {
            currentSpeed = 0;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_D)) {
            currentTurnSpeed = -TURN_SPEED;
        } else if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_A)) {
            currentTurnSpeed = TURN_SPEED;
        } else {
            currentTurnSpeed = 0;
        }
        if (KeyboardHandler.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
           jump();
        }
    }

    private void jump()
    {
        if (!isInAir) {
            isInAir = true;
            this.upwardsSpeed = JUMP_POWER;
        }
    }
}

package RenderEngine;

import input.CursorHandler;
import input.KeyboardHandler;
import input.MouseScrollHandler;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class DisplayManager {

    private static final int WIDTH = 1440;
    private static final int HEIGHT = 960;
    private static long window;
    private static GLFWVidMode videoMode;
    private static GLFWKeyCallback keyCallback;
    private static GLFWScrollCallback scroll_callback;
    private static GLFWCursorPosCallback cursor_pos_callback;
    private static float lastFrameTime;
    private static float delta;

    public static void createDisplay() {
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        window = glfwCreateWindow(WIDTH, HEIGHT, "LWJGL", 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Failed to create window");
        }
        // Init keyhandler
        glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());
        glfwSetScrollCallback(window, scroll_callback = new MouseScrollHandler());
        glfwSetCursorPosCallback(window, cursor_pos_callback = new CursorHandler());

        videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2);
        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        lastFrameTime = getCurrentTime();
    }

    public static void updateDisplay() {
        glfwPollEvents();
        glfwSwapBuffers(window);
        float currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime);
        lastFrameTime = currentFrameTime;
    }

    public static float getDelta()
    {
        return delta;
    }

    public static boolean closeDisplay() {
        return glfwWindowShouldClose(window);
    }

    public static int getWidth()
    {
        return videoMode.width();
    }

    public static int getHeight()
    {
       return videoMode.height();
    }

    private static float getCurrentTime()
    {
        return (float)org.lwjgl.glfw.GLFW.glfwGetTime();
    }
}

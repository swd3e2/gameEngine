package RenderEngine;

import input.KeyboardHandler;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class DisplayManager {

    private static final int WIDTH = 1440;
    private static final int HEIGHT = 960;
    private static long window;
    private static GLFWVidMode videoMode;
    private static GLFWKeyCallback keyCallback;

    public static void createDisplay() {
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        window = glfwCreateWindow(WIDTH, HEIGHT, "LWJGL", 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Failed to create window");
        }
        // Init keyhandler
        glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());

        videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2);
        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    public static void updateDisplay() {
        glfwPollEvents();
        glfwSwapBuffers(window);
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

}

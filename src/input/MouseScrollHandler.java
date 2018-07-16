package input;

import org.lwjgl.glfw.GLFWScrollCallback;

// Our MouseScrollHandler class extends the abstract class
// abstract classes should never be instantiated so here
// we create a concrete that we can instantiate
public class MouseScrollHandler extends GLFWScrollCallback {

    private static double cameraPos = 10;

    @Override
    public void invoke(long l, double v, double v1) {
        cameraPos += v1;
        System.out.println(cameraPos);
    }

    public static double getCameraPos()
    {
        return cameraPos;
    }
}
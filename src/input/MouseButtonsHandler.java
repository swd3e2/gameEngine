package input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonsHandler extends GLFWMouseButtonCallback {

    private static boolean  isLeftMousePressed = false;

    @Override
    public void invoke(long window, int button, int action, int mods) {
        System.out.println(button);
        isLeftMousePressed = button == 1;
    }

    public static boolean isLeftMousePressed() {
        return isLeftMousePressed;
    }
}

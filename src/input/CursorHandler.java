package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorHandler extends GLFWCursorPosCallback
{

    private static double xPos = 1;
    private static double yPos = 1;
    private static double yPosChange =0, yPosChangeOld=1;
    private static double xPosChange =0, xPosChangeOld=1;
    private static boolean isMouseMoved = false;
    @Override
    public void invoke(long l, double v, double v1)
    {

        yPosChangeOld = yPosChange;
        yPosChange = yPos - v1;

        xPosChangeOld = xPosChange;
        xPosChange = xPos - v;

        xPos = v;
        yPos = v1;
        isMouseMoved = true;
    }

    public static double getyPosChange()
    {
        return yPosChange;
    }

    public static double getxPos()
    {
        isMouseMoved = false;
        return xPosChange;
    }

    public static boolean isIsMouseMoved()
    {
        return isMouseMoved;
    }
}

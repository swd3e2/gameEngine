import RenderEngine.*;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import org.joml.Vector3f;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TextureLoader;
import RenderEngine.OBJLoader;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }
        DisplayManager.createDisplay();

        Loader loader = new Loader();


        RawModel model = OBJLoader.loadObjModel("dragon",loader);
        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(new TextureLoader("res/2.png").getTextureID()));
        ModelTexture texture = texturedModel.getTexture();
        texture.setShineDamper(5);
        texture.setReflectivity(1);
        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, 25), 0, 0, 0, 1);

        Terrain terrain = new Terrain(0,0,loader, new ModelTexture(new TextureLoader("res/2.png").getTextureID()));
        Terrain terrain2 = new Terrain(1,0,loader, new ModelTexture(new TextureLoader("res/2.png").getTextureID()));

        Light light = new Light(new Vector3f(0,25,0), new Vector3f(1,1,1));
        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();

        while(!DisplayManager.closeDisplay()) {
            entity.increaseRotation(0, 0.5f, 0);
            camera.move();

            renderer.processEntity(entity);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);


            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        glfwTerminate();
    }
}

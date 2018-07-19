import RenderEngine.*;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import gui.GuiRenderer;
import gui.GuiTexture;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.joml.Vector2f;
import org.joml.Vector3f;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import textures.TextureLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }
        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //*************************TERRAIN TEXTURE STUFF******************************//
        TerrainTexture backgroundTexture = new TerrainTexture(new TextureLoader("grassy2.png").getTextureID());
        TerrainTexture rTexture = new TerrainTexture(new TextureLoader("mud.png").getTextureID());
        TerrainTexture gTexture = new TerrainTexture(new TextureLoader("grassFlowers.png").getTextureID());
        TerrainTexture bTexture = new TerrainTexture(new TextureLoader("path.png").getTextureID());


        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(new TextureLoader("blendMap.png").getTextureID());

        Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");
        Terrain terrain1 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");
        //*****************************************************************************//

        //*****************************PLAYER STUFF************************************//
        ModelData playerStatic = OBJFileLoader.loadOBJ("stanfordBunny");
        TexturedModel playerModel = new TexturedModel(
                loader.loadToVAO(
                        playerStatic.getVertices(),
                        playerStatic.getTextureCoords(),
                        playerStatic.getNormals(),
                        playerStatic.getIndices()
                ),
                new ModelTexture(new TextureLoader("armor.png")));
        Player player = new Player(playerModel, new Vector3f(300,10,140), 0,0,0, 0.1f);

        //*****************************************************************************//

        Light light = new Light(new Vector3f(300,10,150), new Vector3f(1,1,1));
        Camera camera = new Camera(player);

        MasterRenderer renderer = new MasterRenderer();

        //*****************************WORLD MODELS************************************//
        ModelData treeObj = OBJFileLoader.loadOBJ("lowPolyTree");
        TexturedModel staticModel = new TexturedModel(
                loader.loadToVAO(
                        treeObj.getVertices(),
                        treeObj.getTextureCoords(),
                        treeObj.getNormals(),
                        treeObj.getIndices()
                ),
                new ModelTexture(new TextureLoader("lowPolyTree.png")));

        ModelData grassObj = OBJFileLoader.loadOBJ("grassModel");
        TexturedModel flower = new TexturedModel(
                loader.loadToVAO(
                        grassObj.getVertices(),
                        grassObj.getTextureCoords(),
                        grassObj.getNormals(),
                        grassObj.getIndices()
                ),
                new ModelTexture(new TextureLoader("diffuse.png")));

        ModelData fernObj = OBJFileLoader.loadOBJ("fern");
        TexturedModel fern = new TexturedModel(
                loader.loadToVAO(
                        fernObj.getVertices(),
                        fernObj.getTextureCoords(),
                        fernObj.getNormals(),
                        fernObj.getIndices()
                ),
                new ModelTexture(new TextureLoader("fern.png")));
        Random random = new Random();

        fern.getTexture().setHasTransparency(true);
        fern.getTexture().setNumberOfRows(2);
        flower.getTexture().setHasTransparency(true);
        flower.getTexture().setUseFakeLighting(true);
        flower.getTexture().setNumberOfRows(4);


        ArrayList<Entity> entities = new ArrayList<Entity>();
        for (int i = 0; i < 1150; i++) {
            float z = random.nextFloat() * 800;
            float x = random.nextFloat() * 800;
            float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(staticModel, new Vector3f(x, y, z), 0, 0, 0, 2f));
        }

        for (int i = 0; i < 1320; i++) {
            float z = random.nextFloat() * 800;
            float x = random.nextFloat() * 800;
            float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(flower, random.nextInt(6), new Vector3f(x, y, z), 0, 0, 0, 1));
        }

        for (int i = 0; i < 1180; i++) {
            float z = random.nextFloat() * 800;
            float x = random.nextFloat() * 800;
            float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, 0, 0, 1));
        }

        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(new TextureLoader("1.png").getTextureID(), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        guis.add(gui);
        GuiRenderer  guiRenderer = new GuiRenderer(loader);

        while(!DisplayManager.closeDisplay()) {

            camera.move();
            player.move(terrain);
            light.move();
            for (Entity entity: entities) {
                renderer.processEntity(entity);
            }
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain1);


            renderer.render(light, camera);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        glfwTerminate();
    }
}

import RenderEngine.*;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.joml.Vector3f;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import textures.TextureLoader;

import java.util.ArrayList;
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



        //****************************************************************************//
        ModelData treeObj = OBJFileLoader.loadOBJ("lowPolyTree");
        TexturedModel staticModel = new TexturedModel(
                loader.loadToVAO(
                        treeObj.getVertices(),
                        treeObj.getTextureCoords(),
                        treeObj.getNormals(),
                        treeObj.getIndices()
                ),
                new ModelTexture(new TextureLoader("lowPolyTree.png").getTextureID()));

        ModelData grassObj = OBJFileLoader.loadOBJ("grassModel");
        TexturedModel grass = new TexturedModel(
                loader.loadToVAO(
                        grassObj.getVertices(),
                        grassObj.getTextureCoords(),
                        grassObj.getNormals(),
                        grassObj.getIndices()
                ),
                new ModelTexture(new TextureLoader("grassTexture.png").getTextureID()));
        TexturedModel flower = new TexturedModel(
                loader.loadToVAO(
                        grassObj.getVertices(),
                        grassObj.getTextureCoords(),
                        grassObj.getNormals(),
                        grassObj.getIndices()
                ),
                new ModelTexture(new TextureLoader("flower.png").getTextureID()));

        ModelData fernObj = OBJFileLoader.loadOBJ("fern");
        TexturedModel fern = new TexturedModel(
                loader.loadToVAO(
                        fernObj.getVertices(),
                        fernObj.getTextureCoords(),
                        fernObj.getNormals(),
                        fernObj.getIndices()
                ),
                new ModelTexture(new TextureLoader("fern.png").getTextureID()));
        Random random = new Random();

        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        fern.getTexture().setHasTransparency(true);
        flower.getTexture().setHasTransparency(true);
        flower.getTexture().setUseFakeLighting(true);


        ArrayList<Entity> treesArray = new ArrayList<Entity>();
        for (int i = 0; i < 800; i++) {
            treesArray.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 800 + 200, 0, random.nextFloat() * 800), 0, 0, 0, 1.5f));
        }

        ArrayList<Entity> grassArray = new ArrayList<Entity>();
        for (int i = 0; i < 1600; i++) {
            grassArray.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 + 200 , 0, random.nextFloat() * 800), 0, 0, 0, 1));
        }

        ArrayList<Entity> plantsArray = new ArrayList<Entity>();
        for (int i = 0; i < 800; i++) {
            plantsArray.add(new Entity(flower, new Vector3f(random.nextFloat() * 800 + 200, 0, random.nextFloat() * 800), 0, 0, 0, 1));
        }

        ArrayList<Entity> fernArray = new ArrayList<Entity>();
        for (int i = 0; i < 480; i++) {
            fernArray.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 + 200, 0, random.nextFloat() * 800), 0, 0, 0, 1));
        }
    //*****************************PLAYER STUFF***************************************//
        ModelData playerStatic = OBJFileLoader.loadOBJ("stanfordBunny");
        TexturedModel playerModel = new TexturedModel(
                loader.loadToVAO(
                        playerStatic.getVertices(),
                        playerStatic.getTextureCoords(),
                        playerStatic.getNormals(),
                        playerStatic.getIndices()
                ),
                new ModelTexture(new TextureLoader("2.png").getTextureID()));
        Player player = new Player(playerModel, new Vector3f(300,10,140), 0,0,0, 1);
        //****************************************************************************//
        /*
         * Init camera, light and terrain
         */
        Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap);
        Terrain terrain1 = new Terrain(-1, -1, loader, texturePack, blendMap);
        Light light = new Light(new Vector3f(300,10,150), new Vector3f(1,1,1));
        Camera camera = new Camera(player);

        MasterRenderer renderer = new MasterRenderer();

        while(!DisplayManager.closeDisplay()) {

            camera.move();
            player.move();
            light.move();
            for (Entity entity: treesArray) {
                renderer.processEntity(entity);
            }
            for (Entity entity: grassArray) {
                renderer.processEntity(entity);
            }
            for (Entity entity: plantsArray) {
                renderer.processEntity(entity);
            }
            for (Entity entity: fernArray) {
                renderer.processEntity(entity);
            }
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain1);


            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        glfwTerminate();
    }
}

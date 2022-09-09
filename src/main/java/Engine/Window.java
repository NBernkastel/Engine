package Engine;

import Scenes.BaseScene;
import Scenes.Menu;
import eventHandlers.KeyListener;
import eventHandlers.MouseListener;
import eventHandlers.WindowResizeListener;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private String title;
    private long glfwWindow;
    private boolean vsync = true;

    private int targetWidth = 1920;
    private int targetHeight = 1080;
    private float targetAspectRatio = (float)targetWidth / (float)targetHeight;
    private int width, height;

    private static Window window = null;

    private static Scene currentScene;
    private static Scene menu;
    public static boolean isMenuCall = false;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "ZXc";
    }

    public static void changeScene(int newScene) {
        /*
         * Add new scene configurations in here.
         * That way you can have more then one scene.
         */
        menu = new Menu();
        menu.init();
        menu.start();
        switch (newScene) {
            case 0:
                currentScene = new BaseScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public static Scene getScene() {
        if (isMenuCall)
            return get().menu;
        else
            return get().currentScene;
    }

    public void run() {
        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
       // glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
       // glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        //glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
       // glfwWindowHint( GLFW_SCALE_TO_MONITOR,GLFW_TRUE);
        // Create the window
      // glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        glfwWindowHint(GLFW_RED_BITS, mode.redBits());
        glfwWindowHint(GLFW_GREEN_BITS, mode.greenBits());
        glfwWindowHint(GLFW_BLUE_BITS, mode.blueBits());
        glfwWindowHint(GLFW_REFRESH_RATE, mode.refreshRate());
        // Create the window
        glfwWindow = glfwCreateWindow(mode.width(), mode.height(), this.title, glfwGetPrimaryMonitor(), NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(vsync ? 1 : 0);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glBlendFuncSeparate( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_DST_ALPHA );
        // Set resize callback after we make the current context.
        glfwSetWindowSizeCallback(glfwWindow, WindowResizeListener::resizeCallback);
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        this.targetWidth = vidMode.width();
        this.targetHeight = vidMode.height();
        this.targetAspectRatio = (float)this.targetWidth / (float)this.targetHeight;

        Window.changeScene(0);
    }

    public void loop() {
        float lastFrameTime = -1f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            float time = (float)glfwGetTime();
            float dt = lastFrameTime - time;
            if (lastFrameTime == -1) {
                dt = 1f / 60f;
            }
            lastFrameTime = time;

            // Poll events
            glfwPollEvents();

            glClearColor(0f, 0f, 0f, 1f);
            glClear(GL_COLOR_BUFFER_BIT);
            if (isMenuCall == true)
                menu.update(dt);
            else
                currentScene.update(dt);
            glfwSwapBuffers(glfwWindow);
        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth(){
        return targetWidth;
    }
    public int getHeight(){
        return targetHeight;
    }
    public float getTargetAspectRatio() {
        return this.targetAspectRatio;
    }
}

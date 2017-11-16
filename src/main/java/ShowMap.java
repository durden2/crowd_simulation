import javafx.geometry.Pos;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.floor;
import static java.lang.StrictMath.sin;
import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Created by Gandi on 12/11/2017.
 */
public class ShowMap {

    private long window;

    private float normalize(float x, boolean isYAxis) {
        float divider = 600f;
        if (isYAxis) {
            divider = 800f;
        }
        float x_ = ((x * 2) / divider) - 1;
        return x_;
    }
    public void run(Map map) {
        init();
        loop(map);

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void DrawCircle(float cx, float cy, float r, int num_segments)
    {
        glBegin(GL_LINE_LOOP);
        for(int ii = 0; ii < num_segments; ii++)
        {
            float theta = 2.0f * 3.1415926f * (float)ii / (float)num_segments;//get the current angle

            float x = r * (float)cos(theta);//calculate the x component
            float y = r * (float)sin(theta);//calculate the y component

            glVertex2f(normalize(x + cx, false), normalize(y + cy, true));//output vertex

        }
        glEnd();
    }
    private void init() {
        Constants c = new Constants();
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(c.mapWidth, c.mapHeight, "Crowd simulation", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop(Map map) {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        //  glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glClear(GL_COLOR_BUFFER_BIT);
            glLoadIdentity();

            glBegin(GL_POINTS);
            float x = 0.0f;
            float y = 0.0f;

            for (int i = 0; i < Constants.mapHeight; i++) {
                for (int j = 0; j < Constants.mapWidth; j++) {
                    if (map.points[i][j].elementTypeVariable == elementType.OBSTACLE) {
                        glColor3f(0f, 0f, 1.0f);
                        x = normalize(map.points[i][j].position.x, false);
                        y = normalize(map.points[i][j].position.y, true);
                        glVertex2f(x, y);
                    }
                }
            }

            for (int g = 0; g < map.pedestrians.length; g++) {
                if (map.pedestrians[g].getPath() != null) {
                    for (int i = 0; i < map.pedestrians[g].getPath().size(); i++) {
                        {
                            glColor3f(0.25f, 0.75f, 0.5f);
                            x = normalize(map.pedestrians[g].getPath().get(i).x, false);
                            y = normalize(map.pedestrians[g].getPath().get(i).y, true);
                            glVertex2f(x, y);
                        }
                    }
                }
                glColor3f(1.0f, 0.0f, 0.0f);
                x = normalize(map.pedestrians[g].position.x, false);
                y = normalize(map.pedestrians[g].position.y, true);
                glVertex2f(x, y);
            }
            x = normalize(map.targetNode.position.x, false);
            y = normalize(map.targetNode.position.y, true);
            glVertex2f(x, y);
            glEnd();

            for (int g = 0; g < map.pedestrians.length; g++) {
                DrawCircle(map.pedestrians[g].position.x, map.pedestrians[g].position.y, 20, 300);
            }
            glfwSwapBuffers(window);
            glfwPollEvents();

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
}

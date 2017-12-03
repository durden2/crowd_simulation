import javafx.geometry.Pos;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static java.lang.Math.PI;
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
    private long timeStamp = 125; //milisec

    public vector2d celculateNewVelocity(vector2d force, float mass) {
        vector2d velocity = force.multipleByNumber(timeStamp / 1000);
        return new vector2d(velocity.getX() / mass, velocity.getY() / mass);
    };

    public boolean checkIfObstacleOnTheWay(Position currentPosition, Position newPosititon, ArrayList<Element> obstacles) {
        for (int i = 0; i < obstacles.size(); i++) {
            if ((obstacles.get(i).position.x >= currentPosition.x) && (obstacles.get(i).position.x < newPosititon.x)) {
                if ((obstacles.get(i).position.y >= currentPosition.y) && (obstacles.get(i).position.y < newPosititon.y)) {
                    return true;
                }
            }
        }
        return false;
    }

    private float normalize(float x, boolean isYAxis) {
        float divider = (float) Constants.mapWidth;
        if (isYAxis) {
            divider = (float) Constants.mapHeight;
        }
        float x_ = ((x * 2) / divider) - 1;
        return x_;
    }
    public void run(Map map) throws InterruptedException {
        init();
        try {
            loop(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void DrawCircle(double cx, double cy, float r, int num_segments)
    {
        for(int ii = 0; ii < num_segments; ii++)
        {
            float theta = 2.0f * 3.1415926f * (float)ii / (float)num_segments;//get the current angle

            double x = r * cos(theta);//calculate the x component
            double y = r * sin(theta);//calculate the y component

            glVertex2f(normalize((float) (x + cx), false), normalize((float) (y + cy), true));//output vertex

        }
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

    private void loop(Map map) throws InterruptedException {
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
            long startTime = System.nanoTime() / 1000000;

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glClear(GL_COLOR_BUFFER_BIT);
            glLoadIdentity();

            glBegin(GL_POINTS);
            float x;
            float y;

            // Draw obstacles
            for (int i = 0; i < Constants.mapWidth; i++) {
                for (int j = 0; j < Constants.mapHeight; j++) {
                    if (map.points[i][j].elementTypeVariable == elementType.OBSTACLE) {
                        glColor3f(0f, 0f, 1.0f);
                        x = normalize(map.points[i][j].position.x, false);
                        y = normalize(map.points[i][j].position.y, true);
                        glVertex2f(x, y);
                    }
                }
            }
            // end drawing obstacles

            for (int g = 0; g < map.pedestrians.length; g++) {

                glColor3f(1.0f, 0.0f, 0.0f);
                ArrayList<Position> path = map.pedestrians[g].getPath();
                boolean found = false;
                vector2d r = FinalForce.calculateFinalForce(map.pedestrians[g], map);

                for (int i = 0; i < path.size(); i++) {
                    Position pathElement = path.get(i);
                    if (!found && map.pedestrians[g].indexVisited == i) {
                        Position newPosititon = new Position(pathElement.x + (int)r.getX(), pathElement.y + (int)r.getY());
                        Position currentPosition = map.pedestrians[g].position;
                        if (!checkIfObstacleOnTheWay(currentPosition, newPosititon, map.obstacles)) {
                            vector2d newVelocity = celculateNewVelocity(r, map.pedestrians[g].getMass());
                            map.pedestrians[g].setVelocity(newVelocity.dodaj(map.pedestrians[g].getVelocity()));
                            map.pedestrians[g].velocitySum = map.pedestrians[g].velocitySum.dodaj(map.pedestrians[g].getVelocity());
                            if (Math.round(map.pedestrians[g].velocitySum.getX()) >= 1) {
                                int calkowita = (int) Math.round(map.pedestrians[g].velocitySum.getX());
                                map.pedestrians[g].velocitySum = new vector2d(map.pedestrians[g].velocitySum.getX() - calkowita, map.pedestrians[g].velocitySum.getY() - calkowita);
                                map.pedestrians[g].velocitySum = map.pedestrians[g].getVelocity().dodaj(map.pedestrians[g].velocitySum);
                                map.pedestrians[g].position = newPosititon;
                                map.pedestrians[g].indexVisited = i + calkowita;
                                found = true;
                            }
                        } else {
                            pathElement = path.get(i--);
                            newPosititon = new Position(pathElement.x + (int)r.getX(), pathElement.y + (int)r.getY());
                            map.pedestrians[g].position = newPosititon;
                        }
                        if (map.pedestrians[g].indexVisited >= path.size()) {
                            map.pedestrians[g].finished = true;
                        }
                    }
                    x = normalize(pathElement.x, false);
                    y = normalize(pathElement.y, true);
                    glVertex2f(x, y);
                }

                glColor3f(0.0f, 1.0f, 0.0f);
                /*
                x = normalize((float) (map.pedestrians[g].position.x + r.getX()), false);
                y = normalize((float) (map.pedestrians[g].position.y + r.getX()), true);
                glVertex2f(x, y);*/
                if (!map.pedestrians[g].finished) {
                    DrawCircle(map.pedestrians[g].position.x, map.pedestrians[g].position.y, 6, 100);
                }

                x = normalize(map.pedestrians[g].position.x, false);
                y = normalize(map.pedestrians[g].position.y, true);
                glVertex2f(x, y);
            }

            // draw target node
            x = normalize(map.targetNode.position.x, false);
            y = normalize(map.targetNode.position.y, true);
            glVertex2f(x, y);
            glEnd();

            long currentTime = System.nanoTime() / 1000000;
            Thread.sleep(timeStamp - (currentTime - startTime));

            glfwSwapBuffers(window);
            glfwPollEvents();

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
}

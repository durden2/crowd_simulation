import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static java.lang.StrictMath.cos;
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
    private long timeStamp = 170; //milisec
    private long simulationStartTime = 0;
    private boolean simulationFinished = false;

    public void calculateCurrentDensity(Map map) {
        Pedestrian[] pedestrians = map.pedestrians;
        for (int i = 0; i < pedestrians.length; i++) {
            int x = pedestrians[i].position.x;
            if (pedestrians[i].position.x % 4 != 0) {
                x += pedestrians[i].position.x % 4;
            }

            int y = pedestrians[i].position.y;
            if (pedestrians[i].position.y % 4 != 0) {
                y += pedestrians[i].position.y % 4;
            }
            if (y < Constants.mapHeight && x < Constants.mapWidth) {
                try {
                    float den = map.points[x][y].density + 0.25f;
                    map.points[x][y].density = den;
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
    }


    public vector2d celculateAcc(vector2d force, float mass) {
        vector2d acc = force.divideByNumber(mass);
        return acc;
    }

    public boolean isMoveHorizontal(Position a, Position b) {
        if (a.x == b.x || a.y == b.y) {
            return true;
        }
        return false;
    }

    public void calculatePathElementsWent(ArrayList<Position> path, Pedestrian currentAgent, vector2d distance) {
        double dist = vector2d.calculateVectorMagnitude(distance) + currentAgent.distanceLeft;
        dist *= Constants.mapScale;
        if (currentAgent.indexVisited == path.size()) {
            currentAgent.finished = true;
        } else {
            for (int t = currentAgent.indexVisited; t < path.size(); t++) {
                if (Double.isNaN(dist)) {
                    currentAgent.indexVisited = path.size() - 1;
                    currentAgent.finished = true;
                    currentAgent.distanceLeft = 0;
                    break;
                }
                if (dist < 1) {
                    if (t == (path.size() - 3)) {
                        currentAgent.finished = true;
                        currentAgent.distanceLeft = 0;
                        currentAgent.indexVisited = path.size() - 1;
                    }
                    currentAgent.indexVisited = t++;
                    currentAgent.distanceLeft = dist / Constants.mapScale;
                    break;
                }
                dist--;
            }
        }
    }

    public vector2d calculateDistance (vector2d force, Pedestrian currentAgent) {
        vector2d newAcc = celculateAcc(force, currentAgent.getMass());
        float t = timeStamp / 1000f;

        vector2d currentVelocityVector = currentAgent.getDesiredVelocity();
        vector2d velocityWithAcc = newAcc.multipleByNumber(t);
        vector2d newVelocity = new vector2d(velocityWithAcc.getX() + currentVelocityVector.getX(), velocityWithAcc.getY() + currentVelocityVector.getY());
        if (vector2d.calculateVectorMagnitude(newVelocity) > 5) {
            vector2d unit = vector2d.calculateUnitVector(newVelocity);
            newVelocity = new vector2d((unit.getX() * Constants.maxPedestrianVelocity) * t, (unit.getY() * Constants.maxPedestrianVelocity) * t);
        }
        vector2d startDistance = newVelocity.multipleByNumber(t);
        vector2d finalDistance = startDistance;
        currentAgent.setVelocity(newVelocity);
        return finalDistance;

    }

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
            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glLoadIdentity();

            glBegin(GL_POINTS);
            float x;
            float y;

            ArrayList<Pedestrian> tempPedestrians = new ArrayList<>();
            for (int g = 0; g < map.pedestrians.length; g++) {
                if (!map.pedestrians[g].finished) {
                    tempPedestrians.add(map.pedestrians[g]);
                }
            }
            Pedestrian[] stockArr = new Pedestrian[tempPedestrians.size()];
            stockArr = tempPedestrians.toArray(stockArr);
            map.pedestrians = stockArr;

            for (int g = 0; g < map.pedestrians.length; g++) {
                Pedestrian currentAgent = map.pedestrians[g];

                glColor3f(1.0f, 0.0f, 0.0f);
                ArrayList<Position> path = currentAgent.getPath();
                double distanceX = 0d;
                double distanceY = 0d;
                Position currentPathPosition = path.get(currentAgent.indexVisited);
                int indexVisited = currentAgent.indexVisited;
                if (!currentAgent.stopped) {
                    vector2d force = FinalForce.calculateFinalForce(currentAgent, map);
                    vector2d distance = calculateDistance(force, currentAgent);
                    calculatePathElementsWent(path, currentAgent, distance);
                    distanceX = distance.getX();
                    distanceY = distance.getY();
                    distanceX *= Constants.mapScale;
                    distanceY *= Constants.mapScale;
                }

                for (int i = 0; i < path.size(); i++) {
                    if (!map.pedestrians[g].finished && indexVisited == i) {
                        Position newPosition = new Position(currentPathPosition.x + (int) distanceX, currentPathPosition.y + (int) distanceY);
                        Collisions.avoid(map, currentAgent, newPosition);
                        if (!currentAgent.stopped || !checkIfObstacleOnTheWay(currentAgent.position, newPosition, map.obstacles)) {
                            currentAgent.position = newPosition;
                            //AStar a = new AStar();
                            //ArrayList<Position> path2 = a.calculatePath(new Element(newPosition, elementType.PEDESTRIAN), map.targetNode, map);
                            //currentAgent.setPath(path2);
                            //currentAgent.realPath.add(newPosition);
                            map.walkedPaths.add(newPosition);
                        }
                        break;
                    }
                }

                //draw paths
                /*for (int i = 0; i < path.size(); i++) {
                    Position pathElement = path.get(i);
                    x = normalize(pathElement.x, false);
                    y = normalize(pathElement.y, true);
                    glVertex2f(x, y);
                }*/
                //end drawing paths

                //draw real paths
                for (int i = 0; i < map.walkedPaths.size(); i++) {
                    glColor3f(1.0f, 0f, 0f);
                    Position pathElement = map.walkedPaths.get(i);
                    x = normalize(pathElement.x, false);
                    y = normalize(pathElement.y, true);
                    glVertex2f(x, y);
                }
                //end drawing paths

                //draw pedestrians
                glColor3f(0.0f, 1.0f, 0.0f);
                if (!currentAgent.finished) {
                    for(int r = 0; r <=5; r++) {
                        DrawCircle(currentAgent.position.x, currentAgent.position.y, r, 100);
                    }
                }
                x = normalize(currentAgent.position.x, false);
                y = normalize(currentAgent.position.y, true);
                glVertex2f(x, y);
                //end drawing pedestrians
            }

            /*glColor3f(1.0f, 1.0f, 1.0f);
            for (int d = 0; d < pos.size(); d++) {
                Position curr = pos.get(d);
                x = normalize(curr.x, false);
                y = normalize(curr.y, true);
                glVertex2f(x, y);
            }*/


            // Draw obstacles
            for (int i = 0; i < Constants.mapWidth; i++) {
                for (int j = 0; j < Constants.mapHeight; j++) {
                    if (map.points[i][j].elementTypeVariable == elementType.OBSTACLE) {
                        glColor3f(0f, 0f, 0f);
                        x = normalize(map.points[i][j].position.x, false);
                        y = normalize(map.points[i][j].position.y, true);
                        glVertex2f(x, y);
                    }
                }
            }
            // end drawing obstacles

            // draw target node
            glColor3f(1.0f, 0.0f, 0.0f);
            for(int z = 0; z < map.targetNodes.length; z++) {
                for(int r = 0; r <=8; r++) {
                    DrawCircle(map.targetNodes[z].position.x, map.targetNodes[z].position.y, r, 100);
                }
            }
            glEnd();
            //end drawing target node

            calculateCurrentDensity(map);
            if (!simulationFinished) {
                if (map.pedestrians.length < 1) {
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter("out.txt", "UTF-8");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    writer.print("k");
                    for (int i = 0; i < Constants.mapWidth; i +=4) {
                        writer.print(i + "k");
                    }
                    writer.println("k");
                    for (int j = 0; j < Constants.mapHeight; j+=4) {
                        writer.print(j + "k");
                        for (int i = 0; i < Constants.mapWidth; i +=4) {
                                writer.print("k" + map.points[i][j].density);
                        }
                        writer.println("k");
                    }


                    writer.close();
                    System.out.println("Simulation finished in time");
                    System.out.println(this.simulationStartTime);
                    System.out.println("Number of waitings: " + map.waitings);
                    this.simulationFinished = true;
                    throw new InterruptedException("SKONCZONO");
                }
            }
            long currentTime = System.nanoTime() / 1000000;
            this.simulationStartTime += timeStamp - (currentTime - startTime);
            Thread.sleep(timeStamp - (currentTime - startTime));

            glfwSwapBuffers(window);
            glfwPollEvents();

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
}

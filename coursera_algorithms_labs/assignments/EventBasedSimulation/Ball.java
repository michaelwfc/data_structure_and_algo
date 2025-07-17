import edu.princeton.cs.algs4.StdRandom;

public class Ball {
    private double rx, ry; // position
    private double vx, vy; // velocity
    private final double radius; // radius

    public Ball() { /* initialize position and velocity */
        rx = StdRandom.uniformDouble(0,1);
        ry=  StdRandom.uniformDouble(0,1);
        vx=  StdRandom.uniformDouble(0,0.02);
        vy=  StdRandom.uniformDouble(0,0.02);
        radius = 0.01;
    }


    public void move(double dt) {
        //check for collision with walls
        if ((rx + vx * dt < radius) || (rx + vx * dt > 1.0 - radius)) {
            vx = -vx;
        }
        if ((ry + vy * dt < radius) || (ry + vy * dt > 1.0 - radius)) {
            vy = -vy;
        }
        rx = rx + vx * dt;
        ry = ry + vy * dt;
    }

    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }
}
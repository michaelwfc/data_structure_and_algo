
//import edu.princeton.cs.algs4

import edu.princeton.cs.algs4.StdRandom;

public class Particle {
    private double rx, ry; // position
    private double vx, vy; // velocity
    private final double radius; // radius
    private final double mass; // mass
    private int count; // number of collisions

    public Particle(){
        rx = StdRandom.uniformDouble(0,1);
        ry=  StdRandom.uniformDouble(0,1);
        vx=  StdRandom.uniformDouble(0,0.01);
        vy=  StdRandom.uniformDouble(0,0.01);
        radius = 0.02;// StdRandom.uniformDouble(0,0.02);
        mass=  0.01; StdRandom.uniformDouble(0,0.02);
    }
    public Particle(double rx, double ry,
                    double vx, double vy,
                    double s,
                    double mass) {
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.radius = s;
        this.mass = mass;
        count = 0;

    }

    //change position to reflect passage of time dt
    public void move(double dt) {
        rx = rx + vx * dt;
        ry = ry + vy * dt;
    }

    public int count() {
        return count;
    }

    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }

    // time until this particle hits particle b
    public double timeToHit(Particle that) {
        if (this == that) return Double.POSITIVE_INFINITY;
        double dx = that.rx - this.rx, dy = that.ry - this.ry;
        double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
        double dvdr = dx * dvx + dy * dvy;
        if (dvdr > 0) return Double.POSITIVE_INFINITY;
        double dvdv = dvx * dvx + dvy * dvy;
        double drdr = dx * dx + dy * dy;
        double sigma = this.radius + that.radius;
        double d = (dvdr * dvdr) - dvdv * (drdr - sigma * sigma);
        if (d < 0) return Double.POSITIVE_INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    public double timeToHitVerticalWall() {
        if (vx > 0) return (1- radius- rx) / vx;
        else if (vx == 0) return Double.POSITIVE_INFINITY;
        else return - (rx-radius) / vx;
    }


    public double timeToHitHorizontalWall() {
        if (vy > 0) return (1 - radius- ry) / vy;
        else if (vy == 0) return Double.POSITIVE_INFINITY;
        else return - (ry-radius) / vy;
    }

    //change particle velocities to reflect collision
    public void bounceOff(Particle that) {
        double dx = that.rx - this.rx, dy = that.ry - this.ry;
        double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
        double dvdr = dx * dvx + dy * dvy;
        double dist = this.radius + that.radius;
        double J = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
        double Jx = J * dx / dist;
        double Jy = J * dy / dist;
        this.vx += Jx / this.mass;
        this.vy += Jy / this.mass;
        that.vx -= Jx / that.mass;
        that.vy -= Jy / that.mass;
        this.count++;
        that.count++;
    }

    public void bounceOffVerticalWall() {
        vx = -vx;
    }

    //change velocity to reflect hitting horizontal wall
    public void bounceOffHorizontalWall() {
        vy = -vy;
    }





}
/*
* https://introcs.cs.princeton.edu/java/assignments/collisions.html
* Event-driven simulation
* With event-driven simulation we focus on those times at which interesting events occur.
* In the hard disc model, all particles travel in straight line trajectories at constant speeds between collisions.
* Thus, our main challenge is to determine the ordered sequence of particle collisions.
* We address this challenge by maintaining a priority queue of future events, ordered by time.
* At any given time, the priority queue contains all future collisions that would occur, assuming each particle moves in a straight line trajectory forever.
* As particles collide and change direction, some of the events scheduled on the priority queue become "stale" or "invalidated", and no longer correspond to physical collisions.
* We can adopt a lazy strategy, leaving such invalidated collision on the priority queue, waiting to identify and discard them as they are deleted.
*
* The main event-driven simulation loop works as follows:
- Delete the impending event, i.e., the one with the minimum priority t.
- If the event corresponds to an invalidated collision, discard it. The event is invalid if one of the particles has participated in a collision since the event was inserted onto the priority queue.
- If the event corresponds to a physical collision between particles i and j:
   - Advance all particles to time t along a straight line trajectory.
   - Update the velocities of the two colliding particles i and j according to the laws of elastic collision.
   - Determine all future collisions that would occur involving either i or j, assuming all particles move in straight line trajectories from time t onwards. Insert these events onto the priority queue.
- If the event corresponds to a physical collision between particles i and a wall, do the analogous thing for particle i.

* This event-driven approach results in a more robust, accurate, and efficient simulation than the time-driven one.
*
*
* */


import edu.princeton.cs.algs4.MinPQ;

public class CollisionSystem {
    private MinPQ<Event> pq; // the priority queue
    private double t = 0.0; // simulation clock time
    private Particle[] particles; // the array of particles

    public CollisionSystem(Particle[] particles) {
        this.particles = particles;
    }

    private class Event implements Comparable<Event> {
        private final double time;  //time when the event is predicted to happen,
        private final Particle a, b;
        private final int countA, countB;

        /*
        * four different types of events, as follows:
        ■ Neither a nor b null: particle-particle collision
        ■ a not null and b null: collision between a and a vertical wall
        ■ a null and b not null: collision between b and a horizontal wall
        ■ Both a and b null: redraw event (draw all particles)*
        * */
        public Event(double t, Particle a, Particle b) {
            // Create a new event to occur at time t involving a and b.
            this.time = t;
            this.a = a;
            this.b = b;
            if (a != null) countA = a.count(); // counts the number of collisions
            else countA = -1;
            if (b != null) countB = b.count();  // counts the number of collisions
            else countB = -1;
        }

        public int compareTo(Event that) {
            if (this.time < that.time) return -1;
            else if (this.time > that.time) return +1;
            else return 0;
        }

        public boolean isValid() {
            if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
        }
    }


    /*
     * calculates all potential future collisions involving particle a (either with another particle or with
     * a wall) and puts an event corresponding to each onto the priority queue.
     * */
    private void predictCollisions(Particle a, double limit) {
        if (a == null) return;
        for (int i = 0; i < particles.length; i++) {
            // Put collision with particles[i] on pq.
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= limit)
                pq.insert(new Event(t + dt, a, particles[i]));
        }
        // Put collision with vertical walls on pq.
        double dtX = a.timeToHitVerticalWall();
        if (t + dtX <= limit)
            pq.insert(new Event(t + dtX, a, null));

        // Put collision with horizontal walls on pq.
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtY <= limit)
            pq.insert(new Event(t + dtY, null, a));

    }

    public void redraw(double limit, double Hz) { // Redraw event: redraw all particles.
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++) particles[i].draw();
        StdDraw.show(20);
        if (t < limit)
            pq.insert(new Event(t + 1.0 / Hz, null, null));
    }

    /*
     * simulate
     * limit:
     * */
    public void simulate(double limit, double Hz) {
        // the priority queue is initialized with events representing all predicted future collisions involving each particle.
        //involving all particle-wall and all particle-particle pairs.
        pq = new MinPQ<Event>();
        for (int i = 0; i < particles.length; i++)
            predictCollisions(particles[i], limit);

        pq.insert(new Event(0.0, null, null)); // Add redraw event.

        while (!pq.isEmpty()) {
            // Delete the impending event from PQ (min priority = t).
            Event event = pq.delMin();
            if (!event.isValid()) continue; // If the event has been invalidated, ignore it.
            for (int i = 0; i < particles.length; i++)
                particles[i].move(event.time - t); // Update particle positions
            t = event.time; // updates time

            //Update the velocities of the colliding particle(s).
            Particle a = event.a, b = event.b;
            if (a != null && b != null) a.bounceOff(b);
            else if (a != null && b == null) a.bounceOffVerticalWall();
            else if (a == null && b != null) b.bounceOffHorizontalWall();
            else if (a == null && b == null) redraw(limit, Hz);

            //  adds new events to reflect changes.
            predictCollisions(a, limit);
            predictCollisions(b, limit);

        }
    }

    public static void main(String[] args) {
        StdDraw.show(0);
//        int N = Integer.parseInt(args[0]);
        int N=20;
        Particle[] particles = new Particle[N];
        for (int i = 0; i < N; i++)
            particles[i] = new Particle();
        CollisionSystem system = new CollisionSystem(particles);
        system.simulate(10000, 0.5);
    }
}
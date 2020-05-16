package ui.ds.timetracker;

// The TickGenerator class will contain the clock that will
// generate an output every x refresh time
public class TickGenerator extends Thread {
    private final int refreshTime;
    private final TimerClock clock;

    // We start the watch thread with a thread parallel to the main thread
    public TickGenerator(final int refreshTime, final TimerClock clock) {
        this.setDaemon(true);

        this.refreshTime = refreshTime;
        this.clock = clock;
        this.start();
    }

    // Secondary thread containing the clock with its refresh time.
    // Each cooling time generates a "tick" of the Timer class.
    @Override
    public final void run() {
        try {

            while (true) {
                sleep(this.refreshTime);
                this.clock.tick();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

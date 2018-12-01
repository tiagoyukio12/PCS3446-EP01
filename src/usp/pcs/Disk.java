package usp.pcs;

import java.util.ArrayList;
import java.util.Random;

class Disk {
    private float diskPos;
    private float diskRev;
    private float diskSpeed;
    private ArrayList<Program> queue = new ArrayList<>();
    private Program currentJob = null;

    Disk(float diskPos, float diskRev, float diskSpeed) {
        this.diskPos = diskPos;
        this.diskRev = diskRev;
        this.diskSpeed = diskSpeed;
    }

    void processIO(SimEvents simEvents, Program program) {
        if (isAvailable()) {
            currentJob = program;
            simEvents.addEvent(new SimEvent(processTime(simEvents, currentJob), 6, currentJob.getId()));
        } else
            queue.add(program);
    }

    void completeIO(SimEvents simEvents) {
        if (!queue.isEmpty()) {
            currentJob = queue.get(0);
            simEvents.addEvent(new SimEvent(processTime(simEvents, currentJob), 6, currentJob.getId()));
            queue.remove(0);
        } else {
            currentJob = null;
        }
    }

    private boolean isAvailable() {
        return currentJob == null;
    }

    private float processTime(SimEvents simEvents, Program program) {
        Random rand = new Random();
        float p = rand.nextFloat();
        return simEvents.getCurrentTime() + diskPos + 60 * p / diskRev * 1000 + program.getRecordSize() / diskSpeed;
    }
}

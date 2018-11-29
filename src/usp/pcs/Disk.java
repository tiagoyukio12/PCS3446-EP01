package usp.pcs;

import java.util.ArrayList;

class Disk {
    private ArrayList<Program> queue = new ArrayList<>();
    private Program currentJob = null;

    void processIO(SimEvents simEvents, Program program) {
        if (isAvailable()) {
            currentJob = program;
            simEvents.addEvent(new SimEvent(processTime(simEvents), 6, currentJob.getId()));
        } else
            queue.add(program);
    }

    void completeIO(SimEvents simEvents) {
        if (!queue.isEmpty()) {
            currentJob = queue.get(0);
            simEvents.addEvent(new SimEvent(processTime(simEvents), 6, currentJob.getId()));
            queue.remove(0);
        } else {
            currentJob = null;
        }
    }

    private boolean isAvailable() {
        return currentJob == null;
    }

    private float processTime(SimEvents simEvents) {
        // TODO: usar formula do artigo
        return simEvents.getCurrentTime() + 25;
    }
}

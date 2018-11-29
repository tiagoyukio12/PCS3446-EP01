package usp.pcs;

import java.util.ArrayList;

class SimProcessor {
    private boolean available = true;
    private ArrayList<Program> queue = new ArrayList<>();
    private Program currentJob;

    SimProcessor() {

    }

    void allocate(Program program) {
        if (available) {
            currentJob = program;
            available = false;
        } else
            queue.add(program);
    }

    boolean isAvailable() {
        return available;
    }
}

package usp.pcs;

import java.util.ArrayList;

class Memory {
    private int availableSpace;
    private ArrayList<Program> queue = new ArrayList<>();

    Memory(int size) {
        availableSpace = size;
    }

    int getAvailableSpace() {
        return availableSpace;
    }

    void allocate(SimEvents simEvents, Program program) {
        if (availableSpace > program.getMemSize()) {
            availableSpace -= program.getMemSize();
            simEvents.addEvent(new SimEvent(simEvents.getCurrentTime(), 3, program.getId()));;
        } else
            queue.add(program);
    }

    void release(SimEvents simEvents, Program program) {
        availableSpace += program.getMemSize();

        int i = 0;
        while (i < queue.size()) {
            Program nextProgram = queue.get(i++);
            if (availableSpace > nextProgram.getMemSize()) {
                availableSpace -= program.getMemSize();
                simEvents.addEvent(new SimEvent(simEvents.getCurrentTime(), 3, program.getId()));
                queue.remove(nextProgram);
            }
        }
    }
}

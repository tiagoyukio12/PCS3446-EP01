package usp.pcs;

import java.util.ArrayList;

class SimProcessor {
    private ArrayList<Program> queue = new ArrayList<>();
    private Program currentJob = null;

    void allocate(SimEvents simEvents, Program program) {
        if (isAvailable()) {
            currentJob = program;
            float interruptTime = currentJob.nextInterruptTime(simEvents.getCurrentTime());
            simEvents.addEvent(new SimEvent(interruptTime, 4, currentJob.getId()));
        } else
            queue.add(program);
    }

    private boolean isAvailable() {
        return currentJob == null;
    }

    void release(SimEvents simEvents) {
        currentJob.updateTimeLeft();

        if (!currentJob.isDone()) {
            simEvents.addEvent(new SimEvent(simEvents.getCurrentTime(), 5, currentJob.getId()));
        } else {
            // Evento para retirar job completo da memoria
            simEvents.addEvent(new SimEvent(simEvents.getCurrentTime(), 7, currentJob.getId()));
        }

        // Atualiza o job ativo
        if (!queue.isEmpty()) {
            currentJob = queue.get(0);
            queue.remove(0);
            float interruptTime = currentJob.nextInterruptTime(simEvents.getCurrentTime());
            simEvents.addEvent(new SimEvent(interruptTime, 4, currentJob.getId()));
        } else {
            currentJob = null;
        }
    }
}

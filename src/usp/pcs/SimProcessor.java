package usp.pcs;

import java.util.ArrayList;

class SimProcessor {
    private ArrayList<Program> queue = new ArrayList<>();
    private Program currentJob = null;

    void allocate(SimEvents simEvents, Program program) {
        if (currentJob == null) {
            currentJob = program;
            currentJob.startProgram(simEvents);
        } else if (program.getPriority() < currentJob.getPriority()) {
            // Remove evento de IO interrupt e atualiza tempo restante do job antigo
            simEvents.cancelIOEvent(currentJob);
            currentJob.updateTimeLeft(simEvents.getCurrentTime());
            queue.add(currentJob);
            // Inicia novo job
            currentJob = program;
            currentJob.startProgram(simEvents);
        } else
            queue.add(program);
    }

    void release(SimEvents simEvents) {
        currentJob.updateTimeLeft(simEvents.getCurrentTime());

        if (!currentJob.isDone()) {
            simEvents.addEvent(new SimEvent(simEvents.getCurrentTime(), 5, currentJob.getId()));
        } else {
            // Evento para retirar job completo da memoria
            simEvents.addEvent(new SimEvent(simEvents.getCurrentTime(), 7, currentJob.getId()));
        }

        // Atualiza o job ativo
        if (!queue.isEmpty()) {
            currentJob = getHighestPriority();
            currentJob.startProgram(simEvents);
        } else {
            currentJob = null;
        }
    }

    // Retorna job com maior prioridade na fila da CPU e remove-o da fila
    Program getHighestPriority() {
        Program highestPriority = queue.get(0);
        for (Program program: queue) {
            if (program.getPriority() < highestPriority.getPriority())
                highestPriority = program;
        }
        queue.remove(highestPriority);
        return highestPriority;
    }
}

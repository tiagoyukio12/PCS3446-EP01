package usp.pcs;

import java.util.ArrayList;

class SimEvents {
    private ArrayList<SimEvent> simEvents = new ArrayList<>();
    private float duration;
    private float currentTime = 0;
    private int currentEvent = 0;

    SimEvents(float duration) {
        this.duration = duration;
    }

    boolean endOfSimulation() {
        return currentTime >= duration || simEvents.isEmpty();
    }

    // Adiciona a lista de eventos mantendo-a ordenada no tempo
    void addEvent(SimEvent simEvent) {
        // TODO: otimizavel?
        float eventTime = simEvent.getTime();
        for (int i = 0; i < simEvents.size(); i++) {
            if (eventTime < simEvents.get(i).getTime()) {
                simEvents.add(i, simEvent);
                return;
            }
        }
        simEvents.add(simEvent);
    }

    SimEvent getNextEvent() {
        if (currentEvent < simEvents.size()) {
            SimEvent nextEvent = simEvents.get(currentEvent++);
            currentTime = nextEvent.getTime();
            return nextEvent;
        }
        currentTime = duration;
        return new SimEvent(0, 0, 0);
    }

    float getCurrentTime() {
        return currentTime;
    }

    void cancelIOEvent(Program currentJob) {
        for (int i = 0; i < simEvents.size(); i++) {
            SimEvent simEvent = simEvents.get(i);
            if (simEvent.getProgram() == currentJob.getId() && simEvent.getTime() > currentTime) {
                simEvents.remove(i);
            }
        }
    }

    void printLog() {
        for (SimEvent simEvent : simEvents) {
            String eventMsg = simEvent.getLog();
            System.out.println(eventMsg);
        }
    }

    void printJob(int i) {
        for (SimEvent simEvent : simEvents) {
            if (simEvent.getProgram() == i) {
                String eventMsg = simEvent.getLog();
                System.out.println(eventMsg);
            }
        }
    }
}

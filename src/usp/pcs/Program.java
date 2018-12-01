package usp.pcs;

class Program {
    private int id;
    private float lastStartTime;
    private float processTime;
    private float timeLeft;
    private int memSize;
    private int ioOperations;
    private int recordSize;
    private int priority;

    Program(int id, float startTime, float processTime, int memSize, int ioOperations, int recordSize, int priority) {
        this.id = id;
        this.lastStartTime = startTime;
        this.processTime = processTime;
        this.timeLeft = processTime;
        this.memSize = memSize;
        this.ioOperations = ioOperations;
        this.recordSize = recordSize;
        this.priority = priority;
    }

    float getLastStartTime() {
        return lastStartTime;
    }

    void startProgram(SimEvents simEvents) {
        // Atualiza instante da ultima ativacao do job
        lastStartTime = simEvents.getCurrentTime();
        // Adiciona evento para liberar CPU
        float interruptTime = nextInterruptTime(simEvents.getCurrentTime());
        simEvents.addEvent(new SimEvent(interruptTime, 4, id));
    }

    int getMemSize() {
        return memSize;
    }

    int getId() {
        return id;
    }

    float nextInterruptTime(float currentTime) {
        return currentTime + processTime / (ioOperations + 1);
    }

    void updateTimeLeft(float currentTime) {
        timeLeft -= currentTime - lastStartTime;
    }

    boolean isDone() {
        return timeLeft <= 0;
    }

    int getPriority() {
        return priority;
    }

    int getRecordSize() {
        return recordSize;
    }
}

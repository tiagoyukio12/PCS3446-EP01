package usp.pcs;

public class Program {
    private int id;
    private float startTime;
    private float processTime;
    private float timeLeft;
    private int memSize;
    private int ioOperations;

    Program(int id, float startTime, float processTime, int memSize, int ioOperations) {
        this.id = id;
        this.startTime = startTime;
        this.processTime = processTime;
        this.timeLeft = processTime;
        this.memSize = memSize;
        this.ioOperations = ioOperations;
    }

    float getStartTime() {
        return startTime;
    }

    int getMemSize() {
        return memSize;
    }

    int getId() {
        return id;
    }

    float nextInterruptTime(float currentTime) {
        return currentTime + processTime / ioOperations;
    }

    void updateTimeLeft() {
        timeLeft -= processTime / ioOperations;
    }

    boolean isDone() {
        return timeLeft <= 0;
    }
}

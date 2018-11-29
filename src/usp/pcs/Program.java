package usp.pcs;

public class Program {
    private int id;
    private float startTime;
    private float processTime;
    private int memSize;
    private int ioOperations;

    Program(int id, float startTime, float processTime, int memSize, int ioOperations) {
        this.id = id;
        this.startTime = startTime;
        this.processTime = processTime;
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
}

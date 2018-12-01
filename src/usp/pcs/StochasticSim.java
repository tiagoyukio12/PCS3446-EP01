package usp.pcs;

import java.util.ArrayList;
import java.util.Random;

class StochasticSim {
    private static final int MEM_SIZE = 1000;  // Main memory size [kilobytes]
    private static final float DURATION = (float) 10e4;  // Duration of the simulation [milliseconds]
    private static final float DISK_SPEED = (float) 10.0e5;  // Transfer speed of the disk [KB/s]
    private static final float DISK_POS = 10; // Positioning time of the disk [milliseconds]
    private static final float DISK_REV = 10000;  // Disk revolution speed [RPM]
    private static final int NUM_JOBS = 25;  // Number of jobs in the simulation
    private ArrayList<Program> programs = new ArrayList<>();
    private SimEvents simEvents;
    private Memory memory = new Memory(MEM_SIZE);
    private SimProcessor processor = new SimProcessor();
    private Disk disk = new Disk(DISK_POS, DISK_REV, DISK_SPEED);

    StochasticSim() {
        simEvents = new SimEvents(DURATION);
    }

    SimEvents run() {
        SimEvent startup = new SimEvent(0, 1, 0);
        simEvents.addEvent(startup);
        while (!simEvents.endOfSimulation())
            execEvent();
        return simEvents;
    }

    private void execEvent() {
        SimEvent simEvent = simEvents.getNextEvent();

        if (simEvent.getType() == 1)
            execRoutine1();
        else {
            Program program = programs.get(simEvent.getProgram());
            if (simEvent.getType() == 2) {
                execRoutine2(program);
            } else if (simEvent.getType() == 3) {
                execRoutine3(program);
            } else if (simEvent.getType() == 4)
                execRoutine4();
            else if (simEvent.getType() == 5) {
                execRoutine5(program);
            } else if (simEvent.getType() == 6) {
                execRoutine6(program);
            } else if (simEvent.getType() == 7) {
                execRoutine7(program);
            }
        }
    }

    // Gera mix de jobs
    private void execRoutine1() {
        Random rand = new Random();
        float Ta = 5;
        float startTime = 0;

        for (int i = 0; i < NUM_JOBS; i++) {
            startTime += (float) (-Ta * Math.log(rand.nextFloat()));
            float processTime = 50 * rand.nextFloat();
            int memSize = genMemSize();
            int ioOperations = genIoOperations();
            int recordSize = genRecordSize();
            int priority = (int) (5 * rand.nextFloat()) + 1;

            Program program = new Program(i, startTime, processTime, memSize, ioOperations, recordSize, priority);
            programs.add(program);
            System.out.println("program " + i + "\nIO Op.: " + ioOperations + ", Priority: " + priority + ", Process " +
                    "Time: " + processTime);
        }

        for (Program program : programs) {
            float time = program.getLastStartTime();
            int type = 2;
            int programId = program.getId();

            SimEvent jobStart = new SimEvent(time, type, programId);
            simEvents.addEvent(jobStart);
        }
    }

    private int genRecordSize() {
        Random rand = new Random();
        float p = rand.nextFloat();
        if (p < 0.1)
            return (int) (1*10e3);
        if (p < 0.3)
            return (int) (2*10e3);
        if (p < 0.7)
            return (int) (3*10e3);
        if (p < 0.9)
            return (int) (4*10e3);
        return (int) (5*10e3);
    }

    private int genIoOperations() {
        Random rand = new Random();
        float p = rand.nextFloat();
        if (p < 0.1)
            return 1;
        if (p < 0.3)
            return 2;
        if (p < 0.7)
            return 5;
        if (p < 0.9)
            return 8;
        return 10;
    }

    private int genMemSize() {
        Random rand = new Random();
        float p = rand.nextFloat();

        if (p < 0.1)
            return 15;
        if (p < 0.3)
            return 25;
        if (p < 0.7)
            return 35;
        if (p < 0.9)
            return 45;
        return 50;
    }

    // Aloca job na memoria principal
    private void execRoutine2(Program program) {
        memory.allocate(simEvents, program);
    }

    // Aloca job ao processador
    private void execRoutine3(Program program) {
        processor.allocate(simEvents, program);
    }

    // Libera processador para operacao de IO
    private void execRoutine4() {
        processor.release(simEvents);
    }

    // Envia operacao IO ao disco
    private void execRoutine5(Program program) {
        disk.processIO(simEvents, program);
    }

    // Libera disco
    private void execRoutine6(Program program) {
        disk.completeIO(simEvents);
        processor.allocate(simEvents, program);
    }

    // Termina job
    private void execRoutine7(Program program) {
        memory.release(simEvents, program);
    }
}

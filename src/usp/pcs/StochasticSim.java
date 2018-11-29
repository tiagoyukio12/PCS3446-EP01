package usp.pcs;

import java.util.ArrayList;
import java.util.Random;

class StochasticSim {
    private static final int MEM_SIZE = 500;
    private static final int NUM_JOBS = 5;
    private ArrayList<Program> programs = new ArrayList<>();
    private SimEvents simEvents;
    private Memory memory = new Memory(MEM_SIZE);
    private SimProcessor processor = new SimProcessor();
    private Disk disk = new Disk();

    StochasticSim(float duration) {
        simEvents = new SimEvents(duration);
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

            Program program = new Program(i, startTime, processTime, memSize, ioOperations);
            programs.add(program);
            System.out.println("program " + i + ": IO Op.: " + ioOperations);
        }

        for (Program program : programs) {
            float time = program.getStartTime();
            int type = 2;
            int programId = program.getId();

            SimEvent jobStart = new SimEvent(time, type, programId);
            simEvents.addEvent(jobStart);
        }
    }

    private int genIoOperations() {
        Random rand = new Random();
        float p = rand.nextFloat();
        if (p < 0.1)
            return 1;
        if (p < 0.3)
            return 5;
        if (p < 0.7)
            return 10;
        if (p < 0.9)
            return 25;
        return 50;
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

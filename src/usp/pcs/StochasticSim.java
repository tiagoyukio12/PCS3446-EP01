package usp.pcs;

import javax.annotation.processing.Processor;
import java.util.ArrayList;
import java.util.Random;

class StochasticSim {
    private static final int MEM_SIZE = 1000;
    private static final int NUM_JOBS = 10;

    private float duration;
    private float currentTime = 0;
    private int currentEvent = 0;
    private ArrayList<SimEvent> simEvents = new ArrayList<>();
    private Memory memory = new Memory(MEM_SIZE);
    private Processor processor;
    private Disk disk;

    StochasticSim(float duration) {
        this.duration = duration;
    }

    void run() {
        SimEvent startup = new SimEvent(0, 1, 0);
        simEvents.add(startup);
        while (currentTime < duration && !simEvents.isEmpty())
            execEvent();
    }

    private void execEvent() {
        SimEvent simEvent = getNextEvent();

        if (simEvent.getType() == 1)
            execRoutine1();
        else if (simEvent.getType() == 2)
            execRoutine2();
        else if (simEvent.getType() == 3)
            execRoutine3();
        else if (simEvent.getType() == 4)
            execRoutine4();
        else if (simEvent.getType() == 5)
            execRoutine5();
        else if (simEvent.getType() == 6)
            execRoutine6();
        else if (simEvent.getType() == 7)
            execRoutine7();
    }

    private SimEvent getNextEvent() {
        if (currentEvent < simEvents.size()) {
            SimEvent nextEvent = simEvents.get(currentEvent++);
            currentTime = nextEvent.getTime();
            return nextEvent;
        }
        currentTime = duration;
        return new SimEvent(0, 0, 0);
    }

    // Gera mix de jobs
    private void execRoutine1() {
        ArrayList<Program> programs = new ArrayList<>();
        Random rand = new Random();
        float Ta = 10;

        for (int i = 0; i < NUM_JOBS; i++) {
            float startTime = (float) (-Ta * Math.log(rand.nextFloat()));
            float processTime = 50 * rand.nextFloat();
            int memSize = genMemSize();
            int ioOperations = genIoOperations();

            Program program = new Program(i, startTime, processTime, memSize, ioOperations);
            programs.add(program);
        }

        for (Program program : programs) {
            float time = program.getStartTime();
            int type = 2;
            int programId = program.getId();

            SimEvent jobStart = new SimEvent(time, type, programId);
            addToList(jobStart);
        }
    }

    // Adiciona a lista de eventos mantendo-a ordenada no tempo
    private void addToList(SimEvent simEvent) {
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

    private int genIoOperations() {
        Random rand = new Random();
        float p = rand.nextFloat();
        if (p < 0.1)
            return 0;
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
    private void execRoutine2() {

    }

    // Aloca job ao processador
    private void execRoutine3() {

    }

    // Libera processador para operacao de IO
    private void execRoutine4() {

    }

    // Envia operacao IO ao disco
    private void execRoutine5() {

    }

    // Libera disco
    private void execRoutine6() {

    }

    // Termina job
    private void execRoutine7() {

    }

    void printLog() {
        for (SimEvent simEvent: simEvents) {
            String eventMsg = simEvent.getLog();
            System.out.println(eventMsg);
        }
    }
}
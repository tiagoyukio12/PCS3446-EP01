package usp.pcs;

class SimEvent {
    private float time;
    private int type;
    private int program;

    SimEvent(float time, int type, int program) {
        this.time = time;
        this.type = type;
        this.program = program;
    }

    int getType() {
        return type;
    }

    float getTime() {
        return time;
    }

    int getProgram() {
        return program;
    }

    String getLog() {
        return "program: " + program + "; type: " + type + "; time: " + time;
    }
}

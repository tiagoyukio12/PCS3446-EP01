package usp.pcs;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        StochasticSim stochasticSim = new StochasticSim(1000);

        SimEvents simEvents = stochasticSim.run();
        simEvents.printLog();
    }
}

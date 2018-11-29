package usp.pcs;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        StochasticSim stochasticSim = new StochasticSim(100);

        stochasticSim.run();
        stochasticSim.printLog();
    }
}

package usp.pcs;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        StochasticSim stochasticSim = new StochasticSim();
        SimEvents simEvents = stochasticSim.run();

        Scanner scanIn = new Scanner(System.in);
        label:
        while (true) {
            System.out.println("Digite um comando: ");
            String input = scanIn.nextLine();
            String[] command = input.split(" ");
            switch (command[0]) {
                case "LOG":
                    simEvents.printLog();
                    break;
                case "JOB":
                    simEvents.printJob(Integer.parseInt(command[1]));
                    break;
                case "STA":
                    simEvents.printStatistics();
                    break;
                case "END":
                    break label;
            }
        }
    }
}

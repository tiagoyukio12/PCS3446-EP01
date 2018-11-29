package usp.pcs;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Memory {
    private int availableSpace;
    private ArrayList<Program> queue = new ArrayList<>();

    Memory(int size) {
        availableSpace = size;
    }

    int getAvailableSpace() {
        return availableSpace;
    }

    void allocate(Program program) {
        if (availableSpace > program.getMemSize())
            availableSpace -= program.getMemSize();
        else
            queue.add(program);
    }
}

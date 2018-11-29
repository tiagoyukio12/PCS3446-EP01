package usp.pcs;

import java.io.*;
import java.util.Scanner;

class Memory {
    private int size;
    private int availableSpace;

    Memory(int size) {
        this.size = size;
    }

    int getAvailableSpace() {
        return availableSpace;
    }
}

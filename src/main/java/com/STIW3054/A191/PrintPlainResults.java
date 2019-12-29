package com.STIW3054.A191;

import java.io.PrintStream;

public class PrintPlainResults implements CkjmOutputHandler {
    private PrintStream p;

    public PrintPlainResults (PrintStream p) {
        this.p = p;
    }
}

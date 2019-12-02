package com.STIW3054.A191;

class Threads {

    static int availableLightThreads() {
        if(totalThreads()<4){
            return 1;
        }else {
            return totalThreads()/4*3+totalThreads()%4;
        }
    }

    static int availableHeavyThreads() {
        if(totalThreads()<4){
            return 1;
        }else {
            return totalThreads()/2;
        }
    }

    static int totalThreads() {
        return Runtime.getRuntime().availableProcessors();
    }
}

package com.STIW3054.A191;

class Threads {

    static int availableThreads() {
        if(totalThreads()<4){
            return totalThreads()/2+totalThreads()%2;
        }else {
            return totalThreads()/4*3+totalThreads()%4;
        }
    }

    static int totalThreads() {
        return Runtime.getRuntime().availableProcessors();
    }
}

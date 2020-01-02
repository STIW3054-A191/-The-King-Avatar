package com.STIW3054.A191;

import java.util.concurrent.TimeUnit;

class TimeElapsed {

    private static long startTime;

    static void start() {
        startTime = System.currentTimeMillis();
    }

    static void endAndOutput() {
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        long days = TimeUnit.MILLISECONDS.toDays(timeElapsed);
        long hours = TimeUnit.MILLISECONDS.toHours(timeElapsed) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % 60;
        long milliseconds = timeElapsed % 1000;

        System.out.print("\nExecution time : ");

        if (days != 0) {
            System.out.print(days + " Days, " + hours + " Hours, " + minutes + " Minutes, " + seconds + " Seconds, " + milliseconds + " Milliseconds.");
        } else if (hours != 0) {
            System.out.print(hours + " Hours, " + minutes + " Minutes, " + seconds + " Seconds, " + milliseconds + " Milliseconds.");
        } else if (minutes != 0) {
            System.out.print(minutes + " Minutes, " + seconds + " Seconds, " + milliseconds + " Milliseconds.");
        } else if (seconds != 0) {
            System.out.print(seconds + " Seconds, " + milliseconds + " Milliseconds.");
        } else {
            System.out.print(milliseconds + " Milliseconds.");
        }
    }
}

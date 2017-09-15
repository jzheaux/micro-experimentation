package com.joshcummings.cats;

public class LoftyBenchmark {
    private static void runOverAndOver(int times, Runnable r) {
        long start = System.nanoTime();
        String testName = Thread.currentThread().getStackTrace()[2].getMethodName();
        for (int i = 0; i < times; i++) {
            r.run();
        }
        double end = (System.nanoTime() - start) / 1_000_000d;
        System.out.printf("%s ran %f ops/ms%n", testName, times / end);
    }

   /* private static double sqrt(double what) {
        double guess = what / 2;
        double lastguess = what;
        for (int i = 0; i < 100 && lastguess != guess; i++) {
            lastguess = guess;
            guess = (guess + what / guess) / 2;
        }
        return guess;
    }*/

    private static double sqrt(double what) {
        return Math.exp(0.5 * Math.log(what));
    }
    
    private static void testSqrt() {
        runOverAndOver(100_000_000, () -> sqrt(10));
    }
    
    private static void testJavaSqrt() {
        runOverAndOver(100_000_000, () -> Math.sqrt(10));
    }
    
    private static void testConstant() {
        runOverAndOver(100_000_000, () -> { int i  = 10; });
    }
    
    public static void main(String[] args) {
        testSqrt(); 
        testJavaSqrt();
        testConstant();
    }
}

package com.java.pwir;

import java.util.Random;

public class MyWorker implements Runnable {
    Upps instance = null;

    public MyWorker(Upps instance) {
        this.instance = instance;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            this.instance.increment();
            try {
                Thread.sleep(new Random().nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}

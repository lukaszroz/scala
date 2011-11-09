public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    boolean asleep = false;

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        new ReaderThread().start();
        new ReaderThread().start();
        new ReaderThread().start();
        new ReaderThread().start();
        new ReaderThread().start();
        number = 42;
        ready = true;
        final NoVisibility nv = new NoVisibility();
        new Thread() {
            @Override
            public void run() {
                nv.asleep = true;
                System.out.println("fallen asleep");
            }
        }.start();
        while (!nv.asleep){
            countSomeSheep();
            Thread.yield();
        }
        System.out.println("Asleep.");
    }

    private static void countSomeSheep() {
        System.out.println("Counting...");
    }
}
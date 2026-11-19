
public class TokenCounter {
    private final int[] buffer = new int[5];
    private int count = 0, in = 0, out = 0;

    public synchronized void put(int token)
            throws InterruptedException {

        while (count == buffer.length) {
            wait();
        }

        buffer[in] = token;
        in = (in + 1) % buffer.length;
        count++;

        System.out.println("Produced: " + token
                           + " | Count: " + count);
        notifyAll();
    }

    public synchronized int take()
            throws InterruptedException {

        while (count == 0) {
            wait();
        }

        int token = buffer[out];
        out = (out + 1) % buffer.length;
        count--;

        System.out.println("Consumed: " + token
                           + " | Count: " + count);
        notifyAll();

        return token;
    }

    public static void main(String[] args)
            throws InterruptedException {

        TokenCounter counter = new TokenCounter();

        Thread cook = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++)
                    counter.put(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread student1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++)
                    counter.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread student2 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++)
                    counter.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        cook.start();
        student1.start();
        student2.start();

        cook.join();
        student1.join();
        student2.join();

        System.out.println("All 20 tokens processed.");
    }
}

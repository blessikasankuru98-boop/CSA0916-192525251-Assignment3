
class ScannerStation extends Thread {
    private int station;

    ScannerStation(int station) {
        this.station = station;
    }

    public void run() {
        try {
            System.out.println("Station " + station + " started");
            Thread.sleep(500);
            System.out.println("Station " + station
                               + " scanned 5 scripts");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ScannerDemo {
    public static void main(String[] args)
            throws InterruptedException {

        ScannerStation[] stations = new ScannerStation[4];

        for (int i = 0; i < 4; i++) {
            stations[i] = new ScannerStation(i + 1);
            stations[i].start();
        }

        for (ScannerStation station : stations) {
            System.out.println("Alive: " + station.isAlive());
            station.join();
        }

        System.out.println("All stations completed.");
    }
}

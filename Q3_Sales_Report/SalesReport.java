
import java.io.*;

public class SalesReport {
    public static void main(String[] args) {
        String[] files = {
            "store1.txt", "store2.txt", "store3.txt"
        };

        int total = 0;

        try (PrintWriter writer =
                 new PrintWriter(new FileWriter("report.txt"))) {

            for (String file : files) {
                try (BufferedReader reader =
                         new BufferedReader(new FileReader(file))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        total += Integer.parseInt(line.trim());
                    }

                    System.out.println("Processed: " + file);

                } catch (IOException | NumberFormatException e) {
                    System.out.println("Problem in " + file + ": "
                                       + e.getMessage());
                }
            }

            writer.println("Total sales: " + total);
            System.out.println("Report created successfully.");

        } catch (IOException e) {
            System.out.println("Report error: " + e.getMessage());
        }
    }
}


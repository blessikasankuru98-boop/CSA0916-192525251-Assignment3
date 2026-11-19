
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            File card = new File("card-reader.txt");
            Scanner reader = new Scanner(card);
            reader.close();

            System.out.print("Enter account number: ");
            String account = sc.nextLine();

            if (!account.matches("\\d{10}")) {
                throw new IllegalArgumentException("Invalid account number");
            }

            System.out.print("Enter withdrawal amount: ");
            int amount = Integer.parseInt(sc.nextLine());

            int balance = 10000;

            if (amount <= 0) {
                throw new IllegalArgumentException(
                    "Amount must be positive");
            }

            if (amount > balance) {
                throw new ArithmeticException(
                    "Insufficient balance");
            }

            balance -= amount;
            System.out.println("Please collect your cash.");
            System.out.println("Remaining balance: " + balance);

        } catch (FileNotFoundException e) {
            System.out.println("Card reader file is missing.");
        } catch (NumberFormatException | ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("ATM session completed.");
        }
    }
}

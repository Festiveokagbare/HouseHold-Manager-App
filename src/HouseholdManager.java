import java.io.*;
import java.util.*;

public class HouseholdManager {
    private static final String FILE_NAME = "household_data.txt";
    private List<Income> incomeEntries = new ArrayList<>();
    private List<Expenses> expenseEntries = new ArrayList<>();

    public static void main(String[] args) {
        HouseholdManager manager = new HouseholdManager();
        manager.loadFromFile();
        manager.run();
        manager.saveToFile();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Hello there! Welcome to Household Income and Expense Tracker!");
            System.out.println("1. Enter income");
            System.out.println("2. Enter expense");
            System.out.println("3. View monthly report");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    enterIncome(scanner);
                    break;
                case 2:
                    enterExpense(scanner);
                    break;
                case 3:
                    viewMonthlyReport(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void enterIncome(Scanner scanner) {
        System.out.print("Enter category e.g Salary, Groceries, Utilities, etc: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter month e.g January, February, March...December: ");
        String month = scanner.nextLine();

        incomeEntries.add(new Income(category, amount, month));
    }

    private void enterExpense(Scanner scanner) {
        System.out.print("Enter category e.g Salary, Groceries, Utilities, etc: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount : ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter month e.g January, February, March...December: ");
        String month = scanner.nextLine();

        expenseEntries.add(new Expenses(category, amount, month));
    }

    private void viewMonthlyReport(Scanner scanner) {
        System.out.print("Enter month: ");
        String month = scanner.nextLine();

        Map<String, Double> incomeMap = new HashMap<>();
        Map<String, Double> expenseMap = new HashMap<>();
        double totalIncome = 0;
        double totalExpenses = 0;

        for (Income entry : incomeEntries) {
            if (entry.getMonth().equalsIgnoreCase(month)) {
                incomeMap.put(entry.getCategory(),
                        incomeMap.getOrDefault(entry.getCategory(), 0.0) + entry.getAmount());
                totalIncome += entry.getAmount();
            }
        }

        for (Expenses entry : expenseEntries) {
            if (entry.getMonth().equalsIgnoreCase(month)) {
                expenseMap.put(entry.getCategory(),
                        expenseMap.getOrDefault(entry.getCategory(), 0.0) + entry.getAmount());
                totalExpenses += entry.getAmount();
            }
        }

        System.out.println("Category-wise totals for " + month + ":");
        System.out.println("Income:");
        for (Map.Entry<String, Double> entry : incomeMap.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Expenses:");
        for (Map.Entry<String, Double> entry : expenseMap.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Net Balance for " + month + ": " + (totalIncome - totalExpenses));
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(incomeEntries);
            oos.writeObject(expenseEntries);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            incomeEntries = (List<Income>) ois.readObject();
            expenseEntries = (List<Expenses>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, no data to load
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}


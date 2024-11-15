/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
import RecordManagement.*;
import java.util.Scanner;
import java.sql.*;
        
public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    private final ProjectManager pm;
    private final Connection conn;
    
    public UserInterface(Connection conn) {
        this.conn = conn;
        this.pm = new ProjectManager(conn);
    }
    public void mainMenu() {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Manage Records");
            System.out.println("2. Perform Transactions");
            System.out.println("3. Generate Reports");
            System.out.println("4. Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> recordManagementMenu();
                case 2 -> transactionMenu();
                case 3 -> reportMenu();
                case 4 -> {
                    System.out.println("Exiting application...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void recordManagementMenu() {
        while (true) {
            System.out.println("\n Select Record to Manage:");
            System.out.println("1. Employees");
            System.out.println("2. Expenses/Transactions");
            System.out.println("3. Tasks");
            System.out.println("4. Projects");
            System.out.println("5. Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> recordFunctionsMenu("Employees");
                case 2 -> recordFunctionsMenu("Expenses/Transactions");
                case 3 -> recordFunctionsMenu("Tasks");
                case 4 -> recordFunctionsMenu("Projects");
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    public void recordFunctionsMenu(String record) {
        while (true) {
            System.out.println("\nSelected Record: " + record);
            System.out.println("1. Add Record");
            System.out.println("2. Edit Record");
            System.out.println("3. Delete Record");
            System.out.println("4. View Record");
            System.out.println("5. Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    if (record.equals("Projects") ) 
                           pm.addProject();
                }
                case 2 -> System.out.println("Editing " + record);
                case 3 -> {
                    if (record.equals("Projects") ) 
                           pm.deleteProject();
                }
                case 4 -> {
                       if (record.equals("Projects") ) 
                           pm.viewProjects();
                }
                case 5 -> { 
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    public void transactionMenu() {
        while (true) {
            System.out.println("\nTransaction Menu:");
            System.out.println("1. Compute Project Expenses");
            System.out.println("2. Assign Task to Employee");
            System.out.println("3. Complete Task");
            System.out.println("4. Calculate Compensation");
            System.out.println("5. Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("Computing Project Expenses...");
                    System.out.println("hi");
                }
                case 2 -> System.out.println("Assigning Task to Employee");
                case 3 -> System.out.println("Completing Task");
                case 4 -> System.out.println("Calculating Compensation");
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    public void reportMenu() {
        while (true) {
            System.out.println("\nReport Menu:");
            System.out.println("1. Employee Task Completion");
            System.out.println("2. Project Progress");
            System.out.println("3. Financial Summary");
            System.out.println("4. Employee Payroll");
            System.out.println("5. Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> System.out.println("Generating Employee Task Completion Report");
                case 2 -> System.out.println("Generating Project Progress Report");
                case 3 -> System.out.println("Generating Financial Summary Report");
                case 4 -> System.out.println("Generating Employee Payroll Report");
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }
    
    public void promptEditProject() {
        int projectId = InputHelper.getIntInput("Enter Project ID to edit: ", false, 0);

        // Display available fields to update
        System.out.println("Select the field to update:");
        System.out.println("1. Project Name");
        System.out.println("2. Start Date");
        System.out.println("3. End Date");
        System.out.println("4. Remaining Budget");
        System.out.println("5. Tasks Per Bonus");
        System.out.println("6. Bonus Amount");
        System.out.println("7. Status");

        int fieldChoice = InputHelper.getIntInput("Enter the number of the field to update: ", false, 0);
        String field;
        Object newValue = null;

        // Determine the field and prompt for the correct data type
        switch (fieldChoice) {
            case 1 -> {
                field = "project_name";
                newValue = InputHelper.getStringInput("Enter new Project Name: ", false, "");
            }
            case 2 -> {
                field = "start_date";
                newValue = InputHelper.getDateInput("Enter new Start Date (yyyy-mm-dd): ", false, null);
            }
            case 3 -> {
                field = "end_date";
                newValue = InputHelper.getDateInput("Enter new End Date (yyyy-mm-dd): ", false, null);
            }
            case 4 -> {
                field = "remaining_budget";
                newValue = InputHelper.getFloatInput("Enter new Remaining Budget: ", false, 0);
            }
            case 5 -> {
                field = "tasks_per_bonus";
                newValue = InputHelper.getIntInput("Enter new Tasks Per Bonus: ", false, 0);
            }
            case 6 -> {
                field = "bonus_amount";
                newValue = InputHelper.getFloatInput("Enter new Bonus Amount: ", false, 0);
            }
            case 7 -> {
                field = "status";
                newValue = InputHelper.getStatusInput("Enter new Status: ");
            }
            default -> {
                System.out.println("Invalid choice. Please try again.");
                return;
            }
        }

        // Call the editProject method with the selected field and new value
        pm.editProject(projectId, field, newValue);
    }

}

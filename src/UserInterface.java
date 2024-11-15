/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
import RecordManagement.*;
import Transactions.*;
import java.util.Scanner;
import java.sql.*;
        
public class UserInterface {
    private final Scanner sc = new Scanner(System.in);

    
    // Transaction objects
    private final TaskProjectCompleter tp_completer;
    // Add additional transaction objects
    
 
    private final ReportGenerator rg;   
    private final Connection conn;  
    
    
    public UserInterface(Connection conn) {
        this.conn = conn;
        this.tp_completer = new TaskProjectCompleter(conn);
        this.rg = new ReportGenerator(conn);
    }
    public void mainMenu() {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Manage Records");
            System.out.println("2. Perform Transactions");
            System.out.println("3. Generate Reports");
            System.out.println("4. Exit");

            System.out.print("Select an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

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
            System.out.println("2. Expenses");
            System.out.println("3. Tasks");
            System.out.println("4. Projects");
            System.out.println("5. Exit");

            System.out.print("Select an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

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
        RecordManager rm;

        // Select the manager based on the record type
        switch (record) {
            //case "Employees" -> manager = new EmployeeManager(conn);
            //case "Expenses" -> rm = new ExpenseManager(conn);
            //case "Tasks" -> manager = new TaskManager(conn); 
            case "Projects" -> rm = new ProjectManager(conn);         
            default -> {
                System.out.println("Invalid record type.");
                return;
            }
        }

        while (true) {
            System.out.println("\nSelected Record: " + record);
            System.out.println("1. Add Record");
            System.out.println("2. Edit Record");
            System.out.println("3. Delete Record");
            System.out.println("4. View Records");
            System.out.println("5. View Specific Record");
            System.out.println("6. Exit");

            System.out.print("Select an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> rm.add();
                case 2 -> rm.promptEdit();
                case 3 -> rm.delete();
                case 4 -> rm.view();
                case 5 -> { 
                    System.out.print("Enter record id: ");
                    int id = sc.nextInt();
                    rm.view(id);
                }
                case 6 -> {return;}
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
            System.out.println("4. Calculate Bonus");
            System.out.println("5. Exit");

            System.out.print("Select an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("Computing Project Expenses");
                    System.out.println("hi");
                }
                case 2 -> System.out.println("Assigning Task to Employee");
                case 3 -> {
                    // TODO: Call view tasks here
                    
                    System.out.print("Enter Task ID to Complete: ");
                    int taskId = sc.nextInt();
                    tp_completer.completeTask(taskId);
                }
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
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> System.out.println("Generating Employee Task Completion Report");
                case 2 -> System.out.println("Generating Project Progress Report");
                case 3 -> {
                    rg.generateFinancialSummaryReport();
                }
                case 4 -> System.out.println("Generating Employee Payroll Report");
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }
    
    

}

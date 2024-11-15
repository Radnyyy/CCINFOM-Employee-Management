/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package RecordManagement;

import java.sql.*;

public class ProjectManager implements RecordManager {
    private final Connection conn;

    public ProjectManager(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void add() {
        String sql = "INSERT INTO Projects (project_id, project_name, start_date, end_date, remaining_budget, tasks_per_bonus, bonus_amount, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Prompt fields of new project
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int projectId = InputHelper.getIntInput("Enter Project ID: ", false, 0);
            String projectName = InputHelper.getStringInput("Enter Project Name: ", false, "");
            Date startDate = InputHelper.getDateInput("Enter Start Date (yyyy-mm-dd): ", false, null);
            float remainingBudget = InputHelper.getFloatInput("Enter Initial Budget: ", false, 0);
            int tasksPerBonus = InputHelper.getIntInput("Enter Tasks Per Bonus: ", true, 0);
            float bonusAmount = InputHelper.getFloatInput("Enter Bonus Amount: ", true, 0);

            pstmt.setInt(1, projectId);
            pstmt.setString(2, projectName);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, null); // Default end date is null
            pstmt.setFloat(5, remainingBudget);
            pstmt.setInt(6, tasksPerBonus);
            pstmt.setFloat(7, bonusAmount);
            pstmt.setString(8, "ongoing");  // Default status is ongoing

            if (pstmt.executeUpdate() > 0)
                System.out.println("Project added successfully.");
            else
                System.out.println("Failed to add project.");
        } catch (SQLException e) {
            System.out.println("Error adding project: " + e);
        }
    }

    @Override
    public void promptEdit() {
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
        Object newValue;

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

        edit(projectId, field, newValue);
    }

    @Override
    public void edit(int projectId, String field, Object newValue) {
        String updateSql = "UPDATE Projects SET " + field + " = ? WHERE project_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            // Set the appropriate type for the new value based on the field
            switch (field) {
                case "project_name" -> pstmt.setString(1, (String) newValue);
                case "start_date", "end_date" -> pstmt.setDate(1, (Date) newValue);
                case "remaining_budget" -> pstmt.setFloat(1, (Float) newValue);
                case "tasks_per_bonus" -> pstmt.setInt(1, (Integer) newValue);
                case "bonus_amount" -> pstmt.setFloat(1, (Float) newValue);
                case "status" -> pstmt.setString(1, (String) newValue);
                default -> {
                    System.out.println("Invalid field name.");
                    return;
                }
            }

            pstmt.setInt(2, projectId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Project updated successfully." : "Failed to update project.");

        } catch (SQLException e) {
            System.out.println("Error updating project: " + e);
        } catch (ClassCastException e) {
            System.out.println("Invalid value type for field '" + field + "': " + e);
        }
    }


    @Override
    public void delete() {
        int projectId = InputHelper.getIntInput("Enter Project ID to delete: ", false, 0);

        String sql = "DELETE FROM Projects WHERE project_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Project deleted successfully." : "Failed to delete project.");
        } catch (SQLException e) {
            System.out.println("Error deleting project: " + e);
        }
    }

    @Override
    public void view() {
        String sql = "SELECT project_id, project_name, start_date, end_date, remaining_budget, tasks_per_bonus, bonus_amount, status FROM Projects";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Projects:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("project_id") +
                                   ", Name: " + rs.getString("project_name") +
                                   ", Start Date: " + rs.getDate("start_date") +
                                   ", End Date: " + rs.getDate("end_date") +
                                   ", Remaining Budget: " + rs.getFloat("remaining_budget") +
                                   ", Tasks Per Bonus: " + rs.getInt("tasks_per_bonus") +
                                   ", Bonus Amount: " + rs.getFloat("bonus_amount") +
                                   ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing projects: " + e);
        } 
    }

    @Override
    public void view(int projectId) {
        String projectSql = "SELECT project_id, project_name, start_date, end_date, remaining_budget, tasks_per_bonus, bonus_amount, status FROM Projects WHERE project_id = ?";

        try (PreparedStatement projectStmt = conn.prepareStatement(projectSql)) {

            // Fetch and display project details
            projectStmt.setInt(1, projectId);
            ResultSet projectRs = projectStmt.executeQuery();

            if (projectRs.next()) {
                System.out.println("ID: " + projectRs.getInt("project_id") +
                                   ", Name: " + projectRs.getString("project_name") +
                                   ", Start Date: " + projectRs.getDate("start_date") +
                                   ", End Date: " + projectRs.getDate("end_date") +
                                   ", Remaining Budget: " + projectRs.getFloat("remaining_budget") +
                                   ", Tasks Per Bonus: " + projectRs.getInt("tasks_per_bonus") +
                                   ", Bonus Amount: " + projectRs.getFloat("bonus_amount") +
                                   ", Status: " + projectRs.getString("status"));
            } else {
                System.out.println("Project with ID " + projectId + " not found.");
                return;
            }

            viewProjectTasks(projectId);
        } catch (SQLException e) {
            System.out.println("Error viewing project: " + e);
        }
    }


    public void viewProjectTasks(int projectId) {
        String sql = "SELECT task_id, task_name, deadline, status, date_complete FROM Tasks WHERE project_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Tasks for Project ID: " + projectId);
            while (rs.next()) {
                System.out.println("Task ID: " + rs.getInt("task_id") +
                                   ", Task Name: " + rs.getString("task_name") +
                                   ", Deadline: " + rs.getDate("deadline") +
                                   ", Status: " + rs.getString("status") +
                                   ", Date Completed: " + rs.getDate("date_complete"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing project tasks: " + e);
        }
    }
}


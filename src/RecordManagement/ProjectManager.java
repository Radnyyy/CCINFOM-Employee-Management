/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package RecordManagement;

import java.sql.*;

public class ProjectManager {
    private final Connection conn;

    public ProjectManager(Connection conn) {
        this.conn = conn;
    }

    public void addProject() {
        String sql = "INSERT INTO Projects (project_id, project_name, start_date, end_date, remaining_budget, tasks_per_bonus, bonus_amount, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int projectId = InputHelper.getIntInput("Enter Project ID: ", false, 0);
            String projectName = InputHelper.getStringInput("Enter Project Name: ", false, "");
            Date startDate = InputHelper.getDateInput("Enter Start Date (yyyy-mm-dd): ", false, null);
            Date endDate = InputHelper.getDateInput("Enter End Date (yyyy-mm-dd): ", false, null);
            float remainingBudget = InputHelper.getFloatInput("Enter Remaining Budget: ", false, 0);
            int tasksPerBonus = InputHelper.getIntInput("Enter Tasks Per Bonus: ", false, 0);
            float bonusAmount = InputHelper.getFloatInput("Enter Bonus Amount: ", false, 0);
            String status = InputHelper.getStatusInput("Enter Status: ");

            pstmt.setInt(1, projectId);
            pstmt.setString(2, projectName);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);
            pstmt.setFloat(5, remainingBudget);
            pstmt.setInt(6, tasksPerBonus);
            pstmt.setFloat(7, bonusAmount);
            pstmt.setString(8, status);

            if (pstmt.executeUpdate() > 0)
                System.out.println("Project added successfully.");
            else
                System.out.println("Failed to add project.");
        } catch (SQLException e) {
            System.err.println("Error adding project: " + e.getMessage());
        }
    }


    public void editProject(int projectId, String field, Object newValue) {
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
            System.err.println("Error updating project: " + e.getMessage());
        } catch (ClassCastException e) {
            System.err.println("Invalid value type for field '" + field + "': " + e.getMessage());
        }
    }


    public void deleteProject() {
        int projectId = InputHelper.getIntInput("Enter Project ID to delete: ", false, 0);

        String sql = "DELETE FROM Projects WHERE project_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Project deleted successfully." : "Failed to delete project.");
        } catch (SQLException e) {
            System.err.println("Error deleting project: " + e.getMessage());
        }
    }

    public void viewProjects() {
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
            System.err.println("Error viewing projects: " + e.getMessage());
        } 
    }

    public void viewProject(int projectId) {
        String sql = "SELECT project_id, project_name, start_date, end_date, remaining_budget, tasks_per_bonus, bonus_amount, status FROM Projects WHERE project_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("project_id") +
                                   ", Name: " + rs.getString("project_name") +
                                   ", Start Date: " + rs.getDate("start_date") +
                                   ", End Date: " + rs.getDate("end_date") +
                                   ", Remaining Budget: " + rs.getFloat("remaining_budget") +
                                   ", Tasks Per Bonus: " + rs.getInt("tasks_per_bonus") +
                                   ", Bonus Amount: " + rs.getFloat("bonus_amount") +
                                   ", Status: " + rs.getString("status"));
            } else {
                System.out.println("Project with ID " + projectId + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error viewing project: " + e.getMessage());
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
            System.err.println("Error viewing project tasks: " + e.getMessage());
        }
    }
}


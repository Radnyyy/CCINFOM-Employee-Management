/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Transactions;
import java.sql.*;
/**
 *
 * @author user
 */

public class TaskProjectCompleter {
    private final Connection conn;

    public TaskProjectCompleter(Connection conn) {
        this.conn = conn;
    }

    public void completeTask(int taskId) {
        try {
            // Begin transaction
            conn.setAutoCommit(false);

            // Updating status of the task to complete
            String updateTaskSql = "UPDATE Tasks SET status = 'completed', date_complete = NOW() WHERE task_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateTaskSql)) {
                pstmt.setInt(1, taskId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Task marked as completed.");
                } else {
                    System.out.println("Task with ID " + taskId + " not found.");
                    conn.rollback(); // Rollback transaction if task is not found
                    return;
                }
            }

            // Updating EmployeeTasks to mark employees as completed if emp_status is 'active'
            String updateEmployeeTaskSql = "UPDATE EmployeeTasks SET emp_status = 'completed', date_complete = NOW() WHERE task_id = ? AND emp_status = 'active'";
            try (PreparedStatement pstmt = conn.prepareStatement(updateEmployeeTaskSql)) {
                pstmt.setInt(1, taskId);
                int employeeRowsAffected = pstmt.executeUpdate();
                if (employeeRowsAffected > 0) {
                    System.out.println("Employee statuses updated for active task completion.");
                } else {
                    System.out.println("No active employees assigned to this task or already completed.");
                }
            }

            checkAndCompleteProject(taskId);

            conn.commit(); // Commit transaction if all steps succeed
            System.out.println("Transaction committed successfully.");

        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback transaction in case of any error
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("Error during transaction rollback: " + rollbackEx.getMessage());
            }
        }
    }

    private void checkAndCompleteProject(int taskId) throws SQLException {
        // Getting the project_id associated with this task
        String getProjectSql = "SELECT project_id FROM Tasks WHERE task_id = ?";
        int projectId;
        try (PreparedStatement pstmt = conn.prepareStatement(getProjectSql)) {
            pstmt.setInt(1, taskId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                projectId = rs.getInt("project_id");
            } else {
                System.out.println("No project found for task ID " + taskId);
                conn.rollback(); // Rollback transaction if project is not found
                return;
            }
        }

        // Checking if all tasks in this project are completed
        String checkTasksSql = "SELECT COUNT(*) AS incomplete_tasks FROM Tasks WHERE project_id = ? AND status != 'completed'";
        try (PreparedStatement pstmt = conn.prepareStatement(checkTasksSql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt("incomplete_tasks") == 0) {
                // Set project status as 'completed' if no incomplete tasks are found
                String completeProjectSql = "UPDATE Projects SET status = 'completed' WHERE project_id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(completeProjectSql)) {
                    updateStmt.setInt(1, projectId);
                    updateStmt.executeUpdate();
                    System.out.println("Project ID " + projectId + " marked as completed.");
                }
            } else {
                System.out.println("Not all tasks are completed for Project ID " + projectId);
            }
        }
    }
}


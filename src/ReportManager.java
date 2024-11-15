/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
import java.sql.*;
        
public class ReportManager {
    private final Connection conn;

    public ReportManager(Connection conn) {
        this.conn = conn;
    }

    
    public void generateFinancialSummaryReport() {
    String sql = "SELECT p.project_id, p.project_name, YEAR(e.date) AS year, MONTH(e.date) AS month, " +
                 "SUM(e.expense_amount) AS total_expenses, p.remaining_budget " +
                 "FROM Projects p " +
                 "LEFT JOIN Expenses e ON p.project_id = e.project_id " +
                 "GROUP BY p.project_id, p.project_name, year, month, p.remaining_budget " +
                 "ORDER BY p.project_id, year, month";

    try (PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        System.out.println("Financial Summary Report (Expenses and Remaining Budget per Project per Month):");
        while (rs.next()) {
            int projectId = rs.getInt("project_id");
            String projectName = rs.getString("project_name");
            int year = rs.getInt("year");
            int month = rs.getInt("month");
            float totalExpenses = rs.getFloat("total_expenses");
            float remainingBudget = rs.getFloat("remaining_budget");

            System.out.printf("Project ID: %d, Name: %s, Year: %d, Month: %d, Total Expenses: %.2f, Remaining Budget: %.2f%n",
                              projectId, projectName, year, month, totalExpenses, remainingBudget);
        }
    } catch (SQLException e) {
        System.err.println("Error generating financial summary report: " + e.getMessage());
    }
}

}

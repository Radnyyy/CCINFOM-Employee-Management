/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
import java.sql.*;
        
public class ReportGenerator {
    private final Connection conn;

    public ReportGenerator(Connection conn) {
        this.conn = conn;
    }

    
    public void generateFinancialSummaryReport() {
        String sql = "SELECT p.project_id, p.project_name, " +
                     "CONCAT(MONTHNAME(e.expense_date), ' ', YEAR(e.expense_date)) AS month_year, " +
                     "SUM(e.cost) AS total_expenses, " +
                     "p.remaining_budget - SUM(e.cost) AS remaining_budget " +
                     "FROM Projects p " +
                     "LEFT JOIN Expenses e ON p.project_id = e.project_id " +
                     "GROUP BY p.project_id, p.project_name, month_year, p.remaining_budget, e.expense_date " +
                     "ORDER BY p.project_id, e.expense_date";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Financial Summary Report:");
            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String projectName = rs.getString("project_name");
                String monthYear = rs.getString("month_year");
                float totalExpenses = rs.getFloat("total_expenses");
                float remainingBudget = rs.getFloat("remaining_budget");

                System.out.printf("Project ID: %d, Name: %s, Month/Year: %s, Total Expenses: %.2f, Remaining Budget: %.2f%n",
                                  projectId, projectName, monthYear, totalExpenses, remainingBudget);
            }
        } catch (SQLException e) {
            System.out.println("Error generating financial summary report: " + e);
        }
    }


}

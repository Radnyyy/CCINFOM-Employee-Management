


import java.sql.*;

public class ConnectionManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root"; 
    private static final String PASS = "password";



    public Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            System.out.println("Connection success");
           
            return conn;
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found.");
        }catch (SQLException ex) {
            System.out.println("SQL Error.");
        }
        
        return null;
    }

  
}

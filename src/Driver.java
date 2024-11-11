import java.sql.*;
        
public class Driver {
    
   
    public static void main(String[] args) {
        ConnectionManager cm = new ConnectionManager();
        
        Connection conn = cm.createConnection();
    }
}

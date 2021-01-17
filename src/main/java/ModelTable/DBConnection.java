package ModelTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    public static Statement  getDBConnection() {
         Connection connection;
         Statement statement=null;

            try {
                 connection = DriverManager.getConnection("jdbc:sqlserver://192.168.63.250", "su", "Gr0m21$h0w");
                     //  connection = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-CGJS7NF\\TEST", "sa", "12345");
        //  connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\sql", "sa", "12345");
                statement = connection.createStatement();
            } catch (SQLException throwables) {
            }
            return statement;

    }
}

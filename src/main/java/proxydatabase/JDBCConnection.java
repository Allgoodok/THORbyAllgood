package proxydatabase;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Created by Vlad on 21.03.2015.
 */
public class JDBCConnection {
    private String url = "jdbc:postgresql://localhost:5432/ProxyList";
    private String username = "postgres";
    private String password = "Terminator133";
    protected String driverJAR = "org.postgresql.Driver";

    public void performConnection(){
        System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");

        try {

            Class.forName(driverJAR);

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;

        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}


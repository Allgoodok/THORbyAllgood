package proxydatabase;
import java.sql.*;

/**
 * Created by Vlad on 21.03.2015.
 */
public class JDBCConnection {
    private static final String url = "jdbc:postgresql://localhost:5432/ProxyList";
    public static final  String username = "postgres";
    public static final  String password = "1234";
    protected String driverJAR = "org.postgresql.Driver";
    public Connection connection = null;

    public JDBCConnection(){
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


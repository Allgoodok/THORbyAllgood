package proxydatabase;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Vlad on 21.03.2015.
 */
public class DBFill {
    private static final String INSERT_NEW ="INSERT INTO proxylist(ipaddress,usability) VALUES(?,?)";
    public JDBCConnection jdbcConnection = new JDBCConnection();

    public DBFill() {}

    public void fillingDB() {
        try {
            jdbcConnection.statement = jdbcConnection.connection.createStatement();
            jdbcConnection.preparedStatement = jdbcConnection.connection.prepareStatement(INSERT_NEW);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        DBFill dbFill = new DBFill();
        try {
            System.out.println(dbFill.jdbcConnection.connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

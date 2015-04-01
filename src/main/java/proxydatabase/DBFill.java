package proxydatabase;


import THORParser.THORParser;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Vlad on 21.03.2015.
 */
public class DBFill {
    private static final String INSERT_NEW_COLUMN12 ="INSERT INTO proxylist(ipaddress,usability,port) VALUES(?,?,?)";
    private static final String INSERT_NEW_COLUMN3 ="INSERT INTO proxylist(port) VALUES(?)";
    private static final String DELETE = "DELETE FROM proxylist";
    public JDBCConnection jdbcConnection = new JDBCConnection();
    public THORParser thorParser = new THORParser();
    public String toPutInDB= null;
    public java.sql.Statement statement  = null;
    public PreparedStatement preparedStatement1 = null;
    public PreparedStatement preparedStatement2 = null;


    public DBFill() {}

    public void fillingDB() {
        try {
            this.statement = jdbcConnection.connection.createStatement();
            this.preparedStatement1 = jdbcConnection.connection.prepareStatement(INSERT_NEW_COLUMN12);
            this.preparedStatement2 = jdbcConnection.connection.prepareStatement(INSERT_NEW_COLUMN3);
            statement.execute(DELETE);
            toPutInDB = thorParser.getProxyList();
            int N = 32;
            int column = 58;
            int tempInt  = 0;
            String tempStr = null;


            String[] splitTable = toPutInDB.split(" ");
            for(int i = 0; i < splitTable.length; i++ ) {
                String[] split = splitTable[i].split(":");
                for (int j = 0; j < split.length; j += 2) {
                    this.preparedStatement1.setString(1, split[j]);
                    this.preparedStatement1.setBoolean(2, false);
                    this.preparedStatement1.setInt(3, Integer.parseInt(split[j + 1]));

                    this.preparedStatement1.execute();

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        DBFill dbFill = new DBFill();
        try {

            dbFill.fillingDB();
            System.out.println(dbFill.jdbcConnection.connection.isClosed());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

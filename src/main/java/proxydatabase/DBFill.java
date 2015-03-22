package proxydatabase;


import THORParser.THORParser;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Vlad on 21.03.2015.
 */
public class DBFill {
    private static final String INSERT_NEW ="INSERT INTO proxylist(ipaddress,usability) VALUES(?,?)";
    public JDBCConnection jdbcConnection = new JDBCConnection();
    public THORParser thorParser = new THORParser();
    public String toPutInDB= null;

    public DBFill() {}

    public void fillingDB() {
        try {
            jdbcConnection.statement = jdbcConnection.connection.createStatement();
            jdbcConnection.preparedStatement = jdbcConnection.connection.prepareStatement(INSERT_NEW);
            toPutInDB = thorParser.getProxyList();
            int i = 0;
            int temp = 0;
            int N = 10;

            while(i < toPutInDB.length()){
                if((int)toPutInDB.charAt(i)==N){
                    jdbcConnection.preparedStatement.setString(1,toPutInDB.substring(temp,i));
                    jdbcConnection.preparedStatement.setBoolean(2, false);
                    jdbcConnection.preparedStatement.execute();

                    temp = i;
                    i++;

                }else{
                    i++;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

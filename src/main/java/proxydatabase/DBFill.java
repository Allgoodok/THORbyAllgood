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
    private static final String INSERT_NEW_COLUMN12 ="INSERT INTO proxylist(ipaddress,usability,port) VALUES(?,?,?)";
    private static final String INSERT_NEW_COLUMN3 ="UPDATE proxylist SET port = ?";
    public JDBCConnection jdbcConnection = new JDBCConnection();
    public THORParser thorParser = new THORParser();
    public String toPutInDB= null;
    public PreparedStatement preparedStatement1 = null;
    public PreparedStatement preparedStatement2 = null;

    public DBFill() {}

    public void fillingDB() {
        try {
            this.preparedStatement1 = jdbcConnection.connection.prepareStatement(INSERT_NEW_COLUMN12);
            this.preparedStatement2 = jdbcConnection.connection.prepareStatement(INSERT_NEW_COLUMN3);
            toPutInDB = thorParser.getProxyList();
            int i = 0;
            int temp = 0;
            int N = 10;
            int column = 58;

            while(i < toPutInDB.length()){
                if((int)toPutInDB.charAt(i)==column){
                    this.preparedStatement1.setString(1,toPutInDB.substring(temp,i));
                    this.preparedStatement1.setBoolean(2, false);
                    this.preparedStatement1.setInt(3,0);

                    this.preparedStatement1.execute();

                    temp = i;
                    i++;

                }else if((int)toPutInDB.charAt(i)==N) {
                    this.preparedStatement2.setInt(1, Integer.parseInt(toPutInDB.substring(temp+1, i)));
                    this.preparedStatement2.execute();
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

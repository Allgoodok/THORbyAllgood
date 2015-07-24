package THORParser;

import proxydatabase.JDBCConnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Vlad on 21.03.2015.
 */
public class PROXYIpAddress {
    public String ipaddress;
    public boolean usability;
    public int id;
    public int port;

    public PROXYIpAddress() {
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public boolean isUsability() {
        return usability;
    }

    public void setUsability(boolean usability) {
        this.usability = usability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean checkIpEfficiency(SocketAddress addr) {
        try {
                int TIMEOUT = 5000;

                Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
                URL url = new URL("http://www.example.com/");
                URLConnection conn = url.openConnection(proxy);
                conn.setConnectTimeout(TIMEOUT);
                conn.setReadTimeout(5000);
                InputStream inputStream = conn.getInputStream();
                System.out.printf("Connection established using proxy ");
                  return true;


        } catch (SocketTimeoutException e){
            System.out.printf("Connection failed using proxy ");
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.printf("Connection failed due to 403 error using proxy ");
            return false;
        }

            return false;
    }

    public void checkForAccesability() throws SQLException {

        String UPDATE_COLUMN_2 = "UPDATE proxylist SET usability = ? WHERE ipaddress = ?";
        JDBCConnection jdbcConnection = new JDBCConnection();
        PreparedStatement preparedStatement = jdbcConnection.connection.prepareStatement(UPDATE_COLUMN_2);
        String query = "select * from proxylist";

        Statement statement = jdbcConnection.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        PROXYIpAddress proxyIpAddress = new PROXYIpAddress();

        String tempIPAddress;


       while (resultSet.next()) {

            SocketAddress socketAddress = new InetSocketAddress(resultSet.getString(1), resultSet.getInt(3));
            tempIPAddress = resultSet.getString(1);
            preparedStatement.setBoolean(1,proxyIpAddress.checkIpEfficiency(socketAddress));
            System.out.println(resultSet.getString(1) + ":" + resultSet.getInt(3));
            preparedStatement.setString(2,tempIPAddress);
            preparedStatement.executeUpdate();

        }

    }

    public static void main(String[] args) throws SQLException {
        PROXYIpAddress proxyIpAddress = new PROXYIpAddress();

        proxyIpAddress.checkForAccesability();

    }
}

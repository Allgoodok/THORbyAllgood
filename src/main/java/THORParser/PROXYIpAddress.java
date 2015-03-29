package THORParser;

import proxydatabase.JDBCConnection;

import java.io.IOException;
import java.net.*;
import java.sql.*;
import java.sql.SQLException;

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

    public boolean checkIpEfficiency() {
        JDBCConnection jdbcConnection = new JDBCConnection();
        String query = "select * from proxylist";
        try {

            Statement statement = jdbcConnection.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                SocketAddress addr = new InetSocketAddress(resultSet.getString(1), resultSet.getInt(4));
                Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);

                URL url = new URL("http://java.example.org/");
                URLConnection conn = url.openConnection(proxy);




            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package THORParser;

/**
 * Created by Vlad on 21.03.2015.
 */
public class PROXYIpAddress {
    public int id;
    public String ipaddress;
    public boolean usability;

    public PROXYIpAddress(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUsability() {
        return usability;
    }

    public void setUsability(boolean usability) {
        this.usability = usability;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
}

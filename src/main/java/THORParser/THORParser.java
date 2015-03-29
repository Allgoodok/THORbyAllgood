package THORParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;


/**
 * Created by Vlad on 20.03.2015.
 */
public class THORParser {

    final String url = "http://www.prime-speed.ru/proxy/free-proxy-list/all-working-proxies.php/";
    String html;
    Document doc;

    public String getProxyList() {
        try {
            html = Jsoup.connect(url).get().toString();
            doc = Jsoup.parse(html);
            String tempList = null;


            Elements pres = doc.select("pre");

            for (Element pre : pres) {
                tempList = pre.getAllElements().text();

            }

            return tempList.substring((tempList.indexOf(".")-1), tempList.length());

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        THORParser thorParser = new THORParser();

        System.out.println(thorParser.getProxyList());
    }
}




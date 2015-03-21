package THORParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * Created by Vlad on 20.03.2015.
 */
public class THORParser {

    String url = "http://www.prime-speed.ru/proxy/free-proxy-list/all-working-proxies.php/";
    String html = null;
    Document doc = null;

    public Elements getProxyList() {
        try {
            html = Jsoup.connect(url).get().toString();
            doc = Jsoup.parse(html);


            Elements pres = doc.select("pre");

            for (Element pre : pres) {
                return pre.getAllElements();

            }
            return null;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}




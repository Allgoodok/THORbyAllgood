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

    final String url = "http://www.ip-adress.com/proxy_list/";
    String html;
    Document doc;

    public String getProxyList() {
        try {
            html = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get().toString();
            doc = Jsoup.parse(html);
            String tempList = "";

            Element table = doc.select("table").get(0);
            Elements tresOdd = doc.getElementsByClass("odd");
            Elements tresEven = doc.getElementsByClass("even");



            for(int i = 1; i < tresEven.size() ; i++) {
                Element trOdd = tresOdd.get(i);
                Element trEven = tresEven.get(i);
                Elements tdesOdd = trOdd.select("td");
                Elements tdesEven = trEven.select("td");
                    tempList += tdesOdd.get(0).text() + " " + tdesEven.get(0).text() + " ";

            }

            return tempList;

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




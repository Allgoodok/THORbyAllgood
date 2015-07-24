package proxyinternetconnection;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 10.06.2015.
 */
public class AuthenticationGoogle {

    private String cookies;
    private HttpHost proxy = new HttpHost("localhost", 8080);
    private DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
    private HttpClient client = HttpClientBuilder.create()
            .setRoutePlanner(routePlanner)
            .build();
    private final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36";
    private String url = "http://accounts.google.com/ServiceLoginAuth";
    private String gmail = "http://mail.google.com/mail/";

    private void sendPost(String url, List<NameValuePair> postParams)
            throws Exception {

        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("Host", "accounts.google.com");
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        post.setHeader("Accept-Language", "en-US,en;q=0.5");
        post.setHeader("Cookie", getCookies());
        post.setHeader("Connection", "keep-alive");
        post.setHeader("Referer", "https://accounts.google.com/ServiceLoginAuth");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        post.setEntity(new UrlEncodedFormEntity(postParams));

        HttpResponse response = client.execute(post);

        int responseCode = response.getStatusLine().getStatusCode();

        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postParams);
        System.out.println("Response Code : " + responseCode);

        switch (responseCode) {

            case 302:
                System.out.println(response.getFirstHeader("Location"));
                break;

            default: ;

        }

        //GetPageContent(response.getFirstHeader("Location").toString());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

         System.out.println(result.toString());

    }

    private String GetPageContent(String url) throws Exception {

        HttpGet request = new HttpGet(url);


        request.setHeader("Host", "zmail.ru");
        request.setHeader("Proxy-Connection", "keep-alive");
        request.setHeader("Cache-Control", "max-age=0");
        request.setHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("User-Agent", USER_AGENT);
        request.setHeader("Referer", "https://www.google.com.ua/");
        request.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        request.setHeader("Accept-Language", "en-US,en;q=0.8");



        HttpResponse response = client.execute(request);
        int responseCode = response.getStatusLine().getStatusCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        for (Header element : response.getAllHeaders()){
            System.out.println(element);
        }




        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        // set cookies
        setCookies(response.getFirstHeader("Set-Cookie") == null ? "" :
                response.getFirstHeader("Set-Cookie").toString());

        return result.toString();

    }

    public List<NameValuePair> getFormParams(
            String html, String username, String password)
            throws UnsupportedEncodingException {

        System.out.println("Extracting form's data...");

        Document doc = Jsoup.parse(html);

        // Google form id
        Element loginform = doc.getElementById("login_panel");
        Elements inputElements = loginform.getElementsByTag("input");

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();

        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("Username"))
                value = username;
            else if (key.equals("Password"))
                value = password;

            paramList.add(new BasicNameValuePair(key, value));

        }

        return paramList;
    }


    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public static void main(String[] args) throws Exception {
        String url2 = "http://zmail.ru/";
        String url1 = "http://mail.triolan.net.ua/";
        String url = "https://accounts.google.com/ServiceLoginAuth";
        String gmail = "https://mail.google.com/mail/";

        // make sure cookies is turn on
        CookieHandler.setDefault(new CookieManager());

       AuthenticationGoogle http = new AuthenticationGoogle();

        String page = http.GetPageContent(url2);
        System.out.println(page);

        List<NameValuePair> postParams =
               http.getFormParams(page, "javator@zmail.com", "Priest315");

        http.sendPost(url2, postParams);

        //String result = http.GetPageContent(gmail);
        //System.out.println(result);


        System.out.println("Done");
    }


}

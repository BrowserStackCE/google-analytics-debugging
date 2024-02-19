import com.browserstack.local.Local;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.HashMap;

public class Main {
    static BrowserMobProxyServer proxyServer;
    static Local local;
    public static void main(String[] args) throws Exception {
        // Proxy Initialisation
        proxyServer = new BrowserMobProxyServer();
        proxyServer.start();
        int proxyPort = proxyServer.getPort();
        System.out.println("Proxy Server running on port:" + Integer.toString(proxyPort));
        // Request Interceptor
        proxyServer.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest httpRequest, HttpMessageContents httpMessageContents, HttpMessageInfo httpMessageInfo) {
                String url = httpMessageInfo.getOriginalUrl();
                if(url.contains("https://analytics.google.com/g/collect")){
                    String modified = url.replaceFirst("https://analytics.google.com/g/collect\\?","https://analytics.google.com/g/collect?_dbg=1&");
                    httpRequest.setUri(modified);
                    System.out.println("Intercepted URL:" + httpRequest.getUri());
                }
                return null;
            }
        });

        // BrowserStack Local Initialisation
        HashMap<String, String> localArgs = new HashMap<String, String>();
        localArgs.put("key",System.getenv("BROWSERSTACK_ACCESS_KEY"));
        localArgs.put("localProxyHost","127.0.0.1");
        localArgs.put("localProxyPort",Integer.toString(proxyPort));
        localArgs.put("forceproxy", "true");
        localArgs.put("forcelocal", "true");
        local = new Local();
        local.start(localArgs);
        System.out.println("BrowserStack Local Started!");
    }
}

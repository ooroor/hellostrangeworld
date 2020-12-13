package net.barakiroth.hellostrangeworld.common.testut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

public class TestCommUt {

    private static final Logger log = LoggerFactory.getLogger(TestCommUt.class);

    public static void waitUntilUrlIsConnectable(final String urlString, final int maxWaitTime) {
        long accumulatedWaitTime = 0;
        long waitTime = 1L;
        boolean urlIsConnectable = false;
        do {
            try {
                Thread.currentThread().sleep(waitTime);
            } catch (InterruptedException e) {
                log.warn("Exception received when sleeping waiting for connectability. Doing nothing, continuing...", e);
            }
            urlIsConnectable = TestCommUt.urlIsConnectable(urlString, (int) waitTime);
            accumulatedWaitTime += accumulatedWaitTime;
            waitTime = 2 * waitTime;
        } while(!urlIsConnectable && (accumulatedWaitTime <= maxWaitTime));
        if ((!urlIsConnectable) && (accumulatedWaitTime > maxWaitTime)) {
            throw new RuntimeException("maxWaitTime " + maxWaitTime + "ms is exceeded: " + accumulatedWaitTime + "ms");
        }
    }

    private static boolean urlIsConnectable(final String urlString, final int waitTime) {

        boolean reachable = false;
        boolean soapIsReachable = false;
        try {
            final URL myURL = new URL(urlString);
            final String host = myURL.getHost();
            reachable = InetAddress.getByName(host).isReachable(waitTime);
            if (reachable) {
                try {
                    final URLConnection myURLConnection = myURL.openConnection();
                    myURLConnection.connect();
                    soapIsReachable = true;
                } catch (IOException e) {
                    log.warn("Exception received when making a connection. Doing nothing, continuing...", e);
                }
            }
        } catch (IOException e) {
            log.warn("Exception received when generating an URL and/or making a connection. Doing nothing, continuing...", e);
        }
        log.debug("waitTime: " + waitTime + ", reachable: " + reachable+ ", soapIsReachable: " + soapIsReachable);

        return soapIsReachable;
    }
}
package com.azuqua.androidAPI;

import android.util.Log;

/**
 * Created by BALASASiDHAR on 25-May-15.
 */
public class Routes {

    // BASE URL
    public static String BASE_URL = "https://alphaapi.azuqua.com";
    public static boolean DEBUG_MODE = false;

    public static String protocol = "https";
    public static String host = "alphaapi.azuqua.com";
    public static int port = 443;


    /* Org Routes */

    // login & get orgs list
    public static final String LOGIN = "/org/login";

    // all flos
    public static final String ALL_FLOS = "/org/flos";


    /* Flo Routes */

    // Turn on a flo
    public static final String FLO_ENABLE = "/flo/:alias/enable";

    // Turn off a flo
    public static final String FLO_DISABLE = "/flo/:alias/disable";

    // Read a flo details
    public static final String FLO_READ = "/flo/:alias/read";

    // Run/Invoke a Flo
    public static final String FLO_RUN = "/flo/:alias/invoke";

    // Get a flo executions count
    public static final String FLO_GET_EXECUTION = "/flo/:id/executions";


    /* Gallery Routes */

    // Get popular flos
    public static final String GALLERY_POPULAR_FLOS = "/gallery/publishedflos/searchsearch/popular";

    // Get published flos
    public static final String GALLERY_PUBLISHED_FLOS = "/gallery/publishedflos";

    // Get recent flos
    public static final String GALLERY_RECENT_FLOS = "/gallery/publishedflos/search/recent";

    // Gallery search by tags
    public static final String GALLERY_SEARCH_BY_TAG = "/gallery/publishedflos/searchsearch/tags";

    // Gallery search by publisher
    public static final String GALLERY_SEARCH_BY_PUBLISHER = "/gallery/publishedflos/search/publisher";

    // Gallery search by description
    public static final String GALLERY_SEARCH_BY_DESCRIPTION = "/gallery/publishedflos/search";

    public static String getBaseURL() {
        BASE_URL = protocol+"://"+host+":"+port;
        Log.i("BASE_URL", BASE_URL);
        return BASE_URL;
    }


    public void Routes(String baseRoute, Boolean debug){
        BASE_URL = baseRoute;
        DEBUG_MODE = debug;
    }

    public void Routes(String protocol, String host, int port){
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public static void setProtocol(String protocol) {
        Routes.protocol = protocol;
    }

    public static void setHost(String host) {
        Routes.host = host;
    }

    public static void setPort(int port) {
        Routes.port = port;
    }

    public static String getProtocol() {
        return protocol;
    }

    public static String getHost() {
        return host;
    }

    public static int getPort() {
        return port;
    }
}

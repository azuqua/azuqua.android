package org.azuqua.android.api;

/**
 * Created by sasidhar on 07-Oct-15.
 */
public class Routes {

    /**
     * API Config
     */
    private static String protocol = "https";
    private static String host = "api.azuqua.com";
    private static int port = 443;

    /**
     * ORG
     */
    static final String ORG_LOGIN = "/login";
    static final String ORG_FLOS = "/org/flos?type=mobile";

    /**
     * FLO
     */
    public static final String FLO_READ = "/flo/:alias/read";
    static final String FLO_INVOKE = "/flo/:alias/invoke";
    static final String FLO_INJECT = "/flo/:alias/inject";
    public static final String FLO_ENABLE = "/flo/:alias/enable";
    public static final String FLO_DISABLE = "/flo/:alias/disable";
    public static final String FLO_EXECUTIONS_COUNT = "/flo/:alias/executions";
    static final String FLO_INPUTS = "/flo/:alias/inputs";

    /**
     * STORE
     */
    public static final String STORE_FLOS_PUBLISHED = "/gallery/publishedflos";
    public static final String STORE_FLOS_POPULAR = "/gallery/publishedflos/search/popular";
    public static final String STORE_FLOS_RECENT = "/gallery/publishedflos/search/recent";
    public static final String STORE_FLOS_SEARCH_BY_PUBLISHER = "/gallery/publishedflos/search/publisher";
    public static final String STORE_FLOS_SEARCH_BY_TAG = "/gallery/publishedflos/search/tags";
    public static final String STORE_FLOS_SEARCH_BY_DESCRIPTION = "/gallery/publishedflos/search";


    public Routes() {
        // empty constructor
    }

    public Routes(String protocol, String host, int port) {
        Routes.protocol = protocol;
        Routes.host = host;
        Routes.port = port;
    }

    public static void setProtocol(String protocol) {
        Routes.protocol = protocol;
    }

    public static void setPort(int port) {
        Routes.port = port;
    }

    public static void setHost(String host) {
        Routes.host = host;
    }

    static String getProtocol() {
        return protocol;
    }

    static String getHost() {
        return host;
    }

    static int getPort() {
        return port;
    }
}

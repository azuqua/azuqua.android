package org.azuqua.android.api;

/**
 * Created by sasidhar on 07-Oct-15.
 */
class Routes {

    /**
     * API Config
     */
    static String PROTOCOL = "https";
    static String HOST = "api.azuqua.com";
    static int PORT = 443;

    /**
     * ORG
     */
    static final String ORG_LOGIN = "/login";
    static final String ORG_FLOS = "/org/flos?type=mobile";

    /**
     * FLO
     */

    static final String FLO_INVOKE = "/flo/:alias/invoke";
    static final String FLO_INJECT = "/flo/:alias/inject";
    static final String FLO_INPUTS = "/flo/:alias/inputs";
    static final String FLO_OUTPUTS = "/flo/:alias/outputs";



//    public Routes() {
//        // empty constructor
//    }
//
//    public Routes(String protocol, String host, int port) {
//        Routes.protocol = protocol;
//        Routes.host = host;
//        Routes.port = port;
//    }
//
//    public static void setProtocol(String protocol) {
//        Routes.protocol = protocol;
//    }
//
//    public static void setPort(int port) {
//        Routes.port = port;
//    }
//
//    public static void setHost(String host) {
//        Routes.host = host;
//    }
//
//    static String getProtocol() {
//        return protocol;
//    }
//
//    static String getHost() {
//        return host;
//    }
//
//    static int getPort() {
//        return port;
//    }
}

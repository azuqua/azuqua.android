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
    static String ORG_FLOS = "/org/flos?org_id=:org_id&channel_key=azuquamobile";

    /**
     * FLO
     */

    static final String FLO_INVOKE = "/flo/:alias/invoke";
    static final String FLO_INJECT = "/flo/:alias/inject";
    static final String FLO_INPUTS = "/flo/:alias/inputs";
    static final String FLO_OUTPUTS = "/flo/:alias/outputs";


    public Routes() {
        // empty constructor
    }

    public Routes(String protocol, String host, int port) {
        Routes.PROTOCOL = protocol;
        Routes.HOST = host;
        Routes.PORT = port;
    }

    public static void setProtocol(String protocol) {
        Routes.PROTOCOL = protocol;
    }

    public static void setPort(int port) {
        Routes.PORT = port;
    }

    public static void setHost(String host) {
        Routes.HOST = host;
    }

    static String getProtocol() {
        return PROTOCOL;
    }

    static String getHost() {
        return HOST;
    }

    static int getPort() {
        return PORT;
    }

}

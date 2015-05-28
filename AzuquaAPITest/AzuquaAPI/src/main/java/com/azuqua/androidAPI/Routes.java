package com.azuqua.androidAPI;

/**
 * Created by BALASASiDHAR on 25-May-15.
 */
public class Routes {

    // BASE URL
    public static String BASE_URL = "http://devapi2.azuqua.com";
    public static boolean DEBUG_MODE = true;

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


    public Routes(String baseRoute, Boolean debug){
        BASE_URL = baseRoute;
        DEBUG_MODE = debug;
    }


}

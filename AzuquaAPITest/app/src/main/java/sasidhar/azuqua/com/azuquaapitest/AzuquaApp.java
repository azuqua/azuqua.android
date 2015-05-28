package sasidhar.azuqua.com.azuquaapitest;

import android.app.Application;

import com.azuqua.androidAPI.Azuqua;
import com.azuqua.androidAPI.model.Flo;
import com.azuqua.androidAPI.model.Org;

import java.util.Collection;

/**
 * Created by BALASASiDHAR on 26-May-15.
 */
public class AzuquaApp extends Application {

    private static Azuqua azuqua;

    private static Collection<Org> orgsCollection;
    private static Collection<Flo> floCollection;
    private static Org currentOrg;
    private static Flo currentFlo;

    public static Azuqua getAzuqua(){
        if(azuqua == null)
            azuqua = new Azuqua();
        return azuqua;
    }

    public static void setOrgsCollection(Collection<Org> orgsCollection) {
        AzuquaApp.orgsCollection = orgsCollection;
    }

    public static Collection<Org> getOrgsCollection() {
        return orgsCollection;
    }


    public static void setFloCollection(Collection<Flo> floCollection) {
        AzuquaApp.floCollection = floCollection;
    }

    public static Collection<Flo> getFloCollection() {
        return floCollection;
    }

    public static void setCurrentOrg(Org currentOrg) {
        AzuquaApp.currentOrg = currentOrg;
    }

    public static Org getCurrentOrg() {
        return currentOrg;
    }

    public static void setCurrentFlo(Flo currentFlo) {
        AzuquaApp.currentFlo = currentFlo;
    }

    public static Flo getCurrentFlo() {
        return currentFlo;
    }
}

package sasidhar.azuqua.com.azuquaapitest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private DataBaseHandler db;
    private SharedPreferences preferences;
    private Editor editor;

    private static String PREFERENCES_NAME = "CONFIG_DETAILS";
    private static String APP_CONFIG = "APP_CONFIGURED";

    private static String[] API_TEST_CASES = {"Sign in","Get All Flos", "Read a Flo", "Invoke a Flo", "Enable a Flo", "Disable a Flo", "Get Flo Execution Count"};

    private ListView testCases;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DataBaseHandler(this);
        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        boolean isAppConfigured = preferences.getBoolean(APP_CONFIG, false);
        if(!isAppConfigured){
            configApp();
        }

        testCases = (ListView) findViewById(R.id.listOfTestCases);
        adapter = new ArrayAdapter(MainActivity.this, R.layout.list_background, API_TEST_CASES);

        testCases.setAdapter(adapter);

        testCases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ""+API_TEST_CASES[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configApp() {
        db.addAPI(new APIList("DevAPI-2","http","devapi2.azuqua.com",80));
        db.addAPI(new APIList("Production","https","api.azuqua.com",443));

        editor = preferences.edit();
        editor.putBoolean(APP_CONFIG, true);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, APIConfigurationsListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

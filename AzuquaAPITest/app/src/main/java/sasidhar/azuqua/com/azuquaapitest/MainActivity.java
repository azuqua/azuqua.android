package sasidhar.azuqua.com.azuquaapitest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.azuqua.androidAPI.Azuqua;
import com.azuqua.androidAPI.AzuquaOrgRequest;
import com.azuqua.androidAPI.model.Org;

import java.util.Collection;


public class MainActivity extends ActionBarActivity {

    private DataBaseHandler db;
    private SharedPreferences preferences;
    private Editor editor;

    private static String PREFERENCES_NAME = "CONFIG_DETAILS";
    private static String APP_CONFIG = "APP_CONFIGURED";

    private Button loginButton;
    private EditText emailHolder, passwordHolder;

    private Azuqua azuqua;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        azuqua = AzuquaApp.getAzuqua();

        db = new DataBaseHandler(this);
        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        boolean isAppConfigured = preferences.getBoolean(APP_CONFIG, false);
        if(!isAppConfigured){
            configApp();
        }

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Signing in");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);

        loginButton = (Button) findViewById(R.id.buttonLogin);
        emailHolder = (EditText) findViewById(R.id.emailId);
        passwordHolder = (EditText) findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String username = emailHolder.getText().toString();
                String password = passwordHolder.getText().toString();

                azuqua.login(username, password, new AzuquaOrgRequest() {
                    @Override
                    public void onResponse(Collection<Org> orgsCollection) {
                        AzuquaApp.setOrgsCollection(orgsCollection);
                        progressDialog.cancel();
                        startActivity(new Intent(MainActivity.this, DashBoardActivity.class));
                        finish();
                    }

                    @Override
                    public void onErrorResponse(String error) {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void configApp() {
        db.addAPI(new APIList("DevAPI-2","http","devapi2.azuqua.com",80,"true"));
        db.addAPI(new APIList("Production","https","api.azuqua.com",443,"false"));

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

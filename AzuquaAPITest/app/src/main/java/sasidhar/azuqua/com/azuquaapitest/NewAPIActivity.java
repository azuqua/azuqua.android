package sasidhar.azuqua.com.azuquaapitest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class NewAPIActivity extends ActionBarActivity {

    private EditText name, host, port;
    private Spinner protocol, debug;

    private String[] _protocol_list = {"http","https"}, _debug_list = {"True", "False"};
    private ArrayAdapter protocolAdapter, debugAdapter;

    private String apiName=null, apiProtocol=null, apiHost=null, apiPort=null, apiDebug=null;

    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_config);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DataBaseHandler(getApplicationContext());

        name = (EditText) findViewById(R.id.configName);
        host = (EditText) findViewById(R.id.hostName);
        port = (EditText) findViewById(R.id.portNumber);

        protocol = (Spinner) findViewById(R.id.spinnerProtocol);
        debug = (Spinner) findViewById(R.id.spinnerDebug);

        protocolAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, _protocol_list);
        debugAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, _debug_list);

        protocol.setAdapter(protocolAdapter);
        debug.setAdapter(debugAdapter);

        protocol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                apiProtocol = _protocol_list[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        debug.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                apiDebug = _debug_list[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_api, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            apiName = name.getText().toString();
            apiHost = host.getText().toString();
            apiPort = port.getText().toString();

            if(apiName.length() == 0){
                name.setError("Please enter name");
            }else if(apiHost.length() == 0){
                host.setError("Please enter host");
            }else if(port.length() == 0){
                port.setError("Please enter port number");
            }else {
                db.addAPI(new APIList(apiName,apiProtocol,apiHost,Integer.parseInt(apiPort),apiDebug));
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

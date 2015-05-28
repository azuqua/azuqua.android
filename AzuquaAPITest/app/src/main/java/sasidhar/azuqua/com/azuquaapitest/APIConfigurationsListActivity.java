package sasidhar.azuqua.com.azuquaapitest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class APIConfigurationsListActivity extends ActionBarActivity {

    private List<APIList> apiLists = Collections.emptyList();
    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiconfigurations_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DataBaseHandler(this);
        listView = (ListView) findViewById(R.id.listOfAPIs);
        registerForContextMenu(listView);

        list = new ArrayList<>();

        createAPIList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void createAPIList() {
        if(list.size() > 0)
            list.clear();
        apiLists = db.getAllAPIs();

        for(APIList api : apiLists)
            list.add(api.getName());

        adapter = new ArrayAdapter(APIConfigurationsListActivity.this, android.R.layout.simple_list_item_single_choice, list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apiconfigurations_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            startActivity(new Intent(APIConfigurationsListActivity.this,NewAPIActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle("Choose Action");
        menu.add(Menu.NONE, 0, Menu.NONE, "Edit");
        menu.add(Menu.NONE, 1, Menu.NONE, "Delete");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Log.i("Item Id : ", info.position + " " + item.getItemId());
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createAPIList();
    }
}

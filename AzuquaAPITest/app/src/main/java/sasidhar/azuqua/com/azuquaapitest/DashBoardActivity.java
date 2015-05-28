package sasidhar.azuqua.com.azuquaapitest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.azuqua.androidAPI.Azuqua;
import com.azuqua.androidAPI.AzuquaAllFlosRequest;
import com.azuqua.androidAPI.model.Flo;
import com.azuqua.androidAPI.model.Org;
import com.azuqua.androidAPI.model.Orgs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class DashBoardActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener, FlosAdapter.RecyclerClickListener {

    private Azuqua azuqua;

    private Spinner orgsSpinner;
    private RecyclerView recyclerView;

    private List<Org> orgsList;
    private List<Flo> flosList;
    private List<String> orgsName, flosName;

    private ArrayAdapter adapterOrgs;
    private FlosAdapter adapterFlos;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        azuqua = AzuquaApp.getAzuqua();

        orgsSpinner = (Spinner) findViewById(R.id.spinnerOrgs);
        orgsList = new ArrayList<>();
        orgsName = new ArrayList<>();

        flosList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.flosList);

        progressDialog = new ProgressDialog(DashBoardActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        for(Org org : AzuquaApp.getOrgsCollection()){
            orgsList.add(org);
            orgsName.add(org.getName());
        }

        adapterOrgs = new ArrayAdapter(DashBoardActivity.this, android.R.layout.simple_spinner_dropdown_item, orgsName);
        orgsSpinner.setAdapter(adapterOrgs);

        orgsSpinner.setOnItemSelectedListener(this);

    }

    // Spinner Item Selected Listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        AzuquaApp.setCurrentOrg(orgsList.get(position));
        switchOrg();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Switching between orgs

    private void switchOrg() {
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
        Org org = AzuquaApp.getCurrentOrg();
        azuqua.setAccessKey(org.getaccess_key());
        azuqua.setAccessSecret(org.getaccess_secret());

        flosList.clear();

        azuqua.getFlos(new AzuquaAllFlosRequest() {
            @Override
            public void onResponse(Collection<Flo> flosCollection) {
                AzuquaApp.setFloCollection(flosCollection);
                generateFloList();
            }

            @Override
            public void onErrorResponse(String error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error : "+error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    // generates a list of flos to display in list view
    private void generateFloList() {
        for(Flo flo : AzuquaApp.getFloCollection()){
            flosList.add(flo);
        }

        adapterFlos = new FlosAdapter(DashBoardActivity.this, flosList);
        adapterFlos.setClickListener(this);
        recyclerView.setAdapter(adapterFlos);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashBoardActivity.this));

        progressDialog.dismiss();
    }

    @Override
    public void itemClicked(View view, int position) {
        Flo flo = flosList.get(position);
        AzuquaApp.setCurrentFlo(flo);
        startActivity(new Intent(DashBoardActivity.this, FloActivity.class));
    }
}

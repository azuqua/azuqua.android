package sasidhar.azuqua.com.azuquaapitest;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.AlertDialog;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.azuqua.androidAPI.AsyncResponse;
import com.azuqua.androidAPI.Azuqua;
import com.azuqua.androidAPI.AzuquaFloRequest;
import com.azuqua.androidAPI.model.Flo;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FloActivity extends ActionBarActivity implements CompoundButton.OnCheckedChangeListener {

    private Azuqua azuqua;
    private Flo flo;
    private TextView floName, floDescription, floVersion, floStatus, floCreated, floUpdated;
    private Switch floControl;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder dialog, alert;

    private AzuquaWidgets widgets;
    private List<EditText> inputViews;
    private Button buttonRunFlo;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flo);

        progressDialog = new ProgressDialog(FloActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        azuqua = AzuquaApp.getAzuqua();
        flo = AzuquaApp.getCurrentFlo();
        widgets = new AzuquaWidgets(FloActivity.this);
        inputViews = new ArrayList<>();

        buttonRunFlo = (Button) findViewById(R.id.buttonRunFlo);
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        getSupportActionBar().setTitle(flo.getName());

        floName = (TextView) findViewById(R.id.floName);
        floDescription = (TextView) findViewById(R.id.floDescription);
        floVersion = (TextView) findViewById(R.id.floVersion);;
        floStatus =(TextView) findViewById(R.id.floStatus);
        floCreated = (TextView) findViewById(R.id.floCreated);
        floUpdated = (TextView) findViewById(R.id.floUpdated);

        floControl = (Switch) findViewById(R.id.floControl);

        flo.read(new AzuquaFloRequest() {
            @Override
            public void onResponse(Flo floData) {

                floName.setText(floData.getName());
                floDescription.setText(floData.getDescription());
                String status = floData.getActive() ? "On" : "Off";
                floStatus.setText(status);
                floControl.setChecked(floData.getActive());
                floVersion.setText(floData.getVersion());
                floCreated.setText(generateTime(floData.getCreated()));
                floUpdated.setText(generateTime(floData.getUpdated()));

                progressDialog.dismiss();
                floControl.setOnCheckedChangeListener(FloActivity.this);
            }

            @Override
            public void onErrorResponse(String error) {
                Toast.makeText(getApplicationContext(), "Flo Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        alert = new AlertDialog.Builder(this);
        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Inputs for Flo to run");
        dialog.setPositiveButton("Run", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.show();
                String[] inputs = flo.getInputs();
                Map<String, String> params = new HashMap<String, String>();

                for (int i = 0; i < inputs.length; i++) {
                    String value = inputViews.get(i).getText().toString();
                    params.put(inputs[i], value);
                }

                JSONObject inputParams = new JSONObject(params);

                try {
                    flo.invoke(inputParams.toString(), new AsyncResponse() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Flo Response", response);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onErrorResponse(String error) {
                            progressDialog.dismiss();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Flo Cancel", "Cancel to run a flo");
            }
        });

        buttonRunFlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] inputs = flo.getInputs();
                if(inputs.length == 0){
                    Toast.makeText(getApplicationContext(),"No Inputs to run this flo.", Toast.LENGTH_SHORT).show();
                }else if(!flo.getActive()){
                    Toast.makeText(getApplicationContext(),"Please Turn on flo and run.", Toast.LENGTH_SHORT).show();
                }else{
                    RunFloFragment runFloFragment = new RunFloFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(android.R.id.content, runFloFragment).commit();
                }
            }
        });
    }

    private String generateTime(String floDate) {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = null;
        try {
            date = format.parse(floDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
        String dateGenerated = simpleDateFormat.format(date);

        return dateGenerated;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        progressDialog.show();
        if(flo.getActive()){
            flo.disable(new AzuquaFloRequest() {
                @Override
                public void onResponse(Flo floData) {
                    if(!floData.getActive())
                        floStatus.setText("Off");
                    flo.setActive(floData.getActive());
                    progressDialog.dismiss();
                }

                @Override
                public void onErrorResponse(String error) {

                }
            });
        }else {
            flo.enable(new AzuquaFloRequest() {
                @Override
                public void onResponse(Flo floData) {
                    if(floData.getActive())
                        floStatus.setText("On");
                    flo.setActive(floData.getActive());
                    progressDialog.dismiss();
                }

                @Override
                public void onErrorResponse(String error) {

                }
            });
        }
    }
}

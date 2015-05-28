package sasidhar.azuqua.com.azuquaapitest;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.azuqua.androidAPI.AsyncResponse;
import com.azuqua.androidAPI.Azuqua;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RunFloFragment extends Fragment {

    private LinearLayout linearLayout;
    private Button invokeFlo;
    private List<EditText> allEditTexts;
    private InputMethodManager imm;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alert;

    private AzuquaWidgets widgets;
    String[] inputs;

    public RunFloFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_run_flo, container, false);
        linearLayout = (LinearLayout) layout.findViewById(R.id.floInputs);
        invokeFlo = (Button) layout.findViewById(R.id.buttonInvoke);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);

        alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Flo result");

        imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        allEditTexts = new ArrayList<>();
        widgets = new AzuquaWidgets(getActivity());

        inputs = AzuquaApp.getCurrentFlo().getInputs();

        for(int i=0; i < inputs.length; i++){
            String input = inputs[i];
            EditText view = widgets.createEditText(input);
            linearLayout.addView(view);
            allEditTexts.add(view);
        }

        invokeFlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                progressDialog.show();
                Map<String,String> params = new HashMap<String, String>();

                for(int i=0; i<inputs.length;i++ ){
                    String value = allEditTexts.get(i).getText().toString();
                    params.put(inputs[i],value);
                }

                final JSONObject object = new JSONObject(params);
                try{
                    AzuquaApp.getCurrentFlo().invoke(object.toString(), new AsyncResponse() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject objectData = null ;
                            String result = "";
                            try {
                                objectData = new JSONObject(response);
                                if(objectData != null){
                                    JSONObject data = objectData.getJSONObject("data");
                                    Iterator keys = data.keys();
                                    while (keys.hasNext()){
                                        String key = (String)keys.next();
                                        result+=key+" : ";
                                        String value = data.getString(key);
                                        result+=value+"\n";
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            alert.setMessage(result);
                            progressDialog.dismiss();
                            alert.show();
                        }

                        @Override
                        public void onErrorResponse(String error) {

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        alert.setPositiveButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });

        return layout;
    }
}

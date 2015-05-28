package sasidhar.azuqua.com.azuquaapitest;

import android.content.Context;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by BALASASiDHAR on 28-May-15.
 */
public class AzuquaWidgets {

    private static Context context;

    public AzuquaWidgets(Context context){
        this.context = context;
    }

    public EditText createEditText(String input){
        EditText editText = new EditText(context);
        editText.setHint(input.toLowerCase());

        LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        params.setMargins(10, 10, 10, 10);
        editText.setLayoutParams(params);

        if(input.contains("number") || input.contains("mobile") || input.contains("age") || input.contains("cell")){
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
        }else if(input.contains("date")){
            editText.setInputType(InputType.TYPE_CLASS_DATETIME);
        }
        else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        return editText;
    }
}

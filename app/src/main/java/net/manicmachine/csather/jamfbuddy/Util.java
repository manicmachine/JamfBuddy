package net.manicmachine.csather.jamfbuddy;

import android.content.Context;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Util {

    // Determine if EditText field is empty or not.
    public static boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }


    // Generate a toast to display the error with centered text.
    public static void displayError(Context context, String error) {
        Toast errorMessage = Toast.makeText(context, (CharSequence) error, Toast.LENGTH_LONG);

        LinearLayout layout = (LinearLayout) errorMessage.getView();
        if (layout.getChildCount() > 0) {
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        }

        errorMessage.show();
    }

}

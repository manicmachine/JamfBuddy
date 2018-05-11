package net.manicmachine.csather.jamfbuddy;

import android.widget.EditText;

public class Util {

    public static boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

}

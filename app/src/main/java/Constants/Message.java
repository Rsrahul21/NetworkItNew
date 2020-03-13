package Constants;

import android.content.Context;
import android.widget.Toast;

public class Message {

    public static void toast(Context context , String message){
        Toast.makeText(context, message,Toast.LENGTH_LONG ).show();
    }

}

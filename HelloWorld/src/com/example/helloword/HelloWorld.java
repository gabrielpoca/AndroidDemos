package com.example.helloword;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HelloWorld extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loading the layout over the resource reference
        setContentView(R.layout.main);

        //get the two controls we created earlier, also with the resource reference and the id
        final TextView tv_View = (TextView) findViewById(R.id.tv_View);
        final EditText et_Text = (EditText) findViewById(R.id.et_Text);

        //add new KeyListener Callback (to record key input)
        et_Text.setOnKeyListener(new OnKeyListener() {
            //function to invoke when a key is pressed

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //check if there is 
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //check if the right key was pressed
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        //add the text to the textview
                        tv_View.setText(tv_View.getText() + ", "
                                + et_Text.getText());
                        //and clear the EditText control
                        et_Text.setText("");
                        return true;
                    }
                }
                return false;
            }

        });
    }
}

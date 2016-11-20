package appewtc.masterung.myfriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private Button signInButton, signUpButton;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);

        //SignIn Controller
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Value
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if ((userString.length() == 0) || (passwordString.length() == 0)) {
                    MyAlert myAlert = new MyAlert(MainActivity.this,
                            R.drawable.kon48, "มีช่องว่าง", "กรอกทุกชอง คะ");
                    myAlert.myDialog();
                } else {
                    checkUserPassword();
                }

            }   // onClick
        });


        //SignUp Controller
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

    }   // Main Method

    private void checkUserPassword() {

        String strResult = null, strTruePassword = null;
        boolean bolStatus = true;

        try {

            GetUser getUser = new GetUser(MainActivity.this);
            getUser.execute();
            strResult = getUser.get();
            Log.d("20novV3", "strResult ==> " + strResult);

            JSONArray jsonArray = new JSONArray(strResult);

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (userString.equals(jsonObject.getString("User"))) {

                    bolStatus = false;
                    strTruePassword = jsonObject.getString("Password");

                }
            }   // for

            if (bolStatus) {
                MyAlert myAlert = new MyAlert(MainActivity.this,
                        R.drawable.rat48, "User False", "No this User in my Database");
                myAlert.myDialog();
            } else if (passwordString.equals(strTruePassword)) {

                startActivity(new Intent(MainActivity.this, FriendListView.class));

            } else {
                MyAlert myAlert = new MyAlert(MainActivity.this,
                        R.drawable.rat48, "Password False", "Please Try Again");
                myAlert.myDialog();
            }



        } catch (Exception e) {
            Log.d("20novV3", "e checkUser ==> " + e.toString());
        }

    }   // checkUser

}   // Main Class

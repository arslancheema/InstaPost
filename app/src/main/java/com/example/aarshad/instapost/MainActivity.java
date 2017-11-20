package com.example.aarshad.instapost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText userNameEditText ;
    EditText passwordEditText ;
    TextView alreadyMemberTextView ;
    Button signUpInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("InstaPost");

        userNameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        alreadyMemberTextView = (TextView) findViewById(R.id.alreadyMemberTextView);
        signUpInButton = (Button) findViewById(R.id.signUpInButton);

        ParseUser user = ParseUser.getCurrentUser();
        if (user != null){
            Log.i("isAuthenticated", "Authenticated ");
            showUsersList();
        }
        // On Enter press, proceed the functionality
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    signUpButtonClicked(view);
                }
                return false;
            }
        });
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }

    public void signUpButtonClicked (View view) {

        if (userNameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        } else {

            String username = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(password);

            if (signUpInButton.getText().toString().matches("Sign Up")) {

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("signUpInBackground", "SignUp Successful ");
                           showUsersList();
                        } else {
                            Log.i("signUpInBackground", "SignUp Unsuccessful " + e.toString());
                            Toast.makeText(MainActivity.this, "SignUp Failed ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                user.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user!=null){
                            Log.i("logInInBackground", "Login Successful ");
                            showUsersList();
                        } else {
                            Log.i("logInInBackground", "Login Failed " + e.toString());
                            Toast.makeText(MainActivity.this, "Login Failed. Check your credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }

    }

    private void showUsersList (){
        Intent usersActivity = new Intent(MainActivity.this, UsersListActivity.class);
        startActivity(usersActivity);
    }

    public void alreadyMemberTextViewClicked (View view){
        if (signUpInButton.getText().toString().matches("Sign Up")){
            signUpInButton.setText("Sign In");
            alreadyMemberTextView.setText("Not Member ? Sign Up");
        } else {
            signUpInButton.setText("Sign Up");
            alreadyMemberTextView.setText("Member ? Sign In");
        }
    }


}

package com.example.aarshad.instapost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {


    ArrayList<String> usersList = new ArrayList<>();
    ListView usersListView ;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        usersListView = (ListView) findViewById(R.id.usersListView);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,usersList);
        usersListView.setAdapter(arrayAdapter);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.isAuthenticated()) {
            Toast.makeText(this, "User Is Logged In", Toast.LENGTH_SHORT).show();
        } else {
            Intent signUpIntent = new Intent(this, MainActivity.class );
            startActivity(signUpIntent);
        }

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size()>0) {
                        for (int i = 0; i < objects.size(); i++) {
                            Log.i("findInBackground", objects.get(i).getString("username"));
                            usersList.add(objects.get(i).getString("username"));
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }

                } else {
                    Log.i("findInBackground", "Couldn't get users " + e.toString());
                }
            }
        });



    }
}

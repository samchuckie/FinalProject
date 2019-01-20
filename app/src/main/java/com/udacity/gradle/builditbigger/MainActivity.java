package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jokeractivity.JokerMainActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public static final String JOKE_EXTRA = "joke_extra";
    public String testingString ;
    @VisibleForTesting
    public  String getExtraname()
    {
        return testingString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        Toast.makeText(this , "Clicked",Toast.LENGTH_SHORT).show();
        new JokesAsyncTask().execute();

    }
    class JokesAsyncTask extends AsyncTask<Void, Void, String> {
        private MyApi myApi = null;
        @Override
        protected String doInBackground(Void... voids) {
            if(myApi==null){
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            }
                        });
                myApi= builder.build();
                try {
                    return myApi.sayHi("joke").execute().getData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Log.e("sam","Joke is "+s);
                testingString = "not null";
                Intent intent= new Intent(getApplicationContext(), JokerMainActivity.class);
                intent.putExtra(JOKE_EXTRA,s);
                startActivity(intent);

            }
            else
            {
                Log.e("sam", "No string exist");
            }

            super.onPostExecute(s);
        }
    }

}

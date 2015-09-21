package br.com.thiengo.parsenotificationexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.thiengo.parsenotificationexample.adapter.GameAdapter;
import br.com.thiengo.parsenotificationexample.domain.Game;
import br.com.thiengo.parsenotificationexample.util.Pref;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "LOG";
    public static final String LIST_KEY = "br.com.thiengo.parsenotificationexample.MainActivity.LIST_KEY";

    private TextView tvUpdate;

    private RecyclerView mRecyclerView;
    private GameAdapter mAdapter;
    private ArrayList<Game> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("uniqueId", installation.getInstallationId() );
        installation.saveInBackground();

        ParsePush.subscribeInBackground("all-games");


        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvUpdate = (TextView) findViewById(R.id.tv_update);
        mList = new ArrayList<>();

        // RECYCLER VIEW CONF
            mRecyclerView = (RecyclerView) findViewById(R.id.rv_games);
            mRecyclerView.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager( this );
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(llm);

            mAdapter = new GameAdapter(this, mList);
            mRecyclerView.setAdapter(mAdapter);


        if( savedInstanceState != null ){
            ArrayList<Game> auxList = savedInstanceState.getParcelableArrayList( LIST_KEY );

            if( auxList != null ){
                mList.addAll( auxList );
                updateTextViewDate();
            }
        }
        else{
            String data = Pref.retrievePrefKeyValue( getApplicationContext(),
                    Pref.PREF_KEY_DATA,
                    null);

            try{
                Gson gson = new Gson();
                ArrayList<Game> games = new ArrayList<>();
                JSONObject jsonObject = new JSONObject( data );
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("games");

                for( int i = 0, tamI = jsonArray.length(); i < tamI; i++ ){
                    Game g = gson.fromJson( jsonArray.getJSONObject( i ).toString(), Game.class );
                    games.add( g );
                }
                processDataToList( games );
            }
            catch( Exception e ){
                Log.i(TAG, "Error: " + e.getMessage());
            }
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList( LIST_KEY, mList );
        super.onSaveInstanceState(outState);
    }


    private void processDataToList( ArrayList<Game> games ){
        mList.clear();
        mList.addAll(games);
        mAdapter.notifyDataSetChanged();
        updateTextViewDate();
    }


    private void updateTextViewDate(){
        if( mList.isEmpty() ){
            tvUpdate.setText( "Nenhum game iniciado" );
        }
        else{
            Calendar c = Calendar.getInstance();
            String date = fixDate( c.get(Calendar.DAY_OF_MONTH ) )+"/";
            date += fixDate( c.get(Calendar.MONTH) )+"/";
            date += c.get(Calendar.YEAR)+" às ";
            date += fixDate( c.get(Calendar.HOUR_OF_DAY) )+"h";
            date += fixDate(c.get(Calendar.MINUTE));

            tvUpdate.setText(Html.fromHtml( "<b>LU:</b> "+date ) );
        }
    }


    private String fixDate( int d ){
        return( d < 10 ? "0"+d : ""+d );
    }


    // LISTENER
        public void onEvent( final ArrayList<Game> games ){
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    processDataToList(games);
                }
            });
        }
}

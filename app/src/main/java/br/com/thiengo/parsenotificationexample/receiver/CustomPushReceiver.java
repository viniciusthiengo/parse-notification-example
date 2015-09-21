package br.com.thiengo.parsenotificationexample.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.thiengo.parsenotificationexample.R;
import br.com.thiengo.parsenotificationexample.domain.Game;
import br.com.thiengo.parsenotificationexample.util.Pref;
import br.com.thiengo.parsenotificationexample.util.Util;
import de.greenrobot.event.EventBus;

/**
 * Created by viniciusthiengo on 9/21/15.
 */
public class CustomPushReceiver extends ParsePushBroadcastReceiver {
    private static final String TAG = "LOG";


    @Override
    protected int getSmallIconId(Context context, Intent intent) {
        return(R.drawable.ic_own );
        //return super.getSmallIconId(context, intent);
    }


    @Override
    protected void onPushReceive(Context context, Intent intent) {

        if( intent == null ){
            return;
        }

        Pref.savePrefKeyValue(context.getApplicationContext(),
                Pref.PREF_KEY_DATA,
                intent.getExtras().getString("com.parse.Data"));

        if( Util.isMyApplicationTaskOnTop( context ) ){
            try{
                Gson gson = new Gson();
                ArrayList<Game> games = new ArrayList<>();
                JSONObject jsonObject = new JSONObject( intent.getExtras().getString("com.parse.Data") );
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("games");

                for( int i = 0, tamI = jsonArray.length(); i < tamI; i++ ){
                    Game g = gson.fromJson( jsonArray.getJSONObject( i ).toString(), Game.class );
                    games.add( g );
                }

                EventBus.getDefault().post( games );
            }
            catch( Exception e ){
                Log.i(TAG, "Error: " + e.getMessage());
            }
        }
        else{
            super.onPushReceive(context, intent);
        }
    }
}

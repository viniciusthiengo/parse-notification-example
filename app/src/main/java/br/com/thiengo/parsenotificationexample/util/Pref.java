package br.com.thiengo.parsenotificationexample.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by viniciusthiengo on 8/30/15.
 */
public class Pref {
    public static final String PREF_KEY_DATA = "br.com.thiengo.parsenotificationexample.util.Pref.PREF_KEY_DATA";

    public static void savePrefKeyValue( Context context, String key, String value ){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor e = sp.edit();
        e.putString( key, value );
        e.apply();
    }
    public static String retrievePrefKeyValue( Context context, String key, String... defaultValue ){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences( context );
        String dValue = defaultValue != null && defaultValue.length > 0 ? defaultValue[0] : "";
        sp.getString(key, dValue );
        return( sp.getString( key, dValue ) );
    }
}

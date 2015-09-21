package br.com.thiengo.parsenotificationexample.application;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

import br.com.thiengo.parsenotificationexample.util.Configuration;

/**
 * Created by viniciusthiengo on 9/20/15.
 */
public class ParseNotificationExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, Configuration.APPLICATION_ID, Configuration.CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}

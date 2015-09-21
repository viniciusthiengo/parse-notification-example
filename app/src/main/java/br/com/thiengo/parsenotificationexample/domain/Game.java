package br.com.thiengo.parsenotificationexample.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by viniciusthiengo on 9/21/15.
 */
public class Game implements Parcelable {
    private SoccerTeam home;
    private SoccerTeam guest;

    public SoccerTeam getHome() {
        return home;
    }

    public void setHome(SoccerTeam home) {
        this.home = home;
    }

    public SoccerTeam getGuest() {
        return guest;
    }

    public void setGuest(SoccerTeam guest) {
        this.guest = guest;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.home, 0);
        dest.writeParcelable(this.guest, 0);
    }

    public Game() {
    }

    protected Game(Parcel in) {
        this.home = in.readParcelable(SoccerTeam.class.getClassLoader());
        this.guest = in.readParcelable(SoccerTeam.class.getClassLoader());
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}

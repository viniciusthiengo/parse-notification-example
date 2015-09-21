package br.com.thiengo.parsenotificationexample.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by viniciusthiengo on 9/21/15.
 */
public class SoccerTeam implements Parcelable {
    private String name;
    private String urlLogo;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.urlLogo);
        dest.writeInt(this.score);
    }

    public SoccerTeam() {
    }

    protected SoccerTeam(Parcel in) {
        this.name = in.readString();
        this.urlLogo = in.readString();
        this.score = in.readInt();
    }

    public static final Parcelable.Creator<SoccerTeam> CREATOR = new Parcelable.Creator<SoccerTeam>() {
        public SoccerTeam createFromParcel(Parcel source) {
            return new SoccerTeam(source);
        }

        public SoccerTeam[] newArray(int size) {
            return new SoccerTeam[size];
        }
    };
}

package br.com.thiengo.parsenotificationexample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.thiengo.parsenotificationexample.R;
import br.com.thiengo.parsenotificationexample.domain.Game;

/**
 * Created by viniciusthiengo on 4/5/15.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {
    private Context mContext;
    private List<Game> mList;
    private LayoutInflater mLayoutInflater;


    public GameAdapter(Context c, List<Game> l){
        mContext = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        v = mLayoutInflater.inflate(R.layout.iten, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        Game g = mList.get(position);

        myViewHolder.tvScore.setText( String.format("%d x %d", g.getHome().getScore(), g.getGuest().getScore()) );
        myViewHolder.tvHome.setText( g.getHome().getName() );
        myViewHolder.tvGuest.setText(g.getGuest().getName());

        Picasso.with(mContext).load( g.getHome().getUrlLogo() ).into(myViewHolder.ivHome);
        Picasso.with(mContext).load( g.getGuest().getUrlLogo() ).into(myViewHolder.ivGuest);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvScore;
        public TextView tvHome;
        public TextView tvGuest;

        public ImageView ivHome;
        public ImageView ivGuest;


        public MyViewHolder(View itemView) {
            super(itemView);

            tvScore = (TextView) itemView.findViewById(R.id.tv_score);
            tvHome = (TextView) itemView.findViewById(R.id.tv_home);
            tvGuest = (TextView) itemView.findViewById(R.id.tv_guest);

            ivHome = (ImageView) itemView.findViewById(R.id.iv_home);
            ivGuest = (ImageView) itemView.findViewById(R.id.iv_guest);
        }
    }
}

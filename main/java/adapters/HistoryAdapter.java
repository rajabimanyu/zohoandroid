package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kct.contacts.R;

import java.util.List;

import data.History;

/**
 * Created by Rinik on 13/10/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<History> historyList;
    private Context context;
    private Activity activity;
    public HistoryAdapter(List<History> historyList, Context context, Activity activity) {
        this.historyList= historyList;
        this.context = context;
        this.activity=activity;
    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_log, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.MyViewHolder holder, int position) {
        History history= historyList.get(position);
        holder.name.setText(history.name);

        if(history.isSaved){
            holder.name.setText(history.name);
        }else {
            holder.name.setText(history.phone);
        }
        holder.time.setText(history.time);
        switch (history.type){
            case 0:holder.type.setText("Outgoing");holder.type.setTextColor(Color.BLUE);break;
            case 1:holder.type.setText("Incoming");holder.type.setTextColor(Color.GREEN);break;
            case 2:holder.type.setText("Missed");holder.type.setTextColor(Color.RED);break;
        }

        if (history.isHaving) {
            Log.d("PATH::",history.path);
            holder.profile.setImageURI(history.path);
        }
    }

    public void refresh(List<History> historyList) {
        //this.historyList.clear();
        this.historyList=historyList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,type,time;
        public SimpleDraweeView profile;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            type = (TextView) view.findViewById(R.id.type);
            time= (TextView) view.findViewById(R.id.time);
            profile = (SimpleDraweeView) view.findViewById(R.id.profile);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + historyList.get(position).phone));
                        context.startActivity(callIntent);
                }
            });
        }

    }
}

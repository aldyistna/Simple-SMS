package com.example.uas.aldyistna.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uas.aldyistna.R;
import com.example.uas.aldyistna.database.Tablesms;

import java.util.List;

public class ListSMSDetailAdapter extends RecyclerView.Adapter<ListSMSDetailAdapter.Holder> {
    private List<Tablesms> listData;
    private Activity context;
    private View itemLayoutView;

    public ListSMSDetailAdapter(Activity context, List<Tablesms> listData) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listdetailsms,
                parent, false);
        Holder holder = new Holder(itemLayoutView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Tablesms itemsms = listData.get(position);
        holder.from.setText(itemsms.getNumber());
        holder.content.setText(itemsms.getSmstext());
        if (itemsms.getFrom().equalsIgnoreCase("r")){
            holder.from.setText(itemsms.getNumber());
            holder.from.setGravity(Gravity.LEFT);
            holder.content.setGravity(Gravity.LEFT);

        }else {
            holder.from.setText("Me");
            holder.from.setGravity(Gravity.RIGHT);
            holder.content.setGravity(Gravity.RIGHT);
        }
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView from, content;
        public Holder(View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            content = itemView.findViewById(R.id.content);
        }
    }
}

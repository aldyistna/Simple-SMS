package com.example.uas.aldyistna.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uas.aldyistna.DetailReadSMS;
import com.example.uas.aldyistna.R;
import com.example.uas.aldyistna.database.Tablesms;

import java.util.List;

public class ListSMSRecieveAdapter extends RecyclerView.Adapter<ListSMSRecieveAdapter.Holder> {
    private List<Tablesms> listData;
    private Activity context;
    private View itemLayoutView;

    public ListSMSRecieveAdapter(Activity context, List<Tablesms> listData) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listsms,
                parent, false);
        Holder holder = new Holder(itemLayoutView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final Tablesms itemsms = listData.get(position);
        holder.number.setText(itemsms.getNumber());
        holder.content.setText(itemsms.getSmstext());
        itemLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailReadSMS.class);
                i.putExtra("phone", holder.number.getText().toString());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView number, content;
        public Holder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
        }
    }
}

package com.example.androidauthpostgresqlnodejs;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

    //Variables for Contacts
    private ArrayList<Data> list = new ArrayList<>();

    public interface OnItemClickListener{
        void onItemClick(View v, int position, int request_code);
    }

    private static OnItemClickListener mListener = null ;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder viewHolder, final int position) {

        viewHolder.onBind(list.get(position));

        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            //            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Call on Dial", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + list.get(position).getUser_phNumber()));
                view.getContext().startActivity(i);
            }
        });

        viewHolder.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(viewHolder.delete.getVisibility() == View.GONE) {
                    viewHolder.delete.setVisibility(View.VISIBLE);
                    viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mListener.onItemClick(view, position, -1);
                            viewHolder.delete.setVisibility(View.GONE);
                        }
                    });
                } else {
                    viewHolder.delete.setVisibility(View.GONE);
                }
//                viewHolder.delete.setVisibility(View.GONE);
                return true;
            }
        });
    }

    void addItem(Data data) {
        list.add(data);
    }

    void deleteItem(int position) {
        list.remove(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView number;
        private CircleImageView image;
        private ImageButton delete;

        Holder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            image = itemView.findViewById(R.id.imageView);
            delete = itemView.findViewById(R.id.delete);
        }

        void onBind(Data data) {
            name.setText(data.getUser_Name());
            number.setText(data.getUser_phNumber());
            image.setImageBitmap(data.getPhoto());
        }
    }
}
package com.example.androidauthpostgresqlnodejs;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

    //Variables for Contacts
    private ArrayList<Contact_Data> list = new ArrayList<>();

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

        viewHolder.recyclerView.setOnClickListener(new View.OnClickListener() {
            //            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Call on Dial", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + list.get(position).getPhone_number()));
                view.getContext().startActivity(i);
            }
        });

        viewHolder.recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
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

    void addItem(Contact_Data contactData) {
        list.add(contactData);
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
        private RelativeLayout recyclerView;

        Holder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            image = itemView.findViewById(R.id.imageView);
            delete = itemView.findViewById(R.id.delete);
            recyclerView = itemView.findViewById(R.id.parent_layout);
        }

        void onBind(Contact_Data contactData) {
            name.setText(contactData.getContact_name());
            number.setText(contactData.getPhone_number());
            image.setImageBitmap(contactData.getPhoto());
        }
    }
}
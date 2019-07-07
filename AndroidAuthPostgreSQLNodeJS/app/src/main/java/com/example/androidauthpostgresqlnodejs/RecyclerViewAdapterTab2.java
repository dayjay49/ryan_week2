package com.example.androidauthpostgresqlnodejs;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterTab2 extends RecyclerView.Adapter<RecyclerViewAdapterTab2.TabTwoViewHolder> {

    private static final String TAG = "RecyclerViewAdapterTab2";

    private Context mContext;
    private ArrayList<Photo> mData;
    private ArrayList<String> mPath;

    public RecyclerViewAdapterTab2(Context mContext,ArrayList<Photo> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position, int request_code);
    }

    private static OnItemClickListener mListener = null ;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @NonNull
    @Override
    public TabTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item, parent, false);
        return new TabTwoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TabTwoViewHolder holder, final int position) {
//
        Bitmap bitmap = BitmapFactory.decodeFile(mData.get(position).getmPath());
        holder.img_thumbnail.setImageBitmap(bitmap);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FullImageActivity.class);

                intent.putExtra("path", mData.get(position).getmPath());
                view.getContext().startActivity(intent);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
            public boolean onLongClick(View view ) {
                if (holder.photoDelete.getVisibility() == View.GONE) {
                    holder.photoDelete.setVisibility(View.VISIBLE);
                    holder.photoDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mListener.onItemClick(view, position, -1);
                            holder.photoDelete.setVisibility(View.GONE);
                        }
                    });
                } else {
                    holder.photoDelete.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class TabTwoViewHolder extends RecyclerView.ViewHolder {

        ImageView img_thumbnail;
        CardView cardView;
        ImageButton photoDelete;

        public TabTwoViewHolder(View itemView){
            super(itemView);
            img_thumbnail = itemView.findViewById(R.id.gallery_image);
            cardView = itemView.findViewById(R.id.cardview_id);
            photoDelete = itemView.findViewById(R.id.photo_delete);
        }
    }
}

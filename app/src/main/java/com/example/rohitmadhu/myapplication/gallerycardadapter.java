package com.example.rohitmadhu.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 *
 * Created by Kiran Anto aka RazorSharp on 1/26/2016.
 * For more Info Contact
 * Kirananto@gmail.com
 * 9495333724
 * All Copyrights Reserved 2016
 *
 */
public class gallerycardadapter extends RecyclerView.Adapter<gallerycardadapter.ViewHolder> {

    private final Context context;
    //List of Gallery
    private final List<gallery> Gallery;

    public gallerycardadapter(List<gallery> Gallery, Context context){
        super();
        //Getting all the Gallery
        this.Gallery = Gallery;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        gallery gallery =  Gallery.get(position);
        Glide.with(context)
                .load(gallery.getImageUrl())//TODO Make a placeholder tomorrow
                .into(holder.imageView);//Glide is used here to load images
        holder.textViewName.setText(gallery.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO implement onClick for the card here..!
                }
            });
        }



    @Override
    public int getItemCount() {
        return Gallery.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView imageView;
        public final TextView textViewName;
        public final CardView cardView= (CardView) itemView.findViewById(R.id.card_view);
        //TODO cardview is taken to use it for onClick Events
        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            textViewName = (TextView) itemView.findViewById(R.id.item_text);


        }

        @Override
        public void onClick(View view) {

        }




    }
}
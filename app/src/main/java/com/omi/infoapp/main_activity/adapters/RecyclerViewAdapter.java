package com.omi.infoapp.main_activity.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.omi.infoapp.R;
import com.omi.infoapp.objects.DataObject;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Collections;
import java.util.List;

/**
 * Created by omar on 1/29/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<DataObject> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private Context context;
    private final Transformation transformation;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context, List<DataObject> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        transformation = new RoundedTransformationBuilder()
                .borderWidthDp(0)
                .cornerRadiusDp(20)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .oval(false)
                .build();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataObject dataObject = mData.get(position);
        holder.dataTextView.setText(dataObject.getDataText());
        Picasso.with(context).load(dataObject.getDataImage()).resize(convertDpToPixel(300), convertDpToPixel(365))
                .transform(transformation).into(holder.dataImageView);
        Picasso.with(context)
                .load(dataObject.getDataImageBlur()) // thumbnail url goes here
                .resize(convertDpToPixel(300), convertDpToPixel(365))
                .transform(transformation)
                .centerCrop()
                .into(holder.dataImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.with(context)
                                .load(dataObject.getDataImage())
                                .resize(convertDpToPixel(300), convertDpToPixel(365))
                                .transform(transformation)
                                .centerCrop()
                                .placeholder(holder.dataImageView.getDrawable())
                                .into(holder.dataImageView);
                    }
                    @Override
                    public void onError() {

                    }
                });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dataTextView;
        public ImageView dataImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            dataTextView = itemView.findViewById(R.id.data_text);
            dataImageView = itemView.findViewById(R.id.data_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public int convertDpToPixel(float dp){
        Resources resources =  context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
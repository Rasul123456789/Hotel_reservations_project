package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder> {

    private Context context;
    private int[] images;

    public ImageSliderAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_slider_image, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        // Фото жүктеу
        holder.photoView.setImageResource(images[position]);

        // Фотоға басқанда толық экран ашу
        holder.photoView.setOnClickListener(v -> {

            // ComfortActivity болса
            if (context instanceof ComfortActivity) {
                ((ComfortActivity) context).showFullScreenImage(images[position]);
            }

            // BusinessActivity болса
            else if (context instanceof BusinessActivity) {
                ((BusinessActivity) context).showFullScreenImage(images[position]);
            }

        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {

        PhotoView photoView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.ivSliderImage);
        }
    }
}

package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.graphics.drawable.Drawable.createFromPath;
import static androidx.core.content.ContextCompat.getDrawable;
import static androidx.core.content.ContextCompat.startActivity;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, List<News> arrayInput) {
        super(context,0, arrayInput);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View newItemView = convertView;
        if(newItemView == null) {
            newItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        //get the{@link News} object located at this position in the list
        News currentNews = (News) getItem(position);

        //find the text view in the list_item.xml layout with the id news_ title
        TextView newsTitle = (TextView) newItemView.findViewById(R.id.news_title);

//      sets the name text view to the title of the news
        newsTitle.setText(currentNews.getNewsTitle());
//      find the text view in the list_item.xml layout with the id news_date
        TextView locationView = (TextView) newItemView.findViewById(R.id.news_date);
//      sets the publication date(mm/dd/yyyy)
        locationView.setText(currentNews.getPublicationDate());
        //find the text view in the list_item.xml layout with the id news_ time
        TextView timeView = (TextView) newItemView.findViewById(R.id.news_time);
//      sets the time it was published
        timeView.setText(currentNews.getPublicationTime());
        //find the text view in the list_item.xml layout with the id news_author
        TextView authorView = (TextView) newItemView.findViewById(R.id.news_author);
//      sets it to the author who wrote it
        if(currentNews.getNewsAuthor() == null){
            authorView.setText("no author found");
        }
        else{
            authorView.setText(currentNews.getNewsAuthor());
        }
//      find the image view in the list_item.xml layout with the id news_image
        ImageView imageView = (ImageView) newItemView.findViewById(R.id.news_image);
//        if statement to avoid NullPointerException
        if(currentNews != null){
//            set the thumbnail
            Picasso.get().load(currentNews.getImageUrl()).into(imageView);
        }

        return newItemView;

    }


}


package com.example.bookcompoundview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by pedro on 25/08/2016.
 */
public class Book extends LinearLayout {

    private ImageView bookCover;

    private TextView bookTitle;

    private TextView bookAuthor;

    private TextView bookPublisher;

    public Book(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.list_row, this);

        this.bookCover = (ImageView) findViewById(R.id.bookCover);
        this.bookTitle = (TextView) findViewById(R.id.bookTitle);
        this.bookAuthor = (TextView) findViewById(R.id.bookAuthor);
        this.bookPublisher = (TextView) findViewById(R.id.bookPublisher);
    }

    public void setBookCover(Drawable bookCover) {
        this.bookCover.setImageDrawable(bookCover);
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle.setText(bookTitle);
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor.setText(bookAuthor);
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher.setText(bookPublisher);
    }
}

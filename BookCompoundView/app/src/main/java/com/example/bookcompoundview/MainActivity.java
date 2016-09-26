package com.example.bookcompoundview;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private Book book1;

    private Book book2;

    private Book book3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.book1 = (Book) findViewById(R.id.list1);
        this.book1.setBookCover(getResources().getDrawable(R.drawable.android));
        this.book1.setBookTitle("Android - Introduction to the development of apps.");
        this.book1.setBookAuthor("Ricard Queirós");
        this.book1.setBookPublisher("FCA");

        this.book2 = (Book) findViewById(R.id.list2);
        this.book2.setBookCover(getResources().getDrawable(R.drawable.html5));
        this.book2.setBookTitle("HTML5");
        this.book2.setBookAuthor("Luis Abreu");
        this.book2.setBookPublisher("FCA");

        this.book3 = (Book) findViewById(R.id.list3);
        this.book3.setBookCover(getResources().getDrawable(R.drawable.mobile));
        this.book3.setBookTitle("Programming for mobile devices in Windows Mobile");
        this.book3.setBookAuthor("Ricardo Queirós");
        this.book3.setBookPublisher("FCA");
    }

}

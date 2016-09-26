package com.example.printimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PrintImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_image);
    }

    public void printImage(View view) {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setOrientation(PrintHelper.ORIENTATION_PORTRAIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kitkat);
        photoPrinter.printBitmap("kitkat.png - Testing Printing Framework", bitmap);
    }

}

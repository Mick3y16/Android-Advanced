package com.example.readweb.helper;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;

import com.example.readweb.model.Article;
import com.example.readweb.model.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pedro on 30/08/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Nome of the database
    private static final String DATABASE_NAME = "contactsManager";

    // Version of the database
    private static final int DATABASE_VERSION = 8;

    // Names of the tables
    private static final String TABLE_ARTICLE = "article";
    private static final String TABLE_TAG = "tag";
    private static final String TABLE_ARTICLE_TAG = "article_tag";

    // Names of the columns of the tables
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_URL = "url";
    private static final String KEY_STATE = "state";
    private static final String KEY_CREATION_DATE = "creation_date";
    private static final String KEY_NAME = "name";
    private static final String KEY_ARTICLE_ID = "article_id";
    private static final String KEY_TAG_ID = "tag_id";

    // Tag for LogCat
    private static final String LOG = "DatabaseHelper";

    // SQL instruction to create the article table.
    private static final String CREATE_TABLE_ARTICLE = "CREATE TABLE "
            + TABLE_ARTICLE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE
            + " TEXT," + KEY_URL + " TEXT," + KEY_STATE + " INTEGER," + KEY_CREATION_DATE
            + " DATETIME" + ")";

    // SQL instruction to create the tag table
    private static final String CREATE_TABLE_TAG = "CREATE TABLE "
            + TABLE_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME
            + " TEXT)";

    // SQL instruction to create the article/tag table
    private static final String CREATE_TABLE_ARTICLE_TAG = "CREATE TABLE "
            + TABLE_ARTICLE_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ARTICLE_ID
            + " INTEGER," + KEY_TAG_ID + " INTEGER)";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creation of the tables.
        sqLiteDatabase.execSQL(CREATE_TABLE_ARTICLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TAG);
        sqLiteDatabase.execSQL(CREATE_TABLE_ARTICLE_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Removal of the existing tables.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE_TAG);

        // Creation of the tables.
        onCreate(sqLiteDatabase);
    }

    public Article createArticle(String title, String url, int state, long[] tagIDs) {
        Article article = new Article(title, url, state);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, article.getTitle());
        contentValues.put(KEY_URL, article.getUrl());
        contentValues.put(KEY_STATE, article.getState());
        contentValues.put(KEY_CREATION_DATE, getDateTime());

        // Insert the article into the database.
        long articleID = sqLiteDatabase.insert(TABLE_ARTICLE, null, contentValues);
        article.setId(articleID);

        // Associate tags to the article.
        for (long tagID : tagIDs) {
            associateTagToArticle(articleID, tagID);
        }

        return article;
    }

    public Tag createTag(String name) {
        Tag tag = new Tag(name);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, tag.getName());

        // Insert the tag into the database.
        long tagID = sqLiteDatabase.insert(TABLE_TAG, null, contentValues);
        tag.setId(tagID);

        return tag;
    }

    public long associateTagToArticle(long articleID, long tagID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ARTICLE_ID, articleID);
        contentValues.put(KEY_TAG_ID, tagID);

        return sqLiteDatabase.insert(TABLE_ARTICLE_TAG, null, contentValues);
    }


    public void closeDatabase() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    private String getDateTime() {
        return new Date().toString();
    }

    public Article getArticleByID(long articleID) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_ARTICLE + " WHERE " + KEY_ID + " = " + articleID;
        Log.e(LOG, query);

        Article article = new Article();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();

            article.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
            article.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
            article.setUrl(cursor.getString(cursor.getColumnIndex(KEY_URL)));
            article.setState(cursor.getInt(cursor.getColumnIndex(KEY_STATE)));
            article.setCreationDate(cursor.getString(cursor.getColumnIndex(KEY_CREATION_DATE)));
        }

        return article;
    }

    public List<Article> getArticlesWithTagName(String tagName) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query;

        if (tagName == null || tagName.trim().isEmpty()) {
            query = "SELECT * FROM " + TABLE_ARTICLE;
        } else {
            query = "SELECT * FROM "
                    + TABLE_ARTICLE + " ta, "
                    + TABLE_TAG + " tt, "
                    + TABLE_ARTICLE_TAG + " tat" +
                    " WHERE tt." + KEY_NAME + " = '" + tagName + "'"
                    + " AND tt." + KEY_ID + " = " + "tat." + KEY_TAG_ID
                    + " AND ta." + KEY_ID + " = " + "tat." + KEY_ARTICLE_ID;


        }
        Log.e(LOG, query);

        List<Article> articlesList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Article article = new Article();

                article.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                article.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                article.setUrl(cursor.getString(cursor.getColumnIndex(KEY_URL)));
                article.setState(cursor.getInt(cursor.getColumnIndex(KEY_STATE)));
                article.setCreationDate(cursor.getString(cursor.getColumnIndex(KEY_CREATION_DATE)));

                // Add the article to the list.
                articlesList.add(article);
            } while(cursor.moveToNext());
        }

        return articlesList;
    }

    public int updateArticle(Article article) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, article.getTitle());
        contentValues.put(KEY_URL, article.getUrl());
        contentValues.put(KEY_STATE, article.getState());

        // Updating the article
        return sqLiteDatabase.update(TABLE_ARTICLE, contentValues, KEY_ID + " = ?", new String[] { String.valueOf(article.getId()) });
    }

    public void removeArticle(long articleID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(TABLE_ARTICLE, KEY_ID + " = ?", new String[] { String.valueOf(articleID) });
    }

    public void removeTag(Tag tag, boolean removeAssociatedArticles) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        if (removeAssociatedArticles) {
            List<Article> articlesList = getArticlesWithTagName(tag.getName());

            // Removing articles before removing the tag.
            for (Article article : articlesList) {
                removeArticle(article.getId());
            }
        }

        // Removing the tag.
        sqLiteDatabase.delete(TABLE_TAG, KEY_ID + " = ?", new String[] { String.valueOf(tag.getId()) });
    }



}

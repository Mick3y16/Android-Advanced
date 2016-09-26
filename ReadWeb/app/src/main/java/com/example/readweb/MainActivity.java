package com.example.readweb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.readweb.helper.DatabaseHelper;
import com.example.readweb.model.Article;
import com.example.readweb.model.Tag;

import java.util.List;

public class MainActivity extends Activity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.databaseHelper = new DatabaseHelper(getApplicationContext());

        // Create tags.
        Tag firstTag = this.databaseHelper.createTag("E-learning");
        Tag secondTag = this.databaseHelper.createTag("Web 2.0");
        Tag thirdTag = this.databaseHelper.createTag("Web Services");
        Tag fourthTag = this.databaseHelper.createTag("XML");

        // Create articles.
        Article firstArticle = this.databaseHelper.createArticle("A survey on elearning",
                "", 0, new long[] { firstTag.getId() });
        Article secondArticle = this.databaseHelper.createArticle("Integrating LMS with an intelligent tutor",
                "", 1, new long[] { thirdTag.getId(), fourthTag.getId() });;
        Article thirdArticle = this.databaseHelper.createArticle("Trends on the Web",
                "", 0, new long[] { secondTag.getId(), fourthTag.getId() });

        // Count articles.
        Log.e("Article Count", "Number of articles: " + this.databaseHelper.getArticlesWithTagName("").size());

        // Get all articles.
        Log.d("Get Articles", "Get all articles");
        List<Article> articlesList = this.databaseHelper.getArticlesWithTagName("");

        for (Article article : articlesList) {
            Log.d("Article", article.getTitle());
        }

        // Get articles with XML tag.
        Log.d("Article", "Get all articles with XML tag");
        articlesList = this.databaseHelper.getArticlesWithTagName(fourthTag.getName());

        for (Article article : articlesList) {
            Log.d("Article", article.getTitle());
        }

        // Remove an article.
        Log.d("Article Count", "Count before removal: " + this.databaseHelper.getArticlesWithTagName("").size());
        Log.d("Remove article", "Remove an article with the tag " + fourthTag.getName());

        this.databaseHelper.removeTag(fourthTag, true);

        Log.d("Article Count", "Count after removal: " + this.databaseHelper.getArticlesWithTagName("").size());

        // Update the title of an article.
        firstArticle.setTitle("A survey on e-learning");
        this.databaseHelper.updateArticle(firstArticle);

        Log.d("Update article", "New title: " + firstArticle.getTitle());

        articlesList = this.databaseHelper.getArticlesWithTagName(fourthTag.getName());

        for (Article article : articlesList) {
            Log.d("Article", article.getTitle());
        }

        // Close the database connection
        this.databaseHelper.closeDatabase();
    }
}

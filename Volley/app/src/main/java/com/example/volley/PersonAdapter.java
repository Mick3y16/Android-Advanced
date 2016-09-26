package com.example.volley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by pedro on 02/09/2016.
 */
public class PersonAdapter extends ArrayAdapter<Person> {

    private static final int LAYOUT = R.layout.person_list_item;

    public PersonAdapter(Context context, List<Person> personList) {
        super(context, LAYOUT, personList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.person_list_item, null);
        }

        NetworkImageView networkImageView = (NetworkImageView) convertView.findViewById(R.id.network_image_view);
        TextView textView = (TextView) convertView.findViewById(R.id.text_view);

        Person person = getItem(position);
        textView.setText(person.getName());
        networkImageView.setImageUrl(person.getImageURL(), VolleySingleton.getInstance(getContext()).getImageLoader());

        return convertView;
    }

}

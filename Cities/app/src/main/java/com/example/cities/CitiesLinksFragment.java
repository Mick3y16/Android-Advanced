package com.example.cities;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by pedro on 23/08/2016.
 */
public class CitiesLinksFragment extends ListFragment {

    private boolean dualFrame;

    private int currentPosition;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Creates the list with the defined array in the XML cities file.
        setListAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.cities, android.R.layout.simple_list_item_activated_1));

        // Check for the existence of a page for the details.
        View detailsFrame = getActivity().findViewById(R.id.details);
        this.dualFrame = (detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE);

        if (savedInstanceState != null) {
            // Returns the last state to the previously marked position.
            this.currentPosition = savedInstanceState.getInt("currentPosition", 0);

            // If in horizontal mode
            if (this.dualFrame) {
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                showDetails(this.currentPosition);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPosition", this.currentPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    private void showDetails(int index) {
        this.currentPosition = index;

        if (this.dualFrame) {
            getListView().setItemChecked(index, true);

            // Checks which fragment is being displayed and changes it if necessary.
            CitiesDetailsFragment citiesDetailsFragment = (CitiesDetailsFragment) getFragmentManager().findFragmentById(R.id.details);

            if (citiesDetailsFragment == null || citiesDetailsFragment.getShownIndex() != index) {
                citiesDetailsFragment = CitiesDetailsFragment.newInstance(index);

                // Replaces the other fragment
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.details, citiesDetailsFragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), CitiesDetailsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }

}

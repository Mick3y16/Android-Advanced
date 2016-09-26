package com.example.viewpagerscrollabletabs;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class ViewPagerActivity extends FragmentActivity {

    private SectionsPageAdapter sectionsPageAdapter;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        // Creates the adapter which returns a fragment to each of the three sections of the app.
        this.sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        // Creates the viewpager object and assigns the adapter to it.
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
        this.viewPager.setAdapter(this.sectionsPageAdapter);
    }

    private class SectionsPageAdapter extends FragmentPagerAdapter {

        public SectionsPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);

            Fragment fragment = new DummySectionFragment();
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale locale = Locale.getDefault();

            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(locale);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(locale);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(locale);
                default:
                    // null return below
            }

            return null;
        }
    }

    public static class DummySectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {}

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main_dummy, container, false);
            TextView textView = (TextView) view.findViewById(R.id.section_label);
            textView.setText(String.format("%d", getArguments().getInt(ARG_SECTION_NUMBER)));

            return view;
        }
    }


}

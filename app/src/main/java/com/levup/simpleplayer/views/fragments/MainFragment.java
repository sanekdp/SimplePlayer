package com.levup.simpleplayer.views.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.levup.simpleplayer.R;
import com.levup.simpleplayer.views.MenuInteractionListener;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    private static final String SOME_VALUE = "SOME_VALUE";

    private Integer mParam1;
    private MenuInteractionListener mListener = null;
    private ViewPager viewPager;

    public static MainFragment newInstance(int value) {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        args.putInt( SOME_VALUE , value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(SOME_VALUE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        if(viewPager != null){
            setupViewPager(viewPager);
            viewPager.setOffscreenPageLimit(2);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new SongsFragment(), this.getString(R.string.songs));
        adapter.addFragment(new AlbumFragment(), this.getString(R.string.albums));
        adapter.addFragment(new ArtistFragment(), this.getString(R.string.artists));
        viewPager.setAdapter(adapter);
    }

//    public void showText(CharSequence text){
//        final TextView textView = (TextView) getView().findViewById(R.id.tv);
//        textView.setText(text);
//    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnMainFragmentEventListener(3);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MenuInteractionListener) {
            mListener = (MenuInteractionListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}

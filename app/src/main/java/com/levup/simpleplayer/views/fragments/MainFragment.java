package com.levup.simpleplayer.views.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.levup.simpleplayer.R;
import com.levup.simpleplayer.views.MenuInteractionListener;


public class MainFragment extends Fragment {
    private static final String SOME_VALUE = "SOME_VALUE";

    // TODO: Rename and change types of parameters
    private Integer mParam1;

    private MenuInteractionListener mListener = null;

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

        getView().findViewById(R.id.btn).setOnClickListener(viewBtn -> {
            final int value = getArguments().getInt(SOME_VALUE);
            mListener.OnMainFragmentEventListener(value);
        });
    }

    public void showText(CharSequence text){
        final TextView textView = (TextView) getView().findViewById(R.id.tv);
        textView.setText(text);
    }

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
}

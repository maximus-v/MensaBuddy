package com.dev.app.mensabuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Max on 08.12.2016.
 */

public class MensaFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_CONTENT = "ARG_CONTENT";

    private int mPage;
    private String mContent;

    public static MensaFragment newInstance(int page, String content) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_CONTENT, content);
        MensaFragment fragment = new MensaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        mContent = getArguments().getString(ARG_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mensa_fragment_page, container, false);
        TextView textView = (TextView) view;
        textView.setText(mContent);
        return view;
    }
}

package com.reckontech.cicnp.sharedelementtest.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reckontech.cicnp.sharedelementtest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EndFragment extends Fragment {

    TextView textView1;
    TextView textView2;

    View view;

    public EndFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_end, container, false);

        textView1 = (TextView) view.findViewById(R.id.endFragment_text1);
        textView2 = (TextView) view.findViewById(R.id.endFragment_text2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //textView1.setTransitionName(getArguments().getString("one"));
            //textView2.setTransitionName(getArguments().getString("two"));
            textView1.setTransitionName("textTransition");
            textView1.setText("Kumar");
        }



        return view;
    }

}

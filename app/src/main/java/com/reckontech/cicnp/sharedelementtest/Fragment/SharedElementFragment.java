package com.reckontech.cicnp.sharedelementtest.Fragment;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reckontech.cicnp.sharedelementtest.R;
import com.reckontech.cicnp.sharedelementtest.RecyclerView.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SharedElementFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {

    View view;

    RecyclerView recyclerView;
    SharedElementRecyclerAdapter adapter;
    List<SharedElementRecyclerDataWrapper> dataList = new ArrayList<>();


    TextView textView;

    public SharedElementFragment() {
        // Required empty public constructor

        adapter = new SharedElementRecyclerAdapter(dataList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shared_element, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerView);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),recyclerView, this));

        dataList.add(new SharedElementRecyclerDataWrapper("title","Description"));
        dataList.add(new SharedElementRecyclerDataWrapper("title","Description"));
        dataList.add(new SharedElementRecyclerDataWrapper("title","Description"));
        dataList.add(new SharedElementRecyclerDataWrapper("title","Description"));



        textView = (TextView) view.findViewById(R.id.main_textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                EndFragment endFragment = new EndFragment();

                setSharedElementEnterTransition(new Slide().setDuration(5000).setInterpolator(new FastOutSlowInInterpolator()));
                setSharedElementReturnTransition(new Slide().setDuration(5000).setInterpolator(new FastOutSlowInInterpolator()));

                endFragment.setSharedElementEnterTransition(new Slide().setDuration(500));
                setEnterTransition(new Fade().setDuration(2000));
                endFragment.setExitTransition(new Fade().setDuration(5000));

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .replace(R.id.main_coordinatorLayout, endFragment)
                        .addSharedElement(textView, "textTransition")
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(View v, int position) {
        TextView textView1 = (TextView) v.findViewById(R.id.recycler_row_title);
        TextView textView2 = (TextView) v.findViewById(R.id.recycler_row_description);

        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bundle.putString("one",textView1.getTransitionName().toString());
            bundle.putString("two",textView2.getTransitionName().toString());
        }

        // Inflate transitions to apply
        Transition changeTransform;
        Transition explodeTransform;

        changeTransform = TransitionInflater.from(getContext()).
                inflateTransition(R.transition.change_transform);
        explodeTransform = TransitionInflater.from(getContext()).
                inflateTransition(android.R.transition.explode);

        //setSharedElementEnterTransition(new Fade().setDuration(5000));
        //setSharedElementReturnTransition(new Fade().setDuration(5000));
        setExitTransition(new Fade().setDuration(5000));

        EndFragment endFragment = new EndFragment();
        endFragment.setArguments(bundle);

        endFragment.setSharedElementEnterTransition(new Fade().setDuration(5000));
        endFragment.setExitTransition(new Fade().setDuration(5000));

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
            .replace(R.id.main_coordinatorLayout, endFragment)
            .addToBackStack(null)
            .addSharedElement(textView1, "transition_one_"+position)
            .addSharedElement(textView2, "transition_two_"+position);
        fragmentTransaction.commit();
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }

    public class DetailsTransition extends TransitionSet {
        public DetailsTransition() {
            setOrdering(ORDERING_TOGETHER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addTransition(new ChangeBounds()).
                        addTransition(new ChangeTransform()).
                        addTransition(new ChangeImageTransform());
            }
        }
    }
}

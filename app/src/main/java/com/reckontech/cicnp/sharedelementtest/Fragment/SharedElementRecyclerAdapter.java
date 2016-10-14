package com.reckontech.cicnp.sharedelementtest.Fragment;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.reckontech.cicnp.sharedelementtest.R;

import java.util.List;

public class SharedElementRecyclerAdapter extends RecyclerView.Adapter<SharedElementRecyclerAdapter.ViewHolder> {

    public interface AnimationInterface{
        public void setAnimation(View view);
    }

    AnimationInterface animationInterface;

    public void setAnimationInterface(AnimationInterface animationInterface)
    {
        this.animationInterface = animationInterface;
    }

    private List<SharedElementRecyclerDataWrapper> recyclerDataList;
    Context context;


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title, description;

        public ViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.recycler_row_title);
            description = (TextView) view.findViewById(R.id.recycler_row_description);
        }
    }

    public SharedElementRecyclerAdapter(List<SharedElementRecyclerDataWrapper> recyclerDataList){
        this.recyclerDataList = recyclerDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(recyclerDataList.get(position).title.toString());
        holder.description.setText(recyclerDataList.get(position).description);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.title.setTransitionName("transition_one_"+position);
            holder.description.setTransitionName("transition_two_"+position);
        }
    }

    @Override
    public int getItemCount() {
        return recyclerDataList.size();
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, SharedElementRecyclerDataWrapper data) {
        recyclerDataList.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(SharedElementRecyclerDataWrapper data) {
        int position = recyclerDataList.indexOf(data);
        recyclerDataList.remove(position);
        notifyItemRemoved(position);
    }

    // Clean all elements of the recycler
    public void clear() {
        recyclerDataList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<SharedElementRecyclerDataWrapper> list) {
        recyclerDataList.addAll(list);
        notifyDataSetChanged();
    }
}

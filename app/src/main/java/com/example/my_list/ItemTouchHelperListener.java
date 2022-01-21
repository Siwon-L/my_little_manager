package com.example.my_list;
import android.view.View;
import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public interface ItemTouchHelperListener {
    boolean onItemMove(int from_position, int to_position);
    void onItemSwipe(int position);
    void onRightClick(int position, RecyclerView.ViewHolder viewHolder);
    void onItemClick(RecyclerView.ViewHolder holder, View view, int position);
    void onFinish(int position, HashMap<String, String> hm);
}





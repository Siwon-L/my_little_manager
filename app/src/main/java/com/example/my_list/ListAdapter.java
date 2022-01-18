package com.example.my_list;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_list.ItemTouchHelperListener;

import java.util.ArrayList;
import java.util.HashMap;



public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> implements ItemTouchHelperListener {
    ArrayList<HashMap<String, String>> items = new ArrayList<>();
    Context context;
    private AdapterView.OnItemClickListener mListener = null;

    public ListAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //LayoutInflater를 이용해서 원하는 레이아웃을 띄워줌
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.simple_list_item_activated_3, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) { //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어준다.
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(HashMap<String, String> hm) {
        items.add(hm);
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) { //이동할 객체 저장

//        Person person = items.get(from_position); //이동할 객체 삭제
//        items.remove(from_position); //이동하고 싶은 position에 추가
//        items.add(to_position, person);//Adapter에 데이터 이동알림
//        notifyItemMoved(from_position, to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }
    //왼쪽 버튼 누르면 수정할 다이얼로그 띄우기
    @Override
    public void onLeftClick(AdapterView.OnItemClickListener listener) { //수정 버튼 클릭시 다이얼로그 생성
        this.mListener = listener ;
    } //오른쪽 버튼 누르면 아이템 삭제

    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onFinish(int position, HashMap<String, String> hm) {
        items.set(position, hm);
        notifyItemChanged(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView list1, list2, list3;


        public ItemViewHolder(View itemView) {
            super(itemView);
            list1 = itemView.findViewById(R.id.text1);
            list2 = itemView.findViewById(R.id.text2);
            list3 = itemView.findViewById(R.id.text3);
        }

        public void onBind(HashMap<String, String> hm) {
            list1.setText("sss");
            list2.setText(hm.get("text2"));
            list3.setText(hm.get("text3"));
        }
    }
}

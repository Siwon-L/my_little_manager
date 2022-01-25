package com.example.my_list;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginIDFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginIDFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "st";
    private static final String ARG_PARAM2 = "loginid";
    private static final String ARG_PARAM3 = "pwd";
    private static final String ARG_PARAM4 = "url";
    private static final String ARG_PARAM5 = "index";
    private static final String ARG_PARAM6 = "id";
    private ListAdapter mSAdapter;
    private ArrayList<HashMap<String, String>> mListData;
    private int mISelectedID = 0;
    private int mID = 0;
    private int mISelectedItem = -1;
    private RecyclerView mRecyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private int mParam5;
    private int mParam6;
    private SQLiteDatabase mDB;
    private DBHelper mdbHelper;
    private Snackbar snackbar;
    private ItemTouchHelper helper;


    public LoginIDFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @param param4 Parameter 4.
     * @param param5 Parameter 5.
     * @param param6 Parameter 6.
     * @return A new instance of fragment LoginIDFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginIDFragment newInstance(String param1, String param2, String param3, String param4, int param5, int param6) {
        LoginIDFragment fragment = new LoginIDFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putInt(ARG_PARAM5, param5);
        args.putInt(ARG_PARAM6, param6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mISelectedItem = getArguments().getInt(ARG_PARAM5);
            mISelectedID = getArguments().getInt(ARG_PARAM6);
        }
    }

    private void loadTable() {
        mDB = mdbHelper.getReadableDatabase();
        mSAdapter.itemClear();

        Cursor cursor = mDB.rawQuery(DBContract.SQL_LOAD, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> hitem = new HashMap<>();
            int nID = cursor.getInt(0);
            mID = Math.max(mID/*지금까지 읽어온 레코드중 가장 큰값*/, nID/*현재 읽어온 레코드의 아이디값*/);
            if (cursor.getString(1) != "") {
                hitem.put("id", String.valueOf(nID));
                hitem.put("item1", cursor.getString(1));
                hitem.put("item2", cursor.getString(2));
                hitem.put("pwd", cursor.getString(3));
                hitem.put("item3", cursor.getString(4));
                mSAdapter.addItem(hitem);
            }


        }

        mSAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_i_d, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FloatingActionButton fadID = view.findViewById(R.id.IdB);
        final FloatingActionButton fadE = view.findViewById(R.id.EB);
        fadE.setVisibility(fadE.GONE);


        mSAdapter = new ListAdapter(getContext());


        mRecyclerView = view.findViewById(R.id.RecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);


        helper = new ItemTouchHelper(new ItemTouchHelperCallback(mSAdapter));
        helper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mSAdapter);




        mdbHelper = new DBHelper(getActivity());
        loadTable();

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
//                if (i != mISelectedItem) {
//                    fadID.setVisibility(fadID.GONE);
//                    fadE.setVisibility(fadE.VISIBLE);
//                    mISelectedItem = i;//인덱스 저장
//                    HashMap<String, String> item = ((HashMap<String, String>/*타입변환*/) mSAdapter.getItem(i));/*Object*/
//                    Toast.makeText(getActivity(), item.get("pwd"), Toast.LENGTH_LONG).show();
//
//                    mISelectedID = Integer.parseInt(item.get("id"));
//
//                    snackbar = Snackbar.make(view, item.get("st") + "항목이 선택되었습니다", Snackbar.LENGTH_LONG)
//                            .setAction("삭제", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//
//                                    mListData.remove(i);
//                                    mDB.delete(DBContract.TABLE_NAME, "id=" + mISelectedID, null);
//                                    mSAdapter.notifyDataSetChanged();
//                                    fadID.setVisibility(fadID.VISIBLE);
//                                    fadE.setVisibility(fadE.GONE);
//                                    mISelectedItem = -1;
//
//
//                                }
//                            });
//                    snackbar.show();
//
//                } else {
//                    fadID.setVisibility(fadID.VISIBLE);
//                    fadE.setVisibility(fadE.GONE);
//                    mISelectedItem = -1;
//                    mISelectedID = 0;
//                    snackbar.dismiss();
//
//
//                }
//
//
//            }
//        });
//
//
        fadID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                bundle.putInt("index", -1);
                bundle.putInt("id", 0);
                NavHostFragment.findNavController(LoginIDFragment.this).navigate(R.id.action_loginIDFragment_to_IDEditeFragment, bundle);

            }
        });

        mSAdapter.setOnItemClicklistener(new ItemTouchHelperListener() {
            @Override
            public boolean onItemMove(int from_position, int to_position) {
                return false;
            }

            @Override
            public void onItemSwipe(int position) {




            }

            @Override
            public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {

                mISelectedID = Integer.parseInt(/*item.get("id")*/mSAdapter.getItem(position).get("id"));
                mDB.delete(DBContract.TABLE_NAME, "id=" + (mISelectedID), null);
                mSAdapter.notifyDataSetChanged();
                mISelectedItem = -1;


            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {

                HashMap<String, String> item =mSAdapter.getItem(position);
                Bundle bundle = new Bundle();
                String a= String.valueOf(Integer.parseInt(item.get("id")));
                Toast.makeText(getActivity(),a,Toast.LENGTH_SHORT).show();
                mISelectedItem=position;

                bundle.putString("st", item.get("item1"));
                bundle.putString("loginid", item.get("item2"));
                bundle.putString("pwd", item.get("pwd"));
                bundle.putString("url", item.get("item3"));
                bundle.putInt("index", position);
                bundle.putInt("id", mISelectedID);
                NavHostFragment.findNavController(LoginIDFragment.this).navigate(R.id.action_loginIDFragment_to_IDEditeFragment, bundle);


            }

            @Override
            public void onFinish(int position, HashMap<String, String> hm) {

            }
        });
        if (mParam1 != null) {
            int item = mISelectedItem;
            HashMap<String, String> hitem = new HashMap<>();
            hitem.put("item1", mParam1);
            hitem.put("item2", mParam2);
            hitem.put("pwd", mParam3);
            hitem.put("item3", mParam4);
            ContentValues values = new ContentValues();
            values.put(DBContract.COL_NAME, mParam1);
            values.put(DBContract.COL_LID, mParam2);
            values.put(DBContract.COL_PWD, mParam3);
            values.put(DBContract.COL_URL, mParam4);//인텐트를 통해 넘겨받아서  ContentValues  values객체에 저장

            mDB = mdbHelper.getWritableDatabase();//조회가 아닌 인서트하고 없데이트 할수있는 메소드
            if (item == -1) {/*만약 아이템값이 -1 이면 추가하는경우*/
                values.put(DBContract.COL_ID, ++/*가장큰값보다 하나더증가시켜 저장*/mID);
                hitem.put("id", String.valueOf(mID));
                mDB.insert(DBContract.TABLE_NAME, null, values);
                mSAdapter.addItem(hitem);

                Toast.makeText(getActivity(), "추가", Toast.LENGTH_LONG).show();
            } else {
                hitem.put("id", String.valueOf(mISelectedID));

                mDB.update(DBContract.TABLE_NAME, values, "id=" + mISelectedID, null);
                //mListData.set(item, hitem);
                mSAdapter.Set(item,hitem);
            }
            mSAdapter.notifyDataSetChanged();/*수정이 적용될수있도록*/

        }
        mParam1 = null;

    }
    private void setUpRecyclerView(){
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mdbHelper != null) {
            mdbHelper.close();
        }
    }
}
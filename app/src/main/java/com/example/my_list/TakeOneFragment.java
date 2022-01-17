package com.example.my_list;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TakeOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TakeOneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM5 = "index";
    private static final String ARG_PARAM6 = "id";
    private SimpleAdapter mSAdapter;
    private ArrayList<HashMap<String,String>> mListData;
    private int mISelectedID=0;
    private int mID=0;
    private int mISelectedItem= -1;
    private ListView mListView;
    // TODO: Rename and change types of parameters
    private String mParam1;


    private SQLiteDatabase mDBTo;
    private DBHelperTO mdbHelperTo;


    public TakeOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param5 Parameter 5.
     * @param param6 Parameter 6.
     * @return A new instance of fragment SubFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TakeOneFragment newInstance(String param1, String param2,String param3,String param4,int param5,int param6) {
        TakeOneFragment fragment = new TakeOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
            mISelectedItem = getArguments().getInt(ARG_PARAM5);
            mISelectedID= getArguments().getInt(ARG_PARAM6);
        }
    }
    private void loadTable(){
        mDBTo=mdbHelperTo.getReadableDatabase();
        mListData.clear();

        Cursor cursor = mDBTo.rawQuery(DBContractTO.SQL_LOAD, null);
        while (cursor.moveToNext()){

                HashMap<String, String> hitem = new HashMap<>();
                int nID = cursor.getInt(0);
                mID = Math.max(mID/*지금까지 읽어온 레코드중 가장 큰값*/, nID/*현재 읽어온 레코드의 아이디값*/);
                hitem.put("id", String.valueOf(nID));
                hitem.put("name", cursor.getString(1));


                mListData.add(hitem);


        }

        mSAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_take_one, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FloatingActionButton fadAdd=view.findViewById(R.id.Add);
        final Button btnCG = view.findViewById(R.id.buttonCG);

        mListData =new ArrayList<>();
        mSAdapter=new SimpleAdapter(getActivity(),mListData,android.R.layout.simple_list_item_1,new String[]{"name"},new int[]{android.R.id.text1});
        mListView=view.findViewById(R.id.listView);
        mListView.setAdapter(mSAdapter);
        mdbHelperTo=new DBHelperTO(getActivity());
        loadTable();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {



                mISelectedItem = i;//인덱스 저장
                HashMap<String, String> item = ((HashMap<String, String>/*타입변환*/) mSAdapter.getItem(i));/*Object*/

                Snackbar.make(view, item.get("name")+" 챙겼는지 한번 더 확인해 보세요", Snackbar.LENGTH_INDEFINITE).show();

                mISelectedID = Integer.parseInt(item.get("id"));
                mListData.remove(i);
                mDBTo.delete(DBContractTO.TABLE_NAME, "id=" + mISelectedID, null);
                mSAdapter.notifyDataSetChanged();

                mISelectedItem=-1;





            }
        });

        btnCG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(TakeOneFragment.this).navigate(R.id.action_takeOneFragment_to_takeFragment);
            }
        });

        fadAdd.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               Bundle bundle =new Bundle();

            bundle.putInt("index",-1);
                bundle.putInt("id",0);
                NavHostFragment.findNavController(TakeOneFragment.this).navigate(R.id.action_takeOneFragment_to_takeEditeFragment,bundle);

            }
        });





        if(mParam1 !=null){
            int item=mISelectedItem;
            HashMap<String,String> hitem=new HashMap<>();
            hitem.put("name",mParam1);
            hitem.put("loginid","");
            hitem.put("pwd","");
            hitem.put("url","");
            ContentValues values=new ContentValues();
            values.put(DBContractTO.COL_NAME, mParam1);
            values.put(DBContractT.COL_LID, "");
            values.put(DBContractT.COL_PWD, "");
            values.put(DBContractT.COL_URL, "");
            //인텐트를 통해 넘겨받아서  ContentValues  values객체에 저장

            mDBTo = mdbHelperTo.getWritableDatabase();//조회가 아닌 인서트하고 없데이트 할수있는 메소드
            if(item==-1){/*만약 아이템값이 -1 이면 추가하는경우*/
                values.put(DBContractTO.COL_ID, ++/*가장큰값보다 하나더증가시켜 저장*/mID);
                hitem.put("id", String.valueOf(mID));
                mDBTo.insert(DBContractTO.TABLE_NAME,null, values);
                mListData.add(hitem);
                Toast.makeText(getActivity(),"추가",Toast.LENGTH_LONG).show();
            }
            else{
                hitem.put("id", String.valueOf(mISelectedID));

                mDBTo.update(DBContractTO.TABLE_NAME, values, "id="+ mISelectedID, null);
                mListData.set(item,hitem);
            }
            mSAdapter.notifyDataSetChanged();/*수정이 적용될수있도록*/

        }mParam1=null;

    }@Override
    public void onDestroy() {
        super.onDestroy();
        if (mdbHelperTo != null) {
            mdbHelperTo.close();
        }
    }
}
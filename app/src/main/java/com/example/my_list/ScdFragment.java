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
 * Use the {@link ScdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "apm";
    private static final String ARG_PARAM2 = "place";
    private static final String ARG_PARAM3 = "time";
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
    private String mParam2;
    private String mParam3;
    private Snackbar snackbar;


    private SQLiteDatabase mDB;
    private DBHelperScd mdbHelper;


    public ScdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @param param5 Parameter 5.
     * @param param6 Parameter 6.
     * @return A new instance of fragment LoginIDFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScdFragment newInstance(String param1, String param2,String param3,int param5,int param6) {
        ScdFragment fragment = new ScdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);

        args.putInt(ARG_PARAM5, param5);
        args.putInt(ARG_PARAM6, param6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);//약속
            mParam2 = getArguments().getString(ARG_PARAM2);//장소
            mParam3 = getArguments().getString(ARG_PARAM3);//시간

            mISelectedItem = getArguments().getInt(ARG_PARAM5);
            mISelectedID= getArguments().getInt(ARG_PARAM6);
        }
    }
    private void loadTable(){
        mDB=mdbHelper.getReadableDatabase();
        mListData.clear();

        Cursor cursor = mDB.rawQuery(DBContractScd.SQL_LOAD, null);
        while (cursor.moveToNext()){
            HashMap<String,String> hitem=new HashMap<>();
            int nID=cursor.getInt(0);
            mID = Math.max(mID/*지금까지 읽어온 레코드중 가장 큰값*/ ,nID/*현재 읽어온 레코드의 아이디값*/);

                hitem.put("id", String.valueOf(nID));
                hitem.put("apm", cursor.getString(1));
                hitem.put("place", cursor.getString(2));
                hitem.put("time", cursor.getString(3));

                mListData.add(hitem);



        }

        mSAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scd, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FloatingActionButton fadID=view.findViewById(R.id.Add);
        final FloatingActionButton fadE=view.findViewById(R.id.Info);
        fadE.setVisibility(fadE.GONE);
        mListData =new ArrayList<>();
        mSAdapter=new SimpleAdapter(getActivity(),mListData,R.layout.simple_list_item_activated_3,new String[]{"apm","place","time"},new int[]{R.id.text1,R.id.text2,R.id.text3});
        mListView=view.findViewById(R.id.listView);
        mListView.setAdapter(mSAdapter);
        mdbHelper=new DBHelperScd(getActivity());
        loadTable();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(i!=mISelectedItem) {
                    fadID.setVisibility(fadID.GONE);
                    fadE.setVisibility(fadE.VISIBLE);
                    mISelectedItem = i;//인덱스 저장
                    HashMap<String, String> item = ((HashMap<String, String>/*타입변환*/) mSAdapter.getItem(i));/*Object*/
                    Toast.makeText(getActivity(), "약속시간: "+item.get("time"), Toast.LENGTH_LONG).show();

                    mISelectedID = Integer.parseInt(item.get("id"));

                   snackbar = Snackbar.make(view, item.get("apm")+"항목이 선택되었습니다", Snackbar.LENGTH_LONG)
                            .setAction("삭제", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    mListData.remove(i);
                                    mDB.delete(DBContractScd.TABLE_NAME, "id=" + mISelectedID, null);
                                    mSAdapter.notifyDataSetChanged();
                                    fadID.setVisibility(fadID.VISIBLE);
                                    fadE.setVisibility(fadE.GONE);
                                    mISelectedItem=-1;



                                }
                            });
                   snackbar.show();

                }else  {
                    fadID.setVisibility(fadID.VISIBLE);
                    fadE.setVisibility(fadE.GONE);
                    mISelectedItem=-1;
                    mISelectedID=0;
                    snackbar.dismiss();

                }


            }
        });




        fadID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle =new Bundle();

                bundle.putInt("index",-1);
                bundle.putInt("id",0);
                NavHostFragment.findNavController(ScdFragment.this).navigate(R.id.action_scdFragment_to_scdEditeFragment,bundle);

            }
        });
        fadE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> item = (HashMap<String, String>)mSAdapter.getItem(mISelectedItem);
                Bundle bundle =new Bundle();

                bundle.putString("apm",item.get("apm"));
                bundle.putString("place",item.get("place"));
                bundle.putString("time",item.get("time"));

                bundle.putInt("index",mISelectedItem);
                bundle.putInt("id",mISelectedID);
                NavHostFragment.findNavController(ScdFragment.this).navigate(R.id.action_scdFragment_to_scdEditeFragment,bundle);
            }
        });




        if(mParam1 !=null){
            int item=mISelectedItem;
            HashMap<String,String> hitem=new HashMap<>();
            hitem.put("apm",mParam1);
            hitem.put("place",mParam2);
            hitem.put("time",mParam3);
            ContentValues values=new ContentValues();
            values.put(DBContractScd.COL_NAME, mParam1);
            values.put(DBContractScd.COL_LID, mParam2);
            values.put(DBContractScd.COL_PWD, mParam3);
            values.put(DBContractScd.COL_URL, "");//인텐트를 통해 넘겨받아서  ContentValues  values객체에 저장

            mDB = mdbHelper.getWritableDatabase();//조회가 아닌 인서트하고 없데이트 할수있는 메소드
            if(item==-1){/*만약 아이템값이 -1 이면 추가하는경우*/
                values.put(DBContractScd.COL_ID, ++/*가장큰값보다 하나더증가시켜 저장*/mID);
                hitem.put("id", String.valueOf(mID));
                mDB.insert(DBContractScd.TABLE_NAME,null, values);
                mListData.add(hitem);
                Toast.makeText(getActivity(),"추가",Toast.LENGTH_LONG).show();
            }
            else{
                hitem.put("id", String.valueOf(mISelectedID));

                mDB.update(DBContractScd.TABLE_NAME, values, "id="+ mISelectedID, null);
                mListData.set(item,hitem);
            }
            mSAdapter.notifyDataSetChanged();/*수정이 적용될수있도록*/

        }mParam1=null;

    }@Override
    public void onDestroy() {
        super.onDestroy();
        if (mdbHelper != null) {
            mdbHelper.close();
        }
    }
}
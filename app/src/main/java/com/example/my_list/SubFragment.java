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
 * Use the {@link SubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "st";
    private static final String ARG_PARAM3 = "amount";
    private static final String ARG_PARAM4 = "day";
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
    private String mParam4;

    private SQLiteDatabase mDBsub;
    private DBHelperS mdbHelpersub;

    private Snackbar snackbar;


    public SubFragment() {
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
     * @return A new instance of fragment SubFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubFragment newInstance(String param1, String param2,String param3,String param4,int param5,int param6) {
        SubFragment fragment = new SubFragment();
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
            mISelectedID= getArguments().getInt(ARG_PARAM6);
        }
    }
    private void loadTable(){
        mDBsub=mdbHelpersub.getReadableDatabase();
        mListData.clear();

        Cursor cursor = mDBsub.rawQuery(DBContractS.SQL_LOAD, null);
        while (cursor.moveToNext()){

                HashMap<String, String> hitem = new HashMap<>();
                int nID = cursor.getInt(0);
                mID = Math.max(mID/*지금까지 읽어온 레코드중 가장 큰값*/, nID/*현재 읽어온 레코드의 아이디값*/);
                hitem.put("id", String.valueOf(nID));
                hitem.put("name", cursor.getString(1));
                hitem.put("st", cursor.getString(2));
                hitem.put("amount", cursor.getString(3));
                hitem.put("day", cursor.getString(4));
                mListData.add(hitem);


        }

        mSAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FloatingActionButton fadAdd=view.findViewById(R.id.Add);
        final FloatingActionButton fadInfo=view.findViewById(R.id.Info);
        fadInfo.setVisibility(fadInfo.GONE);
        mListData =new ArrayList<>();
        mSAdapter=new SimpleAdapter(getActivity(),mListData,R.layout.simple_list_item_activated_4,new String[]{"name","st","amount","day"},new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4});
        mListView=view.findViewById(R.id.listView);
        mListView.setAdapter(mSAdapter);
        mdbHelpersub=new DBHelperS(getActivity());
        loadTable();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(i!=mISelectedItem) {
                    fadAdd.setVisibility(fadAdd.GONE);
                    fadInfo.setVisibility(fadInfo.VISIBLE);
                    mISelectedItem = i;//인덱스 저장
                    HashMap<String, String> item = ((HashMap<String, String>/*타입변환*/) mSAdapter.getItem(i));/*Object*/
                    Toast.makeText(getActivity(), item.get("amount"), Toast.LENGTH_LONG).show();

                    mISelectedID = Integer.parseInt(item.get("id"));

                    snackbar = Snackbar.make(view, item.get("st")+"항목이 선택되었습니다", Snackbar.LENGTH_LONG)
                            .setAction("삭제", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    mListData.remove(i);
                                    mDBsub.delete(DBContractS.TABLE_NAME, "id=" + mISelectedID, null);
                                    mSAdapter.notifyDataSetChanged();
                                    fadAdd.setVisibility(fadAdd.VISIBLE);
                                    fadInfo.setVisibility(fadInfo.GONE);
                                    mISelectedItem=-1;



                                }
                            });
                    snackbar.show();

                }else  {
                    fadAdd.setVisibility(fadAdd.VISIBLE);
                    fadInfo.setVisibility(fadInfo.GONE);
                    mISelectedItem=-1;
                    mISelectedID=0;
                    snackbar.dismiss();
                }


            }
        });




        fadAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle =new Bundle();

                bundle.putInt("index",-1);
                bundle.putInt("id",0);
                NavHostFragment.findNavController(SubFragment.this).navigate(R.id.action_subFragment_to_subEditeFragment,bundle);

            }
        });
        fadInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> item = (HashMap<String, String>)mSAdapter.getItem(mISelectedItem);
                Bundle bundle =new Bundle();

                bundle.putString("name",item.get("name"));
                bundle.putString("st",item.get("st"));
                bundle.putString("amount",item.get("amount"));
                bundle.putString("day",item.get("day"));
                bundle.putInt("index",mISelectedItem);
                bundle.putInt("id",mISelectedID);
                NavHostFragment.findNavController(SubFragment.this).navigate(R.id.action_subFragment_to_subEditeFragment,bundle);
            }
        });




        if(mParam1 !=null){
            int item=mISelectedItem;
            HashMap<String,String> hitem=new HashMap<>();
            hitem.put("name",mParam1);
            hitem.put("st",mParam2);
            hitem.put("amount",mParam3);
            hitem.put("day",mParam4);
            ContentValues values=new ContentValues();
            values.put(DBContractS.COL_NAME, mParam1);
            values.put(DBContractS.COL_LID, mParam2);
            values.put(DBContractS.COL_PWD, mParam3);
            values.put(DBContractS.COL_URL, mParam4);//인텐트를 통해 넘겨받아서  ContentValues  values객체에 저장

            mDBsub = mdbHelpersub.getWritableDatabase();//조회가 아닌 인서트하고 없데이트 할수있는 메소드
            if(item==-1){/*만약 아이템값이 -1 이면 추가하는경우*/
                values.put(DBContractS.COL_ID, ++/*가장큰값보다 하나더증가시켜 저장*/mID);
                hitem.put("id", String.valueOf(mID));
                mDBsub.insert(DBContractS.TABLE_NAME,null, values);
                mListData.add(hitem);
                Toast.makeText(getActivity(),"추가",Toast.LENGTH_LONG).show();
            }
            else{
                hitem.put("id", String.valueOf(mISelectedID));

                mDBsub.update(DBContractS.TABLE_NAME, values, "id="+ mISelectedID, null);
                mListData.set(item,hitem);
            }
            mSAdapter.notifyDataSetChanged();/*수정이 적용될수있도록*/

        }mParam1=null;

    }@Override
    public void onDestroy() {
        super.onDestroy();
        if (mdbHelpersub != null) {
            mdbHelpersub.close();
        }
    }


}
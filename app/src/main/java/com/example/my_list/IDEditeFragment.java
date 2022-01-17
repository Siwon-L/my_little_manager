package com.example.my_list;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IDEditeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IDEditeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "index";
    private static final String ARG_PARAM2 = "id";
    private static final String ARG_PARAM3 = "st";
    private static final String ARG_PARAM4 = "loginid";
    private static final String ARG_PARAM5 = "pwd";
    private static final String ARG_PARAM6 = "url";


    // TODO: Rename and change types of parameters
    private int mItem=-1;
    private int mISelectedID=0;
    private DBHelper mdbHelper;
    private SQLiteDatabase mDB;
    private String mParamst;
    private String mParamloginid;
    private String mParampwd;
    private String mParamurl;


    public IDEditeFragment() {
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
     * @return A new instance of fragment IDEditeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IDEditeFragment newInstance(int param1,int param2, String param3, String param4, String param5, String param6) {
        IDEditeFragment fragment = new IDEditeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItem = getArguments().getInt(ARG_PARAM1);
            mISelectedID=getArguments().getInt(ARG_PARAM2);
            mParamst=getArguments().getString(ARG_PARAM3);
            mParamloginid=getArguments().getString(ARG_PARAM4);
            mParampwd=getArguments().getString(ARG_PARAM5);
            mParamurl=getArguments().getString(ARG_PARAM6);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_i_d_edite, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        final EditText editST=view.findViewById(R.id.editTextWName);
        final EditText editID=view.findViewById(R.id.editTextTextLoginID);
        final EditText editPWD=view.findViewById(R.id.editTextPwd);
        final EditText editURL=view.findViewById(R.id.editTextTWUrl);

        mdbHelper=new DBHelper(getActivity());
        if(mItem!=-1){
            editST.setText(mParamst);
            editID.setText(mParamloginid);
            editPWD.setText(mParampwd);
            editURL.setText(mParamurl);
        }



        Button btnOK=view.findViewById(R.id.buttonSave);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st=editST.getText().toString().trim();
                String id=editID.getText().toString().trim();
                String pwd=editPWD.getText().toString().trim();
                String url=editURL.getText().toString().trim();

                if(st.isEmpty()||id.isEmpty()||pwd.isEmpty()){
                    Toast.makeText(getActivity(),"사이트이름과 아이디,비밀번호를 바르게 입력하세요.",Toast.LENGTH_LONG).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("index",mItem);
                bundle.putString("st",st);
                bundle.putString("loginid",id);
                bundle.putString("pwd",pwd);
                bundle.putString("url",url);
                bundle.putInt("id",mISelectedID);

                NavHostFragment.findNavController(IDEditeFragment.this).navigate(R.id.action_IDEditeFragment_to_loginIDFragment,bundle);
            }
        });
        Button btnCancel = view.findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController=NavHostFragment.findNavController(IDEditeFragment.this);
                navController.popBackStack();
            }
        });

    }
}
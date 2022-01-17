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
 * Use the {@link SubEditeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubEditeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "index";
    private static final String ARG_PARAM2 = "id";
    private static final String ARG_PARAM3 = "name";
    private static final String ARG_PARAM4 = "st";
    private static final String ARG_PARAM5 = "amount";
    private static final String ARG_PARAM6 = "day";


    // TODO: Rename and change types of parameters
    private int mItem=-1;
    private int mISelectedID=0;
    private DBHelper mdbHelpersub;
    private SQLiteDatabase mDBsub;
    private String mParamname;
    private String mParamst;
    private String mParamamount;
    private String mParamday;


    public SubEditeFragment() {
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
     * @return A new instance of fragment SubEditeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubEditeFragment newInstance(int param1,int param2, String param3, String param4, String param5, String param6) {
        SubEditeFragment fragment = new SubEditeFragment();
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
            mParamname=getArguments().getString(ARG_PARAM3);
            mParamst=getArguments().getString(ARG_PARAM4);
            mParamamount=getArguments().getString(ARG_PARAM5);
            mParamday=getArguments().getString(ARG_PARAM6);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_edite, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        final EditText editName=view.findViewById(R.id.editTextName);
        final EditText editSt=view.findViewById(R.id.editTextSt);
        final EditText editAmount=view.findViewById(R.id.editTextAmt);
        final EditText editDay=view.findViewById(R.id.editTextDay);

        mdbHelpersub=new DBHelper(getActivity());
        if(mItem!=-1){
            editName.setText(mParamname);
            editSt.setText(mParamst);
            editAmount.setText(mParamamount);
            editDay.setText(mParamday);
        }



        Button btnOK=view.findViewById(R.id.buttonSave);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editName.getText().toString().trim();
                String st=editSt.getText().toString().trim();
                String amt=editAmount.getText().toString().trim();
                String day=editDay.getText().toString().trim();

                if(st.isEmpty()||day.isEmpty()){
                    Toast.makeText(getActivity(),"사이트 이름과 날짜를 모두 입력해주세요",Toast.LENGTH_LONG).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("index",mItem);
                bundle.putString("name",name);
                bundle.putString("st",st);
                bundle.putString("amount",amt+" 원");
                bundle.putString("day",day);
                bundle.putInt("id",mISelectedID);

                NavHostFragment.findNavController(SubEditeFragment.this).navigate(R.id.action_subEditeFragment_to_subFragment,bundle);
            }
        });
        Button btnCancel = view.findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController=NavHostFragment.findNavController(SubEditeFragment.this);
                navController.popBackStack();
            }
        });

    }
}
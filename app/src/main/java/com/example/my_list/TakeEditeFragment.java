package com.example.my_list;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TakeEditeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TakeEditeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "index";
    private static final String ARG_PARAM2 = "id";
    private RadioButton ODay,Eday;
    private RadioGroup RG;

    private Button btnS,btnC;
    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;
    private int c=2;
    public TakeEditeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TakeEditeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TakeEditeFragment newInstance(int param1, int param2) {
        TakeEditeFragment fragment = new TakeEditeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_take_edite, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editName=view.findViewById(R.id.editTextN);

        RG=view.findViewById(R.id.RGroup);
        ODay=view.findViewById(R.id.radioOneday);
        Eday=view.findViewById(R.id.radioEveryday);

        btnS=view.findViewById(R.id.buttonSave);
        btnC=view.findViewById(R.id.buttonCancel);


        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radioOneday){
                    Toast.makeText(getActivity(),"하루를 선택하였습니다",Toast.LENGTH_SHORT).show();
                    c=1;
                }else if(i==R.id.radioEveryday){
                    Toast.makeText(getActivity(),"매일을 선택하였습니다.",Toast.LENGTH_SHORT).show();
                    c=0;
                }else {
                    Toast.makeText(getActivity(),"항목을 선택 해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String name=editName.getText().toString().trim();
                if(c==1){
                    Bundle bundle=new Bundle();
                    bundle.putInt("index",mParam1);
                    bundle.putInt("id",mParam2);
                    bundle.putString("name",name);
                    NavHostFragment.findNavController(TakeEditeFragment.this).navigate(R.id.action_takeEditeFragment_to_takeOneFragment,bundle);
                }else if(c==0){
                    Bundle bundle=new Bundle();
                    bundle.putInt("index",mParam1);
                    bundle.putInt("id",mParam2);
                    bundle.putString("name",name);
                    NavHostFragment.findNavController(TakeEditeFragment.this).navigate(R.id.action_takeEditeFragment_to_takeFragment,bundle);
                }else {
                    Toast.makeText(getActivity(),"항목을 선택 해주세요"+name+mParam1+mParam2,Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController=NavHostFragment.findNavController(TakeEditeFragment.this);
                navController.popBackStack();
            }
        });

    }

}
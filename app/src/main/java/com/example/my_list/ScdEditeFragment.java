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
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScdEditeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScdEditeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "index";
    private static final String ARG_PARAM2 = "id";
    private static final String ARG_PARAM3 = "apm";
    private static final String ARG_PARAM4 = "place";



    // TODO: Rename and change types of parameters
    private int mItem=-1;
    private int mISelectedID=0;
    private String mParamapm;
    private String mParamplace;



    public ScdEditeFragment() {
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
    public static ScdEditeFragment newInstance(int param1,int param2, String param3, String param4) {
        ScdEditeFragment fragment = new ScdEditeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItem = getArguments().getInt(ARG_PARAM1);
            mISelectedID=getArguments().getInt(ARG_PARAM2);
            mParamapm=getArguments().getString(ARG_PARAM3);
            mParamplace=getArguments().getString(ARG_PARAM4);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scd_edite, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        final EditText editAPM=view.findViewById(R.id.editTextAPM);
        final EditText editPlace=view.findViewById(R.id.editTextPlace);
        final TimePicker picker=view.findViewById(R.id.PickerTime);
        picker.setIs24HourView(true);


        if(mItem!=-1){
            editAPM.setText(mParamapm);
            editPlace.setText(mParamplace);

        }



        Button btnOK=view.findViewById(R.id.buttonSave);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String apm=editAPM.getText().toString().trim();
                String place=editPlace.getText().toString().trim();
                String time=picker.getHour()+"시" +picker.getMinute()+"분";


                if(place.isEmpty()){
                    Toast.makeText(getActivity(),"약속 장소를 입력하세요.",Toast.LENGTH_LONG).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("index",mItem);
                bundle.putString("apm",apm);
                bundle.putString("place",place);
                bundle.putString("time",time);

                bundle.putInt("id",mISelectedID);

                NavHostFragment.findNavController(ScdEditeFragment.this).navigate(R.id.action_scdEditeFragment_to_scdFragment,bundle);
            }
        });
        Button btnCancel = view.findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController=NavHostFragment.findNavController(ScdEditeFragment.this);
                navController.popBackStack();
            }
        });

    }
}
package com.example.mario;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static java.lang.Integer.parseInt;

public class AddPropFragment extends Fragment implements View.OnClickListener, PropertyDescFragment.OnFragmentInteractionListener
{
    private  EditText name ,address, landmark,noOfFloors,inTime,OutTime,secMul,noticePeriod,minStayTime;
    private Button nextPage;
    private Spinner buildType;


    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    public AddPropFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_add_prop, container, false);
        name=(EditText) v.findViewById(R.id.prop_edit_name);
        address=(EditText) v.findViewById(R.id.prop_edit_address);
        landmark=(EditText) v.findViewById(R.id.prop_edit_landmark);
        noOfFloors=(EditText) v.findViewById(R.id.prop_edit_floors);
        inTime=(EditText) v.findViewById(R.id.prop_edit_intime);
        OutTime=(EditText) v.findViewById(R.id.prop_edit_outtime);
        secMul=(EditText) v.findViewById(R.id.prop_edit_securitydeposit);
        noticePeriod=(EditText) v.findViewById(R.id.prop_edit_noticeperiod);
        nextPage=(Button)v.findViewById(R.id.prop_button_descPage);
        buildType=(Spinner)v.findViewById(R.id.prop_spinner_type);
        minStayTime=(EditText)v.findViewById(R.id.prop_edit_stay);
        //String text = buildType.getSelectedItem().toString();




       // ((Button) v.findViewById(R.id.btn_frag2)).setOnClickListener(this);

        //FirebaseFirestore firestore = FirebaseFirestore.getInstance();


        nextPage.setOnClickListener(this);




        return v;
    }


    @Override
    public void onClick(View v)
    {
        mFragmentManager = getFragmentManager();
        propDetails dataSave= propDetails.getInstance();


        //Map<String,Object> dataSave = new HashMap<String, Object>() ;
        dataSave.propName=name.getText().toString();
        dataSave.propAddress=address.getText().toString();
        dataSave.propLandmark=landmark.getText().toString();
        dataSave.propFloors=(int)parseInt(noOfFloors.getText().toString());
        dataSave.propMinStayTime=(int)parseInt(minStayTime.getText().toString());
        dataSave.propInTime=inTime.getText().toString();
        dataSave.propOutTime=OutTime.getText().toString();
        dataSave.propType= buildType.getSelectedItem().toString();
        dataSave.propSecurityMultiplier=(int)parseInt(secMul.getText().toString());
        dataSave.propNoticePeriod=(int)parseInt(noticePeriod.getText().toString());


        mFragment = new PropertyDescFragment();
        mFragmentManager.beginTransaction().replace(R.id.frame, mFragment).addToBackStack(null).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

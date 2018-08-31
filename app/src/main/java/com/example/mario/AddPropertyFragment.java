package com.example.mario;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddPropertyFragment extends Fragment
{

    private OnFragmentInteractionListener mListener;
    private IncompleteProperty property;
    private Unbinder unbinder;
    @BindView(R.id.prop_edit_name) EditText name;
    @BindView(R.id.prop_spinner_type) Spinner type;
    @BindView(R.id.prop_edit_address) EditText address;
    @BindView(R.id.prop_edit_landmark) EditText land;
    @BindView(R.id.prop_edit_floors) EditText floors;
    @BindView(R.id.prop_edit_stay) EditText stayTime;
    @BindView(R.id.prop_edit_intime) EditText inTime;
    @BindView(R.id.prop_edit_outtime) EditText outTime;
    @BindView(R.id.prop_edit_securitydeposit) EditText deposit;
    @BindView(R.id.prop_edit_noticeperiod) EditText notice;

    public AddPropertyFragment() { }

    public static AddPropertyFragment newInstance(IncompleteProperty p)
    {
        AddPropertyFragment fragment = new AddPropertyFragment();
        Bundle args = new Bundle();
        args.putParcelable(AddPropertyActivity.KEY_PROPERTY, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) property = getArguments().getParcelable(AddPropertyActivity.KEY_PROPERTY);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_add_property, container, false);
        unbinder = ButterKnife.bind(this, v);

        name.setText(property.getName());
        type.setSelection(((ArrayAdapter)type.getAdapter()).getPosition(property.getType()));
        address.setText(property.getAddress());
        land.setText(property.getLandmark());
        floors.setText(Integer.toString(property.getFloors()));
        stayTime.setText(Integer.toString(property.getMinStayTime()));
        inTime.setText(property.getInTime());
        outTime.setText(property.getOutTime());
        notice.setText(Integer.toString(property.getNoticePeriod()));

        return v;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
            {

                throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.prop_button_next)
    public void gotoNext(Button button)
    {
        property.setName(name.getText().toString());
        property.setType(type.getSelectedItem().toString());
        property.setAddress(address.getText().toString());
        property.setLandmark(land.getText().toString());
        try
        {
            property.setFloors(Integer.parseInt(floors.getText().toString()));
            property.setMinStayTime(Integer.parseInt(stayTime.getText().toString()));
            property.setInTime(inTime.getText().toString());
            property.setOutTime(outTime.getText().toString());

            /*deposit*/

            property.setNoticePeriod(Integer.parseInt(notice.getText().toString()));
        }
        catch(NumberFormatException e) { System.out.println(e.toString()); }
        if(button != null) mListener.nextFragment();
    }

    public interface OnFragmentInteractionListener
    {
        void nextFragment();
    }
}

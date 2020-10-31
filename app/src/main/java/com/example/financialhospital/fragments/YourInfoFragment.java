package com.example.financialhospital.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.financialhospital.MainActivity;
import com.example.financialhospital.R;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YourInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YourInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String[] investForList = {"Self", "Wife", "Family"};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText etDateOfPurchase;
    EditText etInvestmentFor;
    Spinner spnInvestFor;
    TextView btnAddMore;
    final Calendar myCalendar = Calendar.getInstance();

    public YourInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YourInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YourInfoFragment newInstance(String param1, String param2) {
        YourInfoFragment fragment = new YourInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_info, container, false);
        etInvestmentFor = view.findViewById(R.id.etInvestmentFor);
        spnInvestFor = view.findViewById(R.id.spnInvestFor);
        btnAddMore = view.findViewById(R.id.btnAddMore);
        etDateOfPurchase= (EditText)  view.findViewById(R.id.etDateOfPurchase);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etDateOfPurchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherFragments otherFragments = new OtherFragments();
                Bundle bundle = new Bundle();
                bundle.putString("fragmentName", "Know You Better");
                otherFragments.setArguments(bundle);
                ((MainActivity)getActivity()).showFragment(otherFragments);

            }
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayAdapter<String> dataAdapterCourses = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, investForList);
        dataAdapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnInvestFor.setDropDownVerticalOffset(50);
        spnInvestFor.setAdapter(dataAdapterCourses);


        etInvestmentFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            spnInvestFor.performClick();
            }
        });


        spnInvestFor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etInvestmentFor.setText(""+spnInvestFor.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etDateOfPurchase.setText(sdf.format(myCalendar.getTime()));
    }
}
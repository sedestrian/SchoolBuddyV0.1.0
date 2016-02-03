package com.technext.fabtest2;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class MarksFragment extends android.support.v4.app.Fragment {

    public static MarksFragment newInstance(String param1, String param2) {
        MarksFragment fragment = new MarksFragment();
        return fragment;
    }

    AppCompatActivity context;
    DatabaseAdapter dbAdapter;

    AppCompatActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                    MaterialDialog dialog = new MaterialDialog.Builder(context)
                            .title("Add a new subject")
                            .customView(R.layout.new_subject, false)
                            .positiveText("Submit")
                            .negativeText("Cancel")
                            .positiveColorRes(R.color.primaryColor)
                            .negativeColorRes(R.color.primaryColor)
                            .neutralColor(Color.WHITE)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
                                    databaseAdapter.insertSubject(((EditText) dialog.getCustomView().findViewById(R.id.editText)).getText().toString());
                                    RecyclerView list = (RecyclerView) activity.findViewById(R.id.markRecycler);
                                    TextView textView = (TextView) activity.findViewById(R.id.empty);
                                    list.setVisibility(View.VISIBLE);
                                    textView.setVisibility(View.GONE);
                                    ((MarksRecyclerAdapter) list.getAdapter()).updateData();
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    super.onNegative(dialog);
                                }
                            })
                            .autoDismiss(true)
                            .show();
                    View customView = dialog.getCustomView();
                    EditText editText = (EditText) customView.findViewById(R.id.editText);
                    final TextView label = (TextView) customView.findViewById(R.id.textView);
                    final int base = label.getCurrentTextColor();
                    editText.setOnFocusChangeListener(
                            new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if(!hasFocus == true){
                                        label.setTextColor(getResources().getColor(R.color.primaryColor));
                                    }
                                    else{
                                        //label.setTextColor(Color.BLACK);
                                    }
                                }
                            }
                    );
        }
    };

    public MarksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marks, container, false);
        FloatingActionButton subjFab = (FloatingActionButton) view.findViewById(R.id.addSubjFab);
        subjFab.setOnClickListener(listener);
        context = (AppCompatActivity) getActivity();
        dbAdapter = new DatabaseAdapter(context);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.markRecycler);
        TextView textView = (TextView) view.findViewById(R.id.empty);
        List<CustomDataTable> allData = dbAdapter.getAllSubjects();
        if(allData.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
        /*
        AddSubjFab fab = new AddSubjFab(context);
        RelativeLayout GFABcontainer = (RelativeLayout) view.findViewById(R.id.GenericFAB_container);
        android.support.v4.app.FragmentManager fragManager = context.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragManager.beginTransaction();
        transaction.add(R.id.GenericFAB_container, fab, "FAB");
        transaction.commit();*/

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MarksRecyclerAdapter(context));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_today).setVisible(false);
        menu.findItem(R.id.eraseDatabase).setVisible(true);
    }
}

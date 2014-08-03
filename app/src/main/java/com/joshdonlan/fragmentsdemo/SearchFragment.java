package com.joshdonlan.fragmentsdemo;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchFragment extends Fragment {

    public interface OnFragmentInteractionListener {
        public void OnSearchFragmentInteraction(String symbol);
    }

    private OnFragmentInteractionListener mListener;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }
    public SearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((TextView) getView().findViewById(R.id.searchText)).setHint("Search");
        }

        ListView searchList = (ListView) getView().findViewById(R.id.searchList);
        ArrayList<String> symbolList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.symbols)));
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,symbolList);
        searchList.setAdapter(searchAdapter);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView selected = (TextView) view;
                mListener.OnSearchFragmentInteraction(selected.getText().toString());
            }
        });

        Button searchButton = (Button) getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView searchField = (TextView) getView().findViewById(R.id.searchText);
                mListener.OnSearchFragmentInteraction(searchField.getText().toString());
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}

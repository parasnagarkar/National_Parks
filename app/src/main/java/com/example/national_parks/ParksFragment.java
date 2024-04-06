package com.example.national_parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.national_parks.adapter.OnparkClickListner;
import com.example.national_parks.adapter.ParkRecyclerViewAdapter;
import com.example.national_parks.data.AsyncResponse;
import com.example.national_parks.data.Repository;
import com.example.national_parks.model.Park;
import com.example.national_parks.model.ParkViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParksFragment extends Fragment implements OnparkClickListner {

    private RecyclerView RV;
    private ParkRecyclerViewAdapter PRVA;
    private List<Park> parkList;
    private ParkViewModel parkViewModel;
    public ParksFragment() {
        // Required empty public constructor
    }
    public static ParksFragment newInstance() {
        ParksFragment fragment = new ParksFragment();
        Bundle args = new Bundle();

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parkViewModel = new ViewModelProvider(requireActivity()).get(ParkViewModel.class);
        if(parkViewModel.getParks().getValue() != null) {
            parkList = parkViewModel.getParks().getValue();
            PRVA = new ParkRecyclerViewAdapter(parkList,this);
            RV.setAdapter(PRVA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_parks, container, false);
        RV = v.findViewById(R.id.park_recycler);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(getActivity()));


        return v;
    }

    @Override
    public void onParkClicked(Park park) {
        Log.d("Nikhil Raut Sexy", "onParkClicked: "+park.getFullName());
        parkViewModel.selectPark(park);
        getFragmentManager().beginTransaction().replace(R.id.Park_Fragment,DetailsFragment.newInstance()).commit();

    }
}
package com.example.national_parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.national_parks.adapter.ViewPagerAdapter;
import com.example.national_parks.model.Park;
import com.example.national_parks.model.ParkViewModel;

import org.w3c.dom.Text;


public class DetailsFragment extends Fragment {

    private ParkViewModel parkViewModel;
    private ViewPagerAdapter VPA;
    private ViewPager2 VP;
    private TextView name,designa,Activities,Topics,Directions,Entrance,OpHours,decription;

    private StringBuilder sb;
    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parkViewModel = new ViewModelProvider(requireActivity()).get(ParkViewModel.class);
        VP = view.findViewById(R.id.Details_ViewPager);
        Activities=view.getRootView().findViewById(R.id.Details_Activities_Text);
        Topics = view.getRootView().findViewById(R.id.Detaiils_Topic_Text);
        Directions = view.getRootView().findViewById(R.id.Details_Direction_Text);
        Entrance = view.getRootView().findViewById(R.id.Details_Enterance_Text);
        OpHours = view.getRootView().findViewById(R.id.Details_Operating_House_text);
        decription = view.getRootView().findViewById(R.id.Details_Description_Text);


        name = view.findViewById(R.id.Details_Park_Name);
        designa = view.findViewById(R.id.Details_Park_Designation);


        parkViewModel.getSelectedPark().observe(getViewLifecycleOwner(), park -> {
            sb = new StringBuilder();
            name.setText(park.getFullName());
            designa.setText(park.getDesignation());
            decription.setText(park.getDescription());

            for (int i = 0; i < park.getActivities().size(); i++) {
                sb.append(park.getActivities().get(i).getName()+" || ");
            }
            Activities.setText(sb);
            Topics.setText(park.getTopics().toString());
            if (park.getEntranceFees().size()>0) {
                Entrance.setText("Title :-"+park.getEntranceFees().get(0).getTitle()+"\tCost :$"+park.getEntranceFees().get(0).getCost()+"\tDescription :-"+park.getEntranceFees().get(0).getDescription());
            } else {
                Entrance.setText(R.string.InfoError);
            }

            StringBuilder opSb = new StringBuilder();
            opSb.append("Monday :-").append(park.getOperatingHours().get(0).getStandardHours().getMonday()).append("\n");
            opSb.append("Tuesday :-").append(park.getOperatingHours().get(0).getStandardHours().getTuesday()).append("\n");
            opSb.append("Wednesday :-").append(park.getOperatingHours().get(0).getStandardHours().getWednesday()).append("\n");
            opSb.append("Thursday :-").append(park.getOperatingHours().get(0).getStandardHours().getThursday()).append("\n");
            opSb.append("Friday :-").append(park.getOperatingHours().get(0).getStandardHours().getFriday()).append("\n");
            opSb.append("Saturday :-").append(park.getOperatingHours().get(0).getStandardHours().getSaturday()).append("\n");
            opSb.append("Sunday :-").append(park.getOperatingHours().get(0).getStandardHours().getSunday()).append("\n");


            OpHours.setText(opSb.toString());

            StringBuilder topicsd = new StringBuilder();
            for (int i = 0; i < park.getTopics().size(); i++) {
                topicsd.append(park.getTopics().get(0).getName()).append(" | ");
            }
            Topics.setText(topicsd);

            if(!TextUtils.isEmpty(park.getDirectionsInfo())) {
                Directions.setText(park.getDirectionsInfo());
            } else {
                Directions.setText(R.string.Directons_Error);
            }
            VPA = new ViewPagerAdapter(park.getImages());
            VP.setAdapter(VPA);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
}
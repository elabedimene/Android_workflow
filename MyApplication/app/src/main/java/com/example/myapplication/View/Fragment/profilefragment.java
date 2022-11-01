package com.example.myapplication.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Controller.Connexion;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.R;

public class profilefragment extends Fragment {



    private Connexion profileAdapter;
    private Profile profilelog;

    public profilefragment(Profile prof) {
        // Required empty public constructor
        this.profilelog=prof;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_profile, container, false);


        ((TextView)view.findViewById(R.id.username)).setText(profilelog.getFirstName()+" "+ profilelog.getLastName());
        ((TextView)view.findViewById(R.id.email)).setText(profilelog.getEmail());

        // Inflate the layout for this fragment
        return view;
    }




}

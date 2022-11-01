package com.example.myapplication.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Controller.TaskAdapter;
import com.example.myapplication.Model.Task;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class tasksfragment extends Fragment {
    private List<Task> mRecyclerViewItems = new ArrayList<>();
    private TaskAdapter mAdapter;
    private RecyclerView rv;
    private String USERNAME , PASSWORD;



    public tasksfragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        USERNAME = getArguments().getString("username");
        PASSWORD = getArguments().getString("password");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_task, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);


        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }





    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        String title[] ={ "My Tasks","My Group Tasks"};

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment temp;
            Bundle bundle=new Bundle();
            switch (position) {
                case 0:
                    temp=new mytasksfragment();
                    bundle.putString("username",USERNAME);
                    bundle.putString("password",PASSWORD);
                    temp.setArguments(bundle);
                    return temp;
                case 1:
                temp=new mygrouptasksfragment();
                bundle.putString("username",USERNAME);
                bundle.putString("password",PASSWORD);
                temp.setArguments(bundle);
                return temp;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}

package com.example.myapplication.View.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Controller.TaskAdapter;
import com.example.myapplication.Model.Task;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class mytasksfragment extends Fragment {
    private List<Task> mRecyclerViewItems = new ArrayList<>();
    private TaskAdapter mAdapter;
    private RecyclerView rv;
    private String USERNAME , PASSWORD;

    String UrMyTasksList="http://digitalisi.tn:8080/engine-rest/filter/e4f31000-b6f5-11ec-b178-c3179e4f32a6/list?firstResult=0&maxResults=15";


    public mytasksfragment() {
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


        View view = inflater.inflate(R.layout.fragment_mytabtask, container, false);

        rv = (RecyclerView)view.findViewById(R.id.task_recycler_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter    = new TaskAdapter(getContext(),mRecyclerViewItems,this,USERNAME,PASSWORD);
        String result;
        try {

            result = mAdapter.getTasks(UrMyTasksList);
            Log.d("list tasks", result);
            parsingData(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rv.setAdapter(mAdapter);
        return view;
    }



    public void parsingData(String jsonData)
    {

        try {
            //JSONObject obj = new JSONObject(jsonData);
            JSONArray m_jArry = new JSONArray(jsonData);

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String id = jo_inside.getString("id");
                String name = jo_inside.getString("name");
                String assignee = jo_inside.getString("assignee");
                String process = jo_inside.getString("processDefinitionId");
                String date = jo_inside.getString("created");
                int priority = jo_inside.getInt("priority");


                Task taskItem = new Task();
                taskItem.setId(id);
                taskItem.setName(name);
                taskItem.setAssignee(assignee);
                taskItem.setProcessDefinitionId(process);
                taskItem.setCreated(date);
                taskItem.setPriority(priority);
                mRecyclerViewItems.add(taskItem);
            }

            rv.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

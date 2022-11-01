package com.example.myapplication.View.Fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Controller.Connexion;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class taskdetailsfragment extends Fragment {

    private static final String ARG_PARAM1 = "idTask";
    private String username ;
    private String password ;
    private String idProcess;
    private Connexion formAdapter;
    private JSONObject TextViewArray;
    private String result;
    private JSONObject objet, lastobjet;
    private Button claimbtn ;//claim
    private String idtask ;  //claim

    //constructor
    public taskdetailsfragment() { }


    //create task detail fragment
    public static taskdetailsfragment newInstance(String param1, String param2) {
        taskdetailsfragment fragment = new taskdetailsfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idProcess = getArguments().getString(ARG_PARAM1);
            idtask = getArguments().getString("idTask");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_task_details, container, false);
        claimbtn =(Button) view.findViewById(R.id.claim);
        username = getArguments().getString("username");
        password = getArguments().getString("password");
        Button complete = (Button)view.findViewById(R.id.cirLoginButton);

        String url ="http://digitalisi.tn:8080/engine-rest/task/"+idtask+"/form-variables";
        try {
            formAdapter=new Connexion();
            result = formAdapter.getData(url,username,password);
            parsingData(result);
            createForm(view);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        claimbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String url;
                Connexion conx= new Connexion();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), "");

                try {
                    if(claimbtn.getText().equals("claim")) {
                         url ="http://digitalisi.tn:8080/camunda/api/engine/engine/default/task/"+idtask+"/claim";
                        String response = formAdapter.postData(url, username, password, body);
                        claimbtn.setText("Unclaim");
                        complete.setEnabled(true);
                        complete.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String url;
                                Connexion conx= new Connexion();
                                MediaType mediaType = MediaType.parse("application/json");
                                RequestBody body = RequestBody.create(MediaType.parse("application/json"), "");

                                try {

                                    url ="http://digitalisi.tn:8080/camunda/api/engine/engine/default/task/"+idtask+"/submit-form";
                                    String response = formAdapter.postData(url, username, password, body);
                                    Toast.makeText(view.getContext(),"Sucess",Toast.LENGTH_SHORT).show();
                                    Fragment task = new tasksfragment() ;
                                    Bundle bundle = new Bundle() ;
                                    bundle.putString("username",username);
                                    bundle.putString("password",password);
                                    task.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.Layout_container, task, "findThisFragment")
                                            .addToBackStack(null)
                                            .commit();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    else
                    {
                        complete.setEnabled(false);
                        url ="http://digitalisi.tn:8080/camunda/api/engine/engine/default/task/"+idtask+"/unclaim";
                        String response = formAdapter.postData(url, username, password, body);
                        claimbtn.setText("claim");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }



    public void createForm (View view) throws JSONException {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.taskformlayout);
        Button btn = new Button(view.getContext());
        int i =0;

        // create form with iterator
        Iterator iteratorObj = TextViewArray .keys();
        ArrayList<String> al_getAllKeys=new ArrayList<String>();
        TextView[] text = new TextView[TextViewArray.length()];
        EditText[] edit = new EditText[TextViewArray.length()];

        CheckBox ch = new CheckBox(getActivity());
        while (iteratorObj.hasNext()) {
            String keyName = (String) iteratorObj.next();
            System.out.println("KEY: " + "------>" + keyName);
            JSONObject value =new JSONObject(String.valueOf(TextViewArray.get(keyName))) ;
            System.out.println("value: " + "------>" + value);

            System.out.println("i: " + "------>" + i);
            LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams
                    ((int) LinearLayout.LayoutParams.MATCH_PARENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);

            params1.setMargins(50,50,50,0);
            text[i] = new TextView(getContext());
            text[i].setText(keyName);
            text[i].setLayoutParams(params1);
            linearLayout.addView(text[i]);


            if(String.valueOf(value.getString("type")).equalsIgnoreCase("boolean")){
                ch.setText(keyName);
                ch.setTextSize(20);
                ch.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                ch.setPadding(20, 20, 20, 20);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams
                        ((int) LinearLayout.LayoutParams.MATCH_PARENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(50,0,50,0);
                ch.setLayoutParams(params);
                linearLayout.addView(ch);


            }
            else{
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams
                        ((int) LinearLayout.LayoutParams.MATCH_PARENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(50,0,50,0);
                edit[i] = new EditText(getActivity());
                edit[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                edit[i].setText(value.getString("value"));
                edit[i].setHint(keyName);
                edit[i].setTextColor(Color.parseColor("#012F49"));
                edit[i].setTextSize(20);
                edit[i].setInputType(InputType.TYPE_CLASS_TEXT);
                edit[i].setMinLines(1);
                edit[i].setMaxLines(3);
                edit[i].setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.backtext));
                edit[i].setPadding(20, 20, 20, 20);
                edit[i].setEnabled(false);
                edit[i].setLayoutParams(params);
                linearLayout.addView(edit[i]);

                i++;
            }

        }
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams
                    ((int) LinearLayout.LayoutParams.MATCH_PARENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(50,50,50,0);


    }

    public void parsingData(String jsonData)
    {

        try {
            JSONObject js= new JSONObject(jsonData);
            TextViewArray = js;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

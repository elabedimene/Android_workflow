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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Controller.Connexion;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class formfragment extends Fragment {

    private static final String ARG_PARAM1 = "idprocess";

    private String username ;
    private String password ;
    private String idProcess;
    private Connexion formAdapter;
    private JSONArray TextViewArray;
    private String result;
    private JSONObject objet, lastobjet;
    private Profile profile ;


    public formfragment() { }

//formfragment constructor
    public static formfragment newInstance(String param1) {

        formfragment fragment = new formfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    //fragment creation
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idProcess = getArguments().getString(ARG_PARAM1);
            profile=(Profile) getArguments().getSerializable("profile");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_form, container, false);

        username = getArguments().getString("username");
        password = getArguments().getString("password");
        String url ="http://digitalisi.tn:8080/engine-rest/process-definition/"+ idProcess +"/form-variables";
        try {
            formAdapter=new Connexion();
            result = formAdapter.getData(url,username,password);
            parsingData(result);
            createForm(view,TextViewArray);

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Inflate the layout for this fragment
        return view;
    }
    public void parsingData(String jsonData)
    {

        try {
            JSONObject js= new JSONObject(jsonData);
            //jsonArray.put(responseData);
            TextViewArray = js.names();

            //String email = obj.getString("email");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void createForm (View view,JSONArray js)
    {

        LinearLayout linearLayout =  (LinearLayout) view.findViewById(R.id.formLinearLayout);
        Button btn = new Button(view.getContext());

        if(TextViewArray != null)
        {
            TextView[] tv = new TextView[js.length()];
            EditText[] et = new EditText[js.length()];

            for(int i=0; i<js.length(); i++)
            {

                try {
                    tv[i] = new TextView(getContext());
                    et[i] = new EditText(getContext());

                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams
                            ((int) LinearLayout.LayoutParams.MATCH_PARENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(50,0,50,0);

                    LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams
                            ((int) LinearLayout.LayoutParams.MATCH_PARENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);

                    params2.setMargins(50,50,50,0);

                    tv[i].setText(String.valueOf(js.get(i)));


                    et[i].setGravity(Gravity.CENTER);
                    et[i].setTextColor(Color.parseColor("#012F49"));
                    et[i].setTextSize(20);
                    et[i].setInputType(InputType.TYPE_CLASS_TEXT);
                    et[i].setMinLines(1);
                    et[i].setMaxLines(3);
                    et[i].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.backtext));
                    et[i].setLayoutParams(params);

                    tv[i].setLayoutParams(params2);
                    linearLayout.addView(tv[i]);
                    linearLayout.addView(et[i]);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams
                ((int) LinearLayout.LayoutParams.MATCH_PARENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(50,50,50,0);
        btn.setText("Submit Form");
        btn.setLayoutParams(params);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject jsnobject = null;

                try {
                    jsnobject = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("json object", String.valueOf(jsnobject));
                objet = new JSONObject();
                lastobjet = new JSONObject();
                for(int j=0; j<js.length(); j++)
                {
                    try {
                        JSONObject value =  jsnobject.getJSONObject(String.valueOf(js.get(j)));
                        value.put("value", et[j].getText().toString());
                        objet.put(String.valueOf(js.get(j)),jsnobject.getJSONObject(String.valueOf(js.get(j))));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                try {
                    lastobjet.put("variables",objet);
                    Log.d(" last objet", String.valueOf(lastobjet));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("objet", String.valueOf(objet));

                new Submit_Form().execute(username, password, idProcess);
                Toast.makeText(view.getContext(),"Sucess",Toast.LENGTH_SHORT).show();
                Fragment process = new processesfragment(profile) ;
                Bundle bundle = new Bundle() ;
                bundle.putString("username",username);
                bundle.putString("password",password);
                process.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Layout_container, process, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });

        }
        linearLayout.addView(btn);

    }
    public class Submit_Form extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String Email= strings[0];
            String Password= strings[1];
            String ID= strings[2];
            String json = lastobjet.toString();

            String url = "http://digitalisi.tn:8080/engine-rest/process-definition/"+idProcess+"/submit-form";


            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);

            try {
                String response=formAdapter.postData( url,password,username, body);

                Log.d("Submit Form Success", response);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}

package com.example.myapplication.Controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ItemClickListener;
import com.example.myapplication.Model.Process;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.R;
import com.example.myapplication.View.Fragment.formfragment;
import com.example.myapplication.View.Fragment.processesfragment;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;

public class ProcessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    OkHttpClient client = new OkHttpClient();

    //create variable
    private final List<Process> recyclerViewItems;
    private final Context mContext;
    processesfragment fragmentone;
    private ItemClickListener clickListener;
    Connexion conx;
    private String username, password;
    private Profile profile ;

    public ProcessAdapter(Context context, List<Process> recyclerViewItems, processesfragment fragmentone, String user, String pass , Profile profile) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.fragmentone = fragmentone;
        this.username = user;
        this.password = pass;
        this.profile = profile ;
    }


    public String getProcesses(String url) throws IOException
    {
        String result = null;
        try {
            conx =new Connexion();
             result = conx.getData(url,username,password);


        } catch (IOException e) {
            e.printStackTrace();
        }
            client.newBuilder()
                    .build();

            return result;

    }


    //create view item-process in fragment-process
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_process,viewGroup, false);
        return new MenuItemViewHolder(itemLayoutView);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        final Process fp = (Process) recyclerViewItems.get(position);
        menuItemHolder.name.setText(fp.getName());

    }


    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public ConstraintLayout processView;
        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name      = (TextView) itemLayoutView.findViewById(R.id.process_name);
            processView = (ConstraintLayout) itemLayoutView.findViewById(R.id.process_item);
            itemLayoutView.setTag(itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            int position = this.getAdapterPosition();
            //process clicked
            final Process proc = recyclerViewItems.get(position);
            //create formfragment
            formfragment form;
            form=new formfragment();
            //form fragment arguments
            Bundle bundle = new Bundle();
            bundle.putString("idprocess", proc.getId());
            bundle.putString("username",username);
            bundle.putString("password",password);
            bundle.putSerializable("profilelog" , profile);
            form.setArguments(bundle);
            fragmentone.getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.Layout_container, form, "findThisFragment")
                    .addToBackStack(null)
                    .commit();

        }
    }

}
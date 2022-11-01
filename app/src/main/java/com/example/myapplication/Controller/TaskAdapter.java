package com.example.myapplication.Controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.ItemClickListener;
import com.example.myapplication.Model.Task;
import com.example.myapplication.R;
import com.example.myapplication.View.Fragment.taskdetailsfragment;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    OkHttpClient client = new OkHttpClient();

    //create variable
    private final List<Task> recyclerViewItems;
    private final Context mContext;

    private ItemClickListener clickListener;

    private String username, password;
    Fragment fragmentone;
    Connexion conx;
    //generate constructor

    public TaskAdapter(Context context, List<Task> recyclerViewItems, Fragment fragmentone, String username, String password) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.fragmentone = fragmentone;
        this.username = username;
        this.password = password;
    }


    public String getTasks(String url) throws IOException
    {
            String result = null;
            try {
                conx =new Connexion();
                 result = conx.getData(url,username,password);


            } catch (IOException e) {
                e.printStackTrace();
            }
                client.newBuilder().build();
                return result;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item,viewGroup, false);
        return new MenuItemViewHolder(itemLayoutView);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        final Task fp = (Task) recyclerViewItems.get(position);

        menuItemHolder.nameTask.setText(fp.getName());
        menuItemHolder.taskprocesname.setText(fp.getProcessDefinitionId());
        menuItemHolder.taskdate.setText(fp.getCreated());

    }


    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

    public void setClickListener(ItemClickListener itemClickListener)
    {
    this.clickListener = itemClickListener;
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameTask;
        public TextView taskprocesname;
        public TextView taskdate;
        public ConstraintLayout taskitemlayout;

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            nameTask      = (TextView) itemLayoutView.findViewById(R.id.task_name);
            taskprocesname      = (TextView) itemLayoutView.findViewById(R.id.taskprocesname);
            taskdate      = (TextView) itemLayoutView.findViewById(R.id.taskdate);
            taskitemlayout  = (ConstraintLayout) itemLayoutView.findViewById(R.id.tasklayoutitem);
            itemLayoutView.setTag(itemView);
            itemView.setOnClickListener(this);

        }

    // task clicked pass to task-detail-fragment
        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            final Task T = recyclerViewItems.get(position);
            taskdetailsfragment detailfrag;
            detailfrag=new taskdetailsfragment();
            Bundle bundle = new Bundle();
            bundle.putString("idTask", T.getId()); //cle idTask
            bundle.putString("username",username);
            bundle.putString("password",password);
            detailfrag.setArguments(bundle);
            fragmentone.getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.Layout_container, detailfrag, "findThisFragment")
                    .commit();

        }
    }

}
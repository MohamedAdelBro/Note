package com.example.mynote.Adapters;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynote.Activities.MainActivity;
import com.example.mynote.Fragment.Edit;
import com.example.mynote.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Note_Adapter extends RecyclerView.Adapter<Note_Adapter.MyAdapter> {
    ArrayList<Model> mNote ;
    Context context;

    public Note_Adapter(ArrayList<Model> mNote, Context context) {
        this.mNote = mNote;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_shap,parent,false);
        MyAdapter myAdapter  = new MyAdapter(view);
        return myAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, final int position) {
        holder.mHeader.setText(mNote.get(position).getmHeader());
        holder.mDescibtion.setText(mNote.get(position).getmDescribtion());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit  edit = new Edit();
                Bundle bundle = new Bundle();
                bundle.putString("Key",mNote.get(position).getmKey());
                bundle.putString("head",mNote.get(position).getmHeader());
                bundle.putString("describtion",mNote.get(position).getmDescribtion());
                edit.setArguments(bundle);
                edit.show(((MainActivity)context).getSupportFragmentManager(),"");
            }
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference("Note").child(mNote.get(position).getmKey());
                databaseReference.removeValue();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNote.size();
    }

    class MyAdapter extends RecyclerView.ViewHolder{
        ImageView mDelete;
         TextView mHeader,mDescibtion;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            mDescibtion = itemView.findViewById(R.id.Description);
            mHeader = itemView.findViewById(R.id.header);
            mDelete = itemView.findViewById(R.id.delete);
        }
    }
}

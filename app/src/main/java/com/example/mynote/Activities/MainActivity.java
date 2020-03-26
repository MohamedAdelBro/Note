package com.example.mynote.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mynote.Adapters.Model;
import com.example.mynote.Adapters.Note_Adapter;
import com.example.mynote.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mHeader, mDescribtion;
    Button mSave;
    DatabaseReference mDatabaseReference;
    RecyclerView mNotes;
    ArrayList<Model> myNote = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String intent = getIntent().getStringExtra("NOD_NAME");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(intent);
        InstView();
    }
    @Override
    protected void onStart() {
        super.onStart();
        getNote();
    }

    private void InstView() {
        mDescribtion = findViewById(R.id.Description);
        mHeader = findViewById(R.id.Header);
        mSave = findViewById(R.id.save);
        mSave.setOnClickListener(this);
        mNotes = findViewById(R.id.MyNote);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        mNotes.setLayoutManager(layoutManager);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                AddNote();
                mHeader.setText("");
                mDescribtion.setText("");
                break;
        }
    }

    void AddNote() {
        String NoteKey = mDatabaseReference.push().getKey();
        Model model = new Model(mHeader.getText().toString(), mDescribtion.getText().toString(), NoteKey);
        mDatabaseReference.child(NoteKey).setValue(model);

    }
    void getNote(){
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myNote.clear();
                for (DataSnapshot Ds : dataSnapshot.getChildren()){
                    Model model = Ds.getValue(Model.class);
                    myNote.add(model);
                    Note_Adapter note_adapter = new Note_Adapter(myNote,MainActivity.this);
                    mNotes.setAdapter(note_adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

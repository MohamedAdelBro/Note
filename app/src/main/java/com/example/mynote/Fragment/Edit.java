package com.example.mynote.Fragment;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mynote.Activities.MainActivity;
import com.example.mynote.Adapters.Model;
import com.example.mynote.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit extends AppCompatDialogFragment implements View.OnClickListener {
    View view;
    EditText mHeader, mDescribtion;
    Button mSave;
    String Key;

    public Edit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit, container, false);

        InstView();
        return view;
    }

    private void InstView() {
        mDescribtion = view.findViewById(R.id.Description);
        mHeader = view.findViewById(R.id.Header);
        mSave = view.findViewById(R.id.save);
        mSave.setOnClickListener(this);
        mDescribtion.setText(getArguments().getString("describtion"));
        mHeader.setText(getArguments().getString("head"));
        Key = getArguments().getString("Key");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                upLoad(Key, mHeader.getText().toString(), mDescribtion.getText().toString());
                dismiss();
                break;
        }
    }

    void upLoad(String key, String header, String description) {
        DatabaseReference aDatabaseReference
                = FirebaseDatabase.getInstance().getReference("Note").child(key);
        Model model = new Model(header, description, Key);
        aDatabaseReference.setValue(model);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,1500);
    }
}

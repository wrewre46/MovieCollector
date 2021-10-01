package com.example.termtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Menu3Fragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.recommend, container, false);
        Button today = (Button) v.findViewById(R.id.today);
        Button weekend = (Button) v.findViewById(R.id.weekend);

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent today = new Intent(getActivity(), BoxOffice_Today.class);
                startActivity(today);

            }
        });
        weekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent weekend = new Intent(getActivity(), BoxOffice_Weekly.class);
                startActivity(weekend);

            }
        });


        return v;
    }

}
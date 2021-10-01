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
import android.widget.Toast;

public class Menu4Fragment extends Fragment {
    public Menu4Fragment(){

    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mypage, container, false);
        Button btn_id = (Button) v.findViewById(R.id.btn_id);
        Button cgv1 = (Button) v.findViewById(R.id.cgv1);
        Button lottecinema1 = (Button) v.findViewById(R.id.lottecinema1);
        Button megabox1 = (Button) v.findViewById(R.id.megabox1);
        Button cgv2 = (Button) v.findViewById(R.id.cgv2);
        Button lottecinema2 = (Button) v.findViewById(R.id.lottecinema2);
        Button megabox2 = (Button) v.findViewById(R.id.megabox2);

        btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
              Intent intent = new Intent(getActivity(), IDActivity.class);
              String param1 = getArguments().getString("Para1");
              String param2 = getArguments().getString("Para2");
              intent.putExtra("ID",param1);
              intent.putExtra("Password",param2);
               startActivity(intent);
            }

        });
        cgv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent cgv1 = new Intent(getActivity(), CGVeventActivity.class);
                startActivity(cgv1);

            }
        });
        lottecinema1.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View arg0) {
                Intent lottecinema1 = new Intent(getActivity(), LottecinemaeventActivity.class);
                startActivity(lottecinema1);

            }
        });
        megabox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent megabox1 = new Intent(getActivity(), MegaboxeventActivity.class);
                startActivity(megabox1);

            }
        });
        cgv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent cgv2 = new Intent(getActivity(), CGVdiscountActivity.class);
                startActivity(cgv2);

            }
        });
        lottecinema2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent lottecinema2 = new Intent(getActivity(), LottecinemadiscountActivity.class);
                startActivity(lottecinema2);

            }
        });
        megabox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent megabox2 = new Intent(getActivity(), MegaboxdiscountActivity.class);
                startActivity(megabox2);

            }
        });
        return v;
    }

}

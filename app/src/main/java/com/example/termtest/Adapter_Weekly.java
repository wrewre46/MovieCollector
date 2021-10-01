package com.example.termtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Weekly extends RecyclerView.Adapter<Adapter_Weekly.MyViewHolder> {

    private ArrayList<MovieItem_Weekly> mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_rank, tv_movieNm, tv_openDt, tv_audiAcc;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);

            tv_rank = (TextView) view.findViewById(R.id.tv_rank);
            tv_movieNm = (TextView) view.findViewById(R.id.tv_movieNm);
            tv_openDt = (TextView) view.findViewById(R.id.tv_opneDt);
            tv_audiAcc = (TextView) view.findViewById(R.id.tv_audiAcc);
        }
    }

    //생성자 - 전달되는 데이터타입에 유의하자.
    public Adapter_Weekly(ArrayList<MovieItem_Weekly> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public Adapter_Weekly.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_boxoffice_cardview_weekly, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_rank.setText(String.valueOf(mDataset.get(position).getRank()));
        holder.tv_movieNm.setText("영화 제목 : "+(mDataset.get(position).getMovieNm()));
        holder.tv_openDt.setText("개봉 날짜 : "+mDataset.get(position).getOpenDt());
        holder.tv_audiAcc.setText("누적 관객 수 : "+String.valueOf(mDataset.get(position).getSalesAmt())); //형변환필요
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

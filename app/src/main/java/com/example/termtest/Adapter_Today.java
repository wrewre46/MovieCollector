package com.example.termtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Today extends RecyclerView.Adapter<Adapter_Today.MyViewHolder> {

    private ArrayList<MovieItem_Today> mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_rank, tv_movieNm, tv_openDt, tv_audiCnt;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);

            tv_rank = (TextView) view.findViewById(R.id.tv_rank);
            tv_movieNm = (TextView) view.findViewById(R.id.tv_movieNm);
            tv_openDt = (TextView) view.findViewById(R.id.tv_opneDt);
            tv_audiCnt = (TextView) view.findViewById(R.id.tv_audiCnt);
        }
    }

    //생성자 - 전달되는 데이터타입에 유의하자.
    public Adapter_Today(ArrayList<MovieItem_Today> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public Adapter_Today.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_boxoffice_cardview_today, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_rank.setText(String.valueOf(mDataset.get(position).getRank()));
        holder.tv_movieNm.setText("영화 제목 : "+(mDataset.get(position).getMovieNm()));
        holder.tv_openDt.setText("개봉 날짜 : "+mDataset.get(position).getOpenDt());
        holder.tv_audiCnt.setText("일별 관객 수 : "+String.valueOf(mDataset.get(position).getSalesAmt())); //형변환필요
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

package com.example.termtest;

public class MovieItem_Weekly {
    private int rank;
    private String movieNm;
    private String openDt;
    private long audiAcc;  //long으로

    public MovieItem_Weekly(int rank, String movieNm, String openDt, long audiAcc) {
        this.rank = rank;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.audiAcc = audiAcc;
    }

    //사용자가 입력할일은 없으니 set은 필요가 없고 get만 필요한듯 하다.

    public int getRank() {
        return rank;
    }

    public String getMovieNm() {
        return movieNm;
    }

    public String getOpenDt() {
        return openDt;
    }

    public long getSalesAmt() {
        return audiAcc;
    }
}

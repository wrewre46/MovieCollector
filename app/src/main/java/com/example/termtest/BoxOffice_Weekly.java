package com.example.termtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BoxOffice_Weekly extends Activity{

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String getTime = sdf.format(date);
    int a = Integer.parseInt(getTime)-7;
    String day = Integer.toString(a);
    public static final String TAG = "BoxOffice_Weekly";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<MovieItem_Weekly> itemArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office_weekly);
        TextView day = (TextView)findViewById(R.id.textView_weekly) ;

        itemArrayList = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        MyAsyncTask mProcessTask = new MyAsyncTask();
        mProcessTask.execute();
        boolean inshowRange = false;
        String showRange = null;
    }

    //AsyncTask 생성 - 모든 네트워크 로직을 여기서 작성해 준다.
    public class MyAsyncTask extends AsyncTask<String, Void, MovieItem_Weekly[]> {
        ProgressDialog progressDialog = new ProgressDialog(BoxOffice_Weekly.this);

        //OkHttp 객체생성
        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\tLoading");
            //show dialog
            progressDialog.show();
        }

        @Override
        protected MovieItem_Weekly[] doInBackground(String... params) {

            //parameter를 더해 주거나 authentication header를 추가할 수 있다.
            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json").newBuilder();
            urlBuilder.addQueryParameter("key", "37086fedf1f615d785a1671e2ebaaab3");
            urlBuilder.addQueryParameter("targetDt",day);
            urlBuilder.addQueryParameter("weekGb","0");
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try{
                Response response = client.newCall(request).execute();

                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                //제공char되는 오픈api 데이터에서 어떤 항목을 가져올지 설정.
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("boxOfficeResult").getAsJsonObject().get("weeklyBoxOfficeList");//원하는 항목까지 찾아가서
                MovieItem_Weekly[] posts =gson.fromJson(rootObject, MovieItem_Weekly[].class);


                return posts;
            }catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(MovieItem_Weekly[] result){
            super.onPostExecute(result);
            progressDialog.dismiss();
            //요청결과를 여기서 처리한다.

            if(result.length > 0) {
                for(MovieItem_Weekly post:result){
                    itemArrayList.add(post);
                }
            }

            
            mAdapter = new Adapter_Weekly(itemArrayList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}

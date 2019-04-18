package com.example.fikridzakwan.stadiumclubrestapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.fikridzakwan.stadiumclubrestapi.API.ApiClient;
import com.example.fikridzakwan.stadiumclubrestapi.API.ApiInterface;
import com.example.fikridzakwan.stadiumclubrestapi.Adapter.StadiumAdapter;
import com.example.fikridzakwan.stadiumclubrestapi.Model.Constans;
import com.example.fikridzakwan.stadiumclubrestapi.Model.StadiumData;
import com.example.fikridzakwan.stadiumclubrestapi.Model.StadiumResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Constans {

    @BindView(R.id.rvStadium)
    RecyclerView rvStadium;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private List<StadiumData> stadiumDataList;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        stadiumDataList = new ArrayList<>();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        showProgress();

        getData();

    }

    private void showProgress() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading • • •");
        progressDialog.show();
    }

    private void getData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StadiumResponse> call = apiInterface.getStadium(SOCCER, COUNTRY);
        call.enqueue(new Callback<StadiumResponse>() {
            @Override
            public void onResponse(Call<StadiumResponse> call, Response<StadiumResponse> response) {
                progressDialog.dismiss();
                swipeRefresh.setRefreshing(false);
                StadiumResponse stadiumResponse = response.body();
                stadiumDataList = stadiumResponse.getTeams();

                Log.i("MainActivity","data: " + stadiumDataList );
                rvStadium.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvStadium.setAdapter(new StadiumAdapter(MainActivity.this, stadiumDataList));
            }

            @Override
            public void onFailure(Call<StadiumResponse> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
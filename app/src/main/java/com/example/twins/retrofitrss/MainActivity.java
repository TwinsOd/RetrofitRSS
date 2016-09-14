package com.example.twins.retrofitrss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.twins.retrofitrss.adapters.RecyclerViewAdapter;
import com.example.twins.retrofitrss.data.ApiManager;
import com.example.twins.retrofitrss.model.Item;
import com.example.twins.retrofitrss.model.RssModel;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> rssList;
    private final CompositeSubscription mSubscriptions = new CompositeSubscription();
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null || !savedInstanceState.containsKey("key")) {
            loadDate();
            rssList = new ArrayList<>();
        } else
            rssList = savedInstanceState.getParcelableArrayList("key");

        // Setup RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Setup Adapter
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, rssList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }
    private void loadDate() {
//      need to be access internet

        Subscription subscription = ApiManager.getListImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataSuccess, this::onDataError);
        mSubscriptions.add(subscription);
    }

    private void onDataSuccess(RssModel rssModel) {
        rssList = rssModel.getChannel().getItems();
        for (int i = 0; i < rssList.size(); i++){
            String src = getValue(rssList.get(i).getDescription(), "src");
            rssList.get(i).setSrc(src);
        }

        mRecyclerViewAdapter.swapAll(rssList);
    }
    private void onDataError(Throwable t) {
        //TODO say to user that there is no Internet
        Log.i("MyLog", "onDataError " + t.toString());
    }

    private String getValue(String text, String value){
        int start = text.indexOf(value + "='");
        text = text.substring(start);
        int finish = text.indexOf("/>");
        text = text.substring(4,finish);
        text = text.replace("'","");
        return text;
    }

    @Override
    public void onDestroy() {
        mSubscriptions.clear();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("key", rssList);
        super.onSaveInstanceState(outState);
    }
}

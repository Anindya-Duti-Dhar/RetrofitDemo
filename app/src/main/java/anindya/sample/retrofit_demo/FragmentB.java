package anindya.sample.retrofit_demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class FragmentB extends Fragment {

    //Defining Variables
    // list inflating variable
    //List<Feed> feeds;
    List<Feed> feeds;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    FeedsAdapter adapter;
    SwipeRefreshLayout refreshLayout;

    // create instance
    public static FragmentB newInstance() {
        FragmentB fragment = new FragmentB();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public FragmentB() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // start view initialize and functionality declare from here

        // initialization of feeds list
        //feeds = new ArrayList<Feed>();
        feeds = new ArrayList<Feed>();

        // view initialize
        mRecyclerView = (RecyclerView) view.findViewById(R.id.feedsRecycler);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        // call server to fetch feeds
        getFeeds();

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.feeds_swipe_layout);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        // pull to refresh method
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // load available feeds
                getFeeds();
                //  then stop pull to refresh
                refreshLayout.setRefreshing(false);
            }
        });

        // Select item on list
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Feed data = feeds.get(position);
                        Log.d("duti: " + "Feed ID:", String.valueOf(data.getId()));
                    }
                }
        );

    }

    public void getFeeds(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<FeedResponse> call = apiService.getAllFeeds();
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse>call, Response<FeedResponse> response) {
                feeds.clear();
                feeds = response.body().getFeed();
                Log.d("duti", "Number of feeds received: " + feeds.size());

                for(int i = 0; i<feeds.size(); i++){
                    Log.d("duti", "Name of feeds received: " + feeds.get(i).getImage());
                }

                // set the recycler view to inflate the list
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new FeedsAdapter(getActivity(), feeds);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<FeedResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("duti", t.toString());
            }
        });
    }
}

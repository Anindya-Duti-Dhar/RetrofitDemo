package anindya.sample.retrofit_demo;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("feed.json")
    Call<FeedResponse> getAllFeeds();
}

package anindya.sample.retrofit_demo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedResponse {

    @SerializedName("feed")
    @Expose
    private List<Feed> feed = null;

    public List<Feed> getFeed() {
        return feed;
    }

    public void setFeed(List<Feed> feed) {
        this.feed = feed;
    }

}
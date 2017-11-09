package anindya.sample.retrofit_demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView postImage;
        public CircleImageView userImage;
        public TextView username, timestamp, status, url;
        //public DynamicSquareLayout squareLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.name);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            status = (TextView) itemView.findViewById(R.id.txtStatusMsg);
            url = (TextView) itemView.findViewById(R.id.txtUrl);

            postImage = (ImageView) itemView.findViewById(R.id.feedImage1);
            userImage = (CircleImageView) itemView.findViewById(R.id.profilePic);

            //squareLayout = (DynamicSquareLayout)itemView.findViewById(R.id.square_image_container);
        }
    }

    private List<Feed> _data;
    private Context mContext;

    // Easy access to the context object in the recycler view
    private Context getContext() {
        return mContext;
    }

    public FeedsAdapter(Context context, List<Feed> _data) {
        this._data = _data;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View feedsView = inflater.inflate(R.layout.feed_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(feedsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Feed data = _data.get(position);

        TextView username = viewHolder.username;
        username.setText(data.getName());

        TextView timestamp = viewHolder.timestamp;

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(data.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        TextView status = viewHolder.status;
        String statusTxt = data.getStatus();


        // Chcek for empty status message
        if (!TextUtils.isEmpty(statusTxt)) {
            status.setText(statusTxt);
            status.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            status.setVisibility(View.GONE);
        }

        TextView url = viewHolder.url;
        String urlTxt = data.getUrl();

        // Checking for null feed url
        if (urlTxt != null) {
            url.setVisibility(View.VISIBLE);
            url.setText(data.getUrl());
            url.setText(Html.fromHtml("<a href=\"" + urlTxt + "\">"
                    + urlTxt + "</a> "));
            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        }
        else {
            url.setVisibility(View.GONE);
        }

        //DynamicSquareLayout squareLayout = viewHolder.squareLayout;

        CircleImageView userImage = viewHolder.userImage;
        Picasso.with(mContext)
                .load(data.getProfilePic()).noFade().into(userImage);

        ImageView postImage = viewHolder.postImage;
        String postImageUrl = data.getImage();
        if (postImageUrl != null) {
            //squareLayout.setVisibility(View.VISIBLE);
            postImage.setVisibility(View.VISIBLE);
            Picasso.with(mContext)
                    .load(data.getImage()).noFade().into(postImage);
        }
        else {
            //squareLayout.setVisibility(View.GONE);
            postImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }
}
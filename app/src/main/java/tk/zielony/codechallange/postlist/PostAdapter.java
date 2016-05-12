package tk.zielony.codechallange.postlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.TextView;
import tk.zielony.codechallange.R;
import tk.zielony.codechallange.api.data.Post;

/**
 * Created by Marcin on 2016-05-12.
 */
public class PostAdapter extends carbon.widget.RecyclerView.Adapter<PostAdapter.ViewHolder, Post> {

    private Post[] items = new Post[0];

    public PostAdapter() {
    }

    public void setItems(Post[] items) {
        this.items = items;
    }

    @Override
    public Post getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_post, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PostAdapter.ViewHolder holder, final int position) {
        Post post = items[position];

        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());
    }

    @Override
    public int getItemViewType(int arg0) {
        return 0;
    }

    public Post[] getItems() {
        return items;
    }

    class ViewHolder extends carbon.widget.RecyclerView.ViewHolder {
        @BindView(R.id.cc_title)
        public TextView title;

        @BindView(R.id.cc_body)
        public TextView body;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

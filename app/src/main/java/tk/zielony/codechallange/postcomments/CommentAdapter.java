package tk.zielony.codechallange.postcomments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.TextView;
import tk.zielony.codechallange.R;
import tk.zielony.codechallange.api.data.Comment;

/**
 * Created by Marcin on 2016-05-12.
 */
public class CommentAdapter extends carbon.widget.RecyclerView.Adapter<CommentAdapter.ViewHolder, Comment> {

    private Comment[] items = new Comment[0];

    public CommentAdapter() {
    }

    public void setItems(Comment[] items) {
        this.items = items;
    }

    @Override
    public Comment getItem(int position) {
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
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_comment, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentAdapter.ViewHolder holder, final int position) {
        Comment comment = items[position];

        holder.name.setText(comment.getName());
        holder.body.setText(comment.getBody());
        holder.email.setText(comment.getEmail());
    }

    @Override
    public int getItemViewType(int arg0) {
        return 0;
    }

    public Comment[] getItems() {
        return items;
    }

    class ViewHolder extends carbon.widget.RecyclerView.ViewHolder {
        @BindView(R.id.cc_name)
        public TextView name;

        @BindView(R.id.cc_body)
        public TextView body;

        @BindView(R.id.cc_email)
        public TextView email;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

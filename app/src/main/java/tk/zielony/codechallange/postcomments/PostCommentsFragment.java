package tk.zielony.codechallange.postcomments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.RecyclerView;
import carbon.widget.Toolbar;
import pl.zielony.fragmentmanager.Fragment;
import pl.zielony.fragmentmanager.FragmentManager;
import tk.zielony.codechallange.R;
import tk.zielony.codechallange.api.CommentEndpoint;
import tk.zielony.codechallange.api.data.Comment;
import tk.zielony.codechallange.api.ExceptionEvent;
import tk.zielony.codechallange.api.data.Post;
import tk.zielony.codechallange.api.PostEndpoint;

/**
 * Created by Marcin on 2016-05-12.
 */
public class PostCommentsFragment extends Fragment {
    private static final String TAG = PostCommentsFragment.class.getSimpleName();
    private static final String LIST = "list";

    @BindView(R.id.cc_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.cc_swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.cc_toolbar)
    Toolbar toolbar;

    private Post post;

    private final CommentAdapter adapter;

    public PostCommentsFragment(FragmentManager fragmentManager) {
        super(fragmentManager);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new CommentAdapter();
        recyclerView.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    protected View onCreateView() {
        View view = View.inflate(getContext(), R.layout.fragment_post, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setPost(Post post) {
        this.post = post;

        toolbar.setTitle(post.getTitle());

        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
        loadData();
    }

    /**
     * this method is called only once, after creation
     */
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    /**
     * this method is called every time this fragment starts except the first time
     */
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    /**
     * this method is called every time this fragment finishes except the last time
     */
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    /**
     * this method is called when the fragment won't be used anymore
     */
    @Override
    protected void onFinish() {
        super.onFinish();
        EventBus.getDefault().unregister(this);
    }

    private void loadData() {
        CommentEndpoint.list(post.getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onList(CommentsEvent event) {
        adapter.setItems(event.getData());
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onException(ExceptionEvent event) {
        // Snackbar anyone?
        Log.e(TAG, "api exception", event.getException());
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onSaveState(Bundle bundle) {
        super.onSaveState(bundle);
        try {
            bundle.putString(LIST, new ObjectMapper().writeValueAsString(adapter.getItems()));
        } catch (JsonProcessingException e) {
            // handle it? nah, don't have time
            e.printStackTrace();
        }
    }

    @Override
    public void onRestoreState(Bundle bundle) {
        super.onRestoreState(bundle);
        try {
            adapter.setItems(new ObjectMapper().readValue(bundle.getString(LIST), Comment[].class));
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            // handle it? nah, don't have time
            e.printStackTrace();
        }
    }
}

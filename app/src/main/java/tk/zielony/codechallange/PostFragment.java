package tk.zielony.codechallange;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.RecyclerView;
import carbon.widget.Toolbar;
import pl.zielony.fragmentmanager.Fragment;
import pl.zielony.fragmentmanager.FragmentManager;
import tk.zielony.codechallange.api.CommentsEvent;
import tk.zielony.codechallange.api.ExceptionEvent;
import tk.zielony.codechallange.api.Post;
import tk.zielony.codechallange.api.PostEndpoint;

/**
 * Created by Marcin on 2016-05-12.
 */
public class PostFragment extends Fragment {
    private static final String TAG = PostFragment.class.getSimpleName();

    @BindView(R.id.cc_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.cc_swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.cc_toolbar)
    Toolbar toolbar;

    private Post post;

    private final CommentAdapter adapter;

    public PostFragment(FragmentManager fragmentManager) {
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


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        EventBus.getDefault().unregister(this);
    }

    private void loadData() {
        PostEndpoint.get(post.getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onList(CommentsEvent event) {
        adapter.setItems(event.getData());
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onException(ExceptionEvent event) {
        Log.e(TAG, "api exception", event.getException());
        swipeRefresh.setRefreshing(false);
    }
}

package tk.zielony.codechallange.postlist;

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
import pl.zielony.fragmentmanager.Fragment;
import pl.zielony.fragmentmanager.FragmentManager;
import pl.zielony.fragmentmanager.FragmentState;
import tk.zielony.codechallange.R;
import tk.zielony.codechallange.api.ExceptionEvent;
import tk.zielony.codechallange.api.data.Post;
import tk.zielony.codechallange.api.PostEndpoint;
import tk.zielony.codechallange.postcomments.PostCommentsFragment;

/**
 * Created by Marcin on 2016-05-12.
 */
public class PostListFragment extends Fragment {
    private static final String TAG = PostListFragment.class.getSimpleName();
    private static final String LIST = "list";

    @BindView(R.id.cc_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.cc_swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private final PostAdapter adapter;

    public PostListFragment(FragmentManager fragmentManager) {
        super(fragmentManager);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new PostAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.setOnItemClickedListener(new RecyclerView.OnItemClickedListener() {
            @Override
            public void onItemClicked(int i) {
                openPost(adapter.getItem(i));
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    protected View onCreateView() {
        View view = View.inflate(getContext(), R.layout.fragment_main, null);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * this method is called only once, after creation
     */
    @Override
    protected void onStart() {
        super.onStart();
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
        loadData();
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
        PostEndpoint.list();
    }

    private void openPost(Post item) {
        PostCommentsFragment fragment = getFragmentManager().replace(PostCommentsFragment.class, R.id.cc_root, FragmentState.Mode.Add);
        fragment.setPost(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onList(PostListEvent event) {
        adapter.setItems(event.getData());
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onException(ExceptionEvent event) {
        // I should show a Snackbar here
        Log.e(TAG, "api exception", event.getException());
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onSaveState(Bundle bundle) {
        super.onSaveState(bundle);
        try {
            bundle.putString(LIST, new ObjectMapper().writeValueAsString(adapter.getItems()));
        } catch (JsonProcessingException e) {
            // no time for exception handling
            e.printStackTrace();
        }
    }

    @Override
    public void onRestoreState(Bundle bundle) {
        super.onRestoreState(bundle);
        try {
            adapter.setItems(new ObjectMapper().readValue(bundle.getString(LIST), Post[].class));
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            // no time for exception handling
            e.printStackTrace();
        }
    }

}

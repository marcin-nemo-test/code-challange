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
import pl.zielony.fragmentmanager.Fragment;
import pl.zielony.fragmentmanager.FragmentManager;
import tk.zielony.codechallange.api.ExceptionEvent;
import tk.zielony.codechallange.api.ListEvent;
import tk.zielony.codechallange.api.PostEndpoint;

/**
 * Created by Marcin on 2016-05-12.
 */
public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private final PostAdapter adapter;

    @BindView(R.id.cc_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.cc_swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    public MainFragment(FragmentManager fragmentManager) {
        super(fragmentManager);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new PostAdapter();
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
        View view = View.inflate(getContext(), R.layout.fragment_main, null);
        ButterKnife.bind(this, view);
        return view;
    }

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
        PostEndpoint.list();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onList(ListEvent event) {
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

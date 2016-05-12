package tk.zielony.codechallange;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.RecyclerView;
import pl.zielony.fragmentmanager.Fragment;
import pl.zielony.fragmentmanager.FragmentManager;

/**
 * Created by Marcin on 2016-05-12.
 */
public class MainFragment extends Fragment {
    @BindView(R.id.cc_recycler)
    RecyclerView recyclerView;

    public MainFragment(FragmentManager fragmentManager) {
        super(fragmentManager);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new PostAdapter());
    }

    @Override
    protected View onCreateView() {
        View view = View.inflate(getContext(), R.layout.fragment_main, null);
        ButterKnife.bind(this, view);
        return view;
    }
}

package tk.zielony.codechallange;

import android.os.Bundle;

import pl.zielony.fragmentmanager.FragmentActivity;
import pl.zielony.fragmentmanager.FragmentState;
import tk.zielony.codechallange.postlist.PostListFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // first time, add fragment manually
        if (savedInstanceState == null)
            getFragmentManager2().add(PostListFragment.class, R.id.cc_root, FragmentState.Mode.Join);
    }
}

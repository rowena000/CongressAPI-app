package shanchi.congressapi;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class CommitteeFragment extends Fragment {
    private FragmentTabHost mTabHost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("HOUSE").setIndicator("HOUSE"), CommitteeHouseListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("SENATE").setIndicator("SENATE"), CommitteeSenateListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("JOINT").setIndicator("JOINT"), CommitteeJointListFragment.class, null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                setTabColor(mTabHost);
            }

            private void setTabColor(TabHost tabHost) {
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    TextView view = (TextView)tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    view.setTypeface(null, Typeface.NORMAL);
                }

                TextView textView = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
                textView.setTypeface(null, Typeface.BOLD);
            }
        });

        return mTabHost;
    }
}

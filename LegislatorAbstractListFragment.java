package shanchi.congressapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import shanchi.congressapi.object.LegislatorObject;

public class LegislatorAbstractListFragment extends ListFragment
        implements View.OnClickListener {

    public static final int OPEN_DETAIL = 1001;
    ArrayList legislatorList;
    LegislatorFetchTask mTask;
    Map<String, Integer> mapIndex;
    ListView legislatorListView;
    LinearLayout indexLayout;
    TextView inprogressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_legislatorlist, container, false);

        legislatorListView = (ListView) v.findViewById(android.R.id.list);
//        legislatorListView = (ListView) v.findViewById(R.id.legislator_listview);
        indexLayout = (LinearLayout) v.findViewById(R.id.side_index);
        inprogressView = (TextView) v.findViewById(R.id.text_inprogress);

        startURLFetch();

        return v;
    }

    public void getIndexList(ArrayList legislatorList) {

    }

    private void displayIndex() {

        TextView textView;
        if (mapIndex != null && indexLayout != null) {
            if (indexLayout.getChildCount() > 0) {
                indexLayout.removeAllViews();
            }
            List<String> indexList = new ArrayList<String>(mapIndex.keySet());
            for (String index : indexList) {
                textView = (TextView) this.getActivity().getLayoutInflater().inflate(R.layout.side_index_item, null);
                textView.setText(index);
                textView.setOnClickListener(this);
                indexLayout.addView(textView);
            }
        }
    }

    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        legislatorListView.setSelection(mapIndex.get(selectedIndex.getText()));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (isTaskRunning(mTask)) {
            showInprogress();
        } else {
            hideInprogress();
        }

        if (legislatorList != null) {
            populateResult(legislatorList);
        }
        super.onActivityCreated(savedInstanceState);
    }

    public void showInprogress() {
        legislatorListView.setVisibility(View.GONE);
        inprogressView.setVisibility(View.VISIBLE);
    }

    public void hideInprogress() {
        inprogressView.setVisibility(View.GONE);
        legislatorListView.setVisibility(View.VISIBLE);
    }

    public void populateResult(ArrayList<LegislatorObject> legislatorList) {
        ArrayAdapter<String> adapter = getLegislators(legislatorList);
        legislatorListView.setAdapter(adapter);
        displayIndex();

    }

    public ArrayAdapter getLegislators(ArrayList<LegislatorObject> legislatorList) {
        LegislatorCustomAdapter adapter = new LegislatorCustomAdapter(getActivity(), R.layout.legislator_list_item, legislatorList);
        getIndexList(legislatorList);
        return adapter;
    }

    public void startURLFetch() {

    }

    protected boolean isTaskRunning(LegislatorFetchTask task) {
        if (task == null) {
            return  false;
        } else if (task.getStatus() == LegislatorFetchTask.Status.FINISHED) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        LegislatorCustomAdapter adapter = (LegislatorCustomAdapter)l.getAdapter();
        LegislatorObject object = adapter.getItem(position);

        Intent intent = new Intent(getContext(), LegislatorDetailActivity.class);
        intent.putExtra("detailsObject", object);

//        startActivity(intent);
        startActivityForResult(intent, OPEN_DETAIL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_DETAIL && this instanceof LegislatorFavoriteListFragment) {
            startURLFetch();
        }
    }
}

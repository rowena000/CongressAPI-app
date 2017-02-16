package shanchi.congressapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


import shanchi.congressapi.object.CommitteeObject;

public class CommitteeAbstractFragment extends ListFragment {
    public static final int OPEN_DETAIL = 1001;
    CommitteeFetchTask mTask;
    ListView committeeView;
    TextView inprogressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_billlist, container, false);

        committeeView = (ListView) v.findViewById(android.R.id.list);
        inprogressView = (TextView) v.findViewById(R.id.text_inprogress);

        startURLFetch();

        return v;
    }

    public void startURLFetch() {

    }


    public void showInprogress() {
        committeeView.setVisibility(View.GONE);
        inprogressView.setVisibility(View.VISIBLE);
    }

    public void hideInprogress() {
        inprogressView.setVisibility(View.GONE);
        committeeView.setVisibility(View.VISIBLE);
    }

    public void populateResult(ArrayList<CommitteeObject> committeeList) {
        ArrayAdapter<String> adapter = getCommittees(committeeList);
        committeeView.setAdapter(adapter);

    }

    public ArrayAdapter getCommittees(ArrayList<CommitteeObject> committeeList) {
        CommitteeCustomAdapter adapter = new CommitteeCustomAdapter(getActivity(), R.layout.bill_list_item, committeeList);
        return adapter;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        CommitteeCustomAdapter adapter = (CommitteeCustomAdapter)l.getAdapter();
        CommitteeObject object = adapter.getItem(position);

        Intent intent = new Intent(getContext(), CommitteeDetailActivity.class);
        intent.putExtra("detailsObject", object);

        startActivityForResult(intent, OPEN_DETAIL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_DETAIL & this instanceof CommitteeFavoriteListFragment) {
            startURLFetch();
        }
    }
}

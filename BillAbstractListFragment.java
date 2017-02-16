package shanchi.congressapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import shanchi.congressapi.object.BillObject;
import shanchi.congressapi.object.LegislatorObject;
import shanchi.congressapi.object.StoredListObject;

public class BillAbstractListFragment extends ListFragment {
    public static final int OPEN_DETAIL = 1001;
    BillFetchTask mTask;
    ListView billView;
    TextView inprogressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_billlist, container, false);

        billView = (ListView) v.findViewById(android.R.id.list);
        inprogressView = (TextView) v.findViewById(R.id.text_inprogress);

        startURLFetch();

        return v;
    }

    public void startURLFetch() {

    }


    public void showInprogress() {
        billView.setVisibility(View.GONE);
        inprogressView.setVisibility(View.VISIBLE);
    }

    public void hideInprogress() {
        inprogressView.setVisibility(View.GONE);
        billView.setVisibility(View.VISIBLE);
    }

    public void populateResult(ArrayList<BillObject> billList) {
        ArrayAdapter<String> adapter = getBills(billList);
        billView.setAdapter(adapter);

    }

    public ArrayAdapter getBills(ArrayList<BillObject> billList) {
        BillCustomAdapter adapter = new BillCustomAdapter(getActivity(), R.layout.bill_list_item, billList);

        return adapter;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        BillCustomAdapter adapter = (BillCustomAdapter)l.getAdapter();
        BillObject object = adapter.getItem(position);

        Intent intent = new Intent(getContext(), BillDetailActivity.class);
        intent.putExtra("detailsObject", object);

//        startActivity(intent);
        startActivityForResult(intent, OPEN_DETAIL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_DETAIL & this instanceof BillFavoriteListFragment) {
            startURLFetch();
        }
    }

}

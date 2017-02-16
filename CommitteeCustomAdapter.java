package shanchi.congressapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import shanchi.congressapi.object.BillObject;
import shanchi.congressapi.object.CommitteeObject;


public class CommitteeCustomAdapter extends ArrayAdapter<CommitteeObject> {
    private ArrayList<CommitteeObject> committeeList;
    private final Context context;

    public CommitteeCustomAdapter(Context context, int textViewResourceId, ArrayList<CommitteeObject> list) {
        super(context, textViewResourceId, list);
        this.context = context;
        committeeList = new ArrayList<CommitteeObject>(list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.bill_list_item, parent, false);


        TextView billId = (TextView) rowView.findViewById(R.id.bill_id);
        TextView billTitle = (TextView) rowView.findViewById(R.id.bill_title);
        TextView activeat = (TextView) rowView.findViewById(R.id.bill_active_at);

        CommitteeObject obj = committeeList.get(position);

        billId.setText(obj.getCommittee_id());
        billTitle.setText(obj.getName());
        activeat.setText(obj.getChamber());

        return rowView;
    }

    @Nullable
    @Override
    public CommitteeObject getItem(int position) {
        return committeeList.get(position);
    }
}

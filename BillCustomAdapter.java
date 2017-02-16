package shanchi.congressapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import shanchi.congressapi.object.BillObject;
import shanchi.congressapi.object.LegislatorObject;


public class BillCustomAdapter extends ArrayAdapter<BillObject> {
    private ArrayList<BillObject> billList;
    private final Context context;

    public BillCustomAdapter(Context context, int textViewResourceId, ArrayList<BillObject> list) {
        super(context, textViewResourceId, list);
        this.context = context;
        billList = new ArrayList<BillObject>(list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.bill_list_item, parent, false);


        TextView billId = (TextView) rowView.findViewById(R.id.bill_id);
        TextView billTitle = (TextView) rowView.findViewById(R.id.bill_title);
        TextView activeat = (TextView) rowView.findViewById(R.id.bill_active_at);

        BillObject obj = billList.get(position);
        billId.setText(obj.getBillId());
        billTitle.setText(obj.getOfficialTitle());
        activeat.setText(obj.getIntroducedOn());

        return rowView;
    }

    @Nullable
    @Override
    public BillObject getItem(int position) {
        return billList.get(position);
    }
}

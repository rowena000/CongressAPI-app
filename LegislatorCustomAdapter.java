package shanchi.congressapi;

import android.content.Context;
import android.media.Image;
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

import shanchi.congressapi.object.LegislatorObject;

public class LegislatorCustomAdapter extends ArrayAdapter<LegislatorObject> {
    private ArrayList<LegislatorObject> legislatorList;
    private final Context context;

    public LegislatorCustomAdapter(Context context, int textViewResourceId, ArrayList<LegislatorObject> list) {
        super(context, textViewResourceId, list);
        this.context = context;
        legislatorList = new ArrayList<LegislatorObject>(list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.legislator_list_item, parent, false);

        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.thumbnail);
        TextView fullName = (TextView) rowView.findViewById(R.id.fullname);
        TextView desc = (TextView) rowView.findViewById(R.id.description);
        ImageView arrow = (ImageView) rowView.findViewById(R.id.arrow_icon);

        LegislatorObject obj = legislatorList.get(position);
        fullName.setText(obj.getLastName() + ", " + obj.getFirstName());
        desc.setText("(" + obj.getParty() + ")" + obj.getState() + " - " + obj.getDistrict());

        String thumbnailURL = "https://theunitedstates.io/images/congress/original/" + obj.getBioguideId() + ".jpg";
        Picasso.with(context).load(thumbnailURL).into(thumbnail);

        return rowView;
    }

    @Nullable
    @Override
    public LegislatorObject getItem(int position) {
        return legislatorList.get(position);
    }
}

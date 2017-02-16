package shanchi.congressapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import shanchi.congressapi.object.BillObject;
import shanchi.congressapi.object.LegislatorObject;
import shanchi.congressapi.object.StoredListObject;

public class BillDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        setTitle("Bill Info");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        BillObject object = (BillObject) extra.getSerializable("detailsObject");

        ImageView fav_icon = (ImageView) findViewById(R.id.detail_fav_bill);
        fav_icon.setOnClickListener(new IconOnclickListener(object));
        if (existInStorage(object.getBillId())) {
            fav_icon.setImageResource(R.mipmap.ic_yellow_star);
        }
        TextView billID = (TextView)findViewById(R.id.detail_billID);
        billID.setText(object.getBillId());

        TextView title = (TextView)findViewById(R.id.detail_title);
        title.setText(object.getOfficialTitle());

        TextView type = (TextView)findViewById(R.id.detail_type);
        type.setText(object.getBillType());

        TextView sponsor = (TextView)findViewById(R.id.detail_sponsor);
        sponsor.setText(object.getSponsor());

        TextView chamber = (TextView)findViewById(R.id.detail_chamber_bill);
        chamber.setText(object.getChamber());

        TextView status = (TextView)findViewById(R.id.detail_status);
        status.setText(object.getStatus());

        TextView introducedOn = (TextView)findViewById(R.id.detail_introducedOn);
        introducedOn.setText(object.getIntroducedOn());

        TextView congressUrl = (TextView)findViewById(R.id.detail_congress_url);
        congressUrl.setText(object.getCongressUrl());

        TextView versionStatus = (TextView)findViewById(R.id.detail_version_status);
        versionStatus.setText(object.getVersionStatus());

        TextView billUrl = (TextView)findViewById(R.id.detail_bill_url);
        billUrl.setText(object.getBillUrl());


    }

    private boolean existInStorage(String id) {
        SharedPreferences preferences = getSharedPreferences("StoredData", Context.MODE_PRIVATE);

        StoredListObject listObject;
        String serilizedData = preferences.getString("ListObject", null);
        if (serilizedData != null) {
            listObject = StoredListObject.create(serilizedData);
            return listObject.getBills().containsKey(id);
        }
        return  false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class IconOnclickListener implements View.OnClickListener {
        BillObject mObj;

        public IconOnclickListener(BillObject object) {
            this.mObj = object;
        }

        @Override
        public void onClick(View view) {
            ImageView img = (ImageView)view;

            SharedPreferences preferences =  getSharedPreferences("StoredData", Context.MODE_PRIVATE);

            StoredListObject listObject;
            String serilizedData = preferences.getString("ListObject", null);
            if (serilizedData == null) {
                listObject = new StoredListObject();
            } else {
                listObject = StoredListObject.create(serilizedData);
            }

            if (listObject.getBills().containsKey(mObj.getBillId())) {
                //if exist, remove from storage
                listObject.getBills().remove(mObj.getBillId());
                img.setImageResource(R.mipmap.ic_empty_star);
            } else {
                //if not exist, add to storage
                listObject.getBills().put(mObj.getBillId(), mObj);
                img.setImageResource(R.mipmap.ic_yellow_star);
            }

            SharedPreferences.Editor editor = preferences.edit();
            String test = listObject.serialize();
            editor.putString("ListObject", test);
            editor.commit();
        }
    }
}

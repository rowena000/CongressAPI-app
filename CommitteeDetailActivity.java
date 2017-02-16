package shanchi.congressapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import shanchi.congressapi.object.CommitteeObject;
import shanchi.congressapi.object.StoredListObject;

public class CommitteeDetailActivity  extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_committee_detail);

        setTitle("Committee Info");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        CommitteeObject object = (CommitteeObject) extra.getSerializable("detailsObject");

        ImageView fav_icon = (ImageView) findViewById(R.id.detail_fav_committee);
        fav_icon.setOnClickListener(new CommitteeDetailActivity.IconOnclickListener(object));
        if (existInStorage(object.getCommittee_id())) {
            fav_icon.setImageResource(R.mipmap.ic_yellow_star);
        }

        TextView id = (TextView)findViewById(R.id.detail_committeeID);
        id.setText(object.getCommittee_id());

        TextView name = (TextView)findViewById(R.id.detail_committeeName);
        name.setText(object.getName());

        ImageView chamberIcon = (ImageView)findViewById(R.id.detail_chamberimg);
        if (object.getChamber().equals("House")) {
            chamberIcon.setImageResource(R.mipmap.ic_house);
        } else if (object.getChamber().equals("Senate")) {
            chamberIcon.setImageResource(R.mipmap.ic_senate);
        }
        TextView chamber = (TextView)findViewById(R.id.detail_chambername);
        chamber.setText(object.getChamber());

        TextView parrentComm = (TextView) findViewById(R.id.detail_parrentComm);
        parrentComm.setText(object.getParent_committee_id());

        TextView phone = (TextView) findViewById(R.id.detail_phone);
        phone.setText(object.getPhone());

        TextView office = (TextView) findViewById(R.id.detail_office);
        office.setText(object.getOffice());
    };

    private boolean existInStorage(String id) {
        SharedPreferences preferences = getSharedPreferences("StoredData", Context.MODE_PRIVATE);

        StoredListObject listObject;
        String serilizedData = preferences.getString("ListObject", null);
        if (serilizedData != null) {
            listObject = StoredListObject.create(serilizedData);
            return listObject.getCommittees().containsKey(id);
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
        CommitteeObject mObj;

        public IconOnclickListener(CommitteeObject object) {
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

            if (listObject.getCommittees().containsKey(mObj.getCommittee_id())) {
                //if exist, remove from storage
                listObject.getCommittees().remove(mObj.getCommittee_id());
                img.setImageResource(R.mipmap.ic_empty_star);
            } else {
                //if not exist, add to storage
                listObject.getCommittees().put(mObj.getCommittee_id(), mObj);
                img.setImageResource(R.mipmap.ic_yellow_star);
            }

            SharedPreferences.Editor editor = preferences.edit();
            String test = listObject.serialize();
            editor.putString("ListObject", test);
            editor.commit();
        }
    }
}

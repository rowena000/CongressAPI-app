package shanchi.congressapi;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import shanchi.congressapi.object.LegislatorObject;
import shanchi.congressapi.object.StoredListObject;


public class LegislatorDetailActivity extends ActionBarActivity {

    private ProgressBar mProgress;
    private int mProgressStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislator_detail);

        setTitle("Legislator Info");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        LegislatorObject object = (LegislatorObject)extra.getSerializable("detailsObject");

        //render UI
        TextView name = (TextView)findViewById(R.id.detail_name);
        ImageView thumbnail = (ImageView)findViewById(R.id.detail_thumbnail);

        name.setText(object.getTitle() + ". " + object.getLastName() + ", " + object.getFirstName());
        String thumbnailURL = "https://theunitedstates.io/images/congress/original/" + object.getBioguideId() + ".jpg";
        Picasso.with(this.getApplicationContext()).load(thumbnailURL).into(thumbnail);

        TextView email = (TextView)findViewById(R.id.detail_email);
        email.setText(object.getEmail());

        TextView chamber = (TextView)findViewById(R.id.detail_chamber);
        chamber.setText(object.getChamber());

        TextView contact = (TextView)findViewById(R.id.detail_contact);
        contact.setText(object.getContact());

        TextView start_term = (TextView)findViewById(R.id.detail_start_term);
        start_term.setText(object.getStart_term());

        TextView end_term = (TextView)findViewById(R.id.detail_end_term);
        end_term.setText(object.getEnd_term());

        TextView office = (TextView)findViewById(R.id.detail_office);
        office.setText(object.getOffice());

        TextView state = (TextView)findViewById(R.id.detail_state);
        state.setText(object.getState_short());

        TextView fax = (TextView)findViewById(R.id.detail_fax);
        fax.setText(object.getFax());

        TextView birthday = (TextView)findViewById(R.id.detail_birthday);
        birthday.setText(object.getBirthday());

        mProgress = (ProgressBar) findViewById(R.id.detail_term);

        DateFormat df = new SimpleDateFormat("MMM dd, yyyy");

        String startDateString = object.getStart_term();
        String endDateString = object.getEnd_term();

        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = df.parse(startDateString);
            endDate = df.parse(endDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date now = new Date();

        mProgressStatus = (int) ((now.getTime() - startDate.getTime()) * 100 / (endDate.getTime() - startDate.getTime()));
        mProgress.setProgress(mProgressStatus);

        TextView percentage = (TextView)findViewById(R.id.detail_percentage);
        percentage.setText(mProgressStatus + "%");
        int px = (int)convertDpToPixel(150 * (100 - mProgressStatus) / 100, this);
        percentage.setPadding(0,0,px,0);

        TextView party = (TextView)findViewById(R.id.detail_party);
        party.setText("R".equals(object.getParty()) ? "Republican" : "Democrat");

        ImageView partyIcon = (ImageView)findViewById(R.id.detail_party_icon);
        partyIcon.setImageResource("R".equals(object.getParty()) ? R.mipmap.ic_party_r : R.mipmap.ic_party_d);

        ImageView icon_fav = (ImageView)findViewById(R.id.detail_fav);
        ImageView icon_fb = (ImageView)findViewById(R.id.detail_fb);
        ImageView icon_tw = (ImageView)findViewById(R.id.detail_tw);
        ImageView icon_web = (ImageView)findViewById(R.id.detail_web);

        icon_fav.setOnClickListener(new IconOnclickListener(object));
        icon_fb.setOnClickListener(new IconOnclickListener(object));
        icon_tw.setOnClickListener(new IconOnclickListener(object));
        icon_web.setOnClickListener(new IconOnclickListener(object));

        if (existInStorage(object.getBioguideId())) {
            icon_fav.setImageResource(R.mipmap.ic_yellow_star);
        }
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
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

    private boolean existInStorage(String guid) {
        SharedPreferences preferences = getSharedPreferences("StoredData", Context.MODE_PRIVATE);

        StoredListObject listObject;
        String serilizedData = preferences.getString("ListObject", null);
        if (serilizedData != null) {
            listObject = StoredListObject.create(serilizedData);
            return listObject.getLegislators().containsKey(guid);
        }
        return  false;
    }

    private class IconOnclickListener implements View.OnClickListener {
        LegislatorObject mObj;

        public IconOnclickListener(LegislatorObject object) {
            this.mObj = object;
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.detail_fav) {
                ImageView img = (ImageView)view;

                SharedPreferences preferences = getSharedPreferences("StoredData", Context.MODE_PRIVATE);

                StoredListObject listObject;
                String serilizedData = preferences.getString("ListObject", null);
                if (serilizedData == null) {
                    listObject = new StoredListObject();
                } else {
                    listObject = StoredListObject.create(serilizedData);
                }

                if (listObject.getLegislators().containsKey(mObj.getBioguideId())) {
                    //if exist, remove from storage
                    listObject.getLegislators().remove(mObj.getBioguideId());
                    img.setImageResource(R.mipmap.ic_empty_star);
                } else {
                    //if not exist, add to storage
                    listObject.getLegislators().put(mObj.getBioguideId(), mObj);
                    img.setImageResource(R.mipmap.ic_yellow_star);
                }

                SharedPreferences.Editor editor = preferences.edit();
                String test = listObject.serialize();
                editor.putString("ListObject", test);
                editor.commit();

            } else if (id == R.id.detail_fb) {
                if (mObj.getFacebook() == null) {
                    showToast("Facebook page not exist");
                } else {
                    openPage(mObj.getFacebook());
                }

            } else if (id == R.id.detail_tw) {
                if (mObj.getTwitter() == null) {
                    showToast("Twitter page not exist");
                } else {
                    openPage(mObj.getTwitter());
                }
            } else if (id == R.id.detail_web) {
                if (mObj.getwebsite() == null) {
                    showToast("Web page not exist");
                } else {
                    openPage(mObj.getwebsite());
                }
            }
        }

        private void showToast(String msg) {
            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        private void openPage(String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

}

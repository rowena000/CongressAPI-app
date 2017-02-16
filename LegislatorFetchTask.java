package shanchi.congressapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import shanchi.congressapi.object.LegislatorObject;
import shanchi.congressapi.object.StoredListObject;


public class LegislatorFetchTask extends AsyncTask<String, String, Object> {

    LegislatorAbstractListFragment container;
    String content;
    boolean sortByState;

    public LegislatorFetchTask(LegislatorAbstractListFragment f, boolean byState) {
        this.container = f;
        sortByState = byState;
    }

    @Override
    protected Object doInBackground(String... strings) {
        BufferedReader reader = null;
        try {
            // API.fetchURL(params[0]);
            String urlStr = strings[0];
            if ("favorites".equals(urlStr)) {

                SharedPreferences preferences = container.getActivity().getSharedPreferences("StoredData", Context.MODE_PRIVATE);
                StoredListObject listObject;
                String serilizedData = preferences.getString("ListObject", null);
                if (serilizedData != null) {
                    listObject = StoredListObject.create(serilizedData);
                    LinkedHashMap<String, LegislatorObject> legislatorMap = listObject.getLegislators();
                    return legislatorMap;
                }
            }

            URL url = new URL(strings[0]);
            URLConnection conn = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            content = sb.toString();

        }catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {}
        }
        System.out.println(strings[0]);
//        return Arrays.asList(DataSource.legislatorName);
        return content;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        container.showInprogress();
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (container != null && container.getActivity() != null) {
            try {
                ArrayList<LegislatorObject> legiList = new ArrayList<LegislatorObject>();

                if (result instanceof String) {
                    JSONObject jsonResponse = new JSONObject(content);
                    JSONArray results = jsonResponse.optJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        LegislatorObject legiObj = new LegislatorObject();
                        JSONObject resultObj = results.getJSONObject(i);
                        String name = resultObj.optString("last_name") + ", " + resultObj.optString("first_name");
                        legiObj.setFirstName(resultObj.optString("first_name"));
                        legiObj.setLastName(resultObj.optString("last_name"));
                        legiObj.setBioguideId(resultObj.optString("bioguide_id"));
                        legiObj.setParty(resultObj.optString("party"));
                        legiObj.setDistrict(resultObj.optString("district"));
                        legiObj.setState(resultObj.optString("state_name"));
                        legiObj.setEmail(resultObj.optString("oc_email"));
                        legiObj.setTitle(resultObj.optString("title"));
                        legiObj.setChamber(resultObj.optString("chamber"));
                        legiObj.setContact(resultObj.optString("phone"));
                        legiObj.setStart_term(resultObj.optString("term_start"));
                        legiObj.setEnd_term(resultObj.optString("term_end"));
                        legiObj.setOffice(resultObj.optString("office"));
                        legiObj.setFax(resultObj.optString("fax"));
                        legiObj.setBirthday(resultObj.optString("birthday"));
                        legiObj.setState_short(resultObj.optString("state"));
                        legiObj.setFacebook(resultObj.optString("facebook_id"));
                        legiObj.setTwitter(resultObj.optString("twitter_id"));
                        legiObj.setwebsite(resultObj.optString("website"));
                        legiList.add(legiObj);

                    }
                } else if (result instanceof LinkedHashMap) {
                    LinkedHashMap<String, LegislatorObject> legislatorMap = (LinkedHashMap)result;
                    for (String key : legislatorMap.keySet()) {
                        legiList.add(legislatorMap.get(key));
                    }
                }

                if (sortByState) {
                    Collections.sort(legiList, new legislatorByStateComparator());
                } else {
                    Collections.sort(legiList);
                }
                container.populateResult(legiList);
                container.hideInprogress();
            } catch (Exception e) {

            }
            this.container = null;
        }
    }
}

class legislatorByStateComparator implements Comparator<LegislatorObject> {
    public int compare(LegislatorObject l1, LegislatorObject l2) {
        return l1.getState().compareTo(l2.getState());
    }
}

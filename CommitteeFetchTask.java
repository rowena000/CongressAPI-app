package shanchi.congressapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import shanchi.congressapi.object.CommitteeObject;
import shanchi.congressapi.object.StoredListObject;


public class CommitteeFetchTask extends AsyncTask<String, String, Object> {
    CommitteeAbstractFragment container;
    String content;

    public CommitteeFetchTask(CommitteeAbstractFragment f) {
        this.container = f;
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
                    LinkedHashMap<String, CommitteeObject> committeeMap = listObject.getCommittees();
                    return committeeMap;
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
                ArrayList<CommitteeObject> committeeList = new ArrayList<CommitteeObject>();

                if (result instanceof String) {
                    JSONObject jsonResponse = new JSONObject(content);
                    JSONArray results = jsonResponse.optJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        CommitteeObject committeeObject = new CommitteeObject();
                        JSONObject resultObj = results.getJSONObject(i);
                        committeeObject.setChamber(resultObj.optString("chamber"));
                        committeeObject.setCommittee_id(resultObj.optString("committee_id"));
                        committeeObject.setName(resultObj.optString("name"));
                        committeeObject.setSubcommittee(resultObj.optString("subcommittee"));

                        boolean subcommitee = resultObj.optBoolean("subcommittee");
                        if (subcommitee) {
                            committeeObject.setParent_committee_id(resultObj.optString("parent_committee_id"));
                        } else {
                            committeeObject.setParent_committee_id("N.A.");
                        }

                        committeeObject.setOffice(resultObj.optString("office"));

                        committeeObject.setPhone(resultObj.optString("phone"));

                        committeeList.add(committeeObject);

                    }
                } else if (result instanceof LinkedHashMap) {
                    LinkedHashMap<String, CommitteeObject> committeeMap = (LinkedHashMap)result;
                    for (String key : committeeMap.keySet()) {
                        committeeList.add(committeeMap.get(key));
                    }
                }

                Collections.sort(committeeList);

                container.populateResult(committeeList);
                container.hideInprogress();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.container = null;
        }
    }
}

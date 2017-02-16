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

import shanchi.congressapi.object.BillObject;
import shanchi.congressapi.object.StoredListObject;


public class BillFetchTask extends AsyncTask<String, String, Object> {
    BillAbstractListFragment container;
    String content;

    public BillFetchTask(BillAbstractListFragment f) {
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
                    LinkedHashMap<String, BillObject> billMap = listObject.getBills();
                    return billMap;
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
                ArrayList<BillObject> billList = new ArrayList<BillObject>();

                if (result instanceof String) {
                    JSONObject jsonResponse = new JSONObject(content);
                    JSONArray results = jsonResponse.optJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        BillObject billObject = new BillObject();
                        JSONObject resultObj = results.getJSONObject(i);
                        billObject.setBillId(resultObj.optString("bill_id"));
                        billObject.setOfficialTitle(resultObj.optString("official_title"));
                        billObject.setBillType(resultObj.optString("bill_type"));
                        billObject.setSponsor(resultObj.optJSONObject("sponsor").optString("title") + ". " +
                                resultObj.optJSONObject("sponsor").optString("last_name") + ", " + resultObj.optJSONObject("sponsor").optString("first_name"));

                        billObject.setChamber(resultObj.optString("chamber"));
                        billObject.setStatus(resultObj.optJSONObject("history").optString("active"));
                        billObject.setIntroducedOn(resultObj.optString("introduced_on"));
                        billObject.setCongressUrl(resultObj.optJSONObject("urls").optString("congress"));

                        JSONObject latestVersionJson = resultObj.optJSONObject("last_version");
                        if (latestVersionJson != null && latestVersionJson != JSONObject.NULL) {
                            billObject.setVersionStatus(latestVersionJson.optString("version_name"));
                            JSONObject urlJson = latestVersionJson.optJSONObject("urls");
                            if (urlJson != null && urlJson != JSONObject.NULL) {
                                billObject.setBillUrl(urlJson.optString("pdf"));
                            }
                        } else {
                            billObject.setVersionStatus("N.A.");
                            billObject.setBillUrl("N.A.");
                        }
                        

                        billList.add(billObject);
                    }
                } else if (result instanceof LinkedHashMap) {
                    LinkedHashMap<String, BillObject> billMap = (LinkedHashMap)result;
                    for (String key : billMap.keySet()) {
                        billList.add(billMap.get(key));
                    }
                }
                Collections.sort(billList);
                container.populateResult(billList);
                container.hideInprogress();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.container = null;
        }
    }
}

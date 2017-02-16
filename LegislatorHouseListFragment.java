package shanchi.congressapi;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import shanchi.congressapi.object.LegislatorObject;


public class LegislatorHouseListFragment extends LegislatorAbstractListFragment {

    public void startURLFetch() {
        //String url = "http://104.198.0.197:8080/legislators&chamber=house";
        String url = "http://congressinformation.hwpssmc2mf.us-west-2.elasticbeanstalk.com/?congressdb=legislators&chamber=house";
        mTask = new LegislatorFetchTask(this, false);
        mTask.execute(url);

    }

    public void getIndexList(ArrayList legislatorList) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < legislatorList.size(); i++) {
            String item = ((LegislatorObject) legislatorList.get(i)).getLastName();
            String index = item.substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }
}

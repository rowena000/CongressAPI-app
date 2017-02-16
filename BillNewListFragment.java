package shanchi.congressapi;


public class BillNewListFragment extends BillAbstractListFragment {
    public void startURLFetch() {
        String url = "http://congressinformation.hwpssmc2mf.us-west-2.elasticbeanstalk.com/?congressdb=bills&active=false";
        mTask = new BillFetchTask(this);
        mTask.execute(url);
    }
}

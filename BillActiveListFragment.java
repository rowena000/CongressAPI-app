package shanchi.congressapi;


public class BillActiveListFragment extends BillAbstractListFragment {

    public void startURLFetch() {
        String url = "http://congressinformation.hwpssmc2mf.us-west-2.elasticbeanstalk.com/?congressdb=bills&active=true";
        mTask = new BillFetchTask(this);
        mTask.execute(url);
    }
}

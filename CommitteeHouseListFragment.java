package shanchi.congressapi;


public class CommitteeHouseListFragment extends CommitteeAbstractFragment {
    public void startURLFetch() {
        String url = "http://congressinformation.hwpssmc2mf.us-west-2.elasticbeanstalk.com/?congressdb=committees&chamber=house";
        mTask = new CommitteeFetchTask(this);
        mTask.execute(url);
    }
}

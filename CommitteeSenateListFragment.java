package shanchi.congressapi;

public class CommitteeSenateListFragment extends CommitteeAbstractFragment {
    public void startURLFetch() {
        String url = "http://congressinformation.hwpssmc2mf.us-west-2.elasticbeanstalk.com/?congressdb=committees&chamber=senate";
        mTask = new CommitteeFetchTask(this);
        mTask.execute(url);
    }
}

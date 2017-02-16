package shanchi.congressapi;

public class CommitteeJointListFragment extends CommitteeAbstractFragment {
    public void startURLFetch() {
        String url = "http://congressinformation.hwpssmc2mf.us-west-2.elasticbeanstalk.com/?congressdb=committees&chamber=joint";
        mTask = new CommitteeFetchTask(this);
        mTask.execute(url);
    }
}

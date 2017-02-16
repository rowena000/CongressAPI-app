package shanchi.congressapi;


public class CommitteeFavoriteListFragment extends CommitteeAbstractFragment {
    public void startURLFetch() {
        String url = "favorites";
        mTask = new CommitteeFetchTask(this);
        mTask.execute(url);
    }
}

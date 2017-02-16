package shanchi.congressapi;

public class BillFavoriteListFragment extends BillAbstractListFragment {
    public void startURLFetch() {
        String url = "favorites";
        mTask = new BillFetchTask(this);
        mTask.execute(url);
    }
}

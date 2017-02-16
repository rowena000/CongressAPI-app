package shanchi.congressapi.object;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedHashTreeMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class StoredListObject implements Serializable {

    private LinkedHashMap<String, LegislatorObject> legislators;
    private LinkedHashMap<String, BillObject> bills;
    private LinkedHashMap<String, CommitteeObject> committees;

    public StoredListObject() {
        legislators = new LinkedHashMap<>();
        bills = new LinkedHashMap<>();
        committees = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, LegislatorObject> getLegislators() {
        return legislators;
    }

    public void setLegislators(LinkedHashMap<String, LegislatorObject> legislators) {
        this.legislators = legislators;
    }

    public LinkedHashMap<String, BillObject> getBills() {
        return bills;
    }

    public void setBills(LinkedHashMap<String, BillObject> bills) {
        this.bills = bills;
    }

    public LinkedHashMap<String, CommitteeObject> getCommittees() {
        return committees;
    }

    public void setCommittees(LinkedHashMap<String, CommitteeObject> committees) {
        this.committees = committees;
    }

    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static public StoredListObject create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, StoredListObject.class);
    }
}

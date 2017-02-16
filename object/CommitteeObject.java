package shanchi.congressapi.object;

import java.io.Serializable;


public class CommitteeObject implements Serializable, Comparable<CommitteeObject> {
    private String chamber;
    private String committee_id;
    private String name;
    private String office;
    private String phone;
    private String subcommittee;
    private String parent_committee_id;

    public String getParent_committee_id() {
        return parent_committee_id;
    }

    public void setParent_committee_id(String parent_committee_id) {
        this.parent_committee_id = parent_committee_id;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        chamber = chamber.substring(0,1).toUpperCase() + chamber.substring(1);
        this.chamber = chamber;
    }

    public String getCommittee_id() {
        return committee_id;
    }

    public void setCommittee_id(String committee_id) {
        this.committee_id = committee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        if (office == null || "null".equals(office) || "".equals(office)) {
            this.office = "N.A.";
        } else {
            this.office = office;
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || "null".equals(phone) || "".equals(phone)) {
            this.phone = "N.A.";
        } else {
            this.phone = phone;
        }
    }

    public String getSubcommittee() {
        return subcommittee;
    }

    public void setSubcommittee(String subcommittee) {
        this.subcommittee = subcommittee;
    }

    @Override
    public int compareTo(CommitteeObject committeeObject) {
        return this.getName().compareTo(committeeObject.getName());
    }
}

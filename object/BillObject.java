package shanchi.congressapi.object;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillObject implements Serializable, Comparable<BillObject> {
    private String billId;
    private String billType;
    private String chamber;
    private String officialTitle;
    private String activeAt;
    private String sponsor;
    private String status;
    private String introducedOn;
    private String congressUrl;
    private String versionStatus;
    private String billUrl;

    public String getCongressUrl() {
        return congressUrl;
    }

    public void setCongressUrl(String congressUrl) {
        this.congressUrl = congressUrl;
    }

    public String getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(String versionStatus) {
        this.versionStatus = versionStatus;
    }

    public String getBillUrl() {
        return billUrl;
    }

    public void setBillUrl(String billUrl) {
        this.billUrl = billUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if ("true".equals(status)) this.status = "Active";
        else this.status = "New";
    }



    public String getIntroducedOn() {
        return introducedOn;
    }

    public void setIntroducedOn(String introducedOn) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date intro = new Date();

        try {
            intro = df.parse(introducedOn);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

        this.introducedOn = formatter.format(intro);
    }




    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }




    public String getActiveAt() {
        return activeAt;
    }

    public void setActiveAt(String activeAt) {
        this.activeAt = activeAt;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        if (billId != null) {
            billId = billId.toUpperCase();
        }
        this.billId = billId;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {

        this.billType = billType.toUpperCase();
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getOfficialTitle() {
        return officialTitle;
    }

    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    @Override
    public int compareTo(BillObject billObject) {
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            date1 = df.parse(this.introducedOn);
            date2 = df.parse(billObject.introducedOn);
        } catch (ParseException e) {

        }
        return 0 - date1.compareTo(date2);
    }
}

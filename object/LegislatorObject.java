package shanchi.congressapi.object;

import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LegislatorObject implements Comparable<LegislatorObject>, Serializable {
    private String fullName;
    private String firstName;
    private String lastName;
    private String bioguideId;
    private String party;
    private String district;
    private String state;
    private String title;
    private String email;
    private String chamber;
    private String start_term;
    private String end_term;
    private String office;
    private String fax;
    private String birthday;
    private String state_short;
    private String facebook;
    private String twitter;
    private String website;

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        if (twitter == null || "null".equalsIgnoreCase(twitter) || "".equals(twitter)) {
            this.twitter = null;
        } else {
            this.twitter = "https://twitter.com/" + twitter;
        }
    }

    public String getwebsite() {
        return website;
    }

    public void setwebsite(String web) {
        if (web == null || "null".equalsIgnoreCase(web)) {
            this.website = null;
        } else {
            this.website = web;
        }
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        if (facebook == null || "null".equalsIgnoreCase(facebook) || "".equals(facebook)) {
            this.facebook = null;
        } else {
            this.facebook = "https://www.facebook.com/" + facebook;
        }
    }

    public String getState_short() {
        return state_short;
    }

    public void setState_short(String state_short) {

        this.state_short = state_short;
    }

    public String getStart_term() {
        return start_term;
    }

    public void setStart_term(String start_term) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date startTerm = new Date();

        try {
            startTerm = df.parse(start_term);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

        this.start_term = formatter.format(startTerm);
    }

    public String getEnd_term() {
        return end_term;
    }

    public void setEnd_term(String end_term) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date endTerm = new Date();

        try {
            endTerm = df.parse(end_term);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

        this.end_term = formatter.format(endTerm);
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        if (office == null || "null".equals(office) || "".equals(office)) this.office = "N.A.";
        else this.office = office;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        if (fax == null || "null".equals(fax) || "".equals(fax)) this.fax = "N.A.";
        else this.fax = fax;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date birth = new Date();

        try {
            birth = df.parse(birthday);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

        this.birthday = formatter.format(birth);
    }

    public String getChamber() {
        if ("house".equals(chamber)) return "House";
        else if ("senate".equals(chamber)) return "Senate";
        else return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        if (contact == null || "null".equals(contact) || "".equals(contact)) this.contact = "N.A.";
        else this.contact = contact;
    }

    private String contact;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        if (district == null || "null".equals(district) || "".equals(district)) {
            this.district = "N.A";
        } else {
            this.district = district;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }


    public String getBioguideId() {
        return bioguideId;
    }

    public void setBioguideId(String bioguideId) {
        this.bioguideId = bioguideId;
    }


    @Override
    public int compareTo(LegislatorObject legislatorObject) {
        return this.getLastName().compareTo(legislatorObject.getLastName());
    }

}

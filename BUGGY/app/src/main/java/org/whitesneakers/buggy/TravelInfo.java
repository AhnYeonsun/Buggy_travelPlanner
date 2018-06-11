package org.whitesneakers.buggy;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class TravelInfo {
    public String InfoTitle;
    public String InfoStartDate;
    public String InfoEndDate;
    public long InfoNumDates;

    private FirebaseAuth mAuth;
    private DatabaseReference toDBforDate;

    public TravelInfo(){};
    public TravelInfo(String InfoTitle, String InfoStartDate, String InfoEndDate, long InfoNumDates){
        this.InfoTitle = InfoTitle;
        this.InfoStartDate = InfoStartDate;
        this.InfoEndDate = InfoEndDate;
        this.InfoNumDates = InfoNumDates;
    }
}

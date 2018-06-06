package com.example.ahn.signinwithgoogle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ahn on 2018-06-04.
 */

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

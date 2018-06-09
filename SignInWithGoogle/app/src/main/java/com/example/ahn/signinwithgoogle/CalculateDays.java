package com.example.ahn.signinwithgoogle;

/**
 * Created by ahn on 2018-06-04.
 */

public class CalculateDays {
    static String[] days;
    static String title;

    public CalculateDays(){}

    public CalculateDays(String startDate, String endDate, int numDates, String planTitle){
        this.title = planTitle;

        int y1 = Integer.parseInt(startDate.substring(0,4));
        int m1 = Integer.parseInt(startDate.substring(5,7));
        int d1 = Integer.parseInt(startDate.substring(8,10));
        int m2 = Integer.parseInt(endDate.substring(5,7));
        int d2 = Integer.parseInt(endDate.substring(8,10));
        days = new String[numDates];
        String tempDate = startDate;
        int tempM = m1;
        int tempD = d1;
        int tempY = y1;

        if(m1==m2){
            for (int i = 0; i < numDates; i++){
                tempDate = String.valueOf(tempY) + "-" + String.valueOf(tempM) + "-" + String.valueOf(tempD);
                days[i] = tempDate;
                tempD++;
            }
        }
        else if(m1 < m2){//달 넘어가는 경우

            for (int i = 0; i < numDates; i++){
                tempDate = String.valueOf(tempY) + "-" + String.valueOf(tempM) + "-" + String.valueOf(tempD);
                days[i] = tempDate;
                if((tempD==31)&&(tempM==1||tempM==3||tempM==5||tempM==7||tempM==8||tempM==10||tempM==12)){ //31일
                    if(m1==12){ //12월 31일
                        tempM = 1;
                        tempD = 1;
                        tempY++;
                    }
                    else{ //00월 31일
                        tempM++;
                        tempD = 1;
                    }
                }
                else if((tempD==30)&&(tempM==4||tempM==6||tempM==9||tempM==11)){ //30일
                    tempM++;
                    tempD = 1;
                }
                else if((tempD==28)&&(tempM==2)){ //28일
                    tempM++;
                    tempD = 1;
                }
                else{
                    tempD++;
                }
            }
        }
    }
}

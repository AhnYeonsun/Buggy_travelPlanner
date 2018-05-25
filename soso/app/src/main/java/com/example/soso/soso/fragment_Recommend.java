package com.example.soso.soso;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class fragment_Recommend extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    GetArea getArea = new GetArea(); //object of GetArea() function
    Spinner regionSpinnerList, sigunguSpinnerList;

    String region = "", sigungu = "", contentTypeID = "";
    AlertDialog.Builder builder;
    ArrayList<String> regionList, sigunguList;
    ArrayAdapter<String> regionSpinnerAdapter, sigunguSpinnerAdapter;
    String regionCode = new String();
    boolean check = false;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_recommend);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        builder = new AlertDialog.Builder(this);
        getArea.getRegionCode();

        // ******* 지역 스피너 리스트 세팅********* //
        regionSpinnerList = findViewById(R.id.regionList);
        regionList = new ArrayList<String>(Arrays.asList(""));
        regionSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regionList);

        Iterator<String> iterator = getArea.regionHashMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            regionSpinnerAdapter.add(key);
        }
        regionSpinnerAdapter.notifyDataSetChanged();
        regionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinnerList.setAdapter(regionSpinnerAdapter);
        // *************************************** //

        // ************** 지역스피너 입력 후, 시군구 스피너 리스트 세팅 ****************  //
        sigunguSpinnerList = (Spinner) findViewById(R.id.sigunguList); //spinner of sigungu list
        sigunguList = new ArrayList<String>(Arrays.asList("")); //array list of spinner
        sigunguSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sigunguList);

        regionSpinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (check) {
                    sigunguList.clear();
                    sigunguSpinnerAdapter.clear();
                    sigunguSpinnerList.setAdapter(sigunguSpinnerAdapter);
                    getArea.sigunguHashMap.clear();
                    regionCode = new SearchHashMap(getArea.regionHashMap, parent.getItemAtPosition(position).toString()).searching();
                    getArea.getSigunguCode(regionCode);

                    Iterator<String> iterator = getArea.sigunguHashMap.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        sigunguSpinnerAdapter.add(key);
                    }

                    sigunguSpinnerAdapter.notifyDataSetChanged();
                    sigunguSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // ****************************************** //
                }
                else{check = true;}
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public String getRegionItem() {
        return (String) regionSpinnerList.getSelectedItem();
    }

    public String getSigunguItem() {
        return (String) sigunguSpinnerList.getSelectedItem();
    }

    public GetArea getObject() {
        return getArea;
    }

    public String getContentTypeID(){return contentTypeID;}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragment__search_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            contentTypeID="";
            switch (position) {
                case 1:
                    contentTypeID = "12";
                    break;
                case 2:
                    contentTypeID = "14";
                    break;
                case 3:
                    contentTypeID = "15";
                    break;
                case 4:
                    contentTypeID = "25";
                    break;
                case 5:
                    contentTypeID = "28";
                    break;
                case 6:
                    contentTypeID = "32";
                    break;
                case 7:
                    contentTypeID = "38";
                    break;
                case 8:
                    contentTypeID = "39";
                    break;
                default:
                    contentTypeID = "";
                    break;
            }
            return new Fragment_RecomListView();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 9;
        }
    }
}

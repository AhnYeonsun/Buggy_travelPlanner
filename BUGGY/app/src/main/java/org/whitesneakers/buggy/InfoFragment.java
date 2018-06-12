package org.whitesneakers.buggy;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InfoFragment extends Fragment {
    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        return fragment;
    }

   TextView info1,info2,info3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_info, container, false);

        info1=view.findViewById(R.id.info1);
        info2=view.findViewById(R.id.info2);
        info3=view.findViewById(R.id.info3);

        info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://buggy.imweb.me/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("mailto:whiteSneakers3@gmail.com");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(intent);
            }
        });

        info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Tutorial.class);
                startActivity(intent);
            }
        });
       return view;
    }


}


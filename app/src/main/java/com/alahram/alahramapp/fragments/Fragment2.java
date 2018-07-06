package com.alahram.alahramapp.fragments;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alahram.alahramapp.R;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {


    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment2 =  inflater.inflate(R.layout.fragment_fragment2, container, false);
        TextView btnContact=(TextView)fragment2.findViewById(R.id.tvContact);
        TextView btnWhatsapp=(TextView)fragment2.findViewById(R.id.tvwhatspp);
        TextView btnFacebook=(TextView)fragment2.findViewById(R.id.tvfacebook);

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "00966553572260";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* String smsNumber = "01286967002"; // E164 format without '+' sign
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
                sendIntent.setPackage("com.whatsapp");
                if (getActivity().getIntent().resolveActivity(getActivity().getPackageManager()) == null) {
                    Toast.makeText(getActivity(), "Error" , Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(sendIntent);*/
                int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText("00966567984900");
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("whatsapp","00966567984900");
                    clipboard.setPrimaryClip(clip);
                }
                Toast.makeText(getActivity(),"تم نسخ رقم واتس ابب",Toast.LENGTH_LONG).show();
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String url = "https://www.facebook.com/%D8%B4%D8%B1%D9%83%D8%A9-%D8%A7%D9%84%D8%A3%D9%87%D8%B1%D8%A7%D9%85-%D9%84%D9%84%D8%B4%D8%AD%D9%86-%D8%A5%D9%84%D9%8A-%D9%85%D8%B5%D8%B1-692865884058416/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                //https://www.facebook.com/%D8%B4%D8%B1%D9%83%D8%A9-%D8%A7%D9%84%D8%A3%D9%87%D8%B1%D8%A7%D9%85-%D9%84%D9%84%D8%B4%D8%AD%D9%86-%D8%A5%D9%84%D9%8A-%D9%85%D8%B5%D8%B1-692865884058416/
            }
        });
        return fragment2;

    }

}

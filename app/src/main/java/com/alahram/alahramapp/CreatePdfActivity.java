package com.alahram.alahramapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alahram.alahramapp.Ui.*;
import com.alahram.alahramapp.Ui.AttachActivity;
import com.alahram.alahramapp.Utilitis.Preferences;
import com.alahram.alahramapp.model.AddRequestModel;
import com.alahram.alahramapp.model.Constant;
import com.alahram.alahramapp.model.RequestQueueSingleton;
import com.alahram.alahramapp.pdfCreate.VerticalTextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codelab on 1/22/2018.
 */

public class CreatePdfActivity extends AppCompatActivity {
    Preferences mpreference;

    static final String REQ_TAG = "VACTIVITY";
    RequestQueue requestQueue;
    String filerecieverName= "";
    String more="";


    /** ButterKnife Code **/
    @BindView(R.id.pageView)
    RelativeLayout mPageView;
    @BindView(R.id.myRootView)
    ScrollView mMyRootView;
    @BindView(R.id.myView)
    RelativeLayout mMyView;
    @BindView(R.id.header)
    LinearLayout mHeader;
    @BindView(R.id.billNumberHint)
    TextView mBillNumberHint;
    @BindView(R.id.billNumber)
    TextView mBillNumber;
    @BindView(R.id.verticalLine)
    View mVerticalLine;
    @BindView(R.id.billDetails)
    LinearLayout mBillDetails;
    @BindView(R.id.mobileNumberHint)
    TextView mMobileNumberHint;
    @BindView(R.id.mobileNumber)
    TextView mMobileNumber;
    @BindView(R.id.NumberHint)
    TextView mNumberHint;
    @BindView(R.id.Number)
    TextView mNumber;
    @BindView(R.id.dateHint)
    TextView mDateHint;
    @BindView(R.id.date)
    TextView mDate;
    @BindView(R.id.receiverNameHint)
    TextView mReceiverNameHint;
    @BindView(R.id.receiverName)
    TextView mReceiverName;
    @BindView(R.id.senderNameHint)
    TextView mSenderNameHint;
    @BindView(R.id.senderName)
    TextView mSenderName;
    @BindView(R.id.receiverAddressHint)
    TextView mReceiverAddressHint;
    @BindView(R.id.receiverAddress)
    TextView mReceiverAddress;
    @BindView(R.id.receiverPhonesHint)
    TextView mReceiverPhonesHint;
    @BindView(R.id.receiverPhones)
    TextView mReceiverPhones;
    @BindView(R.id.verticalTextView)
    VerticalTextView mVerticalTextView;
    @BindView(R.id.tableHeader)
    LinearLayout mTableHeader;
    @BindView(R.id.tableItem)
    LinearLayout mTableItem;
    @BindView(R.id.tableFooter)
    LinearLayout mTableFooter;
    @BindView(R.id.totalHint)
    TextView mTotalHint;
    @BindView(R.id.total)
    TextView mTotal;
    @BindView(R.id.rules)
    LinearLayout mRules;
    @BindView(R.id.ruleLine0)
    TextView mRuleLine0;
    @BindView(R.id.ruleLine1)
    TextView mRuleLine1;
    @BindView(R.id.ruleLine2)
    TextView mRuleLine2;
    @BindView(R.id.ruleLine3)
    TextView mRuleLine3;
    @BindView(R.id.ruleLine4)
    TextView mRuleLine4;
    @BindView(R.id.ruleLine5)
    TextView mRuleLine5;
    @BindView(R.id.ruleLine6)
    TextView mRuleLine6;
    @BindView(R.id.ruleLine7)
    TextView mRuleLine7;
    @BindView(R.id.ruleLine8)
    TextView mRuleLine8;
    @BindView(R.id.verticalLine2)
    View mVerticalLine2;
    @BindView(R.id.signatureDetails)
    LinearLayout mSignatureDetails;
    @BindView(R.id.receiverInOfficeHint)
    TextView mReceiverInOfficeHint;
    @BindView(R.id.receiverInOffice)
    TextView mReceiverInOffice;
    @BindView(R.id.receiverIdHint)
    TextView mReceiverIdHint;
    @BindView(R.id.receiverId)
    TextView mReceiverId;
    @BindView(R.id.signature1Hint)
    TextView mSignature1Hint;
    @BindView(R.id.signature1)
    TextView mSignature1;
    @BindView(R.id.signature2Hint)
    TextView mSignature2Hint;
    @BindView(R.id.signature2)
    TextView mSignature2;
    @BindView(R.id.footer)
    LinearLayout mFooter;
    @BindView(R.id.line1)
    TextView mLine1;
    @BindView(R.id.line2)
    TextView mLine2;
    @BindView(R.id.page2_myView)
    RelativeLayout mPage2MyView;
    @BindView(R.id.page2_header)
    LinearLayout mPage2Header;
    @BindView(R.id.page2_title)
    TextView mPage2Title;
    @BindView(R.id.page2_billNumberView)
    RelativeLayout mPage2BillNumberView;
    @BindView(R.id.page2_billNumberHint)
    TextView mPage2BillNumberHint;
    @BindView(R.id.page2_billNumber)
    TextView mPage2BillNumber;
    @BindView(R.id.page2_countHint)
    TextView mPage2CountHint;
    @BindView(R.id.page2_count)
    TextView mPage2Count;
    @BindView(R.id.page2_verticalLine)
    View mPage2VerticalLine;
    @BindView(R.id.page2_billDetails)
    LinearLayout mPage2BillDetails;
    @BindView(R.id.page2_dateHint)
    TextView mPage2DateHint;
    @BindView(R.id.page2_date)
    TextView mPage2Date;
    @BindView(R.id.page2_receiverNameHint)
    TextView mPage2ReceiverNameHint;
    @BindView(R.id.page2_receiverName)
    TextView mPage2ReceiverName;
    @BindView(R.id.page2_cityHint)
    TextView mPage2CityHint;
    @BindView(R.id.page2_city)
    TextView mPage2City;
    @BindView(R.id.page2_countryHint)
    TextView mPage2CountryHint;
    @BindView(R.id.page2_country)
    TextView mPage2Country;
    @BindView(R.id.page2_tableView)
    LinearLayout mPage2TableView;

    @BindView(R.id.page2_verticalLine2)
    View mPage2VerticalLine2;
    @BindView(R.id.page2_reciverDetails)
    LinearLayout mPage2ReciverDetails;
    @BindView(R.id.page2_mobileHint)
    TextView mPage2MobileHint;
    @BindView(R.id.page2_mobile)
    TextView mPage2Mobile;
    @BindView(R.id.page2_receiverHint)
    TextView mPage2ReceiverHint;
    @BindView(R.id.page2_receiver)
    TextView mPage2Receiver;
    @BindView(R.id.page2_signatureHint)
    TextView mPage2SignatureHint;
    @BindView(R.id.page2_signature)
    TextView mPage2Signature;
    @BindView(R.id.page2_idNumberHint)
    TextView mPage2IdNumberHint;
    @BindView(R.id.page2_idNumber)
    TextView mPage2IdNumber;
    @BindView(R.id.page2_verticalLine3)
    View mPage2VerticalLine3;
    @BindView(R.id.page2_verticalLine4)
    View mPage2VerticalLine4;

    @BindView(R.id.secretNumberHint)
    TextView mSecretNumberHint;
    @BindView(R.id.secretNumber)
    TextView mSecretNumber;


    /** ButterKnife Code **/


    /** ButterKnife Code **/
    @BindView(R.id.page3_myView)
    LinearLayout mPage3MyView;
    @BindView(R.id.page3_tableView1)
    LinearLayout mPage3TableView1;
    @BindView(R.id.page3_billNumber1)
    TextView mPage3BillNumber1;
    @BindView(R.id.page3_billNumberHint1)
    TextView mPage3BillNumberHint1;
    @BindView(R.id.page3_senderName1)
    TextView mPage3SenderName1;
    @BindView(R.id.page3_senderNameHint1)
    TextView mPage3SenderNameHint1;
    @BindView(R.id.page3_receiverName1)
    TextView mPage3ReceiverName1;
    @BindView(R.id.page3_receiverNameHint1)
    TextView mPage3ReceiverNameHint1;
    @BindView(R.id.page3_direction1)
    TextView mPage3Direction1;
    @BindView(R.id.page3_directionHint1)
    TextView mPage3DirectionHint1;
    @BindView(R.id.page3_contentNumber1)
    TextView mPage3ContentNumber1;
    @BindView(R.id.page3_contentHint1)
    TextView mPage3ContentHint1;
    @BindView(R.id.page3_mobile1)
    TextView mPage3Mobile1;
    @BindView(R.id.page3_mobileHint1)
    TextView mPage3MobileHint1;
    @BindView(R.id.page3_leftLine1)
    View mPage3LeftLine1;
    @BindView(R.id.page3_topLine1)
    View mPage3TopLine1;
    @BindView(R.id.page3_rightLine1)
    View mPage3RightLine1;
    @BindView(R.id.page3_bottomLine1)
    View mPage3BottomLine1;
    @BindView(R.id.page3_tableView2)
    LinearLayout mPage3TableView2;
    @BindView(R.id.page3_billNumber2)
    TextView mPage3BillNumber2;
    @BindView(R.id.page3_billNumberHint2)
    TextView mPage3BillNumberHint2;
    @BindView(R.id.page3_senderName2)
    TextView mPage3SenderName2;
    @BindView(R.id.page3_senderNameHint2)
    TextView mPage3SenderNameHint2;
    @BindView(R.id.page3_receiverName2)
    TextView mPage3ReceiverName2;
    @BindView(R.id.page3_receiverNameHint2)
    TextView mPage3ReceiverNameHint2;
    @BindView(R.id.page3_direction2)
    TextView mPage3Direction2;
    @BindView(R.id.page3_directionHint2)
    TextView mPage3DirectionHint2;
    @BindView(R.id.page3_contentNumber2)
    TextView mPage3ContentNumber2;
    @BindView(R.id.page3_contentHint2)
    TextView mPage3ContentHint2;
    @BindView(R.id.page3_mobile2)
    TextView mPage3Mobile2;
    @BindView(R.id.page3_mobileHint2)
    TextView mPage3MobileHint2;
    @BindView(R.id.page3_leftLine2)
    View mPage3LeftLine2;
    @BindView(R.id.page3_topLine2)
    View mPage3TopLine2;
    @BindView(R.id.page3_rightLine2)
    View mPage3RightLine2;
    @BindView(R.id.page3_bottomLine2)
    View mPage3BottomLine2;
    @BindView(R.id.page3_tableView3)
    LinearLayout mPage3TableView3;
    @BindView(R.id.page3_billNumber3)
    TextView mPage3BillNumber3;
    @BindView(R.id.page3_billNumberHint3)
    TextView mPage3BillNumberHint3;
    @BindView(R.id.page3_senderName3)
    TextView mPage3SenderName3;
    @BindView(R.id.page3_senderNameHint3)
    TextView mPage3SenderNameHint3;
    @BindView(R.id.page3_receiverName3)
    TextView mPage3ReceiverName3;
    @BindView(R.id.page3_receiverNameHint3)
    TextView mPage3ReceiverNameHint3;
    @BindView(R.id.page3_direction3)
    TextView mPage3Direction3;
    @BindView(R.id.page3_directionHint3)
    TextView mPage3DirectionHint3;
    @BindView(R.id.page3_contentNumber3)
    TextView mPage3ContentNumber3;
    @BindView(R.id.page3_contentHint3)
    TextView mPage3ContentHint3;
    @BindView(R.id.page3_mobile3)
    TextView mPage3Mobile3;
    @BindView(R.id.page3_mobileHint3)
    TextView mPage3MobileHint3;
    @BindView(R.id.page3_leftLine3)
    View mPage3LeftLine3;
    @BindView(R.id.page3_topLine3)
    View mPage3TopLine3;
    @BindView(R.id.page3_rightLine3)
    View mPage3RightLine3;
    @BindView(R.id.page3_bottomLine3)
    View mPage3BottomLine3;
    @BindView(R.id.page4_myView)
    LinearLayout mPage4MyView;
    @BindView(R.id.page4_tableView1)
    LinearLayout mPage4TableView1;
    @BindView(R.id.page4_billNumber1)
    TextView mPage4BillNumber1;
    @BindView(R.id.page4_billNumberHint1)
    TextView mPage4BillNumberHint1;
    @BindView(R.id.page4_senderName1)
    TextView mPage4SenderName1;
    @BindView(R.id.page4_senderNameHint1)
    TextView mPage4SenderNameHint1;
    @BindView(R.id.page4_receiverName1)
    TextView mPage4ReceiverName1;
    @BindView(R.id.page4_receiverNameHint1)
    TextView mPage4ReceiverNameHint1;
    @BindView(R.id.page4_direction1)
    TextView mPage4Direction1;
    @BindView(R.id.page4_directionHint1)
    TextView mPage4DirectionHint1;
    @BindView(R.id.page4_contentNumber1)
    TextView mPage4ContentNumber1;
    @BindView(R.id.page4_contentHint1)
    TextView mPage4ContentHint1;
    @BindView(R.id.page4_mobile1)
    TextView mPage4Mobile1;
    @BindView(R.id.page4_mobileHint1)
    TextView mPage4MobileHint1;
    @BindView(R.id.page4_leftLine1)
    View mPage4LeftLine1;
    @BindView(R.id.page4_topLine1)
    View mPage4TopLine1;
    @BindView(R.id.page4_rightLine1)
    View mPage4RightLine1;
    @BindView(R.id.page4_bottomLine1)
    View mPage4BottomLine1;
    @BindView(R.id.page4_tableView2)
    LinearLayout mPage4TableView2;
    @BindView(R.id.page4_billNumber2)
    TextView mPage4BillNumber2;
    @BindView(R.id.page4_billNumberHint2)
    TextView mPage4BillNumberHint2;
    @BindView(R.id.page4_senderName2)
    TextView mPage4SenderName2;
    @BindView(R.id.page4_senderNameHint2)
    TextView mPage4SenderNameHint2;
    @BindView(R.id.page4_receiverName2)
    TextView mPage4ReceiverName2;
    @BindView(R.id.page4_receiverNameHint2)
    TextView mPage4ReceiverNameHint2;
    @BindView(R.id.page4_direction2)
    TextView mPage4Direction2;
    @BindView(R.id.page4_directionHint2)
    TextView mPage4DirectionHint2;
    @BindView(R.id.page4_contentNumber2)
    TextView mPage4ContentNumber2;
    @BindView(R.id.page4_contentHint2)
    TextView mPage4ContentHint2;
    @BindView(R.id.page4_mobile2)
    TextView mPage4Mobile2;
    @BindView(R.id.page4_mobileHint2)
    TextView mPage4MobileHint2;
    @BindView(R.id.page4_leftLine2)
    View mPage4LeftLine2;
    @BindView(R.id.page4_topLine2)
    View mPage4TopLine2;
    @BindView(R.id.page4_rightLine2)
    View mPage4RightLine2;
    @BindView(R.id.page4_bottomLine2)
    View mPage4BottomLine2;
    @BindView(R.id.page4_tableView3)
    LinearLayout mPage4TableView3;
    @BindView(R.id.page4_billNumber3)
    TextView mPage4BillNumber3;
    @BindView(R.id.page4_billNumberHint3)
    TextView mPage4BillNumberHint3;
    @BindView(R.id.page4_senderName3)
    TextView mPage4SenderName3;
    @BindView(R.id.page4_senderNameHint3)
    TextView mPage4SenderNameHint3;
    @BindView(R.id.page4_receiverName3)
    TextView mPage4ReceiverName3;
    @BindView(R.id.page4_receiverNameHint3)
    TextView mPage4ReceiverNameHint3;
    @BindView(R.id.page4_direction3)
    TextView mPage4Direction3;
    @BindView(R.id.page4_directionHint3)
    TextView mPage4DirectionHint3;
    @BindView(R.id.page4_contentNumber3)
    TextView mPage4ContentNumber3;
    @BindView(R.id.page4_contentHint3)
    TextView mPage4ContentHint3;
    @BindView(R.id.page4_mobile3)
    TextView mPage4Mobile3;
    @BindView(R.id.page4_mobileHint3)
    TextView mPage4MobileHint3;
    @BindView(R.id.page4_leftLine3)
    View mPage4LeftLine3;
    @BindView(R.id.page4_topLine3)
    View mPage4TopLine3;
    @BindView(R.id.page4_rightLine3)
    View mPage4RightLine3;
    @BindView(R.id.page4_bottomLine3)
    View mPage4BottomLine3;
    /** ButterKnife Code **/
    Typeface tf;
    private ArrayList<AddRequestModel> data = new ArrayList<>();
    private int totalPrice=0;
    String currencyUnit=" ريال";
    void setFontToTextView()
    {
        tf = Typeface.createFromAsset(getResources().getAssets(), "GE_Thameen_Book.otf");
        mLine1.setTypeface(tf);
        mLine2.setTypeface(tf);
        mBillNumber.setTypeface(tf);
        mMobileNumberHint.setTypeface(tf);
        mMobileNumber.setTypeface(tf);
        mNumberHint.setTypeface(tf);
        mNumber.setTypeface(tf);
        mDateHint.setTypeface(tf);
        mDate.setTypeface(tf);
        mReceiverNameHint.setTypeface(tf);
        mReceiverName.setTypeface(tf);
        mSenderNameHint.setTypeface(tf);
        mSenderName.setTypeface(tf);
        mReceiverAddressHint.setTypeface(tf);
        mReceiverAddress.setTypeface(tf);
        mReceiverPhonesHint.setTypeface(tf);
        mReceiverPhones.setTypeface(tf);
        mRuleLine0.setTypeface(tf);
        mRuleLine1.setTypeface(tf);
        mRuleLine2.setTypeface(tf);
        mRuleLine3.setTypeface(tf);
        mRuleLine4.setTypeface(tf);
        mRuleLine5.setTypeface(tf);
        mRuleLine6.setTypeface(tf);
        mRuleLine7.setTypeface(tf);
        mRuleLine8.setTypeface(tf);
        mReceiverInOfficeHint.setTypeface(tf);
        mReceiverInOffice.setTypeface(tf);
        mReceiverIdHint.setTypeface(tf);
        mReceiverId.setTypeface(tf);
        mSignature1Hint.setTypeface(tf);
        mSignature1.setTypeface(tf);
        mSignature2Hint.setTypeface(tf);
        mSignature2.setTypeface(tf);
        mLine1.setTypeface(tf);
        mLine2.setTypeface(tf);
        mVerticalTextView.setTypeface(tf);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }
    Button btnRecieve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forceRTLIfSupported();
        setContentView(R.layout.activity_print_pdf);
        btnRecieve =(Button)findViewById(R.id.btnReciever);


        requestQueue = RequestQueueSingleton.getInstance(CreatePdfActivity.this)
                .getRequestQueue();

        ButterKnife.bind(this);
        mpreference=new Preferences(this);
        mPageView.bringToFront();
        setFontToTextView();
        mMyRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        Bundle bundle = getIntent().getExtras();

        ArrayList<AddRequestModel> allData = (ArrayList<AddRequestModel>) bundle.getSerializable("dataList");
        more = getIntent().getExtras().getString("more");

        for (int i=0 ; i< allData.size() ; i++)
        {
            if(allData.get(i).getCounter() > 0)
                data.add(allData.get(i));
        }

        for (int i=0;i<data.size();i++)
        {
            totalPrice+=data.get(i).getCounter()*Integer.valueOf(data.get(i).getOrderprice());
            Log.d("dataItem",""+data.get(i).toString());
        }

        if(data.size() < 10)
        {
           // for(int i=0 ; i<10 ; i++)
             //   data.add(new AddRequestModel(-1,"",""));
        }

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("انشاء طلب جديد");
        Button fab = (Button) findViewById(R.id.nextbtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(CreatePdfActivity.this,AttachActivity.class);
                startActivity(i);
            }
        });
        bindDataToStickersView("");
        bindDataToBill1();
        bindDataToBill2();

    }

    private void  bindDataToBill1()
    {
        String billNumber=String.valueOf(getRandomBillNumber())+" كود سري: "+mpreference.getSecretNum();
        String mobileNumber=mpreference.getClientPhoneKsa();
        if(mpreference.getClientPhoneEgy().equals(null) || mpreference.getClientPhoneEgy().equals(""))
        {}
        else
            mobileNumber += "/"+mpreference.getClientPhoneEgy();

        String itemNumber=String.valueOf(data.size());
        String date=getCurrantDate();

        String receiverName=mpreference.getRecivername();
        String senderName=mpreference.getclientname();
        String receiverAddress=mpreference.getReciverAddressDetial();
        String receiverPhones=mpreference.getReciverPhoneEgy();
        if(mpreference.getReciverPhoneKsa().equals(null) || mpreference.getReciverPhoneKsa().equals(""))
        {}
        else
            receiverPhones += " / "+mpreference.getReciverPhoneKsa();

        String total=String.valueOf(totalPrice);
        String receiverInOffice="hen recier in office name";
        String receiverId=mpreference.getReciverNationalId();
        String signature1="";
        String signature2="";


        mBillNumber.setText(billNumber);
        mMobileNumber.setText(mobileNumber);
        mNumber.setText(itemNumber);
        mDate.setText(date);
        mReceiverName.setText(receiverName);
        mSenderName.setText(senderName);
        mReceiverAddress.setText(receiverAddress);
        mReceiverPhones.setText(receiverPhones);
        mTotal.setText(total +currencyUnit);
        mReceiverInOffice.setText(receiverInOffice);
        mReceiverId.setText(receiverId);
        mSignature1.setText(signature1);
        mSignature2.setText(signature2);

        LayoutInflater layoutInflator = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTableItem.removeAllViews();
        for (int i=0;i<data.size();i++)
        {

            TextView bayanTextView,quantityTextView,priceTextView,totalTextView;
            View child = layoutInflator.inflate(R.layout.table_item, null);
            bayanTextView=child.findViewById(R.id.bayan);
            quantityTextView=child.findViewById(R.id.quantity);
            priceTextView=child.findViewById(R.id.price);
            totalTextView=child.findViewById(R.id.total);

            if(!data.get(i).getOrdername().equals(""))
                bayanTextView.setText(data.get(i).getOrdername());
            if(data.get(i).getCounter() != 0)
                quantityTextView.setText(String.valueOf(data.get(i).getCounter()));
            if(!data.get(i).getOrderprice().equals(""))
            priceTextView.setText(data.get(i).getOrderprice() +currencyUnit);
            if(!data.get(i).getOrderprice().equals(""))
            totalTextView.setText(String.valueOf((data.get(i).getCounter() * (Integer.valueOf(data.get(i).getOrderprice()))))+ currencyUnit );

            child.setTag(i);
            child.setBackgroundColor(Color.WHITE);
            mTableItem.addView(child);
        }
    }

    private void bindDataToBill2()
    {
        String billNumber=String.valueOf(getRandomBillNumber());
        String itemCount=String.valueOf(data.size());
        String date=getCurrantDate();

        String receiverName=mpreference.getRecivername();
        String city=mpreference.getReciverAddressDetial12();
        String country=mpreference.getReciverAddressDetial1();
        String mobileNumber=mpreference.getReciverPhoneEgy();
        if(mpreference.getReciverPhoneKsa().equals(null) || mpreference.getReciverPhoneKsa().equals(""))
        {}
        else
            mobileNumber += " / "+mpreference.getReciverPhoneKsa();

        String receiver=mpreference.getRecivername();
        String signature="";
        String idNumber=mpreference.getReciverNationalId();

        mPage2Count.setText(itemCount);
        mPage2Date.setText(date);
        mPage2ReceiverName.setText(receiverName);
        mPage2City.setText(city);
        mPage2Country.setText(country);
        mPage2Mobile.setText(mobileNumber);
        mPage2Receiver.setText(receiver);
        mPage2Signature.setText(signature);
        mPage2IdNumber.setText(idNumber);
        mPage2BillNumber.setText(billNumber);

        LayoutInflater layoutInflator1 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPage2TableView.removeAllViews();
        for (int i=0;i<(data.size()-1);i+=2)
        {
            TextView page2_item2Number,page2_item2,page2_item1Number,page2_item1;
            View child = layoutInflator1.inflate(R.layout.table_item2, null);
            page2_item2Number=child.findViewById(R.id.page2_item2Number);
            page2_item2=child.findViewById(R.id.page2_item2);
            page2_item1Number=child.findViewById(R.id.page2_item1Number);
            page2_item1=child.findViewById(R.id.page2_item1);
            page2_item1Number.setText(" ( "+String.valueOf(i+1)+" ) ");
            page2_item1.setText(data.get(i).getOrdername());

            page2_item2Number.setText(" ( "+String.valueOf(i+2)+" ) ");

            page2_item2.setText(data.get(i+1).getOrdername());

            child.setTag(i);
            mPage2TableView.addView(child);
        }

        if (data.size()%2!=0)
        {
            TextView page2_item2Number,page2_item2,page2_item1Number,page2_item1;
            View child = layoutInflator1.inflate(R.layout.table_item2, null);
            page2_item2Number=child.findViewById(R.id.page2_item2Number);
            page2_item2=child.findViewById(R.id.page2_item2);
            page2_item1Number=child.findViewById(R.id.page2_item1Number);
            page2_item1=child.findViewById(R.id.page2_item1);
            page2_item1Number.setText(" ( "+String.valueOf((data.size())) +" ) ");
            page2_item1.setText(data.get(data.size()-1).getOrdername());
            page2_item2.setText("");
            page2_item2Number.setText("");
            child.setTag(data.size()-1);
            mPage2TableView.addView(child);
        }
    }

    private void bindDataToStickersView(String content)
    {
        Preferences preferences=new Preferences(this);
        // Stickers
        String stickersBillNumber=mpreference.getRequestNum()+"";
        String stickersSenderName=preferences.getclientname();
        String stickersReceiverName= preferences.getRecivername();
        String stickersDirection=preferences.getReciverAddressDetial();
        String stickersContent=content;

        String stickersMobileNumber=preferences.getReciverPhoneEgy();
        if(mpreference.getReciverPhoneKsa().equals(null) || mpreference.getReciverPhoneKsa().equals(""))
        {}
        else
            stickersMobileNumber += "/"+mpreference.getReciverPhoneKsa();

//        mPage4ReceiverName1.setText(stickersReceiverName);
//        mPage4ReceiverName2.setText(stickersReceiverName);
//        mPage4ReceiverName3.setText(stickersReceiverName);

        mPage3ReceiverName1.setText(stickersReceiverName);
        mPage3ReceiverName2.setText(stickersReceiverName);
        mPage3ReceiverName3.setText(stickersReceiverName);

//        mPage4BillNumber1.setText(stickersBillNumber);
//        mPage4BillNumber2.setText(stickersBillNumber);
//        mPage4BillNumber3.setText(stickersBillNumber);

        mPage3BillNumber1.setText(stickersBillNumber);
        mPage3BillNumber2.setText(stickersBillNumber);
        mPage3BillNumber3.setText(stickersBillNumber);

//        mPage4ContentNumber1.setText(stickersContent);
//        mPage4ContentNumber2.setText(stickersContent);
//        mPage4ContentNumber3.setText(stickersContent);

        mPage3ContentNumber1.setText(stickersContent);
        mPage3ContentNumber2.setText(stickersContent);
        mPage3ContentNumber3.setText(stickersContent);

//        mPage4Direction1.setText(stickersDirection);
//        mPage4Direction2.setText(stickersDirection);
//        mPage4Direction3.setText(stickersDirection);

        mPage3Direction1.setText(stickersDirection);
        mPage3Direction2.setText(stickersDirection);
        mPage3Direction3.setText(stickersDirection);

//        mPage4Mobile1.setText(stickersMobileNumber);
//        mPage4Mobile2.setText(stickersMobileNumber);
//        mPage4Mobile3.setText(stickersMobileNumber);

        mPage3Mobile1.setText(stickersMobileNumber);
        mPage3Mobile2.setText(stickersMobileNumber);
        mPage3Mobile3.setText(stickersMobileNumber);


        mPage3SenderName1.setText(stickersSenderName);
        mPage3SenderName2.setText(stickersSenderName);
        mPage3SenderName3.setText(stickersSenderName);
    }

    int billNumber=-1;
    public int getRandomBillNumber() {
        return mpreference.getRequestNum();
    }
    public String getCurrantDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String dateFormat = formatter.format(date.getTime());
        return dateFormat;
    }
    ProgressDialog dialog;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void savePdf(final View view) {




        if (fn_permission()) {
            dialog = ProgressDialog.show(CreatePdfActivity.this, "",
                    "جاري التحميل...", true);

            try {
                String url = Constant.serversite + "/api/AlAhram/AddOrUpdateRequest";

                final JSONObject jsonBody = new JSONObject();

                int UserId = mpreference.getUserId();
                jsonBody.put("UserId",UserId);

                JSONObject RecieverInfo = new JSONObject();

                RecieverInfo.put("FullName",mpreference.getRecivername());
                RecieverInfo.put("Phone2",mpreference.getReciverPhoneEgy());
                RecieverInfo.put("Phone1",mpreference.getReciverPhoneKsa());

                JSONObject Address1_R = new JSONObject();
                Address1_R.put("CityId", mpreference.getRecieverCity());
                Address1_R.put("AddressText", mpreference.getReciverAddressDetial());

                RecieverInfo.put("Address1",Address1_R);
                RecieverInfo.put("NationalID",mpreference.getReciverNationalId());

                jsonBody.put("RecieverInfo",RecieverInfo);

                JSONObject ClientInfo = new JSONObject();

                ClientInfo.put("FullName",mpreference.getclientname());
                ClientInfo.put("Phone2",mpreference.getClientPhoneEgy());
                ClientInfo.put("Phone1",mpreference.getClientPhoneKsa());

                JSONObject Address1_C = new JSONObject();
                Address1_C.put("CityId", mpreference.getClientCity());
                Address1_C.put("AddressText", mpreference.getClientAddressDetial());

                ClientInfo.put("Address1",Address1_C);
                ClientInfo.put("NationalID",mpreference.getClientNationalId());
                ClientInfo.put("Nationality",mpreference.getClientNationality());

                jsonBody.put("ClientInfo",ClientInfo);

                jsonBody.put("Paid",0);
                jsonBody.put("Remaining",0);
                jsonBody.put("Required",totalPrice);
                jsonBody.put("EntitiesNote",more);


                JSONObject Items = new JSONObject();

                for(int i=0 ; i< data.size() ; i++)
                {
                    Items.put(data.get(i).getId()+"",data.get(i).getCounter());
                }

                jsonBody.put("Items",Items);



                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                // .setText("String Response : "+ response.toString());
                                Log.i("respones", "succed");
                                try {
                                    int RequestId = response.getInt("RequestId");
                                    mpreference.setRequestnum(RequestId);
                                    int SecretNum = response.getInt("Serial");
                                    mpreference.setSecretNum(SecretNum);


                                    mSecretNumber.setTypeface(tf);
                                    mSecretNumberHint.setTypeface(tf);


                                    // add this line to function bindDataToBill1()

                                 //   String secretNumber=SecretNum+"";

                                    mSecretNumber.setText(SecretNum+"");
                                    mBillNumber.setText(RequestId+"");
                                   // mPage2IdNumber.setText(RequestId+"");
                                    mPage2BillNumber.setText(RequestId+"");

                                //   view.setClickable(false);
                                 //   view.setEnabled(false);
                                    view.setVisibility(View.INVISIBLE);
                                    btnRecieve.setVisibility(View.VISIBLE);

                                    mPage3BillNumber1.setText(RequestId+"");
                                    mPage3BillNumber2.setText(RequestId+"");
                                    mPage3BillNumber3.setText(RequestId+"");

                                    createPdf1();
                                    createPdf2();
                                    createPdfSticker();
                                }catch (Exception ex){
                                    Toast.makeText(CreatePdfActivity.this,"حدث خطأ تقني",Toast.LENGTH_LONG).show();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Toast.makeText(CreatePdfActivity.this,"حدث خطأ تقني اثناء الاتصال بالخادم",Toast.LENGTH_LONG).show();
                        // clientname.setText("Error getting response");
                        error.printStackTrace();
                    }
                });
                jsonObjectRequest.setTag(REQ_TAG);
                requestQueue.add(jsonObjectRequest);



            }catch (Exception ex){

            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void btnRecieverClicked(final View view) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/AlAhram" +"/"+ filerecieverName);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, "Open File");

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }

    }

    public static final int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;

    Bitmap getBitmapImageOfView(View view  )
    {
        mMyRootView.setDrawingCacheEnabled(true);
        Bitmap screen= getBitmapFromView(view);
        //here give id of our root layout (here its my RelativeLayout's id)
        return screen;

    }
    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        //draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    boolean fileOneFinish=false;
    boolean fileTwoFinish=false;
    boolean fileThreeFinish=false;

    String fileNameclinet;
    String fileNameclinetSticker;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void createPdf1(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        PdfDocument document = new PdfDocument();


        Bitmap bitmap1;
        PdfDocument.PageInfo pageInfo1;
        PdfDocument.Page page1;
        Canvas canvas1;


        bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.myView));
        pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
        page1 = document.startPage(pageInfo1);
        canvas1 = page1.getCanvas();
        bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
        canvas1.drawBitmap(bitmap1, 0, 0, null);
        document.finishPage(page1);


//        int pageNumber=2;
//        for (int i=0;i<data.size();i++) {
//            for (int j=0;j<data.get(i).getCounter();j++) {
//
//                mPage3ContentNumber1.setText(data.get(i).getOrdername());
//                mPage3ContentNumber2.setText(data.get(i).getOrdername());
//                mPage3ContentNumber3.setText(data.get(i).getOrdername());
//                Bitmap bitmap2;
//                PdfDocument.PageInfo pageInfo2;
//                PdfDocument.Page page2;
//                Canvas canvas2;
//                bitmap2 = getBitmapImageOfView(this.getWindow().findViewById(R.id.page3_myView));
//                pageInfo2 = new PdfDocument.PageInfo.Builder(bitmap2.getWidth(), bitmap2.getHeight(), pageNumber).create();
//                pageNumber++;
//                page2 = document.startPage(pageInfo2);
//                canvas2 = page2.getCanvas();
//                bitmap2 = Bitmap.createScaledBitmap(bitmap2, bitmap2.getWidth(), bitmap2.getHeight(), true);
//                canvas2.drawBitmap(bitmap2, 0, 0, null);
//                document.finishPage(page2);
//            }
//        }

        File root = new File(Environment.getExternalStorageDirectory(), "AlAhram");
        if (!root.exists()) {
            root.mkdirs();
        }
        MediaScannerConnection.scanFile(this, new String[] {root.toString()}, null, null);

        Preferences preferences=new Preferences(this);
         fileNameclinet=preferences.getclientname()+"_فاتورة العميل_"+String.valueOf(getRandomBillNumber())+".pdf";
        File filePath = new File(root,fileNameclinet);
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(filePath);
            document.writeTo(fileOutputStream);
            Toast.makeText(this, "تم انشاء الفواتير قم بالطباعه اولا ثم اضغط علي التالي " , Toast.LENGTH_LONG).show();
            fileOutputStream.close();
            document.close();
            fileOneFinish=true;
            dismissDialog();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            fileOneFinish=true;
            dismissDialog();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void createPdfSticker(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        PdfDocument document = new PdfDocument();


        Bitmap bitmap1;
        PdfDocument.PageInfo pageInfo1;
        PdfDocument.Page page1;
        Canvas canvas1;


//        bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.myView));
//        pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
//        page1 = document.startPage(pageInfo1);
//        canvas1 = page1.getCanvas();
//        bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
//        canvas1.drawBitmap(bitmap1, 0, 0, null);
//        document.finishPage(page1);


        int pageNumber=2;
        for (int i=0;i<data.size();i++) {
            for (int j=0;j<data.get(i).getCounter();j++) {

                mPage3ContentNumber1.setText(data.get(i).getOrdername());
                mPage3ContentNumber2.setText(data.get(i).getOrdername());
                mPage3ContentNumber3.setText(data.get(i).getOrdername());
                Bitmap bitmap2;
                PdfDocument.PageInfo pageInfo2;
                PdfDocument.Page page2;
                Canvas canvas2;
                bitmap2 = getBitmapImageOfView(this.getWindow().findViewById(R.id.page3_myView));
                pageInfo2 = new PdfDocument.PageInfo.Builder(bitmap2.getWidth(), bitmap2.getHeight(), pageNumber).create();
                pageNumber++;
                page2 = document.startPage(pageInfo2);
                canvas2 = page2.getCanvas();
                bitmap2 = Bitmap.createScaledBitmap(bitmap2, bitmap2.getWidth(), bitmap2.getHeight(), true);
                canvas2.drawBitmap(bitmap2, 0, 0, null);
                document.finishPage(page2);
            }
        }

        File root = new File(Environment.getExternalStorageDirectory(), "AlAhram");
        if (!root.exists()) {
            root.mkdirs();
        }
        MediaScannerConnection.scanFile(this, new String[] {root.toString()}, null, null);

        Preferences preferences=new Preferences(this);
        fileNameclinetSticker=preferences.getclientname()+"_استكر_"+String.valueOf(getRandomBillNumber())+".pdf";
        File filePath = new File(root,fileNameclinetSticker);
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(filePath);
            document.writeTo(fileOutputStream);
            Toast.makeText(this, "تم انشاء الفواتير قم بالطباعه اولا ثم اضغط علي التالي " , Toast.LENGTH_LONG).show();
            fileOutputStream.close();
            document.close();
            fileThreeFinish=true;
            dismissDialog();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            fileThreeFinish=true;
            dismissDialog();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void createPdf2(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        PdfDocument document = new PdfDocument();

        Bitmap bitmap;
        PdfDocument.PageInfo pageInfo;
        PdfDocument.Page page;
        Canvas canvas;

        bitmap= getBitmapImageOfView(this.getWindow().findViewById(R.id.page2_myView));
        pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        File root = new File(Environment.getExternalStorageDirectory(), "AlAhram");
        if (!root.exists()) {
            root.mkdirs();
        }
        MediaScannerConnection.scanFile(this, new String[] {root.toString()}, null, null);

        Preferences preferences=new Preferences(this);
        String fileName=preferences.getclientname()+"_فاتورة الاستلام_"+String.valueOf(getRandomBillNumber())+".pdf";
        File filePath = new File(root,fileName);
            filerecieverName = fileName;
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(filePath);
            document.writeTo(fileOutputStream);
            Toast.makeText(this,  "تم انشاء الفواتير قم بالطباعه اولا ثم اضغط علي التالي " , Toast.LENGTH_LONG).show();
            fileOutputStream.close();
            document.close();
            fileTwoFinish=true;
            dismissDialog();





        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            fileTwoFinish=true;
            dismissDialog();
        }
    }

    private boolean fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(CreatePdfActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(CreatePdfActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(CreatePdfActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(CreatePdfActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
            boolean_permission = false;
            return boolean_permission;
        }
        else {
            boolean_permission = true;
            return boolean_permission;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                boolean_permission = true;
                bindDataToStickersView("");
                bindDataToBill1();
                bindDataToBill2();
                createPdf1();
                createPdf2();
                createPdfSticker();
            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void dismissDialog()
    {
        if (fileOneFinish&& fileTwoFinish && fileThreeFinish) {
            dialog.dismiss();

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/AlAhram" +"/"+ fileNameclinet);
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(file),"application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Intent intent = Intent.createChooser(target, "Open File");

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
            }

        }
    }

    private void openFolder()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + "/AlAhram/");
        intent.setDataAndType(uri, "*/*");
        startActivity(Intent.createChooser(intent, "Open folder"));
    }

}

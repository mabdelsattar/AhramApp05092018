package com.abdelsattar.alahramapp;

import android.Manifest;
import android.annotation.TargetApi;
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

import com.abdelsattar.alahramapp.Ui.AttachActivity;
import com.abdelsattar.alahramapp.Utilitis.Preferences;
import com.abdelsattar.alahramapp.model.AddRequestModel;
import com.abdelsattar.alahramapp.pdfCreate.VerticalTextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forceRTLIfSupported();
        setContentView(R.layout.activity_print_pdf);
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
        data = (ArrayList<AddRequestModel>) bundle.getSerializable("dataList");

        for (int i=0;i<data.size();i++)
        {
            totalPrice+=data.get(i).getCounter()*Integer.valueOf(data.get(i).getOrderprice());
            Log.d("dataItem",""+data.get(i).toString());
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
        bindDataToStickersView();
        bindDataToBill1();
        bindDataToBill2();

    }

    private void  bindDataToBill1()
    {
        String billNumber=String.valueOf(getRandomBillNumber());
        String mobileNumber=mpreference.getClientPhoneKsa();
        String itemNumber=String.valueOf(data.size());
        String date=getCurrantDate();

        String receiverName=mpreference.getRecivername();
        String senderName=mpreference.getclientname();
        String receiverAddress=mpreference.getReciverAddressDetial();
        String receiverPhones=mpreference.getReciverPhoneEgy();
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

            bayanTextView.setText(data.get(i).getOrdername());
            quantityTextView.setText(String.valueOf(data.get(i).getCounter()));
            priceTextView.setText(data.get(i).getOrderprice() +currencyUnit);
            totalTextView.setText(String.valueOf((data.get(i).getCounter() * (Integer.valueOf(data.get(i).getOrderprice()))))+ currencyUnit );
            child.setTag(i);
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
        String receiver=mpreference.getRecivername();
        String signature="";
        String idNumber=mpreference.getReciverNationalId();

        mPage2BillNumber.setText(billNumber);
        mPage2Count.setText(itemCount);
        mPage2Date.setText(date);
        mPage2ReceiverName.setText(receiverName);
        mPage2City.setText(city);
        mPage2Country.setText(country);
        mPage2Mobile.setText(mobileNumber);
        mPage2Receiver.setText(receiver);
        mPage2Signature.setText(signature);
        mPage2IdNumber.setText(idNumber);

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

    private void bindDataToStickersView()
    {
        // Stickers
        String stickersBillNumber=String.valueOf(getRandomBillNumber());
        String stickersSenderName="محمد عبد الستار";
        String stickersReceiverName="محمد عنتر";
        String stickersDirection="الاتجاه الاتجاه";
        String stickersContent="content content content";
        String stickersMobileNumber="01112688365";

        mPage4ReceiverName1.setText(stickersReceiverName);
        mPage4ReceiverName2.setText(stickersReceiverName);
        mPage4ReceiverName3.setText(stickersReceiverName);

        mPage3ReceiverName1.setText(stickersReceiverName);
        mPage3ReceiverName2.setText(stickersReceiverName);
        mPage3ReceiverName3.setText(stickersReceiverName);

        mPage4BillNumber1.setText(stickersBillNumber);
        mPage4BillNumber2.setText(stickersBillNumber);
        mPage4BillNumber3.setText(stickersBillNumber);
        mPage3BillNumber1.setText(stickersBillNumber);
        mPage3BillNumber2.setText(stickersBillNumber);
        mPage3BillNumber3.setText(stickersBillNumber);

        mPage4ContentNumber1.setText(stickersContent);
        mPage4ContentNumber2.setText(stickersContent);
        mPage4ContentNumber3.setText(stickersContent);
        mPage3ContentNumber1.setText(stickersContent);
        mPage3ContentNumber2.setText(stickersContent);
        mPage3ContentNumber3.setText(stickersContent);

        mPage4Direction1.setText(stickersDirection);
        mPage4Direction2.setText(stickersDirection);
        mPage4Direction3.setText(stickersDirection);
        mPage3Direction1.setText(stickersDirection);
        mPage3Direction2.setText(stickersDirection);
        mPage3Direction3.setText(stickersDirection);

        mPage4Mobile1.setText(stickersMobileNumber);
        mPage4Mobile2.setText(stickersMobileNumber);
        mPage4Mobile3.setText(stickersMobileNumber);
        mPage3Mobile1.setText(stickersMobileNumber);
        mPage3Mobile2.setText(stickersMobileNumber);
        mPage3Mobile3.setText(stickersMobileNumber);

        mPage4SenderName1.setText(stickersSenderName);
        mPage4SenderName2.setText(stickersSenderName);
        mPage4SenderName3.setText(stickersSenderName);
        mPage3SenderName1.setText(stickersSenderName);
        mPage3SenderName2.setText(stickersSenderName);
        mPage3SenderName3.setText(stickersSenderName);
    }


    public int getRandomBillNumber() {

        Random rand = new Random();
        int randomNum = rand.nextInt((99999 - 10000) + 1) + 10000;
        return randomNum;
    }
    public String getCurrantDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String dateFormat = formatter.format(date.getTime());
        return dateFormat;
    }
//    ProgressDialog progressDialog;
//    boolean file1Finish,file2Finish;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void savePdf(View view) {
//        file1Finish=false;
//        file2Finish=false;
//
//        progressDialog = new ProgressDialog(CreatePdfActivity.this);
//        progressDialog.setMessage("جاري حفظ الفواتير...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
//        progressDialog.show(); // Display Progress Dialog
//        progressDialog.setCancelable(false);


        createPdf1();
        createPdf2();
    }
//    void dismissDialog()
//    {
//        if (file1Finish&& file2Finish)
//            progressDialog.dismiss();
//    }
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
   /* @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void createPdf1(){
        fn_permission();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        PdfDocument document = new PdfDocument();



        Bitmap bitmap2;
        PdfDocument.PageInfo pageInfo2;
        PdfDocument.Page page2;
        Canvas canvas2;
        bitmap2= getBitmapImageOfView(this.getWindow().findViewById(R.id.page3_myView));
        pageInfo2 = new PdfDocument.PageInfo.Builder(bitmap2.getWidth(), bitmap2.getHeight(), 2).create();
        page2 = document.startPage(pageInfo2);
        canvas2 = page2.getCanvas();
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, bitmap2.getWidth(), bitmap2.getHeight(), true);
        canvas2.drawBitmap(bitmap2, 0, 0 , null);
        document.finishPage(page2);

        Bitmap bitmap3;
        PdfDocument.PageInfo pageInfo3;
        PdfDocument.Page page3;
        Canvas canvas3;
        bitmap3 = getBitmapImageOfView(this.getWindow().findViewById(R.id.page4_myView));
        pageInfo3 = new PdfDocument.PageInfo.Builder(bitmap3.getWidth(), bitmap3.getHeight(), 3).create();
        page3 = document.startPage(pageInfo3);
        canvas3 = page3.getCanvas();
        bitmap3 = Bitmap.createScaledBitmap(bitmap3, bitmap3.getWidth(), bitmap3.getHeight(), true);
        canvas3.drawBitmap(bitmap3, 0, 0 , null);
        document.finishPage(page3);


        Bitmap bitmap1;
        PdfDocument.PageInfo pageInfo1;
        PdfDocument.Page page1;
        Canvas canvas1;


        bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.page2_myView));
        pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
        page1 = document.startPage(pageInfo1);
        canvas1 = page1.getCanvas();
        bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
        canvas1.drawBitmap(bitmap1, 0, 0, null);
        document.finishPage(page1);
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AlAhram");
        root.mkdirs();

        File filePath = new File(root + File.separator + String.valueOf(new Date().getTime())+".pdf");
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done " , Toast.LENGTH_LONG).show();
//            file1Finish=true;
//            dismissDialog();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//            file1Finish=true;
//            dismissDialog();
        }
        document.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void createPdf2(){
        fn_permission();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        PdfDocument document = new PdfDocument();

        Bitmap bitmap;
        PdfDocument.PageInfo pageInfo;
        PdfDocument.Page page;
        Canvas canvas;

        bitmap= getBitmapImageOfView(this.getWindow().findViewById(R.id.myView));
        pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AlAhram");
        root.mkdirs();

        File filePath = new File(root + File.separator + String.valueOf(new Date().getTime())+".pdf");
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done " , Toast.LENGTH_LONG).show();
//            file2Finish=true;
//            dismissDialog();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//            file2Finish=true;
//            dismissDialog();
        }
        document.close();
    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void createPdf1(){
        fn_permission();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        PdfDocument document = new PdfDocument();



        Bitmap bitmap2;
        PdfDocument.PageInfo pageInfo2;
        PdfDocument.Page page2;
        Canvas canvas2;
        bitmap2= getBitmapImageOfView(this.getWindow().findViewById(R.id.page3_myView));
        pageInfo2 = new PdfDocument.PageInfo.Builder(bitmap2.getWidth(), bitmap2.getHeight(), 2).create();
        page2 = document.startPage(pageInfo2);
        canvas2 = page2.getCanvas();
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, bitmap2.getWidth(), bitmap2.getHeight(), true);
        canvas2.drawBitmap(bitmap2, 0, 0 , null);
        document.finishPage(page2);

        Bitmap bitmap3;
        PdfDocument.PageInfo pageInfo3;
        PdfDocument.Page page3;
        Canvas canvas3;
        bitmap3 = getBitmapImageOfView(this.getWindow().findViewById(R.id.page4_myView));
        pageInfo3 = new PdfDocument.PageInfo.Builder(bitmap3.getWidth(), bitmap3.getHeight(), 3).create();
        page3 = document.startPage(pageInfo3);
        canvas3 = page3.getCanvas();
        bitmap3 = Bitmap.createScaledBitmap(bitmap3, bitmap3.getWidth(), bitmap3.getHeight(), true);
        canvas3.drawBitmap(bitmap3, 0, 0 , null);
        document.finishPage(page3);


        Bitmap bitmap1;
        PdfDocument.PageInfo pageInfo1;
        PdfDocument.Page page1;
        Canvas canvas1;


        bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.page2_myView));
        pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
        page1 = document.startPage(pageInfo1);
        canvas1 = page1.getCanvas();
        bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
        canvas1.drawBitmap(bitmap1, 0, 0, null);
        document.finishPage(page1);
        File root = new File(Environment.getExternalStorageDirectory(), "AlAhram");
        if (!root.exists()) {
            root.mkdirs();
        }
        MediaScannerConnection.scanFile(this, new String[] {root.toString()}, null, null);

        File filePath = new File(root,String.valueOf(new Date().getTime())+".pdf");
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(filePath);
            document.writeTo(fileOutputStream);
            Toast.makeText(this, "Done " , Toast.LENGTH_LONG).show();
            fileOutputStream.close();
            document.close();
//            file1Finish=true;
//            dismissDialog();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//            file1Finish=true;
//            dismissDialog();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void createPdf2(){
        fn_permission();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        PdfDocument document = new PdfDocument();

        Bitmap bitmap;
        PdfDocument.PageInfo pageInfo;
        PdfDocument.Page page;
        Canvas canvas;

        bitmap= getBitmapImageOfView(this.getWindow().findViewById(R.id.myView));
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

        File filePath = new File(root,String.valueOf(new Date().getTime())+".pdf");
//        filePath.mkdirs();
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(filePath);
            document.writeTo(fileOutputStream);
            Toast.makeText(this, "Done " , Toast.LENGTH_LONG).show();
            fileOutputStream.close();
            document.close();
//            file2Finish=true;
//            dismissDialog();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//            file2Finish=true;
//            dismissDialog();
        }
    }




    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

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
        } else {
            boolean_permission = true;

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                boolean_permission = true;
            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}

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

import org.json.JSONArray;
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
    int RequestId;


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


    @BindView(R.id.page2_dateHint_sender)
    TextView page2_dateHint_sender;

    @BindView(R.id.page2_date_senderTV)
    TextView page2_date_senderTV;

    @BindView(R.id.page2_dateHint_nationalid)
    TextView page2_dateHint_nationalid;

    @BindView(R.id.page2_date_NationalIdTV)
    TextView page2_date_NationalIdTV;

    @BindView(R.id.clientNationalIdHint)
    TextView clientNationalIdHint;

    @BindView(R.id.clientNationalId)
    TextView clientNationalId;


    @BindView(R.id.tvclientnotes)
    TextView tvclientnotes;

    @BindView(R.id.clientnotes2)
    TextView clientnotes2;


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
    @BindView(R.id.page2_myView_2)
    RelativeLayout mPage2MyView2;
    @BindView(R.id.page2_billNumberHint_2)
    TextView mPage2BillNumberHint2;
    @BindView(R.id.page2_billNumber_2)
    TextView mPage2BillNumber2;

    @BindView(R.id.page2_billDetails_2)
    LinearLayout mPage2BillDetails2;
    @BindView(R.id.page2_tableView_2)
    LinearLayout mPage2TableView2;

    @BindView(R.id.clientnotes2_2)
    TextView mClientnotes22;
    @BindView(R.id.Page2_TrodeNumberHint_2)
    TextView mPage2TrodeNumberHint2;
    @BindView(R.id.Page2_TrodeNumber_2)
    TextView mPage2TrodeNumber2;
    @BindView(R.id.page2_reciverDetails_2)
    LinearLayout mPage2ReciverDetails2;
    @BindView(R.id.page2_mobileHint_2)
    TextView mPage2MobileHint2;
    @BindView(R.id.page2_mobile_2)
    TextView mPage2Mobile2;
    @BindView(R.id.page2_receiverHint_2)
    TextView mPage2ReceiverHint2;
    @BindView(R.id.page2_receiver_2)
    TextView mPage2Receiver2;
    @BindView(R.id.page2_signatureHint_2)
    TextView mPage2SignatureHint2;
    @BindView(R.id.page2_signature_2)
    TextView mPage2Signature2;
    @BindView(R.id.page2_idNumberHint_2)
    TextView mPage2IdNumberHint2;
    @BindView(R.id.page2_idNumber_2)
    TextView mPage2IdNumber2;

    @BindView(R.id.page2_myView_3)
    RelativeLayout mPage2MyView3;
    @BindView(R.id.page2_billNumberHint_3)
    TextView mPage2BillNumberHint3;
    @BindView(R.id.page2_billNumber_3)
    TextView mPage2BillNumber3;

    @BindView(R.id.page2_billDetails_3)
    LinearLayout mPage2BillDetails3;
    @BindView(R.id.page2_tableView_3)
    LinearLayout mPage2TableView3;

    @BindView(R.id.clientnotes2_3)
    TextView mClientnotes23;
    @BindView(R.id.Page2_TrodeNumberHint_3)
    TextView mPage2TrodeNumberHint3;
    @BindView(R.id.Page2_TrodeNumber_3)
    TextView mPage2TrodeNumber3;
    @BindView(R.id.page2_reciverDetails_3)
    LinearLayout mPage2ReciverDetails3;
    @BindView(R.id.page2_mobileHint_3)
    TextView mPage2MobileHint3;
    @BindView(R.id.page2_mobile_3)
    TextView mPage2Mobile3;
    @BindView(R.id.page2_receiverHint_3)
    TextView mPage2ReceiverHint3;
    @BindView(R.id.page2_receiver_3)
    TextView mPage2Receiver3;
    @BindView(R.id.page2_signatureHint_3)
    TextView mPage2SignatureHint3;
    @BindView(R.id.page2_signature_3)
    TextView mPage2Signature3;
    @BindView(R.id.page2_idNumberHint_3)
    TextView mPage2IdNumberHint3;
    @BindView(R.id.page2_idNumber_3)
    TextView mPage2IdNumber3;

    @BindView(R.id.page2_myView_4)
    RelativeLayout mPage2MyView4;
    @BindView(R.id.page2_billNumberHint_4)
    TextView mPage2BillNumberHint4;
    @BindView(R.id.page2_billNumber_4)
    TextView mPage2BillNumber4;

    @BindView(R.id.page2_billDetails_4)
    LinearLayout mPage2BillDetails4;
    @BindView(R.id.page2_tableView_4)
    LinearLayout mPage2TableView4;

    @BindView(R.id.clientnotes2_4)
    TextView mClientnotes24;
    @BindView(R.id.Page2_TrodeNumberHint_4)
    TextView mPage2TrodeNumberHint4;
    @BindView(R.id.Page2_TrodeNumber_4)
    TextView mPage2TrodeNumber4;
    @BindView(R.id.page2_reciverDetails_4)
    LinearLayout mPage2ReciverDetails4;
    @BindView(R.id.page2_mobileHint_4)
    TextView mPage2MobileHint4;
    @BindView(R.id.page2_mobile_4)
    TextView mPage2Mobile4;
    @BindView(R.id.page2_receiverHint_4)
    TextView mPage2ReceiverHint4;
    @BindView(R.id.page2_receiver_4)
    TextView mPage2Receiver4;
    @BindView(R.id.page2_signatureHint_4)
    TextView mPage2SignatureHint4;
    @BindView(R.id.page2_signature_4)
    TextView mPage2Signature4;
    @BindView(R.id.page2_idNumberHint_4)
    TextView mPage2IdNumberHint4;
    @BindView(R.id.page2_idNumber_4)
    TextView mPage2IdNumber4;

    @BindView(R.id.page2_myView_5)
    RelativeLayout mPage2MyView5;
    @BindView(R.id.page2_billNumberHint_5)
    TextView mPage2BillNumberHint5;
    @BindView(R.id.page2_billNumber_5)
    TextView mPage2BillNumber5;

    @BindView(R.id.page2_billDetails_5)
    LinearLayout mPage2BillDetails5;
    @BindView(R.id.page2_tableView_5)
    LinearLayout mPage2TableView5;

    @BindView(R.id.clientnotes2_5)
    TextView mClientnotes25;
    @BindView(R.id.Page2_TrodeNumberHint_5)
    TextView mPage2TrodeNumberHint5;
    @BindView(R.id.Page2_TrodeNumber_5)
    TextView mPage2TrodeNumber5;
    @BindView(R.id.page2_reciverDetails_5)
    LinearLayout mPage2ReciverDetails5;
    @BindView(R.id.page2_mobileHint_5)
    TextView mPage2MobileHint5;
    @BindView(R.id.page2_mobile_5)
    TextView mPage2Mobile5;
    @BindView(R.id.page2_receiverHint_5)
    TextView mPage2ReceiverHint5;
    @BindView(R.id.page2_receiver_5)
    TextView mPage2Receiver5;
    @BindView(R.id.page2_signatureHint_5)
    TextView mPage2SignatureHint5;
    @BindView(R.id.page2_signature_5)
    TextView mPage2Signature5;
    @BindView(R.id.page2_idNumberHint_5)
    TextView mPage2IdNumberHint5;
    @BindView(R.id.page2_idNumber_5)
    TextView mPage2IdNumber5;

    @BindView(R.id.page2_myView_6)
    RelativeLayout mPage2MyView6;
    @BindView(R.id.page2_billNumberHint_6)
    TextView mPage2BillNumberHint6;
    @BindView(R.id.page2_billNumber_6)
    TextView mPage2BillNumber6;

    @BindView(R.id.page2_billDetails_6)
    LinearLayout mPage2BillDetails6;
    @BindView(R.id.page2_tableView_6)
    LinearLayout mPage2TableView6;

    @BindView(R.id.clientnotes2_6)
    TextView mClientnotes26;
    @BindView(R.id.Page2_TrodeNumberHint_6)
    TextView mPage2TrodeNumberHint6;
    @BindView(R.id.Page2_TrodeNumber_6)
    TextView mPage2TrodeNumber6;
    @BindView(R.id.page2_reciverDetails_6)
    LinearLayout mPage2ReciverDetails6;
    @BindView(R.id.page2_mobileHint_6)
    TextView mPage2MobileHint6;
    @BindView(R.id.page2_mobile_6)
    TextView mPage2Mobile6;
    @BindView(R.id.page2_receiverHint_6)
    TextView mPage2ReceiverHint6;
    @BindView(R.id.page2_receiver_6)
    TextView mPage2Receiver6;
    @BindView(R.id.page2_signatureHint_6)
    TextView mPage2SignatureHint6;
    @BindView(R.id.page2_signature_6)
    TextView mPage2Signature6;
    @BindView(R.id.page2_idNumberHint_6)
    TextView mPage2IdNumberHint6;
    @BindView(R.id.page2_idNumber_6)
    TextView mPage2IdNumber6;

    @BindView(R.id.page2_myView_7)
    RelativeLayout mPage2MyView7;
    @BindView(R.id.page2_billNumberHint_7)
    TextView mPage2BillNumberHint7;
    @BindView(R.id.page2_billNumber_7)
    TextView mPage2BillNumber7;

    @BindView(R.id.page2_billDetails_7)
    LinearLayout mPage2BillDetails7;
    @BindView(R.id.page2_tableView_7)
    LinearLayout mPage2TableView7;

    @BindView(R.id.clientnotes2_7)
    TextView mClientnotes27;
    @BindView(R.id.Page2_TrodeNumberHint_7)
    TextView mPage2TrodeNumberHint7;
    @BindView(R.id.Page2_TrodeNumber_7)
    TextView mPage2TrodeNumber7;
    @BindView(R.id.page2_reciverDetails_7)
    LinearLayout mPage2ReciverDetails7;
    @BindView(R.id.page2_mobileHint_7)
    TextView mPage2MobileHint7;
    @BindView(R.id.page2_mobile_7)
    TextView mPage2Mobile7;
    @BindView(R.id.page2_receiverHint_7)
    TextView mPage2ReceiverHint7;
    @BindView(R.id.page2_receiver_7)
    TextView mPage2Receiver7;
    @BindView(R.id.page2_signatureHint_7)
    TextView mPage2SignatureHint7;
    @BindView(R.id.page2_signature_7)
    TextView mPage2Signature7;
    @BindView(R.id.page2_idNumberHint_7)
    TextView mPage2IdNumberHint7;
    @BindView(R.id.page2_idNumber_7)
    TextView mPage2IdNumber7;

    @BindView(R.id.page2_myView_8)
    RelativeLayout mPage2MyView8;
    @BindView(R.id.page2_billNumberHint_8)
    TextView mPage2BillNumberHint8;
    @BindView(R.id.page2_billNumber_8)
    TextView mPage2BillNumber8;

    @BindView(R.id.page2_billDetails_8)
    LinearLayout mPage2BillDetails8;
    @BindView(R.id.page2_tableView_8)
    LinearLayout mPage2TableView8;

    @BindView(R.id.clientnotes2_8)
    TextView mClientnotes28;
    @BindView(R.id.Page2_TrodeNumberHint_8)
    TextView mPage2TrodeNumberHint8;
    @BindView(R.id.Page2_TrodeNumber_8)
    TextView mPage2TrodeNumber8;
    @BindView(R.id.page2_reciverDetails_8)
    LinearLayout mPage2ReciverDetails8;
    @BindView(R.id.page2_mobileHint_8)
    TextView mPage2MobileHint8;
    @BindView(R.id.page2_mobile_8)
    TextView mPage2Mobile8;
    @BindView(R.id.page2_receiverHint_8)
    TextView mPage2ReceiverHint8;
    @BindView(R.id.page2_receiver_8)
    TextView mPage2Receiver8;
    @BindView(R.id.page2_signatureHint_8)
    TextView mPage2SignatureHint8;
    @BindView(R.id.page2_signature_8)
    TextView mPage2Signature8;
    @BindView(R.id.page2_idNumberHint_8)
    TextView mPage2IdNumberHint8;
    @BindView(R.id.page2_idNumber_8)
    TextView mPage2IdNumber8;

    @BindView(R.id.page2_myView_9)
    RelativeLayout mPage2MyView9;
    @BindView(R.id.page2_billNumberHint_9)
    TextView mPage2BillNumberHint9;
    @BindView(R.id.page2_billNumber_9)
    TextView mPage2BillNumber9;

    @BindView(R.id.page2_billDetails_9)
    LinearLayout mPage2BillDetails9;
    @BindView(R.id.page2_tableView_9)
    LinearLayout mPage2TableView9;

    @BindView(R.id.clientnotes2_9)
    TextView mClientnotes29;
    @BindView(R.id.Page2_TrodeNumberHint_9)
    TextView mPage2TrodeNumberHint9;
    @BindView(R.id.Page2_TrodeNumber_9)
    TextView mPage2TrodeNumber9;
    @BindView(R.id.page2_reciverDetails_9)
    LinearLayout mPage2ReciverDetails9;
    @BindView(R.id.page2_mobileHint_9)
    TextView mPage2MobileHint9;
    @BindView(R.id.page2_mobile_9)
    TextView mPage2Mobile9;
    @BindView(R.id.page2_receiverHint_9)
    TextView mPage2ReceiverHint9;
    @BindView(R.id.page2_receiver_9)
    TextView mPage2Receiver9;
    @BindView(R.id.page2_signatureHint_9)
    TextView mPage2SignatureHint9;
    @BindView(R.id.page2_signature_9)
    TextView mPage2Signature9;
    @BindView(R.id.page2_idNumberHint_9)
    TextView mPage2IdNumberHint9;
    @BindView(R.id.page2_idNumber_9)
    TextView mPage2IdNumber9;

    /** ButterKnife Code **/





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





    /** ButterKnife Code **/
    @BindView(R.id.myView_2)
    RelativeLayout mMyView2;

    @BindView(R.id.billNumberHint_2)
    TextView mBillNumberHint2;
    @BindView(R.id.billNumber_2)
    TextView mBillNumber2;
    @BindView(R.id.verticalLine_2)
    View mVerticalLine_2;
    @BindView(R.id.verticalTextView_2)
    com.alahram.alahramapp.pdfCreate.VerticalTextView mVerticalTextView2;
    @BindView(R.id.tableHeader_2)
    LinearLayout mTableHeader2;
    @BindView(R.id.tableItem_2)
    LinearLayout mTableItem2;
    @BindView(R.id.tableFooter_2)
    LinearLayout mTableFooter2;
    @BindView(R.id.totalHint_2)
    TextView mTotalHint2;
    @BindView(R.id.total_2)
    TextView mTotal2;
    @BindView(R.id.rules_2)
    LinearLayout mRules2;
    @BindView(R.id.TrodeNumberHint_2)
    TextView mTrodeNumberHint2;
    @BindView(R.id.TrodeNumber_2)
    TextView mTrodeNumber2;
    @BindView(R.id.ruleLine0_2)
    TextView mRuleLine02;
    @BindView(R.id.ruleLine1_2)
    TextView mRuleLine12;
    @BindView(R.id.ruleLine2_2)
    TextView mRuleLine22;
    @BindView(R.id.ruleLine3_2)
    TextView mRuleLine32;
    @BindView(R.id.ruleLine4_2)
    TextView mRuleLine42;
    @BindView(R.id.ruleLine5_2)
    TextView mRuleLine52;
    @BindView(R.id.ruleLine6_2)
    TextView mRuleLine62;
    @BindView(R.id.ruleLine7_2)
    TextView mRuleLine72;
    @BindView(R.id.ruleLine8_2)
    TextView mRuleLine82;
    @BindView(R.id.verticalLine2_2)
    View mVerticalLine22;
    @BindView(R.id.signatureDetails_2)
    LinearLayout mSignatureDetails2;
    @BindView(R.id.receiverInOfficeHint_2)
    TextView mReceiverInOfficeHint2;
    @BindView(R.id.receiverInOffice_2)
    TextView mReceiverInOffice2;
    @BindView(R.id.receiverIdHint_2)
    TextView mReceiverIdHint2;
    @BindView(R.id.receiverId_2)
    TextView mReceiverId2;
    @BindView(R.id.signature1Hint_2)
    TextView mSignature1Hint2;
    @BindView(R.id.signature1_2)
    TextView mSignature12;
    @BindView(R.id.signature2Hint_2)
    TextView mSignature2Hint2;
    @BindView(R.id.signature2_2)
    TextView mSignature22;
    @BindView(R.id.footer_2_2)
    LinearLayout mFooter22;
    @BindView(R.id.line1_2)
    TextView mLine12;
    @BindView(R.id.line2_2)
    TextView mLine22;
    @BindView(R.id.myView_3)
    RelativeLayout mMyView3;

    @BindView(R.id.billNumberHint_3)
    TextView mBillNumberHint3;
    @BindView(R.id.billNumber_3)
    TextView mBillNumber3;
    @BindView(R.id.verticalLine_3)
    View mVerticalLine3;
    @BindView(R.id.verticalTextView_3)
    com.alahram.alahramapp.pdfCreate.VerticalTextView mVerticalTextView3;
    @BindView(R.id.tableHeader_3)
    LinearLayout mTableHeader3;
    @BindView(R.id.tableItem_3)
    LinearLayout mTableItem3;
    @BindView(R.id.tableFooter_3)
    LinearLayout mTableFooter3;
    @BindView(R.id.totalHint_3)
    TextView mTotalHint3;
    @BindView(R.id.total_3)
    TextView mTotal3;
    @BindView(R.id.rules_3)
    LinearLayout mRules3;
    @BindView(R.id.TrodeNumberHint_3)
    TextView mTrodeNumberHint3;
    @BindView(R.id.TrodeNumber_3)
    TextView mTrodeNumber3;
    @BindView(R.id.ruleLine0_3)
    TextView mRuleLine03;
    @BindView(R.id.ruleLine1_3)
    TextView mRuleLine13;
    @BindView(R.id.ruleLine2_3)
    TextView mRuleLine23;
    @BindView(R.id.ruleLine3_3)
    TextView mRuleLine33;
    @BindView(R.id.ruleLine4_3)
    TextView mRuleLine43;
    @BindView(R.id.ruleLine5_3)
    TextView mRuleLine53;
    @BindView(R.id.ruleLine6_3)
    TextView mRuleLine63;
    @BindView(R.id.ruleLine7_3)
    TextView mRuleLine73;
    @BindView(R.id.ruleLine8_3)
    TextView mRuleLine83;
    @BindView(R.id.verticalLine2_3)
    View mVerticalLine23;
    @BindView(R.id.signatureDetails_3)
    LinearLayout mSignatureDetails3;
    @BindView(R.id.receiverInOfficeHint_3)
    TextView mReceiverInOfficeHint3;
    @BindView(R.id.receiverInOffice_3)
    TextView mReceiverInOffice3;
    @BindView(R.id.receiverIdHint_3)
    TextView mReceiverIdHint3;
    @BindView(R.id.receiverId_3)
    TextView mReceiverId3;
    @BindView(R.id.signature1Hint_3)
    TextView mSignature1Hint3;
    @BindView(R.id.signature1_3)
    TextView mSignature13;
    @BindView(R.id.signature2Hint_3)
    TextView mSignature2Hint3;
    @BindView(R.id.signature2_3)
    TextView mSignature23;
    @BindView(R.id.footer_2_3)
    LinearLayout mFooter23;
    @BindView(R.id.line1_3)
    TextView mLine13;
    @BindView(R.id.line2_3)
    TextView mLine23;
    @BindView(R.id.myView_4)
    RelativeLayout mMyView4;

    @BindView(R.id.billNumberHint_4)
    TextView mBillNumberHint4;
    @BindView(R.id.billNumber_4)
    TextView mBillNumber4;
    @BindView(R.id.verticalLine_4)
    View mVerticalLine4;
    @BindView(R.id.verticalTextView_4)
    com.alahram.alahramapp.pdfCreate.VerticalTextView mVerticalTextView4;
    @BindView(R.id.tableHeader_4)
    LinearLayout mTableHeader4;
    @BindView(R.id.tableItem_4)
    LinearLayout mTableItem4;
    @BindView(R.id.tableFooter_4)
    LinearLayout mTableFooter4;
    @BindView(R.id.totalHint_4)
    TextView mTotalHint4;
    @BindView(R.id.total_4)
    TextView mTotal4;
    @BindView(R.id.rules_4)
    LinearLayout mRules4;
    @BindView(R.id.TrodeNumberHint_4)
    TextView mTrodeNumberHint4;
    @BindView(R.id.TrodeNumber_4)
    TextView mTrodeNumber4;
    @BindView(R.id.ruleLine0_4)
    TextView mRuleLine04;
    @BindView(R.id.ruleLine1_4)
    TextView mRuleLine14;
    @BindView(R.id.ruleLine2_4)
    TextView mRuleLine24;
    @BindView(R.id.ruleLine3_4)
    TextView mRuleLine34;
    @BindView(R.id.ruleLine4_4)
    TextView mRuleLine44;
    @BindView(R.id.ruleLine5_4)
    TextView mRuleLine54;
    @BindView(R.id.ruleLine6_4)
    TextView mRuleLine64;
    @BindView(R.id.ruleLine7_4)
    TextView mRuleLine74;
    @BindView(R.id.ruleLine8_4)
    TextView mRuleLine84;
    @BindView(R.id.verticalLine2_4)
    View mVerticalLine24;
    @BindView(R.id.signatureDetails_4)
    LinearLayout mSignatureDetails4;
    @BindView(R.id.receiverInOfficeHint_4)
    TextView mReceiverInOfficeHint4;
    @BindView(R.id.receiverInOffice_4)
    TextView mReceiverInOffice4;
    @BindView(R.id.receiverIdHint_4)
    TextView mReceiverIdHint4;
    @BindView(R.id.receiverId_4)
    TextView mReceiverId4;
    @BindView(R.id.signature1Hint_4)
    TextView mSignature1Hint4;
    @BindView(R.id.signature1_4)
    TextView mSignature14;
    @BindView(R.id.signature2Hint_4)
    TextView mSignature2Hint4;
    @BindView(R.id.signature2_4)
    TextView mSignature24;
    @BindView(R.id.footer_2_4)
    LinearLayout mFooter24;
    @BindView(R.id.line1_4)
    TextView mLine14;
    @BindView(R.id.line2_4)
    TextView mLine24;
    @BindView(R.id.myView_5)
    RelativeLayout mMyView5;

    @BindView(R.id.billNumberHint_5)
    TextView mBillNumberHint5;
    @BindView(R.id.billNumber_5)
    TextView mBillNumber5;
    @BindView(R.id.verticalLine_5)
    View mVerticalLine5;
    @BindView(R.id.verticalTextView_5)
    com.alahram.alahramapp.pdfCreate.VerticalTextView mVerticalTextView5;
    @BindView(R.id.tableHeader_5)
    LinearLayout mTableHeader5;
    @BindView(R.id.tableItem_5)
    LinearLayout mTableItem5;
    @BindView(R.id.tableFooter_5)
    LinearLayout mTableFooter5;
    @BindView(R.id.totalHint_5)
    TextView mTotalHint5;
    @BindView(R.id.total_5)
    TextView mTotal5;
    @BindView(R.id.rules_5)
    LinearLayout mRules5;
    @BindView(R.id.TrodeNumberHint_5)
    TextView mTrodeNumberHint5;
    @BindView(R.id.TrodeNumber_5)
    TextView mTrodeNumber5;
    @BindView(R.id.ruleLine0_5)
    TextView mRuleLine05;
    @BindView(R.id.ruleLine1_5)
    TextView mRuleLine15;
    @BindView(R.id.ruleLine2_5)
    TextView mRuleLine25;
    @BindView(R.id.ruleLine3_5)
    TextView mRuleLine35;
    @BindView(R.id.ruleLine4_5)
    TextView mRuleLine45;
    @BindView(R.id.ruleLine5_5)
    TextView mRuleLine55;
    @BindView(R.id.ruleLine6_5)
    TextView mRuleLine65;
    @BindView(R.id.ruleLine7_5)
    TextView mRuleLine75;
    @BindView(R.id.ruleLine8_5)
    TextView mRuleLine85;
    @BindView(R.id.verticalLine2_5)
    View mVerticalLine25;
    @BindView(R.id.signatureDetails_5)
    LinearLayout mSignatureDetails5;
    @BindView(R.id.receiverInOfficeHint_5)
    TextView mReceiverInOfficeHint5;
    @BindView(R.id.receiverInOffice_5)
    TextView mReceiverInOffice5;
    @BindView(R.id.receiverIdHint_5)
    TextView mReceiverIdHint5;
    @BindView(R.id.receiverId_5)
    TextView mReceiverId5;
    @BindView(R.id.signature1Hint_5)
    TextView mSignature1Hint5;
    @BindView(R.id.signature1_5)
    TextView mSignature15;
    @BindView(R.id.signature2Hint_5)
    TextView mSignature2Hint5;
    @BindView(R.id.signature2_5)
    TextView mSignature25;
    @BindView(R.id.footer_2_5)
    LinearLayout mFooter25;
    @BindView(R.id.line1_5)
    TextView mLine15;
    @BindView(R.id.line2_5)
    TextView mLine25;
    @BindView(R.id.myView_6)
    RelativeLayout mMyView6;

    @BindView(R.id.billNumberHint_6)
    TextView mBillNumberHint6;
    @BindView(R.id.billNumber_6)
    TextView mBillNumber6;
    @BindView(R.id.verticalLine_6)
    View mVerticalLine6;
    @BindView(R.id.verticalTextView_6)
    com.alahram.alahramapp.pdfCreate.VerticalTextView mVerticalTextView6;
    @BindView(R.id.tableHeader_6)
    LinearLayout mTableHeader6;
    @BindView(R.id.tableItem_6)
    LinearLayout mTableItem6;
    @BindView(R.id.tableFooter_6)
    LinearLayout mTableFooter6;
    @BindView(R.id.totalHint_6)
    TextView mTotalHint6;
    @BindView(R.id.total_6)
    TextView mTotal6;
    @BindView(R.id.rules_6)
    LinearLayout mRules6;
    @BindView(R.id.TrodeNumberHint_6)
    TextView mTrodeNumberHint6;
    @BindView(R.id.TrodeNumber_6)
    TextView mTrodeNumber6;
    @BindView(R.id.ruleLine0_6)
    TextView mRuleLine06;
    @BindView(R.id.ruleLine1_6)
    TextView mRuleLine16;
    @BindView(R.id.ruleLine2_6)
    TextView mRuleLine26;
    @BindView(R.id.ruleLine3_6)
    TextView mRuleLine36;
    @BindView(R.id.ruleLine4_6)
    TextView mRuleLine46;
    @BindView(R.id.ruleLine5_6)
    TextView mRuleLine56;
    @BindView(R.id.ruleLine6_6)
    TextView mRuleLine66;
    @BindView(R.id.ruleLine7_6)
    TextView mRuleLine76;
    @BindView(R.id.ruleLine8_6)
    TextView mRuleLine86;
    @BindView(R.id.verticalLine2_6)
    View mVerticalLine26;
    @BindView(R.id.signatureDetails_6)
    LinearLayout mSignatureDetails6;
    @BindView(R.id.receiverInOfficeHint_6)
    TextView mReceiverInOfficeHint6;
    @BindView(R.id.receiverInOffice_6)
    TextView mReceiverInOffice6;
    @BindView(R.id.receiverIdHint_6)
    TextView mReceiverIdHint6;
    @BindView(R.id.receiverId_6)
    TextView mReceiverId6;
    @BindView(R.id.signature1Hint_6)
    TextView mSignature1Hint6;
    @BindView(R.id.signature1_6)
    TextView mSignature16;
    @BindView(R.id.signature2Hint_6)
    TextView mSignature2Hint6;
    @BindView(R.id.signature2_6)
    TextView mSignature26;
    @BindView(R.id.footer_2_6)
    LinearLayout mFooter26;
    @BindView(R.id.line1_6)
    TextView mLine16;
    @BindView(R.id.line2_6)
    TextView mLine26;
    @BindView(R.id.myView_7)
    RelativeLayout mMyView7;

    @BindView(R.id.billNumberHint_7)
    TextView mBillNumberHint7;
    @BindView(R.id.billNumber_7)
    TextView mBillNumber7;
    @BindView(R.id.verticalLine_7)
    View mVerticalLine7;
    @BindView(R.id.verticalTextView_7)
    com.alahram.alahramapp.pdfCreate.VerticalTextView mVerticalTextView7;
    @BindView(R.id.tableHeader_7)
    LinearLayout mTableHeader7;
    @BindView(R.id.tableItem_7)
    LinearLayout mTableItem7;
    @BindView(R.id.tableFooter_7)
    LinearLayout mTableFooter7;
    @BindView(R.id.totalHint_7)
    TextView mTotalHint7;
    @BindView(R.id.total_7)
    TextView mTotal7;
    @BindView(R.id.rules_7)
    LinearLayout mRules7;
    @BindView(R.id.TrodeNumberHint_7)
    TextView mTrodeNumberHint7;
    @BindView(R.id.TrodeNumber_7)
    TextView mTrodeNumber7;
    @BindView(R.id.ruleLine0_7)
    TextView mRuleLine07;
    @BindView(R.id.ruleLine1_7)
    TextView mRuleLine17;
    @BindView(R.id.ruleLine2_7)
    TextView mRuleLine27;
    @BindView(R.id.ruleLine3_7)
    TextView mRuleLine37;
    @BindView(R.id.ruleLine4_7)
    TextView mRuleLine47;
    @BindView(R.id.ruleLine5_7)
    TextView mRuleLine57;
    @BindView(R.id.ruleLine6_7)
    TextView mRuleLine67;
    @BindView(R.id.ruleLine7_7)
    TextView mRuleLine77;
    @BindView(R.id.ruleLine8_7)
    TextView mRuleLine87;
    @BindView(R.id.verticalLine2_7)
    View mVerticalLine27;
    @BindView(R.id.signatureDetails_7)
    LinearLayout mSignatureDetails7;
    @BindView(R.id.receiverInOfficeHint_7)
    TextView mReceiverInOfficeHint7;
    @BindView(R.id.receiverInOffice_7)
    TextView mReceiverInOffice7;
    @BindView(R.id.receiverIdHint_7)
    TextView mReceiverIdHint7;
    @BindView(R.id.receiverId_7)
    TextView mReceiverId7;
    @BindView(R.id.signature1Hint_7)
    TextView mSignature1Hint7;
    @BindView(R.id.signature1_7)
    TextView mSignature17;
    @BindView(R.id.signature2Hint_7)
    TextView mSignature2Hint7;
    @BindView(R.id.signature2_7)
    TextView mSignature27;
    @BindView(R.id.footer_2_7)
    LinearLayout mFooter27;
    @BindView(R.id.line1_7)
    TextView mLine17;
    @BindView(R.id.line2_7)
    TextView mLine27;
    @BindView(R.id.myView_8)
    RelativeLayout mMyView8;

    @BindView(R.id.billNumberHint_8)
    TextView mBillNumberHint8;
    @BindView(R.id.billNumber_8)
    TextView mBillNumber8;
    @BindView(R.id.verticalLine_8)
    View mVerticalLine8;
    @BindView(R.id.verticalTextView_8)
    com.alahram.alahramapp.pdfCreate.VerticalTextView mVerticalTextView8;
    @BindView(R.id.tableHeader_8)
    LinearLayout mTableHeader8;
    @BindView(R.id.tableItem_8)
    LinearLayout mTableItem8;
    @BindView(R.id.tableFooter_8)
    LinearLayout mTableFooter8;
    @BindView(R.id.totalHint_8)
    TextView mTotalHint8;
    @BindView(R.id.total_8)
    TextView mTotal8;
    @BindView(R.id.rules_8)
    LinearLayout mRules8;
    @BindView(R.id.TrodeNumberHint_8)
    TextView mTrodeNumberHint8;
    @BindView(R.id.TrodeNumber_8)
    TextView mTrodeNumber8;
    @BindView(R.id.ruleLine0_8)
    TextView mRuleLine08;
    @BindView(R.id.ruleLine1_8)
    TextView mRuleLine18;
    @BindView(R.id.ruleLine2_8)
    TextView mRuleLine28;
    @BindView(R.id.ruleLine3_8)
    TextView mRuleLine38;
    @BindView(R.id.ruleLine4_8)
    TextView mRuleLine48;
    @BindView(R.id.ruleLine5_8)
    TextView mRuleLine58;
    @BindView(R.id.ruleLine6_8)
    TextView mRuleLine68;
    @BindView(R.id.ruleLine7_8)
    TextView mRuleLine78;
    @BindView(R.id.ruleLine8_8)
    TextView mRuleLine88;
    @BindView(R.id.verticalLine2_8)
    View mVerticalLine28;
    @BindView(R.id.signatureDetails_8)
    LinearLayout mSignatureDetails8;
    @BindView(R.id.receiverInOfficeHint_8)
    TextView mReceiverInOfficeHint8;
    @BindView(R.id.receiverInOffice_8)
    TextView mReceiverInOffice8;
    @BindView(R.id.receiverIdHint_8)
    TextView mReceiverIdHint8;
    @BindView(R.id.receiverId_8)
    TextView mReceiverId8;
    @BindView(R.id.signature1Hint_8)
    TextView mSignature1Hint8;
    @BindView(R.id.signature1_8)
    TextView mSignature18;
    @BindView(R.id.signature2Hint_8)
    TextView mSignature2Hint8;
    @BindView(R.id.signature2_8)
    TextView mSignature28;
    @BindView(R.id.footer_2_8)
    LinearLayout mFooter28;
    @BindView(R.id.line1_8)
    TextView mLine18;
    @BindView(R.id.line2_8)
    TextView mLine28;
    @BindView(R.id.myView_9)
    RelativeLayout mMyView9;

    @BindView(R.id.billNumberHint_9)
    TextView mBillNumberHint9;
    @BindView(R.id.billNumber_9)
    TextView mBillNumber9;
    @BindView(R.id.verticalLine_9)
    View mVerticalLine9;
    @BindView(R.id.verticalTextView_9)
    com.alahram.alahramapp.pdfCreate.VerticalTextView mVerticalTextView9;
    @BindView(R.id.tableHeader_9)
    LinearLayout mTableHeader9;
    @BindView(R.id.tableItem_9)
    LinearLayout mTableItem9;
    @BindView(R.id.tableFooter_9)
    LinearLayout mTableFooter9;
    @BindView(R.id.totalHint_9)
    TextView mTotalHint9;
    @BindView(R.id.total_9)
    TextView mTotal9;
    @BindView(R.id.rules_9)
    LinearLayout mRules9;
    @BindView(R.id.TrodeNumberHint_9)
    TextView mTrodeNumberHint9;
    @BindView(R.id.TrodeNumber_9)
    TextView mTrodeNumber9;
    @BindView(R.id.ruleLine0_9)
    TextView mRuleLine09;
    @BindView(R.id.ruleLine1_9)
    TextView mRuleLine19;
    @BindView(R.id.ruleLine2_9)
    TextView mRuleLine29;
    @BindView(R.id.ruleLine3_9)
    TextView mRuleLine39;
    @BindView(R.id.ruleLine4_9)
    TextView mRuleLine49;
    @BindView(R.id.ruleLine5_9)
    TextView mRuleLine59;
    @BindView(R.id.ruleLine6_9)
    TextView mRuleLine69;
    @BindView(R.id.ruleLine7_9)
    TextView mRuleLine79;
    @BindView(R.id.ruleLine8_9)
    TextView mRuleLine89;
    @BindView(R.id.verticalLine2_9)
    View mVerticalLine29;
    @BindView(R.id.signatureDetails_9)
    LinearLayout mSignatureDetails9;
    @BindView(R.id.receiverInOfficeHint_9)
    TextView mReceiverInOfficeHint9;
    @BindView(R.id.receiverInOffice_9)
    TextView mReceiverInOffice9;
    @BindView(R.id.receiverIdHint_9)
    TextView mReceiverIdHint9;
    @BindView(R.id.receiverId_9)
    TextView mReceiverId9;
    @BindView(R.id.signature1Hint_9)
    TextView mSignature1Hint9;
    @BindView(R.id.signature1_9)
    TextView mSignature19;
    @BindView(R.id.signature2Hint_9)
    TextView mSignature2Hint9;
    @BindView(R.id.signature2_9)
    TextView mSignature29;
    @BindView(R.id.footer_2_9)
    LinearLayout mFooter29;
    @BindView(R.id.line1_9)
    TextView mLine19;
    @BindView(R.id.line2_9)
    TextView mLine29;
    /** ButterKnife Code **/

    /** ButterKnife Code **/
    @BindView(R.id.TrodeNumberHint)
    TextView mTrodeNumberHint;
    @BindView(R.id.TrodeNumber)
    TextView mTrodeNumber;
    /** ButterKnife Code **/



    Typeface tf;
    private ArrayList<AddRequestModel> data = new ArrayList<>();
    private int totalPrice=0;
    String currencyUnit=" ريال";
    void setFontToTextView()
    {
        tf = Typeface.createFromAsset(getResources().getAssets(), "GE_Thameen_Book.otf");

       /* mBillNumber.setTypeface(tf);
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


        mLine12.setTypeface(tf);
        mLine13.setTypeface(tf);
        mLine14.setTypeface(tf);
        mLine15.setTypeface(tf);
        mLine16.setTypeface(tf);
        mLine17.setTypeface(tf);
        mLine18.setTypeface(tf);
        mLine19.setTypeface(tf);

        mLine22.setTypeface(tf);
        mLine23.setTypeface(tf);
        mLine24.setTypeface(tf);
        mLine25.setTypeface(tf);
        mLine26.setTypeface(tf);
        mLine27.setTypeface(tf);
        mLine28.setTypeface(tf);
        mLine29.setTypeface(tf);

        mBillNumber2.setTypeface(tf);
        mBillNumber3.setTypeface(tf);
        mBillNumber4.setTypeface(tf);
        mBillNumber5.setTypeface(tf);
        mBillNumber6.setTypeface(tf);
        mBillNumber7.setTypeface(tf);
        mBillNumber8.setTypeface(tf);
        mBillNumber9.setTypeface(tf);



        mRuleLine0.setTypeface(tf);
        mRuleLine1.setTypeface(tf);
        mRuleLine2.setTypeface(tf);
        mRuleLine3.setTypeface(tf);
        mRuleLine4.setTypeface(tf);
        mRuleLine5.setTypeface(tf);
        mRuleLine6.setTypeface(tf);
        mRuleLine7.setTypeface(tf);
        mRuleLine8.setTypeface(tf);


        mRuleLine02.setTypeface(tf);
        mRuleLine12.setTypeface(tf);
        mRuleLine22.setTypeface(tf);
        mRuleLine32.setTypeface(tf);
        mRuleLine42.setTypeface(tf);
        mRuleLine52.setTypeface(tf);
        mRuleLine62.setTypeface(tf);
        mRuleLine72.setTypeface(tf);
        mRuleLine82.setTypeface(tf);

        mRuleLine03.setTypeface(tf);
        mRuleLine13.setTypeface(tf);
        mRuleLine23.setTypeface(tf);
        mRuleLine33.setTypeface(tf);
        mRuleLine43.setTypeface(tf);
        mRuleLine53.setTypeface(tf);
        mRuleLine63.setTypeface(tf);
        mRuleLine73.setTypeface(tf);
        mRuleLine83.setTypeface(tf);

        mRuleLine04.setTypeface(tf);
        mRuleLine14.setTypeface(tf);
        mRuleLine24.setTypeface(tf);
        mRuleLine34.setTypeface(tf);
        mRuleLine44.setTypeface(tf);
        mRuleLine54.setTypeface(tf);
        mRuleLine64.setTypeface(tf);
        mRuleLine74.setTypeface(tf);
        mRuleLine84.setTypeface(tf);

        mRuleLine05.setTypeface(tf);
        mRuleLine15.setTypeface(tf);
        mRuleLine25.setTypeface(tf);
        mRuleLine35.setTypeface(tf);
        mRuleLine45.setTypeface(tf);
        mRuleLine55.setTypeface(tf);
        mRuleLine65.setTypeface(tf);
        mRuleLine75.setTypeface(tf);
        mRuleLine85.setTypeface(tf);

        mRuleLine06.setTypeface(tf);
        mRuleLine16.setTypeface(tf);
        mRuleLine26.setTypeface(tf);
        mRuleLine36.setTypeface(tf);
        mRuleLine46.setTypeface(tf);
        mRuleLine56.setTypeface(tf);
        mRuleLine66.setTypeface(tf);
        mRuleLine76.setTypeface(tf);
        mRuleLine86.setTypeface(tf);

        mRuleLine07.setTypeface(tf);
        mRuleLine17.setTypeface(tf);
        mRuleLine27.setTypeface(tf);
        mRuleLine37.setTypeface(tf);
        mRuleLine47.setTypeface(tf);
        mRuleLine57.setTypeface(tf);
        mRuleLine67.setTypeface(tf);
        mRuleLine77.setTypeface(tf);
        mRuleLine87.setTypeface(tf);

        mRuleLine08.setTypeface(tf);
        mRuleLine18.setTypeface(tf);
        mRuleLine28.setTypeface(tf);
        mRuleLine38.setTypeface(tf);
        mRuleLine48.setTypeface(tf);
        mRuleLine58.setTypeface(tf);
        mRuleLine68.setTypeface(tf);
        mRuleLine78.setTypeface(tf);
        mRuleLine88.setTypeface(tf);


        mRuleLine09.setTypeface(tf);
        mRuleLine19.setTypeface(tf);
        mRuleLine29.setTypeface(tf);
        mRuleLine39.setTypeface(tf);
        mRuleLine49.setTypeface(tf);
        mRuleLine59.setTypeface(tf);
        mRuleLine69.setTypeface(tf);
        mRuleLine79.setTypeface(tf);
        mRuleLine89.setTypeface(tf);




        mReceiverInOfficeHint.setTypeface(tf);
        mReceiverInOfficeHint2.setTypeface(tf);
        mReceiverInOfficeHint3.setTypeface(tf);
        mReceiverInOfficeHint4.setTypeface(tf);
        mReceiverInOfficeHint5.setTypeface(tf);
        mReceiverInOfficeHint6.setTypeface(tf);
        mReceiverInOfficeHint7.setTypeface(tf);
        mReceiverInOfficeHint8.setTypeface(tf);
        mReceiverInOfficeHint9.setTypeface(tf);
        mReceiverInOffice.setTypeface(tf);
        mReceiverInOffice2.setTypeface(tf);
        mReceiverInOffice3.setTypeface(tf);
        mReceiverInOffice4.setTypeface(tf);
        mReceiverInOffice5.setTypeface(tf);
        mReceiverInOffice6.setTypeface(tf);
        mReceiverInOffice7.setTypeface(tf);
        mReceiverInOffice8.setTypeface(tf);
        mReceiverInOffice9.setTypeface(tf);
        mReceiverIdHint.setTypeface(tf);
        mReceiverIdHint2.setTypeface(tf);
        mReceiverIdHint3.setTypeface(tf);
        mReceiverIdHint4.setTypeface(tf);
        mReceiverIdHint5.setTypeface(tf);
        mReceiverIdHint6.setTypeface(tf);
        mReceiverIdHint7.setTypeface(tf);
        mReceiverIdHint8.setTypeface(tf);
        mReceiverIdHint9.setTypeface(tf);
        tvclientnotes.setTypeface(tf);

        clientnotes2.setTypeface(tf);
        mReceiverId.setTypeface(tf);
        mReceiverId2.setTypeface(tf);
        mReceiverId3.setTypeface(tf);
        mReceiverId4.setTypeface(tf);
        mReceiverId5.setTypeface(tf);
        mReceiverId6.setTypeface(tf);
        mReceiverId7.setTypeface(tf);
        mReceiverId8.setTypeface(tf);
        mReceiverId9.setTypeface(tf);
        mSignature1Hint.setTypeface(tf);
        mSignature1Hint2.setTypeface(tf);
        mSignature1Hint3.setTypeface(tf);
        mSignature1Hint4.setTypeface(tf);
        mSignature1Hint5.setTypeface(tf);
        mSignature1Hint6.setTypeface(tf);
        mSignature1Hint7.setTypeface(tf);
        mSignature1Hint8.setTypeface(tf);
        mSignature1Hint9.setTypeface(tf);
        mSignature1.setTypeface(tf);
        mSignature12.setTypeface(tf);
        mSignature13.setTypeface(tf);
        mSignature14.setTypeface(tf);
        mSignature15.setTypeface(tf);
        mSignature16.setTypeface(tf);
        mSignature17.setTypeface(tf);
        mSignature18.setTypeface(tf);
        mSignature19.setTypeface(tf);
        mSignature2Hint.setTypeface(tf);
        mSignature2Hint2.setTypeface(tf);
        mSignature2Hint3.setTypeface(tf);
        mSignature2Hint4.setTypeface(tf);
        mSignature2Hint5.setTypeface(tf);
        mSignature2Hint6.setTypeface(tf);
        mSignature2Hint7.setTypeface(tf);
        mSignature2Hint8.setTypeface(tf);
        mSignature2Hint9.setTypeface(tf);
        mSignature2.setTypeface(tf);
        mSignature22.setTypeface(tf);
        mSignature23.setTypeface(tf);
        mSignature24.setTypeface(tf);
        mSignature25.setTypeface(tf);
        mSignature26.setTypeface(tf);
        mSignature27.setTypeface(tf);
        mSignature28.setTypeface(tf);
        mSignature29.setTypeface(tf);
        mLine1.setTypeface(tf);
        mLine2.setTypeface(tf);
        mVerticalTextView.setTypeface(tf);
        mVerticalTextView2.setTypeface(tf);
        mVerticalTextView3.setTypeface(tf);
        mVerticalTextView4.setTypeface(tf);
        mVerticalTextView5.setTypeface(tf);
        mVerticalTextView6.setTypeface(tf);
        mVerticalTextView7.setTypeface(tf);
        mVerticalTextView8.setTypeface(tf);
        mVerticalTextView9.setTypeface(tf);

        page2_dateHint_sender.setTypeface(tf);
        page2_date_senderTV.setTypeface(tf);
        page2_dateHint_nationalid.setTypeface(tf);
        page2_date_NationalIdTV.setTypeface(tf);



        clientNationalIdHint.setTypeface(tf);
        clientNationalId.setTypeface(tf);*/

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

        setContentView(R.layout.activity_print_pdf);

        forceRTLIfSupported();
        RelativeLayout pageView = (RelativeLayout) findViewById(R.id.pageView);
        pageView.bringToFront();

        btnRecieve =(Button)findViewById(R.id.btnReciever);

        requestQueue = RequestQueueSingleton.getInstance(CreatePdfActivity.this)
                .getRequestQueue();

//        ButterKnife.bind(this);
        mpreference=new Preferences(this);
//        mPageView.bringToFront();
//        setFontToTextView();
//        mMyRootView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
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
      //  لفقاقفاق
      //  billNumber=String.valueOf(getRandomBillNumber())+" كود سري: "+mpreference.getSecretNum();
        int totalCount =  0;
        for(int index = 0 ; index <data.size(); index++)
        {
            totalCount += (data.get(index).getTrode()*data.get(index).getCounter());
        }
        trodeNumber = String.valueOf(totalCount) + " طرد " ;

//       bindDataToStickersView("");
//        bindDataToBill1();
//        bindDataToBill2();

    }
    String trodeNumber;
   // String billNumber;
    private void  bindDataToBill1()
    {

        String mobileNumber=mpreference.getClientPhoneKsa();
        String clientNotes = mpreference.getNotes();
        tvclientnotes.setText(clientNotes);

        String clientNationalIdStr = mpreference.getClientNationalId();
        clientNationalId.setText(clientNationalIdStr);

        clientNationalIdHint.setText("محافظة:");
        String city=mpreference.getReciverAddressDetial12();
        clientNationalId.setText(city);


        if(mpreference.getClientPhoneEgy().equals(null) || mpreference.getClientPhoneEgy().equals(""))
        {}
        else
            mobileNumber += "/"+mpreference.getClientPhoneEgy();

        String itemNumber=String.valueOf(data.size());
        String date=getCurrantDate();

        String receiverName=mpreference.getRecivername();
        String senderName=mpreference.getclientname();
        String receiverAddress=mpreference.getReciverAddressDetial();
        String receiverPhones= mpreference.getReciverPhoneEgy();
        if(mpreference.getReciverPhoneKsa().equals(null) || mpreference.getReciverPhoneKsa().equals(""))
        {}
        else
            receiverPhones += " / "+mpreference.getReciverPhoneKsa();

        String total=String.valueOf(totalPrice);
        String receiverInOffice="hen recier in office name";
        String receiverId=mpreference.getReciverNationalId();
        String signature1="";
        String signature2="";




        mTrodeNumber.setText(trodeNumber);
        mTrodeNumber2.setText(trodeNumber);
        mTrodeNumber3.setText(trodeNumber);
        mTrodeNumber4.setText(trodeNumber);
        mTrodeNumber5.setText(trodeNumber);
        mTrodeNumber6.setText(trodeNumber);
        mTrodeNumber7.setText(trodeNumber);
        mTrodeNumber8.setText(trodeNumber);
        mTrodeNumber9.setText(trodeNumber);



        mMobileNumber.setText(mobileNumber);
        mNumber.setText(itemNumber);
        mDate.setText(date);
        mReceiverName.setText(receiverName);
        mSenderName.setText(senderName);
        mReceiverAddress.setText(receiverAddress);
        mReceiverPhones.setText(receiverPhones);

        mTotal.setText(total +currencyUnit);
        mTotal2.setText(total +currencyUnit);
        mTotal3.setText(total +currencyUnit);
        mTotal4.setText(total +currencyUnit);
        mTotal5.setText(total +currencyUnit);
        mTotal6.setText(total +currencyUnit);
        mTotal7.setText(total +currencyUnit);
        mTotal8.setText(total +currencyUnit);
        mTotal9.setText(total +currencyUnit);

        mReceiverInOffice.setText(receiverInOffice);
        mReceiverInOffice2.setText(receiverInOffice);
        mReceiverInOffice3.setText(receiverInOffice);
        mReceiverInOffice4.setText(receiverInOffice);
        mReceiverInOffice5.setText(receiverInOffice);
        mReceiverInOffice6.setText(receiverInOffice);
        mReceiverInOffice7.setText(receiverInOffice);
        mReceiverInOffice8.setText(receiverInOffice);
        mReceiverInOffice9.setText(receiverInOffice);

        mReceiverId.setText(receiverId);
        mReceiverId2.setText(receiverId);
        mReceiverId3.setText(receiverId);
        mReceiverId4.setText(receiverId);
        mReceiverId5.setText(receiverId);
        mReceiverId6.setText(receiverId);
        mReceiverId7.setText(receiverId);
        mReceiverId8.setText(receiverId);
        mReceiverId9.setText(receiverId);
        mSignature1.setText(signature1);
        mSignature12.setText(signature1);
        mSignature13.setText(signature1);
        mSignature14.setText(signature1);
        mSignature15.setText(signature1);
        mSignature16.setText(signature1);
        mSignature17.setText(signature1);
        mSignature18.setText(signature1);
        mSignature19.setText(signature1);
        mSignature2.setText(signature2);
        mSignature22.setText(signature2);
        mSignature23.setText(signature2);
        mSignature24.setText(signature2);
        mSignature25.setText(signature2);
        mSignature26.setText(signature2);
        mSignature27.setText(signature2);
        mSignature28.setText(signature2);
        mSignature29.setText(signature2);

        LayoutInflater layoutInflator = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTableItem.removeAllViews();
        mTableItem2.removeAllViews();
        mTableItem3.removeAllViews();
        mTableItem4.removeAllViews();
        mTableItem5.removeAllViews();
        mTableItem6.removeAllViews();
        mTableItem7.removeAllViews();
        mTableItem8.removeAllViews();
        mTableItem9.removeAllViews();

        for (int i=0;i<data.size();i++)
        {

            TextView bayanTextView,quantityTextView,priceTextView,totalTextView , trodeTextView;
            View child = layoutInflator.inflate(R.layout.table_item, null);
            bayanTextView=child.findViewById(R.id.bayan);
            quantityTextView=child.findViewById(R.id.quantity);
            priceTextView=child.findViewById(R.id.price);
            totalTextView=child.findViewById(R.id.total);
            trodeTextView=child.findViewById(R.id.trode);

            if(!data.get(i).getOrdername().equals(""))
                bayanTextView.setText(data.get(i).getOrdername());
            if(data.get(i).getCounter() != 0)
                quantityTextView.setText(String.valueOf(data.get(i).getCounter()));
            if(!data.get(i).getOrderprice().equals(""))
            priceTextView.setText(data.get(i).getOrderprice() +currencyUnit);
            if(!data.get(i).getOrderprice().equals(""))
            totalTextView.setText(String.valueOf((data.get(i).getCounter() * (Integer.valueOf(data.get(i).getOrderprice()))))+ currencyUnit );

            trodeTextView.setText(String.valueOf(data.get(i).getTrode()));

            child.setTag(i);
            child.setBackgroundColor(Color.WHITE);
            if (i>=0 && i<=9) {
                mTableItem.addView(child);
            }
            else if (i>=10 && i<=19) {
                mTrodeNumber.setVisibility(View.GONE);
                mTrodeNumberHint.setVisibility(View.GONE);
                mTableItem2.addView(child);
            }
            else if (i>=20 && i<=29) {
                mTrodeNumber2.setVisibility(View.GONE);
                mTrodeNumberHint2.setVisibility(View.GONE);
                mTableItem3.addView(child);
            }
            else if (i>=30 && i<=39) {
                mTrodeNumber3.setVisibility(View.GONE);
                mTrodeNumberHint3.setVisibility(View.GONE);
                mTableItem4.addView(child);
            }
            else if (i>=40 && i<=49) {
                mTrodeNumber4.setVisibility(View.GONE);
                mTrodeNumberHint4.setVisibility(View.GONE);
                mTableItem5.addView(child);
            }
            else if (i>=50 && i<=59) {
                mTrodeNumber5.setVisibility(View.GONE);
                mTrodeNumberHint5.setVisibility(View.GONE);
                mTableItem6.addView(child);
            }
            else if (i>=60 && i<=69) {
                mTrodeNumber6.setVisibility(View.GONE);
                mTrodeNumberHint6.setVisibility(View.GONE);
                mTableItem7.addView(child);
            }
            else if (i>=70 && i<=79) {
                mTrodeNumber7.setVisibility(View.GONE);
                mTrodeNumberHint7.setVisibility(View.GONE);
                mTableItem8.addView(child);
            }
            else if (i>=80 && i<=89) {
                mTrodeNumber8.setVisibility(View.GONE);
                mTrodeNumberHint8.setVisibility(View.GONE);
                mTableItem9.addView(child);
            }
        }


    }

    private void bindDataToBill2()
    {
        String itemCount="";
        int totalCount =  0;
        for(int index = 0 ; index <data.size(); index++)
        {
            totalCount += (data.get(index).getTrode()*data.get(index).getCounter());
        }
        itemCount = String.valueOf(totalCount);
        String date=getCurrantDate();

        String receiverName=mpreference.getRecivername();
        String city=mpreference.getReciverAddressDetial12();
        String country=mpreference.getReciverAddressDetial1();
        String mobileNumber=mpreference.getReciverPhoneEgy();
        String Notes = mpreference.getNotes();

        String clientname = mpreference.getclientname();
        page2_date_senderTV.setText(clientname);

        String clientNationalidStr =mpreference.getClientNationalId();
        page2_date_NationalIdTV.setText(clientNationalidStr);

       // page2_dateHint_nationalid.setVisibility(View.GONE);
       // page2_date_NationalIdTV.setVisibility(View.GONE);

        clientnotes2.setText(Notes);
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





        mPage2TrodeNumber2.setText(trodeNumber);
        mPage2TrodeNumber3.setText(trodeNumber);
        mPage2TrodeNumber4.setText(trodeNumber);
        mPage2TrodeNumber5.setText(trodeNumber);
        mPage2TrodeNumber6.setText(trodeNumber);
        mPage2TrodeNumber7.setText(trodeNumber);
        mPage2TrodeNumber8.setText(trodeNumber);
        mPage2TrodeNumber9.setText(trodeNumber);


        mPage2Mobile2.setText(mobileNumber);
        mPage2Receiver2.setText(receiver);
        mPage2Signature2.setText(signature);
        mPage2IdNumber2.setText(idNumber);

        mPage2Mobile3.setText(mobileNumber);
        mPage2Receiver3.setText(receiver);
        mPage2Signature3.setText(signature);
        mPage2IdNumber3.setText(idNumber);

        mPage2Mobile3.setText(mobileNumber);
        mPage2Receiver3.setText(receiver);
        mPage2Signature3.setText(signature);
        mPage2IdNumber3.setText(idNumber);


        mPage2Mobile4.setText(mobileNumber);
        mPage2Receiver4.setText(receiver);
        mPage2Signature4.setText(signature);
        mPage2IdNumber4.setText(idNumber);

        mPage2Mobile5.setText(mobileNumber);
        mPage2Receiver5.setText(receiver);
        mPage2Signature5.setText(signature);
        mPage2IdNumber5.setText(idNumber);


        mPage2Mobile6.setText(mobileNumber);
        mPage2Receiver6.setText(receiver);
        mPage2Signature6.setText(signature);
        mPage2IdNumber6.setText(idNumber);


        mPage2Mobile7.setText(mobileNumber);
        mPage2Receiver7.setText(receiver);
        mPage2Signature7.setText(signature);
        mPage2IdNumber7.setText(idNumber);


        mPage2Mobile8.setText(mobileNumber);
        mPage2Receiver8.setText(receiver);
        mPage2Signature8.setText(signature);
        mPage2IdNumber8.setText(idNumber);

        mPage2Mobile9.setText(mobileNumber);
        mPage2Receiver9.setText(receiver);
        mPage2Signature9.setText(signature);
        mPage2IdNumber9.setText(idNumber);


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
            page2_item1Number.setText(" ( "+data.get(i).getCounter()+" ) ");
          //  page2_item1Number.setText("*"+data.get(i).getCounter());
            page2_item1.setText(data.get(i).getOrdername()+"( "+data.get(i).getTrode()+" قطعه)");

            page2_item2Number.setText(" ( "+data.get(i+1).getCounter()+" ) ");
           // page2_item2Number.setText("*"+data.get(i+1).getCounter());

            page2_item2.setText(data.get(i+1).getOrdername()+"( "+data.get(i+1).getTrode()+" قطعه)");

            child.setTag(i);


//            mPage2TableView.addView(child);








            if (i>=0 && i<=19) {
                mPage2TableView.addView(child);
            }
            else if (i>=20 && i<=39) {
                mPage2TableView2.addView(child);
            }
            else if (i>=40 && i<=59) {
                mPage2TrodeNumber2.setVisibility(View.GONE);
                mPage2TrodeNumberHint2.setVisibility(View.GONE);
                mPage2TableView3.addView(child);
            }
            else if (i>=60 && i<=89) {
                mPage2TrodeNumber3.setVisibility(View.GONE);
                mPage2TrodeNumberHint3.setVisibility(View.GONE);
                mPage2TableView4.addView(child);
            }
            else if (i>=90 && i<=109) {
                mPage2TrodeNumber4.setVisibility(View.GONE);
                mPage2TrodeNumberHint4.setVisibility(View.GONE);
                mPage2TableView5.addView(child);
            }

        }

        if (data.size()%2!=0)
        {
            TextView page2_item2Number,page2_item2,page2_item1Number,page2_item1;
            View child = layoutInflator1.inflate(R.layout.table_item2, null);
            page2_item2Number=child.findViewById(R.id.page2_item2Number);
            page2_item2=child.findViewById(R.id.page2_item2);
            page2_item1Number=child.findViewById(R.id.page2_item1Number);
            page2_item1=child.findViewById(R.id.page2_item1);
            page2_item1Number.setText(" ( "+data.get(data.size()-1).getCounter() +" ) ");
            page2_item1.setText(data.get(data.size()-1).getOrdername()+"( "+data.get(data.size()-1).getTrode()+" قطعه)");

            page2_item2.setText("");
            page2_item2Number.setText("");
            child.setTag(data.size()-1);
//            mPage2TableView.addView(child);

            int i=data.size()-1;
            if (i>=0 && i<=19) {
                mPage2TableView.addView(child);
            }
            else if (i>=20 && i<=39) {
                mPage2TableView2.addView(child);
            }
            else if (i>=40 && i<=59) {
                mPage2TrodeNumber2.setVisibility(View.GONE);
                mPage2TrodeNumberHint2.setVisibility(View.GONE);
                mPage2TableView3.addView(child);
            }
            else if (i>=60 && i<=89) {
                mPage2TrodeNumber3.setVisibility(View.GONE);
                mPage2TrodeNumberHint3.setVisibility(View.GONE);
                mPage2TableView4.addView(child);
            }
            else if (i>=90 && i<=109) {
                mPage2TrodeNumber4.setVisibility(View.GONE);
                mPage2TrodeNumberHint4.setVisibility(View.GONE);
                mPage2TableView5.addView(child);
            }
        }
        mClientnotes22.setVisibility(View.GONE);
        mClientnotes23.setVisibility(View.GONE);
        mClientnotes24.setVisibility(View.GONE);
        mClientnotes25.setVisibility(View.GONE);
        mClientnotes26.setVisibility(View.GONE);
        mClientnotes27.setVisibility(View.GONE);
        mClientnotes28.setVisibility(View.GONE);
        mClientnotes29.setVisibility(View.GONE);
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


    public String getCurrantDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateFormat = formatter.format(date.getTime());
        return dateFormat;
    }
    ProgressDialog dialog;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void savePdf(final View view) {


        ButterKnife.bind(CreatePdfActivity.this);

        mPageView.bringToFront();
        setFontToTextView();
        mMyRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        bindDataToStickersView("");
        bindDataToBill1();
        bindDataToBill2();

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

                JSONArray TroodCounts =  new JSONArray();

                for(int i=0 ; i< data.size() ; i++)
                {
                    TroodCounts.put(data.get(i).getTrode());
                }

                jsonBody.put("TroodCounts",TroodCounts);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.i("respones", "succed");
                                try {
                                    RequestId = response.getInt("RequestId");
                                    mpreference.setRequestnum(RequestId);
                                    int SecretNum = response.getInt("Serial");
                                    mpreference.setSecretNum(SecretNum);

                                  //  mSecretNumber.setTypeface(tf);
                                  //  mSecretNumberHint.setTypeface(tf);

                                    mSecretNumber.setText(SecretNum+"");
                                    mBillNumber.setText(RequestId+"");
                                    mBillNumber2.setText(RequestId+"");
                                    mBillNumber3.setText(RequestId+"");
                                    mBillNumber4.setText(RequestId+"");
                                    mBillNumber5.setText(RequestId+"");
                                    mBillNumber6.setText(RequestId+"");
                                    mBillNumber7.setText(RequestId+"");
                                    mBillNumber8.setText(RequestId+"");
                                    mBillNumber9.setText(RequestId+"");


                                    String  str= "رقـم: "+RequestId;

                                    // mPage2BillNumber.setText(RequestId+"");

//                                    mPage2BillNumberHint.setText(RequestId+"");

                                    mPage2BillNumber.setText(RequestId+"");

                                    mPage2BillNumber2.setText(RequestId+"");
                                    mPage2BillNumber3.setText(RequestId+"");
                                    mPage2BillNumber4.setText(RequestId+"");
                                    mPage2BillNumber5.setText(RequestId+"");
                                    mPage2BillNumber6.setText(RequestId+"");
                                    mPage2BillNumber7.setText(RequestId+"");
                                    mPage2BillNumber8.setText(RequestId+"");
                                    mPage2BillNumber9.setText(RequestId+"");

                                    //  mPage2BillNumber.setText(RequestId+"");

                                    view.setVisibility(View.INVISIBLE);
                                    btnRecieve.setVisibility(View.VISIBLE);

                                    mPage3BillNumber1.setText(RequestId+"");
                                    mPage3BillNumber2.setText(RequestId+"");
                                    mPage3BillNumber3.setText(RequestId+"");

                                    createPdf1();
                                    createPdf2();
                                    createPdfSticker();

                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
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







        if (true) {
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
        }
        if (data.size()>=10 ) {
            Bitmap bitmap1;
            PdfDocument.PageInfo pageInfo1;
            PdfDocument.Page page1;
            Canvas canvas1;
            bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.myView_2));
            pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
            page1 = document.startPage(pageInfo1);
            canvas1 = page1.getCanvas();
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
            canvas1.drawBitmap(bitmap1, 0, 0, null);
            document.finishPage(page1);
        }
        if (data.size()>=20 ) {
            Bitmap bitmap1;
            PdfDocument.PageInfo pageInfo1;
            PdfDocument.Page page1;
            Canvas canvas1;
            bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.myView_3));
            pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
            page1 = document.startPage(pageInfo1);
            canvas1 = page1.getCanvas();
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
            canvas1.drawBitmap(bitmap1, 0, 0, null);
            document.finishPage(page1);
        }
        if (data.size()>=30 ) {
            Bitmap bitmap1;
            PdfDocument.PageInfo pageInfo1;
            PdfDocument.Page page1;
            Canvas canvas1;
            bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.myView_4));
            pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
            page1 = document.startPage(pageInfo1);
            canvas1 = page1.getCanvas();
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
            canvas1.drawBitmap(bitmap1, 0, 0, null);
            document.finishPage(page1);
        }
        if (data.size()>=40 ) {
            Bitmap bitmap1;
            PdfDocument.PageInfo pageInfo1;
            PdfDocument.Page page1;
            Canvas canvas1;
            bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.myView_5));
            pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
            page1 = document.startPage(pageInfo1);
            canvas1 = page1.getCanvas();
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
            canvas1.drawBitmap(bitmap1, 0, 0, null);
            document.finishPage(page1);
        }
        if (data.size()>=50 ) {
            Bitmap bitmap1;
            PdfDocument.PageInfo pageInfo1;
            PdfDocument.Page page1;
            Canvas canvas1;
            bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.myView_6));
            pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
            page1 = document.startPage(pageInfo1);
            canvas1 = page1.getCanvas();
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
            canvas1.drawBitmap(bitmap1, 0, 0, null);
            document.finishPage(page1);
        }
        if (data.size()>=60 ) {
            Bitmap bitmap1;
            PdfDocument.PageInfo pageInfo1;
            PdfDocument.Page page1;
            Canvas canvas1;
            bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.myView_7));
            pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
            page1 = document.startPage(pageInfo1);
            canvas1 = page1.getCanvas();
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
            canvas1.drawBitmap(bitmap1, 0, 0, null);
            document.finishPage(page1);
        }
        if (data.size()>=70 ) {
            Bitmap bitmap1;
            PdfDocument.PageInfo pageInfo1;
            PdfDocument.Page page1;
            Canvas canvas1;
            bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.myView_8));
            pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
            page1 = document.startPage(pageInfo1);
            canvas1 = page1.getCanvas();
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
            canvas1.drawBitmap(bitmap1, 0, 0, null);
            document.finishPage(page1);
        }
        if (data.size()>=80 ) {
            Bitmap bitmap1;
            PdfDocument.PageInfo pageInfo1;
            PdfDocument.Page page1;
            Canvas canvas1;
            bitmap1 = getBitmapImageOfView(this.getWindow().findViewById(R.id.myView_9));
            pageInfo1 = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
            page1 = document.startPage(pageInfo1);
            canvas1 = page1.getCanvas();
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
            canvas1.drawBitmap(bitmap1, 0, 0, null);
            document.finishPage(page1);
        }

        File root = new File(Environment.getExternalStorageDirectory(), "AlAhram");
        if (!root.exists()) {
            root.mkdirs();
        }
        MediaScannerConnection.scanFile(this, new String[] {root.toString()}, null, null);

        Preferences preferences=new Preferences(this);
        fileNameclinet=preferences.getclientname()+"_فاتورة العميل_"+String.valueOf(RequestId)+".pdf";
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
    void createPdfSticker() {
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


        int pageNumber = 2;
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).getCounter(); j++) {
              for (int troodIndex = 0; troodIndex < data.get(i).getTrode(); troodIndex++) {

                mPage3ContentNumber1.setText(data.get(i).getOrdername());
                mPage3ContentNumber2.setText(data.get(i).getOrdername());
                mPage3ContentNumber3.setText(data.get(i).getOrdername());

                mPage3BillNumber1.setText(RequestId+" / "+data.get(i).getTrode());
                mPage3BillNumber2.setText(RequestId+" / "+data.get(i).getTrode());
                mPage3BillNumber3.setText(RequestId+" / "+data.get(i).getTrode());


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
    }

        File root = new File(Environment.getExternalStorageDirectory(), "AlAhram");
        if (!root.exists()) {
            root.mkdirs();
        }
        MediaScannerConnection.scanFile(this, new String[] {root.toString()}, null, null);

        Preferences preferences=new Preferences(this);
        fileNameclinetSticker=preferences.getclientname()+"_استكر_"+String.valueOf(RequestId)+".pdf";
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




        if (true) {
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
        }
        if (data.size()>=20) {
            Bitmap bitmap;
            PdfDocument.PageInfo pageInfo;
            PdfDocument.Page page;
            Canvas canvas;

            bitmap= getBitmapImageOfView(this.getWindow().findViewById(R.id.page2_myView_2));
            pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
            page = document.startPage(pageInfo);
            canvas = page.getCanvas();
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            canvas.drawBitmap(bitmap, 0, 0 , null);
            document.finishPage(page);
        }
        if (data.size()>=40 ) {
            Bitmap bitmap;
            PdfDocument.PageInfo pageInfo;
            PdfDocument.Page page;
            Canvas canvas;

            bitmap= getBitmapImageOfView(this.getWindow().findViewById(R.id.page2_myView_3));
            pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
            page = document.startPage(pageInfo);
            canvas = page.getCanvas();
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            canvas.drawBitmap(bitmap, 0, 0 , null);
            document.finishPage(page);
        }
        if (data.size()>=60) {
            Bitmap bitmap;
            PdfDocument.PageInfo pageInfo;
            PdfDocument.Page page;
            Canvas canvas;

            bitmap= getBitmapImageOfView(this.getWindow().findViewById(R.id.page2_myView_4));
            pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
            page = document.startPage(pageInfo);
            canvas = page.getCanvas();
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            canvas.drawBitmap(bitmap, 0, 0 , null);
            document.finishPage(page);
        }
        if (data.size()>=90 ) {
            Bitmap bitmap;
            PdfDocument.PageInfo pageInfo;
            PdfDocument.Page page;
            Canvas canvas;

            bitmap= getBitmapImageOfView(this.getWindow().findViewById(R.id.page2_myView_5));
            pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
            page = document.startPage(pageInfo);
            canvas = page.getCanvas();
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            canvas.drawBitmap(bitmap, 0, 0 , null);
            document.finishPage(page);
        }


        File root = new File(Environment.getExternalStorageDirectory(), "AlAhram");
        if (!root.exists()) {
            root.mkdirs();
        }
        MediaScannerConnection.scanFile(this, new String[] {root.toString()}, null, null);

        Preferences preferences=new Preferences(this);
        String fileName=preferences.getclientname()+"_فاتورة الاستلام_"+String.valueOf(RequestId)+".pdf";
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

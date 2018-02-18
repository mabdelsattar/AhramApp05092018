package com.abdelsattar.alahramapp.Ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.abdelsattar.alahramapp.CreatePdfActivity;
import com.abdelsattar.alahramapp.Helpers.CameraHelper;
import com.abdelsattar.alahramapp.Helpers.ImageConverterBase64;
import com.abdelsattar.alahramapp.Helpers.PermissionHelper;
import com.abdelsattar.alahramapp.Helpers.PhotoWaiterHelper;
import com.abdelsattar.alahramapp.R;
import com.abdelsattar.alahramapp.Utilitis.Preferences;
import com.abdelsattar.alahramapp.model.Constant;
import com.abdelsattar.alahramapp.model.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static com.abdelsattar.alahramapp.Helpers.CameraHelper.getImageContentUri;

public class AttachActivity extends AppCompatActivity {
    Preferences mpreference;

    static final String REQ_TAG = "VACTIVITY";
    RequestQueue requestQueue;
    private ProgressDialog dialog;


    public ImageView pickimage_pill;
    String myimage_pill;
    Button btn_pill;
    private Uri picUri_pill = null;
public  static  int latestSource;
    public  ImageView pickimage_national;
    String myimage_national;
    Button btn_national;
    private Uri picUri_national = null;
    EditText etRemain;
    EditText etPaid;



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
        setContentView(R.layout.activity_attach);
        forceRTLIfSupported();
        mpreference=new Preferences(this);

        requestQueue = RequestQueueSingleton.getInstance(AttachActivity.this)
                .getRequestQueue();
        dialog = ProgressDialog.show(AttachActivity.this, "",
                "جاري التحميل...", true);
dialog.dismiss();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("انشاء طلب جديد");
        Button
                buttonSubmit = (Button)findViewById(R.id.buttonSubmit);

        etPaid = (EditText)findViewById(R.id.ETpaid);
        etRemain = (EditText)findViewById(R.id.ETRemain);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    dialog.show();
                    String url = Constant.serversite + "/api/AlAhram/AddOrUpdateRequest";

                    final JSONObject jsonBody = new JSONObject();

                    int UserId = mpreference.getUserId();
                    jsonBody.put("RequestId",mpreference.getRequestNum());
                 //   jsonBody.put("ClientSignatureImage",myimage_pill);
                   // jsonBody.put("ClientNationalIdImage",myimage_national);

                    jsonBody.put("Paid",Integer.parseInt(etPaid.getText().toString()));
                    jsonBody.put("Remaining",Integer.parseInt(etRemain.getText().toString()));





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
                                        Intent intent = new Intent(AttachActivity.this,ShowAllRequestsActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }catch (Exception ex){
                                        Toast.makeText(AttachActivity.this,"حدث خطأ تقني",Toast.LENGTH_LONG).show();


                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Toast.makeText(AttachActivity.this,"حدث خطأ تقني اثناء الاتصال بالخادم",Toast.LENGTH_LONG).show();
                            // clientname.setText("Error getting response");
                            error.printStackTrace();
                        }
                    });
                    jsonObjectRequest.setTag(REQ_TAG);
                    requestQueue.add(jsonObjectRequest);



                }catch (Exception ex){

                }

                // myimage_national
               //  myimage_pill
                /*
                Intent intent = new Intent(AttachActivity.this,ShowAllRequestsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*/

            }
        });


        btn_pill = (Button) findViewById(R.id.btnAttach);
        btn_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoWaiterHelper.CurrentPhotoWaiter = 3;
                captureImageCameraOrGallery(1);
                latestSource =1;
            }
        });

        btn_national = (Button) findViewById(R.id.btnAttachNationalId);
        btn_national.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoWaiterHelper.CurrentPhotoWaiter = 3;
                captureImageCameraOrGallery(2);
                latestSource = 2;
            }
        });
    }


    public void captureImageCameraOrGallery(int source) {
        boolean camGranted= PermissionHelper.isCameraPermissionGranted(AttachActivity.this);
        if(camGranted)
        {
            boolean storageGranted= PermissionHelper.isStoragePermissionGranted(AttachActivity.this);
            if(storageGranted)
            {
                openMediaScreens(source);
            }
        }


    }
    private void openMediaScreens(final int source)
    {
        PermissionHelper.WaitingStoragePermission = false;
        PermissionHelper.WaitingCamPermission = false;
        final CharSequence[] options = { "التقاط صورة", "اختيار من الاستوديو",
                "الغاء" };
        AlertDialog.Builder builder = new AlertDialog.Builder(
                AttachActivity.this);

        builder.setTitle("اختر");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if (options[which].equals("التقاط صورة")) {
                    try {

                        if(source == 1) {
                            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            File file = CameraHelper.getOutputMediaFile(1);
                            picUri_pill = Uri.fromFile(file); // create
                            i.putExtra(MediaStore.EXTRA_OUTPUT, picUri_pill); // set the image file

                            startActivityForResult(i, source);
                        }else{
                            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            File file = CameraHelper.getOutputMediaFile(1);
                            picUri_national = Uri.fromFile(file); // create
                            i.putExtra(MediaStore.EXTRA_OUTPUT, picUri_pill); // set the image file

                            startActivityForResult(i, source);
                        }
                        } catch (ActivityNotFoundException ex) {
                        String errorMessage = "Whoops - your device doesn't support capturing images!";

                    }

                } else if (options[which].equals("اختيار من الاستوديو")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, source * 10);
                } else if (options[which].equals("الغاء")) {
                    dialog.dismiss();

                }

            }
        });

        builder.create();

        builder.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {



        if (requestCode == 2001 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay! Do the

            PermissionHelper.WaitingCamPermission = false;
            PermissionHelper.isStoragePermissionGranted(AttachActivity.this);
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();

        }
        else if (requestCode == 2002 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            PermissionHelper.WaitingStoragePermission = false;
        }
        else {

            Toast.makeText(this, "You can not use this feature unless you grant all permissions", Toast.LENGTH_LONG).show();

        }

        if(PermissionHelper.WaitingCamPermission == false && PermissionHelper.WaitingStoragePermission == false)
        {
            openMediaScreens(latestSource);
        }



    }
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent intent) {
        super.onActivityResult(requestcode, resultcode, intent);


        if(resultcode != 100 && PhotoWaiterHelper.CurrentPhotoWaiter == PhotoWaiterHelper.PROFILE_PIC )
        {
            if(requestcode == 10 || requestcode == 1)
            {
                if(intent != null || picUri_pill != null)
                {
                    Uri imageUri = null;

                    if(intent != null)
                    {
                        imageUri = intent.getData();
                    }
                    else
                    {
                        File myFile = new File(picUri_pill.getPath());
                        imageUri = getImageContentUri(getApplicationContext(),myFile);
                    }

                    if(imageUri  != null)
                    {
                        String realPath = ImageConverterBase64.GetRealPathFromURI(getApplicationContext(),imageUri);
                        File myFile = new File(realPath);
                        File result= CameraHelper.saveBitmapToFile(myFile,realPath,true);
                        Uri resultimage= getImageContentUri(getApplicationContext(),result);
                                                HandleRoomImage(resultimage,1);
                    }

                }

                picUri_pill = null;
                }
            }

            if(requestcode == 20 || requestcode == 2)
            {
                if(intent != null || picUri_national != null)
                {
                    Uri imageUri = null;

                    if(intent != null)
                    {
                        imageUri = intent.getData();
                    }
                    else
                    {
                        File myFile = new File(picUri_national.getPath());
                        imageUri = getImageContentUri(getApplicationContext(),myFile);
                    }

                    if(imageUri  != null)
                    {
                        String realPath = ImageConverterBase64.GetRealPathFromURI(getApplicationContext(),imageUri);
                        File myFile = new File(realPath);
                        File result= CameraHelper.saveBitmapToFile(myFile,realPath,true);
                        Uri resultimage= getImageContentUri(getApplicationContext(),result);
                        HandleRoomImage(resultimage,2);
                    }

                }

                picUri_national = null;
                }
            }




    public  void HandleRoomImage(Uri selectedImage,int source)
    {
        if(source == 1) {
            pickimage_pill = (ImageView)findViewById(R.id.imgViewInvoice);
            pickimage_pill.setImageURI(selectedImage);
            String imagePath = ImageConverterBase64.GetRealPathFromURI(getApplicationContext(), selectedImage);
            String base64 = null;
            try {
                base64 = ImageConverterBase64.ConvertImageToBase64(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (base64 != null) {
                // Image = base64;
                //myimage feha base64
                myimage_pill = base64;
                Log.i("Amira", myimage_pill);

            } else {
                Toast.makeText(AttachActivity.this, "Maximum allowed size 5 MB, Also you may need to give Estabena permissions to access your files.", Toast.LENGTH_SHORT).show();

            }
        }
        if(source == 2) {
            pickimage_national = (ImageView)findViewById(R.id.imgViewInvoiceNationId);
            pickimage_national.setImageURI(selectedImage);
            String imagePath = ImageConverterBase64.GetRealPathFromURI(getApplicationContext(), selectedImage);
            String base64 = null;
            try {
                base64 = ImageConverterBase64.ConvertImageToBase64(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (base64 != null) {
                // Image = base64;
                //myimage feha base64
                myimage_national = base64;
                Log.i("Amira", myimage_national);

            } else {
                Toast.makeText(AttachActivity.this, "Maximum allowed size 5 MB, Also you may need to give Estabena permissions to access your files.", Toast.LENGTH_SHORT).show();

            }
        }


    }





}

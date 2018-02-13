package com.abdelsattar.alahramapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PrintPdf extends AppCompatActivity {
    public File pdfFile;
    String targetPdf = Environment.getExternalStorageDirectory() + "/Document" + "test";
    // String price,no;
    Preferences mpreference;
    TextView date, nooforders, phone1, recierphone, neededprice, recivername, address;
    List<String> name = new ArrayList<>();
    List<String> price = new ArrayList<>();
    List<String> no = new ArrayList<>();
    ImageView pdfView;
    int sum = 0;
    int neededmoney = 0;

    @Override/**/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.designprint);

        final String pdfFilename = "Report";
        mpreference = new Preferences(this);
        addHeaders();
        addData();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        pdfView = (ImageView) findViewById(R.id.imageView);
        date = (TextView) findViewById(R.id.tv_date);
        nooforders = (TextView) findViewById(R.id.tv_nooftarod);
        phone1 = (TextView) findViewById(R.id.tv_phone);
        Button save = (Button) findViewById(R.id.btn_Print);
        recierphone = (TextView) findViewById(R.id.tvReciverPhone);
        neededprice = (TextView) findViewById(R.id.tv_needprices);
        recivername = (TextView) findViewById(R.id.tv_recivername);
        address = (TextView) findViewById(R.id.tv_ReciverAddress);
        date.setText(formattedDate);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrintPdf.this, allrequests.class));
            }
        });
        for (String element : no) {
            try {
                int num = Integer.parseInt(element);
                sum += num;
            } catch (NumberFormatException nfe) {
                System.out.println("Element " + element + " in the array is not an integer");
            }
        }
        for (String element : price) {
            try {
                int num = Integer.parseInt(element);
                neededmoney += num;
            } catch (NumberFormatException nfe) {
                System.out.println("Element " + element + " in the array is not an integer");
            }
        }
        //  neededprice.setText(neededmoney);
        phone1.setText(mpreference.getReciverPhoneKsa());
        recivername.setText(mpreference.getRecivername());
        address.setText(mpreference.getReciveraddress());
        // nooforders.setText(sum);

    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(10, 40, 10, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        return tv;
    }

    @NonNull
    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {
        TableLayout tl = (TableLayout) findViewById(R.id.table);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "القيمه الاجماليه", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "السعر", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "الكميه", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "البيان", Color.WHITE, Typeface.BOLD, Color.BLUE));


        tl.addView(tr, getTblLayoutParams());
    }

    /**
     * This function add the data to the table
     **/
    public void addData() {
        // int numCompanies = companies.length;

        int numCompanies = RequestsActivity.data.size();
        for (sendbundledata i : RequestsActivity.data) {

            name.add(i.getOrdername());
            price.add(i.getOrderprice());
            no.add(String.valueOf(i.getCounter()));

        }
        TableLayout tl = (TableLayout) findViewById(R.id.table);
        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, name.get(i), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + numCompanies, price.get(i), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + 1, no.get(i), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + numCompanies, "", Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tl.addView(tr, getTblLayoutParams());
        }
    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);

        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }

  /*  private void openPDF() throws IOException {
        File path = new File(Environment.getExternalStorageDirectory()+ "/Document/"+ "test");
        Log.i("file name",path.toString());
        File file = new File(targetPdf);

        ParcelFileDescriptor fileDescriptor = null;
        fileDescriptor = ParcelFileDescriptor.open(
                path, ParcelFileDescriptor.MODE_READ_ONLY);

        //min. API Level 21
        PdfRenderer pdfRenderer = null;
        pdfRenderer = new PdfRenderer(fileDescriptor);

        final int pageCount = pdfRenderer.getPageCount();
        Toast.makeText(this,
                "pageCount = " + pageCount,
                Toast.LENGTH_LONG).show();

        //Display page 0
        PdfRenderer.Page rendererPage = pdfRenderer.openPage(0);
        int rendererPageWidth = rendererPage.getWidth();
        int rendererPageHeight = rendererPage.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(
                rendererPageWidth,
                rendererPageHeight,
                Bitmap.Config.ARGB_8888);
        rendererPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        pdfView.setImageBitmap(bitmap);
        rendererPage.close();

        pdfRenderer.close();
        fileDescriptor.close();
    }*/
   /* private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        }else{
            Toast.makeText(this,"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }*/

}

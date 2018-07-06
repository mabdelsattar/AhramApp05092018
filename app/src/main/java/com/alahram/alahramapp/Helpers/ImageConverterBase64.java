package com.alahram.alahramapp.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by amiraelsayed on 2/17/2018.
 */

public class ImageConverterBase64
{
    private static  String ConversionType = ".png";
    public static String GetFileName(Context context, Uri uri) {
        String result = null;
        String scheme = uri.getScheme();
        if (scheme.equals("file")) {
            result = uri.getLastPathSegment();
        }
        else if (scheme.equals("content")) {
            String[] proj = { MediaStore.Images.Media.TITLE };
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null && cursor.getCount() != 0) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
                cursor.moveToFirst();
                result = cursor.getString(columnIndex);
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        if(result != null)
        {
            result = result + ConversionType;
        }
        return result;
    }

    private static boolean CheckImagesizeLessThan1MB(String imagePath)
    {
        Log.i("Here", imagePath);

        File file = new File(imagePath);
        long length = file.length() / 1024; // Size in KB
        length = length / 1024;

        Log.i("abcS", Long.toString(length));

        if(length > 5)
        {
            return  false;
        }

        return true;
    }

    public static String GetRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String ConvertBitmapToBase64(Bitmap bm)
    {
        if(bm != null)
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            Log.i("abc4",  "ddd");
            bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            Log.i("abc5",  "ddd");
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            Log.i("abc6",  "ddd");
            //Log.i("abc4",  Integer.toString(byteArrayImage.length));
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.i("abc7",  "ddd");
            Log.i("abc8",  encodedImage);
            return encodedImage;
        }

        return  null;
    }

    public static String ConvertImageToBase64(String imagePath) throws IOException {
        if(CheckImagesizeLessThan1MB(imagePath))
        {
            //Toast.makeText(context,"Here", Toast.LENGTH_SHORT).show();

            File file = new File(imagePath);
            if(file.length() > 0)
            {
                Log.i("abc2", imagePath);
                //return encodeFileToBase64Binary(file);
            }
            BitmapFactory.Options options;
            Bitmap bm = null;


            try {
                bm = BitmapFactory.decodeFile(file.getPath());
            } catch (OutOfMemoryError e) {
                try {
                    options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    bm = BitmapFactory.decodeFile(imagePath, options);
                } catch(Exception ex) {
                    Log.i("abcError",ex.getMessage());
                }
            }

            return ConvertBitmapToBase64(bm);
        }

        return null;
    }

    public static int getOrientation(Context context, Uri photoUri) {

        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor == null || cursor.getCount() != 1) {
            return 90;  //Assuming it was taken portrait
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    /**
     * Rotates and shrinks as needed
     */
    public static Bitmap getCorrectlyOrientedImage(Context context, Uri photoUri, int maxWidth)
            throws IOException {

        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();


        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            Log.d("ImageUtil", "Will be rotated");
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        Log.d("ImageUtil", String.format("rotatedWidth=%s, rotatedHeight=%s, maxWidth=%s",
                rotatedWidth, rotatedHeight, maxWidth));
        if (rotatedWidth > maxWidth || rotatedHeight > maxWidth) {
            float widthRatio = ((float) rotatedWidth) / ((float) maxWidth);
            float heightRatio = ((float) rotatedHeight) / ((float) maxWidth);
            float maxRatio = Math.max(widthRatio, heightRatio);
            Log.d("ImageUtil", String.format("Shrinking. maxRatio=%s",
                    maxRatio));

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            Log.d("ImageUtil", String.format("No need for Shrinking. maxRatio=%s",
                    1));

            srcBitmap = BitmapFactory.decodeStream(is);
            Log.d("ImageUtil", String.format("Decoded bitmap successful"));
        }
        is.close();

        /*
         * if the orientation is not 0 (or -1, which means we don't know), we
         * have to do a rotation.
         */
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        return srcBitmap;
    }








}

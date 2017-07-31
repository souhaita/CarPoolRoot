package rode1lift.ashwin.uomtrust.mu.rod1lift.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Ashwin on 28-May-17.
 */



public class Utils {

    public static void disconnectFromFacebook() {

        try {

            if (AccessToken.getCurrentAccessToken() == null) {
                return; // already logged out
            }

            new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                    .Callback() {
                @Override
                public void onCompleted(GraphResponse graphResponse) {

                    LoginManager.getInstance().logOut();

                }
            }).executeAsync();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deletePhoto(File file) {
        file.delete();
    }



    public static byte[] convertBitmapToBlob(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 55, bos);
        return bos.toByteArray();
    }

    public static Bitmap convertBlobToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    /**
     * Reads and returns the rest of the given input stream as a byte array,
     * closing the input stream afterwards.
     */
    public static byte[] toByteArray(InputStream is) throws IOException {
        final int BUFFER_SIZE = 1024 * 4;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[BUFFER_SIZE];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        } finally {
            output.close();
        }
    }

    public static ProgressDialog progressDialogue(Context context, String message){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public static void alertError(final Context context, String message){
        vibrate(context);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.error));
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void clearSharedPref(Context context){
        SharedPreferences prefs =  context.getSharedPreferences(CONSTANT.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void vibrate(Context context){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(700);
    }


    public static void animateLayout(LinearLayout linearLayout){
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

    public static void animateLayout(FrameLayout frameLayout){
        AnimationDrawable animationDrawable = (AnimationDrawable) frameLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

    public static void animateList(LinearLayout linearLayout, Context context, int position){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.list_effect);
        //animation.setStartOffset(position* 100);
        animation.setDuration(750);
        linearLayout.startAnimation(animation);
    }

    public static void animateGrid(LinearLayout linearLayout, Context context, int position){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fab_slide_in_from_left);
        //animation.setStartOffset(position* 300);
        animation.setDuration(2000);
        linearLayout.startAnimation(animation);
    }

    public static void animateFloatingButton(FloatingActionButton floatingActionButton, Context context, int position){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fab_scale_up);
        //animation.setStartOffset(position* 100);
        animation.setDuration(1800);
        floatingActionButton.startAnimation(animation);
    }



    public static void turnGPSOn(Context context){
        Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        context.sendBroadcast(intent);
    }

    public static Bitmap setPhotoRotation(Bitmap originalBmp){
        Bitmap newBitmap = originalBmp;
        if (Build.MANUFACTURER.equalsIgnoreCase("samsung")
                || Build.MANUFACTURER.equalsIgnoreCase("LGE")
                || Build.MANUFACTURER.equalsIgnoreCase("sony")) {

            // rotate image
            Matrix matrix = new Matrix();
            matrix.postRotate(90);

            newBitmap = Bitmap.createBitmap(originalBmp, 0, 0, originalBmp.getWidth(), originalBmp.getHeight(), matrix, false);
            originalBmp.recycle();
        }

        return newBitmap;
    }

    public static Integer getCurrentAccount(Context context){
        SharedPreferences prefs = context.getSharedPreferences(CONSTANT.APP_NAME, MODE_PRIVATE);
        Integer currentAccountId = prefs.getInt(CONSTANT.CURRENT_ACCOUNT_ID, -1);
        return currentAccountId;
    }

    public static void signOut(GoogleApiClient mGoogleApiClient) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
        new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {

            }
        });
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void savePictureToFolder(byte [] imageToSave, String folderId, String fileName, boolean carPhotos) {
        File rootFolder = getRootDirectory();

        try {
            if (!rootFolder.exists()) {
                rootFolder.mkdirs();
            }

            File subFolder;

            if(carPhotos){
                File carFolder = new File(rootFolder,CONSTANT.CAR_PICTURE_PATH);
                if (!carFolder.exists()) {
                    carFolder.mkdirs();
                }

                subFolder = new File(carFolder,folderId);
            }
            else{
                File profilePictureFolder = new File(rootFolder,CONSTANT.PROFILE_PICTURE_PATH);
                if (!profilePictureFolder.exists()) {
                    profilePictureFolder.mkdirs();
                }

                subFolder = profilePictureFolder;

            }

            if (!subFolder.exists()) {
                subFolder.mkdirs();
            }

            File file = new File(subFolder,fileName+".jpg");

            FileOutputStream out = new FileOutputStream(file);
            out.write(imageToSave);
            out.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte [] getPictures(String folderId, String fileName, boolean carPhotos){
        File path = getRootDirectory();
        Bitmap mBitmap = null;

        if(path.exists()){
            try {
                File subFolder;

                if(carPhotos){
                    File folder = new File(path, CONSTANT.CAR_PICTURE_PATH);
                    subFolder = new File(folder, folderId);
                }
                else {
                 subFolder = new File(path, CONSTANT.PROFILE_PICTURE_PATH);
                }

                File photo = new File(subFolder, fileName + ".jpg");
                mBitmap = BitmapFactory.decodeFile(photo.getAbsolutePath());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return convertBitmapToBlob(mBitmap);
    }

    public static void deletePhotos(){
        File dir = getRootDirectory();
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }

    private static File getRootDirectory(){
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), CONSTANT.ROOT_DIRECTORY);
    }
}
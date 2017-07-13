package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncCreateAccount;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.CAMERA;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PERMISSION_CAMERA;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PICK_IMAGE_REQUEST;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class ActivityCompleteDriverRegistration extends Activity {

    private Dialog menuDialog;

    private Uri fileUri;

    private CarDTO carDTO;

    private RelativeLayout rlImg1, rlImg2, rlImg3, rlImg4;

    private ImageView imageViewTouched, getImageViewTouchedTop;

    private ImageView img1, img2, img3,img4;
    private ImageView imgTop1, imgTop2, imgTop3, imgTop4;

    private TextView txtCarDetails, txtYear, txtNumOfPassenger, txtPlateNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_driver_registration);

        LinearLayout llCarMake = (LinearLayout)findViewById(R.id.llCarMake);
        llCarMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCompleteDriverRegistration.this, PickerActivityCarMake.class);
                startActivityForResult(intent, CONSTANT.CAR_MAKE_ACTIVITY);
            }
        });

        LinearLayout llYear = (LinearLayout)findViewById(R.id.llYear);
        llYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCompleteDriverRegistration.this, PickerActivityCarMake.class);
                startActivityForResult(intent, CONSTANT.CAR_MAKE_ACTIVITY);
            }
        });

        LinearLayout llNumOfPassenger = (LinearLayout)findViewById(R.id.llNumOfPassenger);
        llNumOfPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCompleteDriverRegistration.this, PickerActivityCarSeats.class);
                startActivityForResult(intent, CONSTANT.CAR_MAKE_ACTIVITY);
            }
        });

        LinearLayout llPlateNum = (LinearLayout)findViewById(R.id.llPlateNum);
        llPlateNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCompleteDriverRegistration.this, PickerActivityCarPlateNum.class);
                startActivityForResult(intent, CONSTANT.CAR_MAKE_ACTIVITY);
            }
        });

        txtCarDetails = (TextView) findViewById(R.id.txtCarDetails);
        txtYear = (TextView) findViewById(R.id.txtYear);
        txtNumOfPassenger = (TextView) findViewById(R.id.txtNumOfPassenger);
        txtPlateNum = (TextView) findViewById(R.id.txtPlateNum);


        TextView txtNext = (TextView)findViewById(R.id.txtNext);
        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    AccountDTO accountDTO = new AccountDAO(ActivityCompleteDriverRegistration.this).getAccountById(-1);
                    new AsyncCreateAccount(ActivityCompleteDriverRegistration.this).execute(accountDTO);
                }
            }
        });


        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        img3 = (ImageView)findViewById(R.id.img3);
        img4 = (ImageView)findViewById(R.id.img4);

        imgTop1 = (ImageView)findViewById(R.id.imgTop1);
        imgTop2 = (ImageView)findViewById(R.id.imgTop2);
        imgTop3 = (ImageView)findViewById(R.id.imgTop3);
        imgTop4 = (ImageView)findViewById(R.id.imgTop4);


        rlImg1 = (RelativeLayout)findViewById(R.id.rlImg1);
        rlImg2 = (RelativeLayout)findViewById(R.id.rlImg2);
        rlImg3 = (RelativeLayout)findViewById(R.id.rlImg3);
        rlImg4 = (RelativeLayout)findViewById(R.id.rlImg4);



        rlImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewTouched = img1;
                getImageViewTouchedTop = imgTop1;
                showMenu();
            }
        });

        rlImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewTouched = img2;
                getImageViewTouchedTop = imgTop2;
                showMenu();
            }
        });

        rlImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewTouched = img3;
                getImageViewTouchedTop = imgTop3;
                showMenu();
            }
        });

        rlImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewTouched = img4;
                getImageViewTouchedTop = imgTop4;
                showMenu();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case CONSTANT.CAR_MAKE_ACTIVITY:
                    SharedPreferences prefs = getSharedPreferences(CONSTANT.APP_NAME, MODE_PRIVATE);
                    Integer userId = prefs.getInt(CONSTANT.CURRENT_ACCOUNT_ID, -1);
                    carDTO = new CarDAO(ActivityCompleteDriverRegistration.this).getCarByAccountID(userId);

                    if(carDTO != null && carDTO.getCarId() != null) {
                        ImageView imgCarDetails, imgYear, imgNumOfPassenger, imgPlateNum;

                        if(carDTO.getMake() != null) {
                            imgCarDetails = (ImageView)findViewById(R.id.imgCarDetails);
                            imgCarDetails.setVisibility(View.GONE);

                            txtCarDetails.setText(carDTO.getMake() + " " + carDTO.getModel());
                        }

                        if(carDTO.getYear() != null) {
                            imgYear = (ImageView)findViewById(R.id.imgYear);
                            imgYear.setVisibility(View.GONE);

                            txtYear.setText(String.valueOf(carDTO.getYear()));
                        }

                        if(carDTO.getNumOfPassenger() != null) {
                            imgNumOfPassenger = (ImageView)findViewById(R.id.imgNumOfPassenger);
                            imgNumOfPassenger.setVisibility(View.GONE);

                            txtNumOfPassenger.setText(String.valueOf(carDTO.getNumOfPassenger()));
                        }

                        if(carDTO.getPlateNum() != null) {
                            imgPlateNum = (ImageView)findViewById(R.id.imgPlateNum);
                            imgPlateNum.setVisibility(View.GONE);

                            txtPlateNum.setText(carDTO.getPlateNum());
                        }
                    }
                    break;

                case PICK_IMAGE_REQUEST:
                    if (resultCode == RESULT_OK && data != null) {
                        //if single image()
                        try {
                            if(data.getData() != null){
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                                ImageView imageView = getImageView(imageViewTouched);
                                imageView.setImageBitmap(bitmap);
                            }
                            // multiple images
                            else if(data.getData() == null && data.getClipData() != null){
                                int size = data.getClipData().getItemCount() > 4 ? 4 : data.getClipData().getItemCount();

                                int val = getImageViewTouchValue(imageViewTouched);
                                for (int x = 0; x < size; x++) {
                                    Uri uri = data.getClipData().getItemAt(x).getUri();

                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                                    ImageView imageView = getImageView((x+val));
                                    imageView.setImageBitmap(bitmap);
                                }
                            }

                            savePictureToDTO();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if(menuDialog != null)
                        menuDialog.dismiss();

                    break;

                case CAMERA:
                    if(resultCode == RESULT_OK) {
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();

                            // downsizing image as it throws OutOfMemory Exception for larger image
                            options.inSampleSize = 8;
                            Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                            bitmap = Utils.setPhotoRotation(bitmap);

                            ImageView imageView = getImageView(imageViewTouched);
                            imageView.setImageBitmap(bitmap);

                            bitmap.recycle();
                            savePictureToDTO();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if(menuDialog != null)
                        menuDialog.dismiss();
                    break;
            }
    }


    private void savePictureToDTO(){
        if(img1.getDrawable() != null) {
            carDTO.setHasPic1(true);
            carDTO.setPicture1(Utils.convertBitmapToBlob(((BitmapDrawable) img1.getDrawable()).getBitmap()));
        }


        if(img2.getDrawable() != null) {
            carDTO.setHasPic2(true);
            carDTO.setPicture2(Utils.convertBitmapToBlob(((BitmapDrawable) img2.getDrawable()).getBitmap()));
        }


        if(img3.getDrawable() != null) {
            carDTO.setHasPic3(true);
            carDTO.setPicture3(Utils.convertBitmapToBlob(((BitmapDrawable) img3.getDrawable()).getBitmap()));
        }


        if(img4.getDrawable() != null) {
            carDTO.setHasPic4(true);
            carDTO.setPicture4(Utils.convertBitmapToBlob(((BitmapDrawable) img4.getDrawable()).getBitmap()));
        }

        new CarDAO(ActivityCompleteDriverRegistration.this).saveOrUpdateCar(carDTO);
    }

    private ImageView getImageView(int value){

        int modVal = value%5;

        if(modVal == 1) {
            imgTop1.setVisibility(View.GONE);
            return img1;
        }

        else if(modVal == 2) {
            imgTop2.setVisibility(View.GONE);
            return img2;
        }

        else if(modVal == 3) {
            imgTop3.setVisibility(View.GONE);
            return img3;
        }

        else if(modVal == 4) {
                imgTop4.setVisibility(View.GONE);
                return img4;
        }

        return img1;
    }

    private int getImageViewTouchValue(ImageView value){

        if(value.equals(img1)){
            imgTop1.setVisibility(View.GONE);
            return 1;
        }
        else  if(value.equals(img2)){
            imgTop2.setVisibility(View.GONE);
            return 2;
        }

        else  if(value.equals(img3)){
            imgTop3.setVisibility(View.GONE);
            return 3;
        }

        else  if(value.equals(img4)){
            imgTop4.setVisibility(View.GONE);
            return 4;
        }

        return 1;
    }

    private ImageView getImageView(ImageView value){

        if(value.equals(img1)){
            imgTop1.setVisibility(View.GONE);
            return img1;
        }
        else  if(value.equals(img2)){
            imgTop2.setVisibility(View.GONE);
            return img2;
        }

        else  if(value.equals(img3)){
            imgTop3.setVisibility(View.GONE);
            return img3;
        }

        else  if(value.equals(img4)){
            imgTop4.setVisibility(View.GONE);
            return img4;
        }
        return img1;
    }


    private boolean validateForm(){
        boolean validForm = true;

        if(TextUtils.isEmpty(txtCarDetails.getText().toString())) {
            Utils.showToast(ActivityCompleteDriverRegistration.this, getResources().getString(R.string.activity_complete_driver_registration_car_details_error));
            validForm = false;
        }
        else if(TextUtils.isEmpty(txtYear.getText().toString())){
            Utils.showToast(ActivityCompleteDriverRegistration.this,getResources().getString(R.string.activity_complete_driver_registration_year_error) );
            validForm = false;
        }
        else if(TextUtils.isEmpty(txtPlateNum.getText().toString())){
            Utils.showToast(ActivityCompleteDriverRegistration.this,getResources().getString(R.string.activity_complete_driver_registration_plate_num_error) );
            validForm = false;
        }
        else if(TextUtils.isEmpty(txtNumOfPassenger.getText().toString())){
            Utils.showToast(ActivityCompleteDriverRegistration.this,getResources().getString(R.string.activity_complete_driver_registration_number_of_passenger_error) );
            validForm = false;
        }
        else if(!carDTO.isHasPic1()&& !carDTO.isHasPic2()&& !carDTO.isHasPic3()&& !carDTO.isHasPic4()) {
            Utils.showToast(ActivityCompleteDriverRegistration.this, getResources().getString(R.string.activity_complete_driver_registration_image_error));
            validForm = false;
        }

        if(!validForm){
            Utils.vibrate(ActivityCompleteDriverRegistration.this);
        }
        return validForm;
    }


    private void showMenu() {
        menuDialog = new Dialog(ActivityCompleteDriverRegistration.this, R.style.WalkthroughTheme);
        menuDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menuDialog.setContentView(R.layout.dilaogue_camera);
        menuDialog.setCanceledOnTouchOutside(true);
        menuDialog.setCancelable(true);

        TextView txtGallery = (TextView) menuDialog.findViewById(R.id.txtGallery);
        TextView txtCamera = (TextView) menuDialog.findViewById(R.id.txtCamera);

        TextView txtDeleteOne = (TextView) menuDialog.findViewById(R.id.txtDeleteOne);
        TextView txtDeleteAll = (TextView) menuDialog.findViewById(R.id.txtDeleteAll);


        if(carDTO != null && carDTO.getCarId() != null &&
                ( carDTO.isHasPic1()
                    || carDTO.isHasPic2()
                    || carDTO.isHasPic3()
                    || carDTO.isHasPic4())
                ){

            txtDeleteOne.setVisibility(View.VISIBLE);
            txtDeleteAll.setVisibility(View.VISIBLE);

            txtDeleteOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletePicture(false);
                }
            });

            txtDeleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletePicture(true);
                }
            });
        }


        txtCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        txtGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGE_REQUEST);
            }
        });

        menuDialog.show();
    }

    private void setPlusSign(boolean all){
        if(all){
            imgTop1.setVisibility(View.VISIBLE);
            imgTop2.setVisibility(View.VISIBLE);
            imgTop3.setVisibility(View.VISIBLE);
            imgTop4.setVisibility(View.VISIBLE);
        }
        else{
            ImageView imageView = getImageViewTouchedTop;
            imageView.setVisibility(View.VISIBLE);
        }
    }

    private void deletePictureFromDTO(){
        ImageView imageView = imageViewTouched;
        imageView.setImageDrawable(null);

        if(imageViewTouched.equals(imgTop1))
            carDTO.setHasPic1(false);

        else if(imageViewTouched.equals(imgTop1))
            carDTO.setHasPic2(false);

        else if(imageViewTouched.equals(imgTop1))
            carDTO.setHasPic3(false);

        else if(imageViewTouched.equals(imgTop1))
            carDTO.setHasPic4(false);

        savePictureToDTO();
        setPlusSign(false);
    }

    private void deleteAllPictureFromDTO(){
        img1.setImageBitmap(null);
        img2.setImageBitmap(null);
        img3.setImageBitmap(null);
        img4.setImageBitmap(null);

        carDTO.setHasPic1(false);
        carDTO.setHasPic2(false);
        carDTO.setHasPic3(false);
        carDTO.setHasPic4(false);

        new CarDAO(ActivityCompleteDriverRegistration.this).saveOrUpdateCar(carDTO);

        setPlusSign(true);
    }

    private void deletePicture(final boolean all){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ActivityCompleteDriverRegistration.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(ActivityCompleteDriverRegistration.this);
        }

        String message = null;
        if(all)
            message = getResources().getString(R.string.activity_complete_driver_registration_photo_delete_all_pop_up);
        else
            message = getResources().getString(R.string.activity_complete_driver_registration_photo_delete_one_pop_up);

        builder.setTitle("Delete Picture");
        builder.setMessage(message);

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        if(all)
                            deleteAllPictureFromDTO();
                        else
                            deletePictureFromDTO();

                        menuDialog.dismiss();
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                });

        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }


    private void openCamera(){
        if (
                ActivityCompat.checkSelfPermission(ActivityCompleteDriverRegistration.this, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED
            ||  ActivityCompat.checkSelfPermission(ActivityCompleteDriverRegistration.this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED
            ||  ActivityCompat.checkSelfPermission(ActivityCompleteDriverRegistration.this, Manifest.permission.READ_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(ActivityCompleteDriverRegistration.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CAMERA);
        }
        else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }


    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                CONSTANT.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CAMERA:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED )
                    openCamera();
                break;
        }
    }
}

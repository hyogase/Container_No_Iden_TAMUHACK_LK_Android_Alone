package com.lk.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Recog extends AppCompatActivity {
    private static final int REQUEST_SELECT_IMAGE = 0;
    private Bitmap mBitmap;
    private Uri mImageUri;
    private Button mButtonSelectImage;
    private Button mButtonOnlyTake;
    private ImageView imageView;
    private EditText mEditText;
    private String xm;
    private String ccdm;
    private String uploadFilename;
    private String xhb;
    private String slot1;
    private String xk;
    private String xcfs;
    private String cont_no;
    private String sy;
    private Boolean goToNewAct;
    private boolean recognizeOrNot;
    private boolean saveAfterPhoto;
    private Uri mUriPhotoTaken;
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recog);
        mButtonSelectImage = (Button) findViewById(R.id.buttonSelectImage);
        mButtonOnlyTake = (Button) findViewById(R.id.buttonOnlyTake);
        imageView = (ImageView) findViewById(R.id.selectedImage);

        mEditText = (EditText) findViewById(R.id.editTextResult);
        goToNewAct = false;
   //     mButtonOnlyTake.setVisibility(View.GONE);
    //    mButtonSelectImage.setVisibility(View.VISIBLE);
    }

    public void selectImage_OnlyTakePicture(View view) {
//        mEditText.setText("");
        recognizeOrNot = false;
        saveAfterPhoto = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Save the photo taken to a temporary file.
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File file = File.createTempFile("IMG_", ".jpg", storageDir);
                mUriPhotoTaken = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            } catch (IOException e) {
                setInfo(e.getMessage());
            }
        }
    }
    // Set the information panel on screen.
    private void setInfo(String info) {
        TextView textView = (TextView) findViewById(R.id.info);
        textView.setText(info);
    }
    public void selectImage(View view) {
        mEditText.setText("");

        recognizeOrNot = true;
        saveAfterPhoto = false;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Save the photo taken to a temporary file.
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File file = File.createTempFile("IMG_", ".jpg", storageDir);
                mUriPhotoTaken = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);

                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
               mBitmap = drawable.getBitmap();
               runCloudTextRecognition(mBitmap);
                //startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            } catch (IOException e) {
                setInfo(e.getMessage());
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("AnalyzeActivity", "onActivityResult");
        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
//                    Uri imageUri;
//                    if (data == null || data.getData() == null) {
//                        imageUri = mUriPhotoTaken;
//                    } else {
//                        imageUri = data.getData();
//                    }
                    // If image is selected successfully, set the image URI and bitmap.
                    if (data == null || data.getData() == null) {
                        mImageUri = mUriPhotoTaken;
                    } else {
                        //mImageUri = data.getData();
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(photo);
                        mBitmap=photo;
                    }

                    // Show the image on screen.
//                    ImageView imageView = (ImageView) findViewById(R.id.selectedImage);
//                    imageView.setImageBitmap(mBitmap);
//                    // Add detection log.
//                    //Log.d("AnalyzeActivity", "Image: " + mImageUri + " resized to " + mBitmap.getWidth()
//                    //        + "x" + mBitmap.getHeight());
//                    if (recognizeOrNot) {
//                        xhb = "1";
//                        runCloudTextRecognition(mBitmap);
//                    } else {
//                        xhb = "0";
//                    }
//                    if (saveAfterPhoto) {
//                        //new Thread(networkTask).start();
//                        showToast("The photo has been saved into database");
//                    }
//                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
//                            mImageUri, getContentResolver());

                    if (mBitmap != null) {
                        // Show the image on screen.
                        imageView = (ImageView) findViewById(R.id.selectedImage);
                        imageView.setImageBitmap(mBitmap);
                        // Add detection log.
                        Log.d("AnalyzeActivity", "Image: " + mImageUri + " resized to " + mBitmap.getWidth()
                                + "x" + mBitmap.getHeight());
                        if (recognizeOrNot) {
                            xhb = "1";
                            runCloudTextRecognition(mBitmap);
                        } else {
                            xhb = "0";
                        }
                        if (saveAfterPhoto) {
                            showToast("The photo has been saved into database");
                        }
                    }

                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    goToNewAct = false;
                    mButtonOnlyTake.setVisibility(View.GONE);
                    mButtonSelectImage.setVisibility(View.VISIBLE);

                } else {
                    goToNewAct = true;
                    mButtonOnlyTake.setVisibility(View.VISIBLE);
                    mButtonSelectImage.setVisibility(View.GONE);
                }
                mEditText.setText("");
                cont_no = "";
                break;
            default:
                break;
        }
    }
    private void runCloudTextRecognition(Bitmap mBitmap) {

        mButtonSelectImage.setEnabled(false);

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mBitmap);
        FirebaseVisionDocumentTextRecognizer recognizer = FirebaseVision.getInstance()
                .getCloudDocumentTextRecognizer();
        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionDocumentText>() {
                            @Override
                            public void onSuccess(FirebaseVisionDocumentText texts) {
                                mButtonSelectImage.setEnabled(true);
                                processCloudTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                mButtonSelectImage.setEnabled(true);
                                e.printStackTrace();
                                //mEditText.setText("Identification Failed. Please try later.");
                                mEditText.setText(e.toString());
                            }
                        });
    }
	private void processCloudTextRecognitionResult(FirebaseVisionDocumentText text) {
        // Task completed successfully
        if (text == null) {
            showToast("No text found");
            return;
        }
        //mGraphicOverlay.clear();
        List<FirebaseVisionDocumentText.Block> blocks = text.getBlocks();
        String string=text.getText();
        String tmp=string.replace("\n","");
        tmp=tmp.replace(" ","");
        mEditText.setText(tmp);
//        for (int i = 0; i < blocks.size(); i++) {
//            List<FirebaseVisionDocumentText.Paragraph> paragraphs = blocks.get(i).getParagraphs();
//            for (int j = 0; j < paragraphs.size(); j++) {
//                List<FirebaseVisionDocumentText.Word> words = paragraphs.get(j).getWords();
//                for (int l = 0; l < words.size(); l++) {
//                    //CloudTextGraphic cloudDocumentTextGraphic = new CloudTextGraphic(mGraphicOverlay,
//                    //        words.get(l));
//                    //mGraphicOverlay.add(cloudDocumentTextGraphic);
//                    //mEditText.setText("Hello");
//                    mEditText.append(words.toString());
//					//mEditText.setText(words.get(1).toString());
//                }
//            }
//        }
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}


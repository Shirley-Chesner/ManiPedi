package com.example.manipedi;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

public class ImageLaunchers {
    private ImageView image;

    private final ActivityResultLauncher<Void> cameraLauncher;
    private final ActivityResultLauncher<String> galleryLauncher;

    public ImageLaunchers(Fragment activity, ImageView image, ImageButton fromGalleryBtn, ImageButton takePhotoBtn) {
        this.image = image;

        cameraLauncher = activity.registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
            if (result != null) image.setImageBitmap(result);
        });

        galleryLauncher = activity.registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) image.setImageURI(result);
        });

        takePhotoBtn.setOnClickListener(view1 -> cameraLauncher.launch(null));
        fromGalleryBtn.setOnClickListener(view1 -> galleryLauncher.launch("image/*"));
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Bitmap getPhoto() {
        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();

        return ((BitmapDrawable) image.getDrawable()).getBitmap();
    }
}

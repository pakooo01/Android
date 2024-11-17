package com.example.mobileprogrammingassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ArticleDetailActivity extends AppCompatActivity {

    private static final String DEFAULT_IMAGE_URL = "https://via.placeholder.com/150"; // Default image URL

    private TextView titleTextView, subtitleTextView, bodyTextView, categoryTextView, modificationTextView;
    private ImageView imageView;

    private String imageUrl;

    // ActivityResultLauncher for selecting an image from the gallery
    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImage = result.getData().getData();
                    if (selectedImage != null) {
                        // Update the ImageView with the selected image
                        imageView.setImageURI(selectedImage);

                        // Save the selected image URI (you could also upload it here)
                        imageUrl = selectedImage.toString(); // Save image URI or path
                    }
                }
            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // Find references to UI components
        titleTextView = findViewById(R.id.articleTitle);
        subtitleTextView = findViewById(R.id.articleAbstract);
        bodyTextView = findViewById(R.id.articleBody);
        categoryTextView = findViewById(R.id.articleCategory);
        imageView = findViewById(R.id.articleImage);
        modificationTextView = findViewById(R.id.modificationDetails);

        // Get data from the Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String subtitle = intent.getStringExtra("description"); // Description
        String body = intent.getStringExtra("body"); // Body
        String category = intent.getStringExtra("category"); // Category
        String modificationDate = intent.getStringExtra("modificationDate"); // Modification Date
        String userId = intent.getStringExtra("userId"); // User ID
        imageUrl = intent.getStringExtra("imageUrl"); // Image URL

        // Set data to UI components
        titleTextView.setText(title);
        subtitleTextView.setText(subtitle);
        bodyTextView.setText(body);
        categoryTextView.setText(category);
        modificationTextView.setText("Modified on: " + modificationDate + "\nUser ID: " + userId);

        // Use Glide to load the image or display a default image
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).into(imageView);
        } else {
            Glide.with(this).load(DEFAULT_IMAGE_URL).into(imageView);
        }

        // Set up image click listener to allow changing the image
        imageView.setOnClickListener(view -> {
            // Open the gallery to pick an image
            Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intentGallery);
        });
    }

    // Optional: Handle runtime permissions (if needed)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission denied to access gallery.", Toast.LENGTH_SHORT).show();
        }
    }
}

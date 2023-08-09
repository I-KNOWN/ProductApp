package com.example.productapp.authentication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.productapp.R;
import com.example.productapp.database.UserDatabase;
import com.example.productapp.models.UserModel;
import com.example.productapp.databinding.ActivitySignupBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;


    Bitmap profileBitmpa;
    private ActivityResultLauncher<String> mGetPhoto = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            binding.ivProfile.setImageURI(result);
            createBitmap();

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initButtonClick();


    }

    private void createBitmap() {
        profileBitmpa = Bitmap.createBitmap(binding.ivProfile.getWidth(), binding.ivProfile.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(profileBitmpa);
        binding.ivProfile.draw(canvas);
    }

    private void initButtonClick() {
        binding.btnProfilePen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileImagePicker();
            }
        });
        binding.btnProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileImagePicker();
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
                finish();
            }
        });
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.etName.getText().toString().equals("") &&
                        !binding.etContact.getText().toString().equals("") &&
                        !binding.etEmil.getText().toString().equals("") &&
                        !binding.etPassword.getText().toString().equals("") &&
                        !binding.etConfPassword.getText().toString().equals("")){

                    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(binding.etEmil.getText().toString());
                    if(matcher.matches()){
                        if(binding.etPassword.getText().toString().equals(binding.etConfPassword.getText().toString())){
                            if(binding.chkAll.isChecked()){
                                if(profileBitmpa != null){
                                    createUser();
                                } else{
                                    Toast.makeText(SignupActivity.this, "Profile Image must be selected", Toast.LENGTH_SHORT).show();
                                }
                            } else{
                                Toast.makeText(SignupActivity.this, "Check the Terms and Condition", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignupActivity.this, "Passwords Don't Match", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignupActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignupActivity.this, "All the Fields must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUser() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long inserted = UserDatabase.getInstance(getApplicationContext()).getUserDao().createUser(
                        new UserModel(
                                binding.etName.getText().toString(),
                                Long.parseLong(binding.etContact.getText().toString()),
                                binding.etEmil.getText().toString(),
                                binding.etPassword.getText().toString(),
                                profileBitmpa

                        )
                );
                Log.d("valueofinserted", "inserted: " + inserted);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(inserted > 0){
                            binding.etPassword.setText("");
                            binding.etName.setText("");
                            binding.etContact.setText("");
                            binding.etEmil.setText("");
                            binding.etConfPassword.setText("");
                            binding.chkAll.setChecked(false);
                            binding.ivProfile.setImageResource(R.drawable.male_profile);
                            Toast.makeText(SignupActivity.this, "UserCreated", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SignupActivity.this, "User Failed to Create", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    private void profileImagePicker() {
        mGetPhoto.launch("image/*");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(binding != null){
            binding = null;
        }

    }
}
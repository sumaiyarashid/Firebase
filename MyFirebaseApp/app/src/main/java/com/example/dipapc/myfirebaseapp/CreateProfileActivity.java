package com.example.dipapc.myfirebaseapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CreateProfileActivity extends MenuActivity {

    private static final String TAG = "CreateProfileActivity";

    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private ChildEventListener mchild;
    private Button save;
    private EditText name;
    private EditText email;
    private EditText description;
    private ImageView profilepic;
    private Uri imagedata;
    private StorageReference sref;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 123 && requestCode == RESULT_OK && data.getData() != null){
            User user = new User();
            user.setPhotourl(data.getData());
            imagedata = data.getData();
            Log.d("CreateProfile:",imagedata.toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagedata);
                profilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        mRef = database.getReference(auth.getUid());
        sref = storage.getReference();
        //StorageReference ref1 = ref.child(auth.getUid());
        //initialize the views
        save = (Button)findViewById(R.id.buttonSave);
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        description = (EditText)findViewById(R.id.description);
        profilepic = findViewById(R.id.profilepic);

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),123);
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"profile creating");
                StorageReference picref = sref.child(auth.getUid()).child("images").child("profile pic");
                UploadTask task = picref.putFile(imagedata);
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateProfileActivity.this,"Upload Failed!",Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CreateProfileActivity.this,"Upload Successful!",Toast.LENGTH_SHORT).show();

                    }
                });
                //craete a user object
                User user = new User(name.getText().toString(),email.getText().toString(), description.getText().toString(),imagedata);
                mRef.setValue(user);



            }

        });

    }
}

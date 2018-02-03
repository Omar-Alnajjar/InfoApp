package com.omi.infoapp.create_activity.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omi.infoapp.R;
import com.omi.infoapp.compresser.Compressor;
import com.omi.infoapp.create_activity.CreateActivityMVP;
import com.omi.infoapp.objects.DataObject;
import com.omi.infoapp.root.App;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CreateActivity extends AppCompatActivity implements CreateActivityMVP.View{

    private static final int REQUEST_WRITE_EXTERNAL = 101;

    @BindView(R.id.main_image)
    ImageView mainImage;

    @BindView(R.id.main_text)
    TextView mainText;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    CreateActivityMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL);
            }
        }else{
            init();
        }
    }

    private void init() {
        ((App) getApplication()).getComponent().inject(this);
        presenter.setView(this);
        ButterKnife.bind(this);


        String imagePath = getIntent().getStringExtra("uri");
        if(imagePath != null) {
            presenter.compressImages(new File(imagePath));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.createInfo(new DataObject(mainText.getText().toString()));
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case REQUEST_WRITE_EXTERNAL:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    init();
                } else {
                    finish();
                }
                return;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.rxUnsubscribe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.rxUnsubscribe();
    }

    @Override
    public void doneCreate() {
        Toast.makeText(this, "Create was done", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void setImage(String imageUrl) {
        Picasso.with(this).load(imageUrl).into(mainImage);
    }

    @Override
    public void showSnackbar(String error) {
        Snackbar.make(mainImage, error, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }
}

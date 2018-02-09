package com.omi.infoapp.main_activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.omi.infoapp.R;
import com.omi.infoapp.compresser.PathUtil;
import com.omi.infoapp.create_activity.view.CreateActivity;
import com.omi.infoapp.main_activity.MainActivityMVP;
import com.omi.infoapp.main_activity.adapters.RecyclerViewAdapter;
import com.omi.infoapp.objects.DataObject;
import com.omi.infoapp.root.App;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityMVP.View{

    private static final int PICK_IMAGE = 100;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private RecyclerViewAdapter adapter;
    List<DataObject> dataObjects = new ArrayList<>();

    @Inject
    MainActivityMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        ((App) getApplication()).getComponent().inject(this);
        presenter.setView(this);
        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickImage();


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, dataObjects);
        recyclerView.setAdapter(adapter);

        presenter.loadData("");
    }



    private void pickImage() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            if(data != null) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                try {
                    intent.putExtra("uri", PathUtil.getPath(this,data.getData()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        }
    }

    @Override
    public void updateData(List<DataObject> newDataObjects) {
        dataObjects.clear();
        dataObjects.addAll(newDataObjects);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSnackbar(String error) {
        Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}

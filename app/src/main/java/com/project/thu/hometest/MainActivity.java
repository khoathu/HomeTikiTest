package com.project.thu.hometest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private String fileName = "keywords.json";
    private ArrayList<String> listItems;
    private String[] arrayColors = {"#00793b", "#005b52", "#9b6d00", "#640068", "#006792", "#9a4807"
            , "#9e0728", "#8f0514", "#053356"};
    private ProductAdapter productAdapter;

    @Bind(R.id.rvProduct)
    RecyclerView rvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        loadData();
        setUpList();
    }

    private void loadData() {
        String jsonContent = getStringFileFromAssert(this, fileName);
        listItems = parseJsonDataFromFile(jsonContent);
    }

    private void setUpList() {
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        listItems.addAll(listItems);
        productAdapter = new ProductAdapter(this, listItems, arrayColors);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvProduct.setLayoutManager(horizontalLayoutManager);
        rvProduct.setAdapter(productAdapter);
    }

    public static ArrayList<String> parseJsonDataFromFile(String responseContent) {
        ArrayList<String> listItems = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(responseContent.trim());
            for (int i = 0; i < jsonArray.length(); i++) {
                String item = jsonArray.getString(i);
                listItems.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listItems;
    }

    public static String getStringFileFromAssert(Context context, String fileName) {
        InputStream is = null;
        Writer writer = null;
        String text = "";
        try {
            is = context.getAssets().open(fileName);
            writer = new StringWriter();
            char[] buffer = new char[2048];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is,
                        "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            text = writer.toString();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        } catch (Exception e) {
            Log.e(TAG, "File not found: " + e.toString());
        }
        return text;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

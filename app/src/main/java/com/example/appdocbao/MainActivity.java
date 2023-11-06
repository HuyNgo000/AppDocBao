package com.example.appdocbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvtieude;
    ArrayList<String> arrayTitle,arraylink;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvtieude = (ListView) findViewById(R.id.listviewTieuDe);
        arrayTitle = new ArrayList<>();
        arraylink = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayTitle);
        lvtieude.setAdapter(adapter);

        new ReadRSS().execute("https://vnexpress.net/rss/so-hoa.rss");

        lvtieude.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this, Detail.class);
                intent.putExtra("Linktintuc", arraylink.get(i));
                startActivity(intent);
            }
        });
    }
    private class ReadRSS  extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line ="";
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }
                bufferedReader.close();

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");

            String tieude = "";

            for (int i = 0; i<nodeList.getLength(); i++){
                Element element = (Element) nodeList.item(i);
                tieude = parser.getValue(element,"title");
                arrayTitle.add(tieude);
                arraylink.add(parser.getValue(element,"link"));
            }
            adapter.notifyDataSetChanged();

        }
    }
}
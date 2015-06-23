package com.fourpiconeword.answers.answers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fourpiconeword.answers.answers.Adapters.MyListViewAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private Button bKantipur, bAnnapurna;
    private TextView displayNews1, displayNews2;
    private String title;
    ListView headlines;
    MyListViewAdapter adapter;
    ArrayList<Headlines> listHeadlines = new ArrayList<Headlines>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bKantipur = (Button) findViewById(R.id.button_kantipur);
        bKantipur = (Button) findViewById(R.id.button_annapurna);
        displayNews1 = (TextView) findViewById(R.id.textView_display_news_1);
        //displayNews2 = (TextView) findViewById(R.id.textView_display_news_2);
        headlines = (ListView) findViewById(R.id.listView_headlines);


    }

    public void parseHtml(View view) {

        if (view.getId() == R.id.button_kantipur) {
            MyTaskA myTaskA = new MyTaskA();
            myTaskA.execute();


        }
        if (view.getId() == R.id.button_annapurna) {
            MyTaskB myTaskB=new MyTaskB();
            myTaskB.execute();

        }

    }

    public class MyTaskA extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        Elements contents;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setMessage("Loading.....");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.ekantipur.com/sports/").get();
                title = doc.title();
                // Select the div-tags
                contents = doc.select("div[class=other_news_main padfour fltL col-md-6 col-sm-6 col-xs-12]");
                Log.d("Size of content", "" + contents.size());

                for (Element e : contents) {
                    Log.d(" paragraphs", "" + e.getElementsByTag("p").text());
                    Log.e("anchor", e.getElementsByTag("a").attr("href"));

                    Headlines headlines1=new Headlines();
                    headlines1.headlines=e.getElementsByTag("p").text();
                    headlines1.url=e.getElementsByTag("a").attr("href");

                    listHeadlines.add(headlines1);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            displayNews1.setText(title);

            Log.e("hellloooooooo", "how are uuuuuuuuuuuuuuuuuuuuu");
            adapter=new MyListViewAdapter(MainActivity.this,listHeadlines);
            headlines.setAdapter(adapter);
            headlines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent=new Intent(MainActivity.this,ReceiveIntent.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("url",listHeadlines.get(position).url);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
            // displayNews2.setText(paragraph.text());
            super.onPostExecute(aVoid);
        }
    }
    public class MyTaskB extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        Elements contents;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setMessage("Loading.....");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.espncricinfo.com/").get();
                title = doc.title();
                Elements divs=doc.select("div[class=featured headline-before content]");

                Elements headers=divs.select("h1[class=featured-link]");
                Log.d("Size of headers", "" + divs.size());
                // Select the div-tags
                /*contents = doc.select("div[class=other_news_main padfour fltL col-md-6 col-sm-6 col-xs-12]");
                */


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            displayNews1.setText(title);

            Log.e("hellloooooooo", "how are uuuuuuuuuuuuuuuuuuuuu");

            // displayNews2.setText(paragraph.text());
            super.onPostExecute(aVoid);
        }
    }


    @Override
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
    }
}

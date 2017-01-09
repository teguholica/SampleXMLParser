package com.teguholica.samplexmlparser;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    static final String URL = "http://androidsemua.com/feed/";

    static final String KEY_ITEM = "item";
    static final String KEY_TITLE = "title";
    static final String KEY_LINK = "link";
    static final String KEY_DESC = "description";

    private ListView vList;
    private ArrayList<HashMap<String, String>> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vList = (ListView) findViewById(R.id.list);

        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> selectedMenu = menuItems.get(i);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("title", selectedMenu.get(KEY_TITLE));
                intent.putExtra("desc", selectedMenu.get(KEY_DESC));
                startActivity(intent);
            }
        });

        new GetData().execute();
    }

    private class GetData extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... voids) {
            ArrayList<HashMap<String, String>> menuItems = new ArrayList<>();

            XMLParser parser = new XMLParser();
            String xml = parser.getXmlFromUrl(URL);
            Document doc = parser.getDomElement(xml);

            NodeList itemNode = doc.getElementsByTagName(KEY_ITEM);

            for (int i = 0; i < itemNode.getLength(); i++) {
                HashMap<String, String> map = new HashMap<>();
                Element e = (Element) itemNode.item(i);

                map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
                map.put(KEY_LINK, parser.getValue(e, KEY_LINK));
                map.put(KEY_DESC, parser.getValue(e, KEY_DESC));

                menuItems.add(map);
            }

            return menuItems;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            menuItems = result;
            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), menuItems,
                    R.layout.list_item,
                    new String[] { KEY_TITLE, KEY_LINK }, new int[] {
                    R.id.title, R.id.link });
            vList.setAdapter(adapter);
        }
    }
}

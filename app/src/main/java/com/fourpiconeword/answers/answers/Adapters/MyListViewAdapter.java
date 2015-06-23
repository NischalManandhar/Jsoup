package com.fourpiconeword.answers.answers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fourpiconeword.answers.answers.Headlines;
import com.fourpiconeword.answers.answers.R;

import java.util.ArrayList;

/**
 * Created by lenevo on 3/27/2015.
 */
public class MyListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Headlines> mListHeadlines;
    public MyListViewAdapter(Context context,ArrayList<Headlines> listHeadlines) {
        this.mContext=context;
        this.mListHeadlines=listHeadlines;
    }

    @Override
    public int getCount() {
        return mListHeadlines.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_headline_item,parent,false);
        }
        TextView textViewHeadlines= (TextView) convertView.findViewById(R.id.listView_textView_headlines);
        textViewHeadlines.setText(mListHeadlines.get(position).headlines);

        return convertView;
    }
}

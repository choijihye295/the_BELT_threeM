package com.summer.threemforth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class NoticeListAdapter extends ArrayAdapter<NoticeItem> {
    public NoticeListAdapter(Context context, ArrayList<NoticeItem> arrayList) {
        super(context, 0, arrayList);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.notice_item, viewGroup, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.belt);
        TextView textView2 = (TextView) view.findViewById(R.id.name);
        TextView textView3 = (TextView) view.findViewById(R.id.date);
        NoticeItem noticeItem = (NoticeItem) getItem(i);
        if (noticeItem != null) {
            textView.setText(noticeItem.getBelt());
            textView2.setText(noticeItem.getName());
            textView3.setText(noticeItem.getDate());
        }
        return view;
    }
}

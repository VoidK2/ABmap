

package com.majie.abmap.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.majie.abmap.R;
import com.majie.abmap.base.BaseListAdapter;
import com.majie.abmap.model.AboutModel;

/**
 *  @author majie
 */

public class AboutAdapter extends BaseListAdapter<AboutModel> {
    public AboutAdapter(Context context, List<AboutModel> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = getInflater().inflate(R.layout.item_about, parent, false);
        }
        TextView textName = ViewHolder.get(convertView, R.id.text_name);
        TextView textInfo = ViewHolder.get(convertView, R.id.text_info);

        AboutModel about = getList().get(position);
        textName.setText(about.getName());
        if (null != about.getInfo() && !about.getInfo().isEmpty()) {
            textInfo.setVisibility(View.VISIBLE);
            textInfo.setText(getList().get(position).getInfo());
        }else {
            textInfo.setVisibility(View.GONE);
        }

        return convertView;
    }
}

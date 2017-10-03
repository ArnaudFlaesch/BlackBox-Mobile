package com.blackbox.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackbox.R;
import com.blackbox.model.Element;
import com.blackbox.utils.FileUtils;

import java.util.ArrayList;

/**
 * Created by Arnaud on 01/10/2017.
 */

public class GridAdapter extends BaseAdapter {
    final ArrayList<Element> mItems;
    private int count;
    private Context context;

    public GridAdapter(Context context, final ArrayList<Element> items) {
        this.context = context;
        this.mItems = items;
        this.count = items.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Element getItem(final int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.element_grid_item, null);

        ImageView image = (ImageView) view.findViewById(R.id.imageViewElement);
        TextView name = (TextView) view.findViewById(R.id.name);

        Element element = getItem(position);
        image.setImageResource(FileUtils.getIcon(element.getName(), element.getFolder()));
        name.setText(element.getName());

        return view;
    }
}

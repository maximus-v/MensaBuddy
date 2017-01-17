package com.dev.app.mensabuddy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.app.mensabuddy.ListViewModel.MensaModel;
import com.dev.app.mensabuddy.R;

import java.util.ArrayList;

/**
 * Created by Max on 17.01.2017.
 */

public class MensaAdapter extends ArrayAdapter<MensaModel> {

    private ArrayList<MensaModel> dataSet;
    Context context;

    private static class ViewHolder {
        TextView txtMensaName;
        ImageView imageMensa;
    }

    public MensaAdapter(ArrayList<MensaModel> data, Context context) {
        super(context, R.layout.mensa_item, data);
        this.dataSet = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MensaModel mensaModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.mensa_item, parent, false);
            viewHolder.txtMensaName = (TextView) convertView.findViewById(R.id.mensaName);
            viewHolder.imageMensa = (ImageView) convertView.findViewById(R.id.mensaImage);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtMensaName.setText(mensaModel.getName());

        if (position == 0) {
            viewHolder.imageMensa.setImageResource(R.drawable.ic_location);
        }

        if (position == 1) {
            viewHolder.imageMensa.setImageResource(R.drawable.ic_favorite);
        }

        return result;
    }
}

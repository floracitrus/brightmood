package applications.brightmood;
/**
 * Created by florali on 2/12/17.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import applications.brightmood.R;


public class FourColumn_ListAdapter extends BaseAdapter {

    public ArrayList<Speech> speeches;
    Activity activity;

    FourColumn_ListAdapter(Activity activity, ArrayList<Speech> speeches) {
        super();
        this.speeches = speeches;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return speeches.size();
    }

    @Override
    public Object getItem(int position) {
        return speeches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView mWord;
        TextView mDomain;
        TextView mArea;
        TextView mPreset;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_row, null);
            holder = new ViewHolder();
            holder.mWord = (TextView) convertView.findViewById(R.id.textWord);
            holder.mDomain = (TextView) convertView.findViewById(R.id.textDomain);
            holder.mArea = (TextView) convertView
                    .findViewById(R.id.textArea);
            holder.mPreset = (TextView) convertView.findViewById(R.id.textPreset);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Speech item = speeches.get(position);
        holder.mWord.setText(item.getWord());
        holder.mDomain.setText(item.getDomain());
        holder.mArea.setText(item.getArea());
        holder.mPreset.setText(item.getPreset());

        return convertView;
    }
}

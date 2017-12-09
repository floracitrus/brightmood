package applications.brightmood;
/**
 * Created by florali on 2/12/17.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import applications.brightmood.R;


public class ThreeColumn_ListAdapter extends ArrayAdapter<Speech> {

    private LayoutInflater mInflater;
    private ArrayList<Speech> speeches;
    private int mViewResourceId;

    ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<Speech> speeches) {
        super(context, textViewResourceId, speeches);
        this.speeches = speeches;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

         Speech speech = speeches.get(position);

        if ( speech != null) {
            TextView word = (TextView) convertView.findViewById(R.id.textWord);
            TextView area = (TextView) convertView.findViewById(R.id.textArea);
            TextView preset = (TextView) convertView.findViewById(R.id.textPreset);
            if (word != null) {
                word.setText(speech.getWord());
            }
            if (area != null) {
                area.setText(speech.getArea());
            }
            if (preset != null) {
                preset.setText(speech.getPreset());
            }
            if (preset == null) {
                preset.setText("default");
            }
        }

        return convertView;
    }
}
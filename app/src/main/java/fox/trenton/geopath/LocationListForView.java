package fox.trenton.geopath;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by trenton on 3/8/16.
 */
public class LocationListForView extends ArrayAdapter<String> {
    private String[] loc_ids, user_ids, types, labels, descriptions, path_ids;
    private int[] lats, lons;
    private Date[] timestamps;
    private Activity context;

    public LocationListForView(Activity context, String[] loc_ids, String[] user_ids, String[] types,
                               String[] labels, String[] descriptions, String[] path_ids,
                               int[] lats, int[] lons, Date[] timestamps) {
        super(context, R.layout.list_view_layout, loc_ids);
        this.context = context;
        this.loc_ids = loc_ids;
        this.user_ids = user_ids;
        this.types = types;
        this.labels = labels;
        this.descriptions = descriptions;
        this.path_ids = path_ids;
        this.lats = lats;
        this.lons = lons;
        this.timestamps = timestamps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);

        TextView textViewLabel = (TextView) listViewItem.findViewById(R.id.textViewLabel);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);

        textViewLabel.setText(labels[position]);
        textViewDescription.setText(descriptions[position]);

        return listViewItem;
    }
}
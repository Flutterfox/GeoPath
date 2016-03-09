package fox.trenton.geopath;

import android.app.Activity;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by trenton on 3/7/16.
 */

public class LocationList {
    private String[] loc_ids, user_ids, types, labels, descriptions, path_ids;
    private int[] lats, lons;
    private Date[] timestamps;

    public LocationList(String[] loc_ids, String[] user_ids, String[] types,
                        String[] labels, String[] descriptions, String[] path_ids,
                        int[] lats, int[] lons, Date[] timestamps) {
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

    public List getList() {
        List returnList = new ArrayList();

        returnList.add(loc_ids);
        returnList.add(user_ids);
        returnList.add(types);
        returnList.add(labels);
        returnList.add(descriptions);
        returnList.add(path_ids);
        returnList.add(lats);
        returnList.add(lons);
        returnList.add(timestamps);

        return returnList;
    }
}

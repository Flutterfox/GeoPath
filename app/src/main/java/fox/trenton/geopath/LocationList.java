package fox.trenton.geopath;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by trenton on 3/7/16.
 */

public class LocationList {
    private String[] loc_ids, user_ids, types, labels, descriptions, path_ids;
    private double[] lats, lons;
    private Date[] timestamps;

    public LocationList(String[] loc_ids, String[] user_ids, String[] types,
                        String[] labels, String[] descriptions, String[] path_ids,
                        double[] lats, double[] lons, Date[] timestamps) {
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

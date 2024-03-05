
package com.example.ecommerce.ModelClass.User;

import java.io.Serializable;
import javax.annotation.processing.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Geolocation implements Serializable
{

    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    private final static long serialVersionUID = 7971641954823301847L;

    public Geolocation(String lat, String _long) {
        this.lat = lat;
        this._long = _long;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

}


package com.example.ecommerce.ModelClass.User;

import android.widget.EditText;

import java.io.Serializable;
import javax.annotation.processing.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Address implements Serializable
{

    @SerializedName("geolocation")
    @Expose
    private Geolocation geolocation;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    public Address(Geolocation geolocation, String city, String street, Integer number, String zipcode) {
        this.geolocation = geolocation;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
    }

    private final static long serialVersionUID = -4604726925266289335L;

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}

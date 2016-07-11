package com.example.igiagante.thegarden.home.plants.holders;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.DataHolder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * @author Ignacio Giagante, on 2/6/16.
 */
public class PlantHolder extends DataHolder<Plant> {

    public PlantHolder() {
    }

    public String getPlantId() {
        return getModel().getId();
    }

    public String getName() {
        return getModel().getName();
    }

    public String getGardenId() {
        return getModel().getGardenId();
    }

    public String getSeedDate() {
        return getSeedDateString();
    }

    public float getPhSoil() {
        return getModel().getPhSoil();
    }

    public float getEcSoil() {
        return getModel().getEcSoil();
    }

    public String getGenotype() {
        return getModel().getGenotype();
    }

    public String getFloweringTime() {
        return getModel().getFloweringTime();
    }

    public int getSize() {
        return getModel().getSize();
    }

    public int getHarvest() {
        return getModel().getHarvest();
    }

    public List<Image> getImages() {
        return getModel().getImages();
    }

    public List<Plague> getPlagues() {
        return getModel().getPlagues();
    }

    public List<Flavor> getFlavors() {
        return getModel().getFlavors();
    }

    private String getSeedDateString() {
        String format = "dd/MM";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(getModel().getSeedDate());
    }

    public Image getMainImage() {
        if(getModel().getImages() != null && !getModel().getImages().isEmpty()) {
            return getModel().getImages().get(0);
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(getModel(), 0);
    }

    public static final Parcelable.Creator<PlantHolder> CREATOR = new Parcelable.Creator<PlantHolder>() {
        public PlantHolder createFromParcel(Parcel in) {
            return new PlantHolder(in);
        }

        public PlantHolder[] newArray(int size) {
            return new PlantHolder[size];
        }
    };

    private PlantHolder(Parcel in) {
        model = in.readParcelable(this.getClass().getClassLoader());
    }
}

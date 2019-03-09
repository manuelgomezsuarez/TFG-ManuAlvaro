package tfg_manualvaro.androidmotogp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Temporada implements Parcelable {
    private Integer temporada;

    public Temporada(Integer temporada) {
        this.temporada = temporada;
    }

    public Temporada() {

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(temporada);
    }

    public Temporada(Parcel parcel) {
        this.temporada = parcel.readInt();
    }

    public static final Parcelable.Creator<Temporada> CREATOR = new Parcelable.Creator<Temporada>(){

        @Override
        public Temporada createFromParcel(Parcel parcel) {
            return new Temporada(parcel);
        }

        @Override
        public Temporada[] newArray(int size) {
            return new Temporada[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }


}

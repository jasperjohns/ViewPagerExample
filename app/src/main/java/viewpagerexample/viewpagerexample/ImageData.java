package viewpagerexample.viewpagerexample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asaldanha on 3/22/2016.
 */
public class ImageData implements Parcelable {
    private String imagePath;



    public ImageData(String imagePath){
        this.imagePath= imagePath;
    }

    public ImageData(Parcel in){
        this.imagePath = in.readString();
    }

    public String getImagePath() {return imagePath;}

    public void setImagePath(String imagePath) { this.imagePath = imagePath;}



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
    }

    public static final Parcelable.Creator<ImageData> CREATOR = new Parcelable.Creator<ImageData>(){
        public ImageData createFromParcel(Parcel in){
            return new ImageData(in);
        }

        public ImageData[] newArray(int size){
            return new ImageData[size];
        }
    };


}

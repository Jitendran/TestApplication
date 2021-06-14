package com.test.model.firstfragmentbird;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class BirdItem implements Parcelable {
    private String name;
    private String imageUrl;
    private boolean isClicked;
    private String wikiPediaUrl;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public String getWikiPediaUrl() {
        return wikiPediaUrl;
    }

    public BirdItem(String name, String imageUrl, String wikiPediaUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.wikiPediaUrl = wikiPediaUrl;
    }

    protected BirdItem(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        isClicked = in.readByte() != 0;
        wikiPediaUrl = in.readString();
    }

    public static final Creator<BirdItem> CREATOR = new Creator<BirdItem>() {
        @Override
        public BirdItem createFromParcel(Parcel in) {
            return new BirdItem(in);
        }

        @Override
        public BirdItem[] newArray(int size) {
            return new BirdItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(imageUrl);
        parcel.writeByte((byte) (isClicked ? 1 : 0));
        parcel.writeString(wikiPediaUrl);
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
//                .load(imageUrl).apply(new RequestOptions().circleCrop())
                .load(imageUrl)
                .into(view);
    }
}

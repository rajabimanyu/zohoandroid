package data;

import android.net.Uri;

/**
 * Created by Rinik on 13/10/17.
 */

public class Contact {
    public String name,phone;
    public boolean isHavingPhoto,isSaved;
    public String path;

    public Contact(String name, String phone, boolean isHavingPhoto, String path) {
        this.name = name;
        this.phone = phone;
        this.isHavingPhoto = isHavingPhoto;
        this.path= path;
    }

}

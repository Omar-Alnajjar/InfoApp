package com.omi.infoapp.objects;

/**
 * Created by omar on 1/29/18.
 */

public class DataObject {
    private String id;
    private String dataText;
    private String dataImage;
    private String dataImageBlur;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataText() {
        return dataText;
    }

    public void setDataText(String dataText) {
        this.dataText = dataText;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getDataImageBlur() {
        return dataImageBlur;
    }

    public void setDataImageBlur(String dataImageBlur) {
        this.dataImageBlur = dataImageBlur;
    }
}

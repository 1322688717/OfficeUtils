package com.example.officeutils.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class PDFFileInfo implements Parcelable {
    private String fileName;
    private String filePath;
    private long fileSize;
    private String time;
    private boolean isSelect;


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getFilePath() {
        return filePath == null ? "" : filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName == null ? "" : fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fileName);
        dest.writeString(this.filePath);
        dest.writeLong(this.fileSize);
        dest.writeString(this.time);
    }

    public PDFFileInfo() {
    }

    protected PDFFileInfo(Parcel in) {
        this.fileName = in.readString();
        this.filePath = in.readString();
        this.fileSize = in.readLong();
        this.time = in.readString();
    }

    public static final Creator<PDFFileInfo> CREATOR = new Creator<PDFFileInfo>() {
        @Override
        public PDFFileInfo createFromParcel(Parcel source) {
            return new PDFFileInfo(source);
        }

        @Override
        public PDFFileInfo[] newArray(int size) {
            return new PDFFileInfo[size];
        }
    };

    @Override
    public String toString() {
        return "PDFFildeInfo{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", time='" + time + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}

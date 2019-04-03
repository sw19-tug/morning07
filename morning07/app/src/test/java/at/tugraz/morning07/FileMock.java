package at.tugraz.morning07;

import android.media.Image;

import java.io.File;
import java.util.Date;


public class FileMock extends File
{
    private String name_;
    private Date lastModified_;
    private long size_;

    public FileMock(String pathname, String name, Date lastModified, long length) {
        super(pathname);
        name_ = name;
        lastModified_ = lastModified;
        size_ = length;
    }

    @Override
    public String getName() {
        return name_;
    }

    @Override
    public long lastModified() {
        return lastModified_.getTime();
    }

    @Override
    public long length() {
        return size_;
    }
}


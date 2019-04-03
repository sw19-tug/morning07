package at.tugraz.morning07;

import android.media.Image;

import java.io.File;
import java.util.Date;


public class FileMock extends File
{
    public FileMock(String pathname) {
        super(pathname);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public long lastModified() {
        return super.lastModified();
    }

    @Override
    public long length() {
        return super.length();
    }
}

/*public class MockImage extends Image {
    private String filename_;
    private Date date_;
    private int filesize_;


    public MockImage(String filename, Date date, int filesize) {
        super();
        //super();
        filename_ = filename;
        date_ = date;
        filesize_ = filesize;
    }

    public String getFilename() {
        return filename_;
    }

    public Date getDate() {
        return date_;
    }

    public int getFilesize() {
        return filesize_;
    }
}*/

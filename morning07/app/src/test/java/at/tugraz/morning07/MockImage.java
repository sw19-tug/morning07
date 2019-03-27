package at.tugraz.morning07;

import java.util.Date;


public class MockImage {
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
}

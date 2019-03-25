package restforyou.com.tofsologia.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "records")
public class Record {

    @PrimaryKey
    public long id;
    private String fileName;
    private String description;
    private String photoURI;
    private String textFileURI;

//    public Record(int id, String fileName, String description, String photoURI, String textFileURI) {
//        this.id = id;
//        this.fileName = fileName;
//        this.description = description;
//        this.photoURI = photoURI;
//        this.textFileURI = textFileURI;
//    }
//
//    public Record(){}

    public String getFileName() {
        return fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhotoURI() {
        return photoURI;
    }

    public void setPhotoURI(String photoURI) {
        this.photoURI = photoURI;
    }

    public String getTextFileURI() {
        return textFileURI;
    }

    public void setTextFileURI(String textFileURI) {
        this.textFileURI = textFileURI;
    }

    @Override
    public String toString() {
        return "id "+ id + " description " +description;
    }

}

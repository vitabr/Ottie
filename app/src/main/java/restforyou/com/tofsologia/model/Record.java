package restforyou.com.tofsologia.model;

public class Record {

    private String fileName;
    private String description;
    final private int id;
    final private String photoURI;
    final private String textFileURI;

    public Record(int id, String fileName, String description, String photoURI, String textFileURI) {
        this.id = id;
        this.fileName = fileName;
        this.description = description;
        this.photoURI = photoURI;
        this.textFileURI = textFileURI;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getPhotoURI() {
        return photoURI;
    }

    public String getTextFileURI() {
        return textFileURI;
    }
}

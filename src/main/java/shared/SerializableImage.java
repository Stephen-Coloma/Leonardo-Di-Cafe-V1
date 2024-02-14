package shared;

import javafx.scene.image.Image;

import java.io.Serializable;

public class SerializableImage extends Image implements Serializable {

    public SerializableImage(String url) {
        super(url);
    }
} // end of SerializableImage class

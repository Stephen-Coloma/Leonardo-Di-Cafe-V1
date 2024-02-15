package server.model.inventory;

import shared.Beverage;
import shared.SerializableImage;
import util.ImageCopier;

public class BeverageEditDetailsPopupModel {
    public Beverage beverage;

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public SerializableImage processSerializableImage(String absolutePath) {
        String extension = absolutePath.substring(absolutePath.lastIndexOf('.'));
        String copiedImagePath = ImageCopier.copyImage(absolutePath, beverage.getName() + extension);

        String url = beverage.getImage().getUrl();
        ImageCopier.deleteImage(url.substring(url.lastIndexOf('/') + 1));

        return new SerializableImage("file:" + copiedImagePath);
    } // end of processSerializableImage
} // end of BeverageEditDetailsPopupModel

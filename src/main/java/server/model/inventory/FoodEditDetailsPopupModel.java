package server.model.inventory;

import shared.Food;
import shared.SerializableImage;
import util.ImageCopier;

public class FoodEditDetailsPopupModel {
    private Food food;

    public void setFood(Food food) {
        this.food = food;
    }

    public Food getFood() {
        return food;
    }

    public SerializableImage processSerializableImage(String absolutePath) {
        String extension = absolutePath.substring(absolutePath.lastIndexOf('.'));
        String copiedImagePath = ImageCopier.copyImage(absolutePath, food.getName() + extension);

        String url = food.getImage().getUrl();
        ImageCopier.deleteImage(url.substring(url.lastIndexOf('/') + 1));

        return new SerializableImage("file:" + copiedImagePath);
    } // end of processSerializableImage
} // end of FoodEditDetailsPopupModel

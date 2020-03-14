import java.io.*;

public class Box implements Serializable {
    private int width;
    private int height;

    public void setWidth(int newWidth) {
        width = newWidth;
    }
    public void setHeight(int newHeight) {
        height = newHeight;
    }

    public static void main(String[] args) {
        Box myBox = new Box();
        myBox.setWidth(50);
        myBox.setHeight(20);

        try {
            FileOutputStream fs = new FileOutputStream("fileName.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(myBox);
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

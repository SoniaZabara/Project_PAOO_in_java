package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Honey extends SuperObject{

    public OBJ_Honey() {

        name = "Honey";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/honey_comb.png"));
        }catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}

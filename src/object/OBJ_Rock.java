package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Rock extends SuperObject{

    public OBJ_Rock() {

        name = "Rock";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/rock.png"));
        }catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}

package main;

import object.OBJ_Key;
import object.OBJ_Rock;
import object.OBJ_Honey;

public class AssetSetter {

    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 5*gp.tileSize;
        gp.obj[0].worldY = 5*gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 15*gp.tileSize;
        gp.obj[1].worldY = 15*gp.tileSize;

        gp.obj[2] = new OBJ_Rock();
        gp.obj[2].worldX = 3*gp.tileSize;
        gp.obj[2].worldY = 3*gp.tileSize;

        gp.obj[3] = new OBJ_Honey();
        gp.obj[3].worldX = 6*gp.tileSize;
        gp.obj[3].worldY = 6*gp.tileSize;

    }
}

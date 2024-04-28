package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1,tileNum2;

        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize; //predict where player is
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; //top left corner
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; //top right corner
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize; //predict where player is
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; //top left corner
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; //top right corner
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize; //predict where player is
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; //top left corner
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; //top right corner
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize; //predict where player is
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; //top left corner
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; //top right corner
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player){
        int index = 999;

        for(int i = 0; i < gp.obj.length; i++){

            if(gp.obj[i] != null){
                // get entity solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // get obj solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch(entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed; //simulate where entity would be to prevent colision
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) { //automatically chechs if these two rectangles are collinding
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) { //automatically chechs if these two rectangles are collinding
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) { //automatically chechs if these two rectangles are collinding
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x  += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)) { //automatically chechs if these two rectangles are collinding
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX; //resets values
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}

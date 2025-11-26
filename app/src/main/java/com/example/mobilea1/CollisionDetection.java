package com.example.mobilea1;

import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

public class CollisionDetection {
    public static boolean OverlapAABB2AABB(GameEntity entityA, GameEntity entityB)
    {
        float aLeft = entityA.getPosition().x - entityA.getSize().x / 2; //min1.x
        float aRight = entityA.getPosition().x + entityA.getSize().x / 2; //max1.x
        float aTop = entityA.getPosition().y - entityA.getSize().y / 2; //min1.y
        float aBtm = entityA.getPosition().y + entityA.getSize().y / 2; //max1.y

        float bLeft = entityB.getPosition().x - entityB.getSize().x / 2; //min2.x
        float bRight = entityB.getPosition().x + entityB.getSize().x / 2; //max2.x
        float bTop = entityB.getPosition().y - entityB.getSize().y / 2; //min2.y
        float bBtm = entityB.getPosition().y + entityB.getSize().y / 2; //max2.y


        return !(aLeft> bRight || aRight < bLeft || bBtm < aTop || bTop > aBtm);

    }
    public static boolean OverlapCircle2Circle(GameEntity entityA, GameEntity entityB)
    {
        Vector2 aPos = entityA.getPosition();
        Vector2 bPos = entityB.getPosition();

        Vector2 distVector = new Vector2(aPos.x - bPos.x, aPos.y - bPos.y );
        float distSquared = distVector.x * distVector.x + distVector.y * distVector.y;

        float aR = entityA.getSize().x * 0.5f;
        float bR = entityB.getSize().x * 0.5f;

        return distSquared <= (aR+bR) * (aR+bR);
    }
    public static boolean OverlapCircleToAABB(GameEntity circleEntity, GameEntity AABBEntity)
    {
        float testX = circleEntity.getPosition().x;
        float testY = circleEntity.getPosition().y;

        float half_w = AABBEntity.getSize().x / 2;
        float half_h = AABBEntity.getSize().y / 2;

        float circleX = circleEntity.getPosition().x;
        float circleY = circleEntity.getPosition().y;

        float AABBX = AABBEntity.getPosition().x;
        float AABBY = AABBEntity.getPosition().y;


        if (circleX < (AABBX - half_w)) //left edge
        {
            testX = AABBX - half_w;
        }
        else if (circleX > (AABBX + half_w)) // right edge
        {
            testX = AABBX + half_w;
        }

        if (circleY < (AABBY - half_h)) //bottom edge
        {
            testY = AABBY - half_h;
        }
        else if (circleY > (AABBY + half_h)) //top edge
        {
            testY = AABBY + half_h;
        }

        float xDist = circleX - testX;
        float yDist = circleY - testY;
        float dist = (float) Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));

        dist = Math.abs(dist);

        return dist <= (circleEntity.getSize().x * 0.5f);
    }

}

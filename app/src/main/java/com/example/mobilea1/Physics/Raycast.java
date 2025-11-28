package com.example.mobilea1.Physics;

import com.example.mobilea1.mgp2dCore.Vector2;

public class Raycast {
    public static float dist(float x1, float y1, float x2, float y2)
    {
        return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
    public static Vector2 raycastHitPoint(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y)
    {
        float s1_x = p1_x - p0_x;
        float s1_y = p1_y - p0_y;
        float s2_x = p3_x - p2_x;
        float s2_y = p3_y - p2_y;

        float denominator = (-s2_x * s1_y) + (s1_x * s2_y);
        if (denominator == 0) return null; // parallel

        float s = ((-s1_y * (p0_x - p2_x)) + (s1_x * (p0_y - p2_y))) / denominator;
        float t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / denominator;

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
        {
            float pos_x = p0_x + (t * s1_x);
            float pos_y = p0_y + (t * s1_y);
            return new Vector2(pos_x, pos_y);
        }

        return null;
    }
    public static float getRayCast(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y)
    {
        float s1_x, s1_y, s2_x, s2_y;
        s1_x = p1_x - p0_x;
        s1_y = p1_y - p0_y;
        s2_x = p3_x - p2_x;
        s2_y = p3_y - p2_y;
        float s, t;
        s = ((-s1_y * (p0_x - p2_x)) + (s1_x * (p0_y - p2_y))) / ((-s2_x * s1_y) + (s1_x * s2_y));
        t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);
        if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
        {
            // Collision detected
            float pos_x = p0_x + (t * s1_x);
            float pos_y = p0_y + (t * s1_y);
            return dist(0, 0, pos_x, pos_y);
        }
        return -1; // No collision
    }
}

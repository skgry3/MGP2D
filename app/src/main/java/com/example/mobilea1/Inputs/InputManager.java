package com.example.mobilea1.Inputs;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.mobilea1.mgp2dCore.GameActivity;
import com.example.mobilea1.mgp2dCore.GameEntity;
import com.example.mobilea1.mgp2dCore.Vector2;

import java.util.Vector;

public class InputManager {
    Vector<GameEntity> inputEntities = new Vector<>();
    private final Joystick joystick;
    private final Button jumpButton;
    private final Button switchButton;

    float screenWidth;
    float screenHeight;

    private InputManager() {
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;

        jumpButton = new Button(new Vector2(screenWidth - 200, screenHeight - 200), 100, Button.TYPE.MomentaryPush);
        switchButton = new Button(new Vector2(screenWidth - 100, screenHeight - 400), 100, Button.TYPE.Toggle);
        joystick = new Joystick(new Vector2(0, 0), 70, 40);

        inputEntities.add(jumpButton);
        inputEntities.add(switchButton);
        inputEntities.add(joystick);

        for (GameEntity e : inputEntities) {
            e.show = true;
            e.active = true;
            e.ignoreRaycast = true;
            e.isUI = true;
        }
    }

    private static class BillPughSingleton {
        private static final InputManager INSTANCE = new InputManager();
    }

    public static InputManager getInstance() {
        return InputManager.BillPughSingleton.INSTANCE;
    }

    public Button getJumpButton() {
        return jumpButton;
    }

    public Button getSwitchButton() {
        return switchButton;
    }

    public Joystick getJoystick() {
        return joystick;
    }

    public void handleTouch() {
        MotionEvent event = GameActivity.instance.getMotionEvent();
        if (event == null)
            return;

        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int pointerID = event.getPointerId(index);

        float x = event.getX(index);   // pointer INDEX used
        float y = event.getY(index);


        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                if (x < (screenWidth * 0.5)) {
                    if (joystick.pointerID == -1) {
                        joystick.pointerID = pointerID;

                        joystick.setPosition(new Vector2(x, y));
                        joystick.setPressed(true);
                        joystick.resetActuator();
                        joystick.show = true;

                        //System.out.println("joystick down " + joystick.pointerID);
                    }
                } else if (jumpButton.contains(x, y)) {
                    if (jumpButton.pointerID == -1) {
                        jumpButton.setPressed(true, pointerID);

                        //System.out.println("jumpBtn down " + jumpButton.pointerID);
                    }
                } else if (switchButton.contains(x, y)) {
                    if (switchButton.pointerID == -1) {
                        switchButton.setPressed(true, pointerID);
                        switchButton.toggled = !switchButton.toggled;
                        //System.out.println("switchBtn down " + switchButton.pointerID);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:

                if (joystick.isPressed()) {
                    int stickIndex = event.findPointerIndex(joystick.pointerID);

                    if (stickIndex != -1) {
                        float moveX = event.getX(stickIndex);
                        float moveY = event.getY(stickIndex);
                        joystick.setActuator(new Vector2(moveX, moveY));
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                if (pointerID == joystick.pointerID) {
                    joystick.resetJoystick();
                    //System.out.println("joystick up " + pointerID);
                } else if (pointerID == jumpButton.pointerID) {
                    jumpButton.Unpressed(pointerID);
                    //System.out.println("jumpBtn up " + pointerID);
                } else if (pointerID == switchButton.pointerID) {
                    switchButton.setPressed(false, pointerID);

                    joystick.setPressed(false);
                    joystick.resetActuator();

                    //System.out.println("switchBtn up " + pointerID);
                }
                break;
        }
        touchChecks(event);
    }

    public void touchChecks(MotionEvent event) {
        boolean leftSideTouched = false;
        boolean rightSideTouched = false;

        int i = 0;

        while (i < event.getPointerCount()) {
            float x = event.getX(i);

            if (x < screenWidth * 0.5f) {
                leftSideTouched = true;
            }
            if (x > screenWidth * 0.5f) {
                rightSideTouched = true;
            }
            i++;
        }

        if (!leftSideTouched) {
            // LEFT SIDE HAS NO INPUT
            joystick.resetJoystick();
        }
        if (!rightSideTouched) {
            jumpButton.reset();
        }
    }

    public void update(float dt) {
        for (GameEntity e : inputEntities) {
            e.onUpdate(dt);
        }
    }

    public void render(Canvas canvas) {
        for (GameEntity e : inputEntities) {
            e.onRender(canvas);
        }
    }
}

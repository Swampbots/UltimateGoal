package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.hardware.Gamepad;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

import static com.qualcomm.robotcore.hardware.Gamepad.Type.SONY_PS4;
import static com.qualcomm.robotcore.hardware.Gamepad.Type.UNKNOWN;


public class GamepadPlus {
    private Gamepad gp;
    private Date timer;

    private ArrayList<String> indexHandler = new ArrayList<>();
    private ArrayList<Long> timeActivated = new ArrayList<>();

    private static final float TRIGGER_THRESHOLD = 0.7f;

    private boolean isPS4Controller;
    private boolean ltButton;
    private boolean rtButton;



    public GamepadPlus(@NotNull Gamepad gp) {
        this.gp = gp;

        isPS4Controller = gp.type().equals(SONY_PS4);

        ltButton = false;
        rtButton = false;

        timer = new Date();
    }

    public GamepadPlus(@NotNull Gamepad gp, boolean makeTriggersButtons){
        this.gp = gp;

        isPS4Controller = gp.type().equals(SONY_PS4);

        ltButton = makeTriggersButtons;
        rtButton = makeTriggersButtons;

        timer = new Date();
    }

    public GamepadPlus(@NotNull Gamepad gp, boolean makeLeftTriggerButton, boolean makeRightTriggerButton){
        this.gp = gp;

        isPS4Controller = gp.type().equals(SONY_PS4);

        ltButton = makeLeftTriggerButton;
        rtButton = makeRightTriggerButton;

        timer = new Date();
    }
    public void update(){
        if(gp.type().equals(UNKNOWN)){ //Prevents update from running if not controller is plugged in
            return;
        }
        // Run button updates
        if(isPS4Controller){ // Have to separate PS4 buttons
            updateButtonTime(gp.circle,"circle");
            updateButtonTime(gp.cross,"cross");
            updateButtonTime(gp.triangle,"triangle");
            updateButtonTime(gp.square,"square");
        } else {
            updateButtonTime(gp.a,"a");
            updateButtonTime(gp.b,"b");
            updateButtonTime(gp.x,"x");
            updateButtonTime(gp.y,"y");
        }

        updateButtonTime(gp.dpad_up,"dpad_up");
        updateButtonTime(gp.dpad_down,"dpad_down");
        updateButtonTime(gp.dpad_left,"dpad_left");
        updateButtonTime(gp.dpad_right,"dpad_right");

        updateButtonTime(gp.left_bumper,"left_bumper");
        updateButtonTime(gp.right_bumper,"right_bumper");

        if(ltButton){
            updateButtonTime((gp.left_trigger > TRIGGER_THRESHOLD), "left_trigger");
        }
        if(rtButton){
            updateButtonTime((gp.right_trigger > TRIGGER_THRESHOLD),"right_trigger");
        }
    }

    public boolean isPressed(String button){

        return indexHandler.indexOf(convertButtonInputs(button)) != -1;
    }

    public long howLongPressed(String button){
        if(!isPressed(button)) return 0;

        return timer.getTime() - timeActivated.get(indexHandler.indexOf(convertButtonInputs(button)));
    }

    public boolean wasJustPressed(String button){
        return isPressed(button) && howLongPressed(button) < 5;

    }



    private void updateButtonTime(boolean button, String name) {
        if (button) { // button clicked
            if (indexHandler.indexOf(name) == -1) { // button not in (cycle 0)
                indexHandler.add(name);
                timeActivated.add(timer.getTime());
            } else { // button is in; update time (cycle 1,2,3..,n-1)
                //timeActivated.set(indexHandler.indexOf(name), timer.getTime());
            }
        } else {
            if (indexHandler.indexOf(name) != -1) { // button just released (cycle n); remove from list
                timeActivated.remove(indexHandler.indexOf(name));
                indexHandler.remove(name);

                indexHandler.trimToSize();
                timeActivated.trimToSize();
            }
        }

    }

    private String convertButtonInputs(String button){
        if(isPS4Controller){ // Ensure compatibility between ps4 and non-ps4 controllers
            switch (button){
                case "a":
                    button = "cross";
                    break;
                case "b":
                    button = "circle";
                    break;
                case "x":
                    button = "square";
                    break;
                case "y":
                    button = "triangle";
                    break;
            }
        } else {
            switch (button){
                case  "cross":
                    button = "a";
                    break;
                case "circle":
                    button = "b";
                    break;
                case "square":
                    button = "x";
                    break;
                case "triangle":
                    button = "y";
                    break;
            }
        }
        return button;
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

import static com.qualcomm.robotcore.hardware.Gamepad.Type.SONY_PS4;


public class GamepadPlus {
    private Gamepad gp;
    private Date timer;

    private ArrayList<String> indexHandler = new ArrayList<>();
    private ArrayList<Long> timeActivated = new ArrayList<>();

    private static final float TRIGGER_THRESHOLD = 0.7f;

    private boolean isPS4Controller = false;
    private boolean triggersAsButtons = false;

    public GamepadPlus(@NotNull Gamepad gp) {
        this.gp = gp;

        if(gp.type().equals(SONY_PS4)){
            isPS4Controller = true;
        }

        timer = new Date();

    }

    public GamepadPlus(@NotNull Gamepad gp, boolean makeTriggersButtons){
        this.gp = gp;

        if(gp.type().equals(SONY_PS4)){
            isPS4Controller = true;
        }

        triggersAsButtons = makeTriggersButtons;



        timer = new Date();

    }

    public void update(){
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

        if(triggersAsButtons){
            updateButtonTime((gp.left_trigger > TRIGGER_THRESHOLD),"left_trigger");
            updateButtonTime((gp.right_trigger > TRIGGER_THRESHOLD),"right_trigger");
        }
    }

    public boolean isPressed(String button){
        return indexHandler.indexOf(button) != -1;
    }

    public long howLongPressed(String button){
        if(!isPressed(button)) return 0;

        return timer.getTime() - timeActivated.get(indexHandler.indexOf(button));
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
}

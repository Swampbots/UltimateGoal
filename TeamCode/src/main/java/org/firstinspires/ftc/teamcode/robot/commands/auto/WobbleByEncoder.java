package org.firstinspires.ftc.teamcode.robot.commands.auto;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.subsystems.WobbleArm;

public class WobbleByEncoder implements Command {
    private WobbleArm wobble;

    private ElapsedTime timer;
    private double power;
    private int counts;
    private double timeout;

    private final double DEFAULT_TIMEOUT = 5.0;
    private DcMotor.RunMode prevRunMode;

    public WobbleByEncoder(WobbleArm wobble, int counts, double target, double power, double timeout){
        timer = new ElapsedTime();

        this.wobble = wobble;
        this.counts = counts;
        this.power = power;
        this.timeout = timeout;
    }

    public WobbleByEncoder(WobbleArm wobble, int counts, double target, double power){
        timer = new ElapsedTime();

        this.wobble = wobble;
        this.counts = counts;
        this.power = power;
        this.timeout = DEFAULT_TIMEOUT;
    }

    @Override
    public void start() {
        timer.reset();

        int currentPos = wobble.getArmTargetPos();
    }

    @Override
    public void periodic() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}

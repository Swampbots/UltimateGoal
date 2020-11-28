package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecommander.DogeCommander;
import com.disnodeteam.dogecommander.DogeOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.commands.teleop.TeleOpDriveControl;
import org.firstinspires.ftc.teamcode.robot.commands.teleop.TeleOpIntakeControl;
import org.firstinspires.ftc.teamcode.robot.commands.teleop.TeleOpKickerControl;
import org.firstinspires.ftc.teamcode.robot.commands.teleop.TeleOpShooterControl;
import org.firstinspires.ftc.teamcode.robot.commands.teleop.TeleOpTransferControl;
import org.firstinspires.ftc.teamcode.robot.commands.teleop.TeleOpWobbleControl;
import org.firstinspires.ftc.teamcode.robot.subsystems.Drive;
import org.firstinspires.ftc.teamcode.robot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.robot.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.robot.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.robot.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.robot.subsystems.Wobble;

@TeleOp(name = "Command Drive", group = "TeleOp")
public class CommandDrive extends LinearOpMode implements DogeOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DogeCommander commander = new DogeCommander(this);

        Drive drive             = new Drive(hardwareMap,true);
        Kicker kicker           = new Kicker(hardwareMap);
        Shooter shooter         = new Shooter(hardwareMap);
        Wobble wobble           = new Wobble(hardwareMap);
        Intake intake           = new Intake(hardwareMap);
        Transfer transfer       = new Transfer(hardwareMap);

        commander.registerSubsystem(drive);
        commander.registerSubsystem(kicker);
        commander.registerSubsystem(shooter);
        commander.registerSubsystem(wobble);
        commander.registerSubsystem(intake);
        commander.registerSubsystem(transfer);

        commander.init();
        waitForStart();

        commander.runCommandsParallel(
                new TeleOpDriveControl(drive,gamepad1),
                new TeleOpKickerControl(kicker,gamepad1),
                new TeleOpShooterControl(shooter,gamepad2),
                new TeleOpWobbleControl(wobble,gamepad1,gamepad2),
                new TeleOpIntakeControl(intake,gamepad2),
                new TeleOpTransferControl(transfer,gamepad2)
        );
    }
}

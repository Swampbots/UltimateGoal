package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.teamcode.BasicDriveHardware.INTAKE_MAX_POWER;
import static org.firstinspires.ftc.teamcode.BasicDriveHardware.TRANSFER_MAX_POWER;

@TeleOp(name = "Mecanum Driver Control", group = "TeleOp")
public class BasicDrive extends OpMode {



    BasicDriveHardware hardware = new BasicDriveHardware();


    double intakePower   = 0;
    double transferPower = 0;


    public void init() {
        hardware.init(hardwareMap);








        telemetry.addLine("Ready");
        telemetry.update();
    }

    public void loop() {


        // Do the math
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double twist = -gamepad1.right_stick_x;

         intakePower     = gamepad1.a ? 1.0 * INTAKE_MAX_POWER : 0.0;
         transferPower   = gamepad1.a ? 1.0 * TRANSFER_MAX_POWER : 0.0;







        // Set the power
        hardware.setMecanumPower(drive, strafe, twist, .6);

        hardware.intake.setPower(intakePower);
        hardware.transfer.setPower(transferPower);





        telemetry.addLine("Running");
        telemetry.addLine();
        telemetry.addData("Wheel driver speed mod", .6);
        telemetry.addLine();
    }


}

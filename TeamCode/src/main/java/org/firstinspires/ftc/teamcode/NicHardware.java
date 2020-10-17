package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public class NicHardware {

    // Motors
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor rearLeft;
    public DcMotor rearRight;

    public DcMotor pulleyLeft;
    public DcMotor pulleyRight;

    public DcMotor arm;

    public DcMotor tapeMeasure;

    // Servos

//    public Servo clamp;

    //    public Servo gripper;
    public Servo testGripper;
    public Servo wrist;

    public Servo foundLeft;
    public Servo foundRight;

    public Servo capstone;


    //    // IMU
    BNO055IMU imu;

    // Drive encoder variables
    public final double COUNTS_PER_REV_HD_20    = 560; // REV HD Hex 20:1 motor
    public final double DRIVE_GEAR_REDUCTION    = 20.0 / 26.0; // 15 tooth on motor shaft to 15 tooth on wheel shaft
    public final double WHEEL_DI_INCHES         = 90.0 / 25.4; // 90mm diameter wheel divided by 25.4(in/mm)
    //    public final double COUNTS_PER_INCH         = (COUNTS_PER_REV_HD_20 * DRIVE_GEAR_REDUCTION) / (WHEEL_DI_INCHES * Math.PI);
    public final double COUNTS_PER_INCH_EMPIRICAL = 1000 / 24.0;    // 1000 counts divided by 24.0 inches; determined through testing

    // Servo-specific variables
    public static final double GRIPPER_CLOSED = 0.0;
    public static final double GRIPPER_OPEN = 1.0;

    public static final double CLAMP_LEFT     = 0.3;
    public static final double RELEASE_LEFT   = 1.0;

    private final double CLAMP_RIGHT    = 1.0;
    private final double RELEASE_RIGHT  = 0.3;


    // Speed modifier variables
    public static final double SLOW     = 0.35;
    public static final double NORMAL   = 0.8;
    public static final double FAST     = 1.0;

    public static double wheelSpeedMod  = FAST;
    public static double armSpeedMod    = FAST;

    public static final double WRIST_SCALAR = 0.01;

    // Wrist positions
    public static final double WRIST_PLACING  = 0.20;
    public static final double WRIST_GRABBING = 1.00;
    public static final double WRIST_STARTING = 0.30;
    public static final double WRIST_STORING  = 0.37;

    // Capstone positions
    public static final double CAP_STOWED   = 1.0;
    public static final double CAP_DEPLOYED = 0.0;


    // Arm positions (to seperate different modes of operation for the wrist)
    public static final int ARM_PLACING   = 1900;
    public static final int ARM_GRABBING  = 40;
    public static final int ARM_STARTING  = 0;
    public static final int ARM_STORING   = 200;


    // Pulley control
    public static boolean resetPulleys = false;
    public static final double POWER_THRESHOLD = 0.01;


    // PID variables
    public final double MAX_SPEED = 0.3;
    public final double P = 0.025;
    public final double I = 0.000;
    public final double D = 0.000;
    public final double TOLERANCE = 1.0;

//    public final double DV_TOLERANCE = 0.2;


    // Timeout variable for looping methods
    public static final double TIMEOUT = 2.0;  // 2 seconds

    // Camera variables

    public void init(HardwareMap hwMap) {
        init(hwMap, false, false); // Default is to not initialize the camera or the IMU
    }


    public void init(HardwareMap hardwareMap, boolean initCamera, boolean initIMU) {
        frontLeft   = hardwareMap.dcMotor.get("fl_drive");
        frontRight  = hardwareMap.dcMotor.get("fr_drive");
        rearLeft    = hardwareMap.dcMotor.get("rl_drive");
        rearRight   = hardwareMap.dcMotor.get("rr_drive");

        pulleyLeft      = hardwareMap.dcMotor.get("pulley_left");
        pulleyRight     = hardwareMap.dcMotor.get("pulley_right");




        frontLeft.  setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeft.   setDirection(DcMotorSimple.Direction.REVERSE);





        frontLeft.      setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.     setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearLeft.       setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearRight.      setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);







        /*
            We are using Actuonix PQ-12 linear actuators for the grippers as of Dec 12 2019. They
            take a signal between 1.0 milliseconds (extend; 1000 microseconds) and 2.0 milliseconds
            (retract; 2000 microseconds) according to Actuonix's datasheet. REV Smart Robot Servos,
            for comparison, take a signal between 500 microseconds (-90 degrees) and 2500
            microseconds (90 degrees), so a 2500 microseconds signal is sent when setPosition(1.0)
            is called. To correct the range for the linear actuator servos, We are setting them to
            0.4 (1000 / 2500) and 0.8 (2000 / 2500).
         */
//        gripper.scaleRange(0.2, 0.8);







    }


    // Movement methods
    public void setLeftPower(double power) {
        frontLeft.setPower(power);
        rearLeft.setPower(power);
    }

    public void setRightPower(double power) {
        frontRight.setPower(power);
        rearRight.setPower(power);
    }


    public void setMecanumPower(double drive, double strafe, double twist) {
        setMecanumPower(drive,strafe,twist,1);
    }

    public void setMecanumPower(double drive, double strafe, double twist, double speedMod) {
        frontLeft.setPower( (drive + strafe + twist) * speedMod);
        frontRight.setPower((drive - strafe - twist) * speedMod);
        rearLeft.setPower(  (drive - strafe + twist) * speedMod);
        rearRight.setPower( (drive + strafe - twist) * speedMod);
    }

    public void mecanumDriveFieldCentric(double x, double y, double twist, double heading) {
        // Account for the gyro heading in the drive vector
        double[] rotated = rotateVector(x, y, -1 * heading);    // Heading is negated to correct vector rotation

        // We are making seperate variables here for clarity's sake
        double xIn = rotated[0];
        double yIn = rotated[1];

        // Put motor powers in an array to be normalized to largest value
        double[] motorSpeeds = {
                yIn + xIn + twist,  // Front left
                yIn - xIn - twist,  // Front right
                yIn - xIn + twist,  // Rear left
                yIn + xIn - twist   // Rear right
        };

        // Normalize so largest speed is 1.0
        normalize(motorSpeeds);


        // Set motor powers
        frontLeft  .setPower(motorSpeeds[0]);
        frontRight .setPower(motorSpeeds[1]);
        rearLeft   .setPower(motorSpeeds[2]);
        rearRight  .setPower(motorSpeeds[3]);
    }
    protected static double[] rotateVector(double x, double y, double angle) {
        double cosA = Math.cos(angle * (Math.PI / 180.0));
        double sinA = Math.sin(angle * (Math.PI / 180.0));
        double[] out = new double[2];
        out[0] = x * cosA - y * sinA;
        out[1] = x * sinA + y * cosA;
        return out;
    }
    protected static void normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] /= maxMagnitude;
            }
        }
    }

    public void setDriveCounts(int counts) {
        frontLeft.setTargetPosition    (frontLeft.getCurrentPosition()    + counts);
        frontRight.setTargetPosition   (frontRight.getCurrentPosition()   + counts);
        rearLeft.setTargetPosition     (rearLeft.getCurrentPosition()     + counts);
        rearRight.setTargetPosition    (rearRight.getCurrentPosition()    + counts);
    }

    public void setStrafeCounts(int counts) {
        frontLeft.setTargetPosition    (frontLeft.getCurrentPosition()    + counts);
        frontRight.setTargetPosition   (frontRight.getCurrentPosition()   - counts);
        rearLeft.setTargetPosition     (rearLeft.getCurrentPosition()     - counts);
        rearRight.setTargetPosition    (rearRight.getCurrentPosition()    + counts);
    }


    // Gyroscope methods
    public double normalize180(double angle) {
        while(angle > 180) {
            angle -= 360;
        }
        while(angle <= -180) {
            angle += 360;
        }
        return angle;
    }

    public float heading() {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }

    // Foundation moving methods
    public void clampFoundation() {
        foundLeft.setPosition(CLAMP_LEFT);
        foundRight.setPosition(CLAMP_RIGHT);
    }

    public void releaseFoundation() {
        foundLeft.setPosition(RELEASE_LEFT);
        foundRight.setPosition(RELEASE_RIGHT);
    }








    // Pulley methods
    public void setPulleyMode(DcMotor.RunMode newMode) {
        pulleyLeft. setMode(newMode);
        pulleyRight.setMode(newMode);
    }

    public void setPulleyTargets (int counts) {
        pulleyLeft. setTargetPosition(counts);
        pulleyRight.setTargetPosition(counts);
    }

    public void setPulleyPower(double power) {
        pulleyLeft. setPower(power);
        pulleyRight.setPower(power);
    }

    public boolean getPulleyIsBusy() {
        return (pulleyLeft.isBusy() && pulleyRight.isBusy());   // With && operator, will return false once either is finished
    }







    // OVERRIDE METHODS
    public void stopAllMotors() {
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        rearLeft.setPower(0.0);
        rearRight.setPower(0.0);

        pulleyLeft.setPower(0.0);
        pulleyRight.setPower(0.0);

        arm.setPower(0.0);
        tapeMeasure.setPower(0.0);
    }


}

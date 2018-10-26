//RENAME THE ROBOT TO SOMETHING MORE CREATIVE -PULKIT 9/27/2017
// A most amazing and thought provoking Idea - Naren Ram

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RuckusHardware
{
    /* Public OpMode members. */
    public DcMotor motorLeft   = null;
    public DcMotor  motorRight  = null;
    public DcMotor motorLift = null;
    public DcMotor motorArm = null;
    public DcMotor motorHook = null;

    public Servo servoLeft = null;
    public Servo servoRight = null;
    public Servo servoHook = null;

    ColorSensor sensorColorR = null;
    ColorSensor sensorColorL = null;
    //ModernRoboticsI2cRangeSensor sensorRange = null;

    /* Local OpMode members. */
    HardwareMap hwMap  = null;

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        motorLeft  = hwMap.get(DcMotor.class, "motorLeft");
        motorRight = hwMap.get(DcMotor.class, "motorRight");
        motorLift = hwMap.get(DcMotor.class, "motorLift");
        motorArm = hwMap.get(DcMotor.class, "motorArm");
        motorHook = hwMap.get(DcMotor.class, "motorHook");

        motorRight.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorArm.setDirection(DcMotor.Direction.REVERSE);
        motorLift.setDirection(DcMotor.Direction.REVERSE);
        motorHook.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        motorLeft.setPower(0);
        motorRight.setPower(0);


        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorHook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Define and initialize ALL installed servos.
        servoLeft = hwMap.get(Servo.class, "servoLeft");
        servoRight = hwMap.get(Servo.class, "servoRight");
        servoHook = hwMap.get(Servo.class, "servoHook");

        servoLeft.setPosition(0.2); //l1 r 0
        servoRight.setPosition(0.2);
        servoHook.setPosition(0.2);

        //Sensor stuff
        //sensorRange = hwMap.get(ModernRoboticsI2cRangeSensor.class, "range");
        //two more servos one more motor
        sensorColorR = hwMap.get(ColorSensor.class, "sensorColorR");
        sensorColorL = hwMap.get(ColorSensor.class, "sensorColorL");
    }
}

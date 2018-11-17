//RENAME THE ROBOT TO SOMETHING MORE CREATIVE -PULKIT 9/27/2017
// A most amazing and thought provoking Idea - Naren Ram

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TestRuckusHardware
{
    /* Public OpMode members. */
    public DcMotor motorLeft;
    public DcMotor motorRight;
    public DcMotor motorFront;
    public DcMotor motorBack;
    public DcMotor motorHook;


    public Servo servoLeft;
    public Servo servoRight;
    public Servo servoMark;

    ColorSensor sensorColorR = null;
    ColorSensor sensorColorL = null;

    /* Local OpMode members. */
    HardwareMap hwMap;

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        motorLeft  = hwMap.get(DcMotor.class, "motorLeft");
        motorRight = hwMap.get(DcMotor.class, "motorRight");
        motorBack  = hwMap.get(DcMotor.class, "motorBack");
        motorFront  = hwMap.get(DcMotor.class, "motorFront");
        motorHook  = hwMap.get(DcMotor.class, "motorHook");

        servoRight = hwMap.get(Servo.class, "servoRight");
        servoLeft = hwMap.get(Servo.class, "servoLeft");
        servoMark = hwMap.get(Servo.class, "servoMark");



        motorRight.setDirection(DcMotor.Direction.FORWARD);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFront.setDirection(DcMotor.Direction.FORWARD);
        motorBack.setDirection(DcMotor.Direction.REVERSE);
        motorHook.setDirection(DcMotor.Direction.FORWARD);



        // Set all motors to zero power
        motorLeft.setPower(0);
        motorRight.setPower(0);
        motorFront.setPower(0);
        motorBack.setPower(0);
        motorHook.setPower(0);

        servoLeft.setDirection(Servo.Direction.REVERSE);
        servoRight.setDirection(Servo.Direction.FORWARD);

        servoLeft.setPosition(0.8);
        servoRight.setPosition(0.8);
        servoMark.setPosition(0.0);



        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorHook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //servoFeed = hwMap.get(Servo.class, "servoFeed");


        //Sensor stuff
        sensorColorR = hwMap.get(ColorSensor.class, "sensorColorR");
        sensorColorL = hwMap.get(ColorSensor.class, "sensorColorL");
    }
}

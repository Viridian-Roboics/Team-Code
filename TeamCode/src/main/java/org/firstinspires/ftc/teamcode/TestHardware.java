//RENAME THE ROBOT TO SOMETHING MORE CREATIVE -PULKIT 9/27/2017
// A most amazing and thought provoking Idea - Naren Ram

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;import com.qualcomm.robotcore.hardware.ColorSensor;


public class TestHardware
{
    // Here we define all of the motors we use.
    // Our robot uses omni-wheels and a four motor drive system.
    public DcMotor motorLeft;
    public DcMotor motorRight;
    public DcMotor motorFront;
    public DcMotor motorBack;
    // The motor to raise and lower the hook

    public Servo servoPull = null;

/*
    Rev2mDistanceSensor sensorRange = null;

    ColorSensor colorL;
    ColorSensor colorR;
*/


    // Define hardware map
    HardwareMap hwMap;

    // Initialize standard hardware interfaces
    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        // Here we name the motors.
        motorLeft  = hwMap.get(DcMotor.class, "motorLeft");
        motorRight = hwMap.get(DcMotor.class, "motorRight");
        motorBack  = hwMap.get(DcMotor.class, "motorBack");
        motorFront  = hwMap.get(DcMotor.class, "motorFront");
       
        // Here we set the motor directions.
        motorRight.setDirection(DcMotor.Direction.FORWARD);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFront.setDirection(DcMotor.Direction.FORWARD);
        motorBack.setDirection(DcMotor.Direction.REVERSE);
        

        // Here we set the servo directions.

        // Here we set all motors to zero power for safety.
        motorLeft.setPower(0);
        motorRight.setPower(0);
        motorFront.setPower(0);
        motorBack.setPower(0);
       

        // Here we set all motors to run with encoders.
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        servoPull = hwMap.get(Servo.class, "servoPull");
        servoPull.setPosition(0.5);



        /*
        colorL = hwMap.get(ColorSensor.class, "colorL");
        colorR = hwMap.get(ColorSensor.class, "colorR");
        sensorRange = hwMap.get(Rev2mDistanceSensor.class, "sensorRange");
*/
    }
}

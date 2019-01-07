/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.TensorFlow.Device;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.io.IOException;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

public class ZoDriving extends LinearOpMode {

    /* Declare OpMode members. */
    ZoHardware         robot   = new ZoHardware();
    private ElapsedTime     runtime = new ElapsedTime();
    String xyz = "z";


    static final double     COUNTS_PER_MOTOR_REV    = 288 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 0.5 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.4 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.5;
    static final double     TURN_SPEED              = 0.5;
    static final double COUNTS_PER_MOTOR = 1120; //for the hook motor
    static final double COUNTS_PER_INCH_HOOK = (COUNTS_PER_MOTOR*2*3.1415);
    Rev2mDistanceSensor sensorRangeR;
    Rev2mDistanceSensor sensorRangeL;
    BNO055IMU imu;
    TensorFlow tf;
    @Override
    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        //init distance sensors
        Rev2mDistanceSensor sensorRangeR = hardwareMap.get(Rev2mDistanceSensor.class, "sensorRangeR");
        Rev2mDistanceSensor sensorRangeL = hardwareMap.get(Rev2mDistanceSensor.class, "sensorRangeL");
        //side motors
        robot.motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //lateral motors
        robot.motorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.motorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //hook motor
        robot.motorHook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorHook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        // Wait for the game to start (driver presses PLAY)
        robot.servoMark.setPosition(0);
        tf = new TensorFlow(hardwareMap, Device.Webcam,telemetry);
        waitForStart();
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        //tf.start(); //moved to start of program

    }

    public static double turnDistance(double axleLength, double angle)
    {
        //takes the width of the robot and calculates how far each tread needs to move
        //all movements are optimized to use the least area for movement
        double fractionOfCirc = angle/360.0;
        double circumference = 2 * Math.PI * axleLength;
        double wheelTravel = fractionOfCirc * circumference;

        wheelTravel = Math.round(wheelTravel);

        return wheelTravel;
    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.motorLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.motorRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.motorLeft.setTargetPosition(newLeftTarget);
            robot.motorRight.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.motorLeft.setPower(Math.abs(speed));
            robot.motorRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.motorLeft.isBusy() && robot.motorRight.isBusy())) {

                /*Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
               telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.motorLeft.getCurrentPosition(),
                        robot.motorRight.getCurrentPosition());
               telemetry.update();*/
            }

            // Stop all motion;
            robot.motorLeft.setPower(0);
            robot.motorRight.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    public void latEncoderDrive(double speed, double frontInches, double backInches, double timeoutS) {
        int newFrontTarget;
        int newBackTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontTarget = robot.motorFront.getCurrentPosition() + (int)(frontInches * COUNTS_PER_INCH);
            newBackTarget = robot.motorBack.getCurrentPosition() + (int)(backInches * COUNTS_PER_INCH);
            robot.motorFront.setTargetPosition(newFrontTarget);
            robot.motorBack.setTargetPosition(newBackTarget);

            // Turn On RUN_TO_POSITION
            robot.motorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.motorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.motorFront.setPower(Math.abs(speed));
            robot.motorBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.motorFront.isBusy() && robot.motorBack.isBusy())) {

                // Display it for the driver.
               /* telemetry.addData("Path1",  "Running to %7d :%7d", newFrontTarget,  newBackTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.motorFront.getCurrentPosition(),
                        robot.motorBack.getCurrentPosition());
                telemetry.update();*/
            }

            // Stop all motion;
            robot.motorFront.setPower(0);
            robot.motorBack.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.motorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    public void hookEncoder(double speed, double inches, double timeoutS) {
        int newHookTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newHookTarget = robot.motorHook.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH_HOOK);
            robot.motorHook.setTargetPosition(newHookTarget);

            // Turn On RUN_TO_POSITION
            robot.motorHook.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.motorHook.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.motorHook.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d", newHookTarget);
                telemetry.addData("Path2",  "Running at %7d",
                        robot.motorHook.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.motorHook.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.motorHook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void normalDrive(double lpower, double rpower) {

        if (opModeIsActive()) {
            robot.motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorLeft.setPower(lpower);
            robot.motorRight.setPower(rpower);
        }
    }
    public void gyroDrive(double target, String xyz, double power, double timeoutS)
    {
        //Write code to correct to a target position (NOT FINISHED)
        runtime.reset();
        double angle = readAngle(xyz); //variable for gyro correction around z axis
        double error = angle-target;
        double powerScaled = power*pidMultiplier(error);
        do{
            angle = readAngle(xyz);
            error = angle - target;
            powerScaled = power*pidMultiplier(error);
            /*telemetry.addData("error", error);
            telemetry.update();*/
            if(error > 0)
            {
                if(xyz.equals("z")) {
                    normalDrive(powerScaled, -powerScaled);
                }
                if(xyz.equals("y"))
                {
                    if (opModeIsActive()) {
                        robot.motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        robot.motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        robot.motorLeft.setPower(powerScaled);
                        robot.motorRight.setPower(powerScaled);
                    }
                }
            }
            else if(error < 0)
            {
                if(xyz.equals("z")) {
                    normalDrive(-powerScaled, powerScaled);
                }
                if(xyz.equals("y"))
                {
                    if (opModeIsActive()) {
                        robot.motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        robot.motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        robot.motorLeft.setPower(powerScaled);
                        robot.motorRight.setPower(powerScaled);
                    }
                }
            }
        }while(opModeIsActive() && ((error >1 ) || (error < -1)) && (runtime.seconds() < timeoutS));
        normalDrive(0,0);
    }
    public double pidMultiplier(double error){
        //equation for power multiplier is x/sqrt(x^2 + C)
        int C = 200;
        return Math.abs(error/Math.sqrt((error * error) + C));
    }
    public double readAngle(String xyz)
    {
        Orientation angles;
        Acceleration gravity;
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        if(xyz.equals("x")){
            return angles.thirdAngle;
        }else if(xyz.equals("y")){
            return angles.secondAngle;
        }else if(xyz.equals("z")){
            return angles.firstAngle;
        }else{
            return 0;
        }
    }

    public void releaseMarker()
    {
        robot.servoMark.setPosition(1);
        sleep(500);
        encoderDrive(DRIVE_SPEED, 5, 5, 5);
        robot.servoMark.setPosition(0);
    }
    public void MoveHookUp(boolean direction){
        double distance = 1.6;
        if(direction){
            hookEncoder(3, distance, 5);
        }else{
            hookEncoder(3, distance, 5);
        }
    }


}

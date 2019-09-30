package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@Autonomous(name="TestDrive", group="Zippo")
//@Disabled
public class TestDrive extends TestDriving {
    public void runOpMode()
    {
        /*
        STEPS:
        READ WHILE HANGING
        LAND
        MOVE OUT AND OF THE HOOK AND MOVE BACK CENTER
        KNOCK DOWN THE RIGHT MINERAL AND PARK
        */


        //inherent initialization from the ZoDriving class, this saves code redundancy
        super.runOpMode();

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();
        telemetry.addData("Z", readAngle("z"));
        telemetry.addData("y", readAngle("y"));
        telemetry.addData("x", readAngle("x"));
        telemetry.update();

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        //encoderDrive(DRIVE_SPEED,-24,-24,5);

        sleep(2000);

        //encoderDrive(DRIVE_SPEED,-32,-32,5);


            telemetry.addData("Z", readAngle("z"));
            telemetry.addData("y", readAngle("y"));
            telemetry.addData("x", readAngle("x"));
            telemetry.update();


            //encoderDrive(DRIVE_SPEED, -24, -24, 10);
            //gyroDrive(-90, xyz, -1.0, 7);
        correctionDrive(DRIVE_SPEED, -1.0, -48, -48, 20);
        //gyroDrive(-90, xyz,-1.0, 10);

        //correctionDrive(DRIVE_SPEED, -1.0, -24, -24, 10);

    }
}
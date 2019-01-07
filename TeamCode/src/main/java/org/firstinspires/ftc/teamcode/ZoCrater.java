package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ZoDriving;

@Autonomous(name="Crater", group="Zippo")
public class ZoCrater extends ZoDriving {
    public void runOpMode()
    {
        super.runOpMode();

        MoveHookUp(false);

        sleep(1000);

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        latEncoderDrive(.6,  -4,  -4, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        sleep(500);
        encoderDrive(DRIVE_SPEED, -15, -15, 5);
        String goldBlockPos = "right";

        if (goldBlockPos.equals("left"))
        {
            gyroDrive(30, xyz, 0.3, 5);
            encoderDrive(DRIVE_SPEED, -20, -20, 5);
            gyroDrive(135, xyz, 0.3, 5);
            double tdistance = 5;
            double cdistance = sensorRangeL.getDistance(DistanceUnit.INCH);
            latEncoderDrive(DRIVE_SPEED,-(cdistance-tdistance),-(cdistance-tdistance),3);
            encoderDrive(DRIVE_SPEED, -70, -70, 5);
        }

        else if(goldBlockPos.equals("middle"))
        {
            encoderDrive(DRIVE_SPEED, -20, -20, 5);
            encoderDrive(DRIVE_SPEED, 12, 12, 5);
            gyroDrive(90, xyz, 0.3, 5);
            encoderDrive(DRIVE_SPEED, -36, -36, 5);
            gyroDrive(135, xyz, 0.3, 5);
        }
        else
        {
            gyroDrive(-35, xyz, -0.3, 5);
            encoderDrive(DRIVE_SPEED, -27, -27, 5);
            encoderDrive(DRIVE_SPEED, 12, 12, 5);
            sleep(10000);
            gyroDrive(90, xyz, 0.3, 5);
            encoderDrive(DRIVE_SPEED, -40, -40, 5);
        }

        gyroDrive(135, xyz, 0.3, 5);
        double tdistance = 5;
        double cdistance = sensorRangeL.getDistance(DistanceUnit.INCH);
        latEncoderDrive(DRIVE_SPEED,-(cdistance-tdistance),-(cdistance-tdistance),3);
        encoderDrive(DRIVE_SPEED, -25, -25, 5);
        releaseMarker();
        gyroDrive(-45, xyz, 0.3, 5);
        encoderDrive(DRIVE_SPEED, -70, -70, 5);

    }
}
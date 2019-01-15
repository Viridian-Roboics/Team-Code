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
        //startt tensorflow
        tf.start();
        MoveHookUp(false);

        sleep(1000);

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        latEncoderDrive(.6,  -4,  -4, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        sleep(500);
        encoderDrive(DRIVE_SPEED, -5, -5, 5);
        latEncoderDrive(0.6, 2, 2, 5);

        //uncomment line after problem is resolved
        TensorFlow.MineralLocation goldMineralLocation = tf.getMineralLocation(TensorFlow.RobotOrientation.Left);
        //MineralLocation goldMineralLocation = MineralLocation.Left;
        sleep(500);
        latEncoderDrive(0.6, 2, 2, 5);

        if(goldMineralLocation == TensorFlow.MineralLocation.Left)
        {
            gyroDrive(20, xyz, -0.3, 5);
            encoderDrive(DRIVE_SPEED, -20, -20, 5);
            gyroDrive(-45, xyz, -0.3, 5);
        }

        else if(goldMineralLocation == TensorFlow.MineralLocation.Center)
        {
            encoderDrive(DRIVE_SPEED, -16, -16, 5);
        }

        else
        {
            gyroDrive(-30, xyz, -0.3, 5);
            encoderDrive(DRIVE_SPEED, -20, -20, 5);
        }

        gyroDrive(135, xyz, 0.3, 5);
        double tdistance = 5;
        double cdistance = sensorRangeR.getDistance(DistanceUnit.INCH);
        latEncoderDrive(DRIVE_SPEED,-(cdistance-tdistance),-(cdistance-tdistance),3);
        encoderDrive(DRIVE_SPEED, -25, -25, 5);
        releaseMarker();
        gyroDrive(-45, xyz, 0.3, 5);
        encoderDrive(DRIVE_SPEED, -70, -70, 5);

    }
}
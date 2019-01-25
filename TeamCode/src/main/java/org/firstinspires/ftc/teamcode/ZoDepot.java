package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.teamcode.TensorFlow.RobotOrientation;
import org.firstinspires.ftc.teamcode.TensorFlow.MineralLocation;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ZoDriving;

@Autonomous(name="Depot", group="Zippo")
public class ZoDepot extends ZoDriving {
    public void runOpMode()
    {
        super.runOpMode();
        //Start the custom tensorflow object which has already been creatd in the super class
        tf.start();
        //Pause for a second and read the mineral orientation while hanging
        sleep(1000);
        TensorFlow.MineralLocation goldMineralLocation = tf.getMineralLocation(TensorFlow.RobotOrientation.Left);
        sleep(250);
        //Lower the robot (A boolean decides the direction, passing false lowers the robot)
        MoveHookUp(false);
        sleep(250);
        telemetry.addData("Mineral Location: ", goldMineralLocation);
        telemetry.update();

        //Move out of the hook to the left, drive forward, and move back towards the center
        latEncoderDrive(.6,  -7,  -7, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(DRIVE_SPEED, -5, -5, 5);
        latEncoderDrive(0.6, 7, 7, 5);

        //uncomment line after problem is resolved
        //MineralLocation goldMineralLocation = MineralLocation.Left;

        if(goldMineralLocation == MineralLocation.Left)
        {
            gyroDrive(26, xyz, -0.5, 5);
            encoderDrive(DRIVE_SPEED, -43, -43, 5);
            //gyroDrive(-45, xyz, -0.5, 5);
            latEncoderDrive(.6,10,10,5);
            gyroDrive(45, xyz, -0.5, 5);
            //encoderDrive(DRIVE_SPEED, -20, -20, 5);
            releaseMarker();
        }

        else if(goldMineralLocation == MineralLocation.Center)
        {
            encoderDrive(DRIVE_SPEED, -50, -50, 5);
            releaseMarker();
            gyroDrive(45, xyz, -0.3, 5);

        }

        else
        {
            gyroDrive(-22, xyz, -0.5, 5);
            encoderDrive(DRIVE_SPEED, -38, -38, 5);
            gyroDrive(10, xyz, -0.3, 5);
            encoderDrive(DRIVE_SPEED, -45, -45, 5);
            releaseMarker();
            gyroDrive(45, xyz, -0.5, 5);
        }

        //align with wall
        double tdistance = 5;
        double cdistance = sensorRangeL.getDistance(DistanceUnit.INCH);
        latEncoderDrive(DRIVE_SPEED,-(cdistance-tdistance),-(cdistance-tdistance),3);
        sleep(250);

        //drive to crater
        encoderDrive(DRIVE_SPEED, 70, 70, 5);

        //extend arm into crater [ADD LATER WHEN WE HAVE ARM]

        //shutdown tensorflow (necessary)
        tf.shutdown();
    }
}

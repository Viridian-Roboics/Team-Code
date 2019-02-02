package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.DrawViewSource;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ZoDriving;
//butts
@Autonomous(name="CraterRegionals", group="Zippo")
public class ZoCraterRegionals extends ZoDriving {
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
        //WAIT 10000MS Pulkit is changing into his super suit
        //Start the custom tensorflow object which has already been creatd in the super class
        tf.start();
        //Pause for a second and read the mineral orientation while hanging
        sleep(1000);
        TensorFlow.MineralLocation goldMineralLocation = tf.getMineralLocation(TensorFlow.RobotOrientation.Left);
        sleep(250);
        tf.shutdown();
        //Lower the robot (A boolean decides the direction, passing false lowers the robot)
        MoveHookUp(false);
        sleep(250);
        telemetry.addData("Mineral Location: ", goldMineralLocation);
        telemetry.update();



        //Move out of the hook to the left, drive forward, and move back towards the center
        latEncoderDrive(.6,  -7,  -7, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(DRIVE_SPEED, -6, -6, 7);
        latEncoderDrive(0.6, 6.2, 6.2, 5);

        //The three if statements for handling logic depending on the mineral location
        if(goldMineralLocation == TensorFlow.MineralLocation.Left)
        {
            //PRAISE DAN LAWSON
            //Turn towards the mineral and drive forward
            gyroDrive(35, xyz, -0.5, 5); //turn towards mineral
            encoderDrive(DRIVE_SPEED, -20, -20, 5); //drive through mineral to wall
            latEncoderDrive(DRIVE_SPEED, -13, -13, 5);
            gyroDrive(45, xyz, -0.5, 5); //turn towards mineral
            while(robot.sensorRangeL.getDistance(DistanceUnit.INCH) > 7){
                encoderDrive(1,-4,-4,3);
            }
            latEncoderDrive(DRIVE_SPEED, -58, -58, 5);
        }
        //MOTORHEADS >>>> VIRIDIAN
        else if(goldMineralLocation == TensorFlow.MineralLocation.Center)
        {
            //drive straight and park if center
            encoderDrive(DRIVE_SPEED, -25, -25, 7); //forward and hit mineral
            encoderDrive(DRIVE_SPEED, 10, 10, 7);
            latEncoderDrive(DRIVE_SPEED, -20, -20, 5);
            gyroDrive(90, xyz, -0.5, 5); //turn towards mineral
            while(robot.sensorRangeR.getDistance(DistanceUnit.INCH) > 7){
                encoderDrive(1,-4,-4,3);
            }
            latEncoderDrive(DRIVE_SPEED, -58, -58, 5);
            //encoderDrive(DRIVE_SPEED, 15, 15, 5); //backwards back to starting position
            //gyroDrive(20, xyz, -0.3, 5); //turn to wall
            //encoderDrive(DRIVE_SPEED, -50, -50, 5); //drive to wall
        }
        else
        {
            //turn left and park if left
            gyroDrive(-23, xyz, -0.5, 5); //turn to mineral
            encoderDrive(DRIVE_SPEED, -40, -40, 7); //hit mineral
            //encoderDrive(DRIVE_SPEED, 15, 15, 5); //backwards back to starting position
            //gyroDrive(120, xyz, -0.3, 5); //turn to wall
            //encoderDrive(DRIVE_SPEED, -45, -45, 5);
        }
        releaseMarker();
        latEncoderDrive(DRIVE_SPEED, 70, 70, 5);
        /*gyroDrive(135, xyz, 0.3, 5); //turn towards depot
        encoderDrive(DRIVE_SPEED, 60, 60, 5); //drive to depot

        double tdistance = 5;
        double cdistance = sensorRangeR.getDistance(DistanceUnit.INCH);
        latEncoderDrive(DRIVE_SPEED,-(cdistance-tdistance),-(cdistance-tdistance),3);

        encoderDrive(DRIVE_SPEED, -25, -25, 5);
        releaseMarker();
        gyroDrive(-45, xyz, 0.3, 5);
        encoderDrive(DRIVE_SPEED, -70, -70, 5);*/

    }
}
//Daniel Lawson is the one true god
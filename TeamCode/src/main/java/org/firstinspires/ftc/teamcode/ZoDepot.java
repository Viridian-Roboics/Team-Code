package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ZoDriving;

@Autonomous(name="Depot", group="Zippo")
public class ZoDepot extends ZoDriving {
    public void runOpMode()
    {
        super.runOpMode();
        Rev2mDistanceSensor sensorRangeR = hardwareMap.get(Rev2mDistanceSensor.class, "sensorRangeR");
        Rev2mDistanceSensor sensorRangeL = hardwareMap.get(Rev2mDistanceSensor.class, "sensorRangeL");

        hookEncoder(3, -.81, 5);

        sleep(1000);

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        latEncoderDrive(.6,  -4,  -4, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        sleep(500);
        encoderDrive(DRIVE_SPEED, -5, -5, 5);
        latEncoderDrive(0.6, 2, 2, 5);


        String goldBlockPos = "right";
        /*
            change this later when sampling works. Sampling should return either String
            "left", "middle", or "right" depending on where it determines the gold block
            is positioned.
         */
        sleep(10000);
        latEncoderDrive(0.6, 2, 2, 5);
        if(goldBlockPos.equals("left"))
        {
            gyroDrive(20, xyz, -0.3, 5);
            encoderDrive(DRIVE_SPEED, -48, -48, 5);
            gyroDrive(-45, xyz, -0.3, 5);
            encoderDrive(DRIVE_SPEED, -20, -20, 5);
            releaseMarker();
        }

        else if(goldBlockPos.equals("middle"))
        {
            encoderDrive(DRIVE_SPEED, -50, -50, 5);
            releaseMarker();
            gyroDrive(-45, xyz, -0.3, 5);

        }

        else
        {
            gyroDrive(-30, xyz, -0.3, 5);
            encoderDrive(DRIVE_SPEED, -40, -40, 5);
            gyroDrive(10, xyz, -0.3, 5);
            encoderDrive(DRIVE_SPEED, -45, -45, 5);
            gyroDrive(-45, xyz, -0.3, 5);
            releaseMarker();
        }

        //align with wall
        double tdistance = 5;
        double cdistance = sensorRangeL.getDistance(DistanceUnit.INCH);
        latEncoderDrive(DRIVE_SPEED,-(cdistance-tdistance),-(cdistance-tdistance),3);
        sleep(250);

        //drive to crater
        encoderDrive(DRIVE_SPEED, 70, 70, 5);

        //extend arm into crater [ADD LATER WHEN WE HAVE ARM]

    }
}

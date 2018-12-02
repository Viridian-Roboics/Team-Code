package org.firstinspires.ftc.teamcode.MachineLearningTest;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.*;
import org.opencv.core.MatOfDouble;

public class Test {
    public static void main(String[] args){
        Mat matrix = new Mat(3, 3, CvType.CV_64FC1 );
        matrix.put(0 ,0, 0, -1, 0, -1, 5, -1, 0, -1, 0 );

    }
}

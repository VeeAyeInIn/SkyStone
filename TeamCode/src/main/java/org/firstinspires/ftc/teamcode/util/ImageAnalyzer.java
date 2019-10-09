package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

public class ImageAnalyzer {

    private ImageAnalyzer() {
    } // No instances allowed

    public static Analysis analyze(final Recognition recognition) {
        return new Analysis(recognition);
    }

    private static class Analysis {

        private final Recognition recognition;

        private Analysis(final Recognition recognition) {
            this.recognition = recognition;
        }

        public double getImageSize() {
            return recognition.getImageHeight() * recognition.getImageWidth();
        }

        public double getSize() {
            return recognition.getHeight() * recognition.getWidth();
        }

        public double getRatio() {
            return getSize() / getImageSize();
        }
    }
}

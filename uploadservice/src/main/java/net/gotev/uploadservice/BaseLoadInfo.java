package net.gotev.uploadservice;

import java.util.ArrayList;
import java.util.BitSet;

public interface BaseLoadInfo {
     CharSequence getElapsedTimeString();

     int getProgressPercent();

     CharSequence getUploadRateString();

    ArrayList<String> getSuccessfullyUploadedFiles();

     int getTotalFiles();
}

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/* TODO
   - turn off redFinder when no longer needed.
 */

/* CHANGELIST
 * Change start location
 *
 * */

@Config
@Autonomous(name = "RedRight", group = "robot", preselectTeleOp = "FluffyTeleOp")
public class RedRight extends LinearOpMode {
    //BRING GRABBER UP FIRST THING//
    AutoFluffy fluffy;
    String PATH;
    String SIDE = "Right";
    static double DELTA = 1;
    List<Recognition> currentRecognitions;

    final Pose2d RR_START = new Pose2d(new Vector2d(16.2,-63.2), Math.toRadians(90));
    final Pose2d RR_CENTER_PROP_PUSH = new Pose2d(new Vector2d(14.2,-27.95), Math.toRadians(0.1));
    final Pose2d RR_CENTER_PURPLE_BACKUP = new Pose2d(new Vector2d(14.2,-34.45), Math.toRadians(0.1));
    final Pose2d RR_CENTER_YELLOW_PREP = new Pose2d(new Vector2d(16.7,-36.7), Math.toRadians(0.1));
    final Pose2d RR_READ_YELLOW_CENTER = new Pose2d(new Vector2d(40.2,-36.2), Math.toRadians(0.1));
    final Pose2d RR_RIGHT_PROP_PUSH = new Pose2d(new Vector2d(22.95,-40.2), Math.toRadians(0.1));
    final Pose2d RR_RIGHT_YELLOW_PREP = new Pose2d(new Vector2d(21.2,-45.2), Math.toRadians(0.1));
    final Pose2d RR_READ_YELLOW_RIGHT = new Pose2d(new Vector2d(40.2,-40.8), Math.toRadians(0.1));
    final Pose2d RR_LEFT_PROP_PUSH = new Pose2d(new Vector2d(16.2,-42.7), Math.toRadians(90));
    final Pose2d RR_LEFT_PURPLE_BACKUP = new Pose2d(new Vector2d(7.7,-33.7), Math.toRadians(90));
    final Pose2d RR_READ_YELLOW_LEFT = new Pose2d(new Vector2d(40.2,-28.8), Math.toRadians(0.1));
    final Pose2d RR_PARK_BACKUP = new Pose2d(new Vector2d(37.2,-35.9), Math.toRadians(0.1));
    final Pose2d RR_PARK_FINAL_LEFT = new Pose2d(new Vector2d(50.2,-12), Math.toRadians(0.1));
    final Pose2d RR_PARK_FINAL_CENTER = new Pose2d(new Vector2d(46.7,-36), Math.toRadians(0.1));
    final Pose2d RR_PARK_FINAL_RIGHT = new Pose2d(new Vector2d(50.2,-60.2), Math.toRadians(0.1));

    Menu initMenu = new Menu(this);
    public void runOpMode(){
        initialize();

        initMenu.add(new StringMenuItem("Park Loc", new ArrayList<String> (Arrays.asList("L", "C", "R")), 0));
        initMenu.add(new StringMenuItem("Pixel Pos", new ArrayList<String> (Arrays.asList("L","R")),0));
        initMenu.add(new DoubleMenuItem("Wait Time (0-12)", 0,12,0,1));

        while(!isStarted() && !isStopRequested()){
            
            PATH= fluffy.getPropLocation();
            initMenu.update();
            initMenu.display();
            telemetry.addData("Prop Location", PATH );
            telemetry.addData("Left Sat. Value", fluffy.getLeftMean());
            telemetry.addData("Center Sat. Value", fluffy.getCenterMean());
            telemetry.addData("Right Sat. Value", fluffy.getRightMean());

            telemetry.update();


        }

        sleep((long)((DoubleMenuItem)initMenu.getItem(2)).getDoubleValue()*1000);
        // JRC: Turn off redFinder at this point.

        fluffy.drive.pose = RR_START;
        if(PATH .equals("Left")){
            deliverPurpleLeft();
            yellowLeft();
        } else if(PATH .equals("Right")){
            deliverPurpleRight();
            yellowRight();
        }else{
            deliverPurpleCenter();
            yellowCenter();
        }

        deliverYellow();
        park();
        RobotLog.i(String.format("Final position %f,%f",fluffy.drive.pose.position.x, fluffy.drive.pose.position.y));
    }


    public void initialize(){
        fluffy = new AutoFluffy(this, "Red");
        fluffy.raiseGrabber();
    }

    public void deliverPurpleLeft(){
        Actions.runBlocking(
           fluffy.drive.actionBuilder(fluffy.drive.pose)
                   //.splineTo(new Vector2d(29.5,9.23), .21)
                   .strafeTo(RR_LEFT_PROP_PUSH.position)   // .lineToX(WHATEVERPOSE.position.x)
                   .strafeTo(RR_LEFT_PURPLE_BACKUP.position)  // .strafeTo(WHATEVERPOSE.position)
                   .build());
        fluffy.deliverPurple();

    }
    public void deliverPurpleCenter(){
        Actions.runBlocking(
                fluffy.drive.actionBuilder(fluffy.drive.pose)
                        //.splineTo(new Vector2d(27.93, -10.81), -91.4)
                        //.lineToX(29)
                        .strafeToLinearHeading(RR_CENTER_PROP_PUSH.position, RR_CENTER_PROP_PUSH.heading)
                        .strafeTo(RR_CENTER_PURPLE_BACKUP.position)
                        //.turnTo(Math.toRadians(-91))
                        .build());
        fluffy.deliverPurple();
    }
    public void deliverPurpleRight(){
        Actions.runBlocking(
                fluffy.drive.actionBuilder(fluffy.drive.pose)
                        .strafeToLinearHeading(RR_RIGHT_PROP_PUSH.position, RR_RIGHT_PROP_PUSH.heading)
                        .build());
        fluffy.deliverPurple();
    }

    public void yellowLeft(){
        /* JRC: To read position, use
         * Don't forget to handle the null case gracefully!
         */
        Actions.runBlocking(
              fluffy.drive.actionBuilder(fluffy.drive.pose)
                      .strafeToLinearHeading(RR_READ_YELLOW_LEFT.position, RR_READ_YELLOW_LEFT.heading)
                      .build());
        fluffy.retractPurple();
    }
    public void yellowCenter(){
        Actions.runBlocking(
                fluffy.drive.actionBuilder(fluffy.drive.pose)
                        .strafeTo(RR_CENTER_YELLOW_PREP.position)
                        .strafeTo(RR_READ_YELLOW_CENTER.position)
                        .build());
        fluffy.retractPurple();
    }
    public void yellowRight(){
        Actions.runBlocking(
                fluffy.drive.actionBuilder(fluffy.drive.pose)
                        .strafeTo(RR_RIGHT_YELLOW_PREP.position)
                        .strafeTo(RR_READ_YELLOW_RIGHT.position)
                        .build());
        fluffy.retractPurple();
    }


    public void deliverYellow(){
        Vector2d destination; //need to fix (offset)
        sleep(1000);
        fluffy.drive.pose = fluffy.getPoseFromAprilTag();
        if (PATH.equals("Left")){
            destination = fluffy.tagPositions[3].plus(fluffy.DELIVERY_OFFSET);
        }
        else if (PATH.equals("Center")){
            destination = fluffy.tagPositions[4].plus(fluffy.DELIVERY_OFFSET);
        }
        else{
            destination = fluffy.tagPositions[5].plus(fluffy.DELIVERY_OFFSET);
        }
        if (((StringMenuItem)initMenu.getItem(1)).getStringValue().equals("L")){
            destination = destination.plus(new Vector2d(0,4.5));
        }
        fluffy.raiseLift();
        Actions.runBlocking(
                fluffy.drive.actionBuilder(fluffy.drive.pose)
                        .strafeTo(destination)
                        //.turnTo(fluffy.drive.pose.heading)
                        .build());
        fluffy.raiseFinger();
        Actions.runBlocking(
                fluffy.drive.actionBuilder(fluffy.drive.pose)
                        .lineToX(fluffy.drive.pose.position.x - 1)
                        .build());
    }
    //}
    public void park(){
        Actions.runBlocking(
                fluffy.drive.actionBuilder(fluffy.drive.pose)
                        .lineToX(RR_PARK_BACKUP.position.x)
                        .build());
        fluffy.lowerLift();
        String s = ((StringMenuItem)initMenu.getItem(0)).getStringValue();
        if (s.equals("L")){
            Actions.runBlocking(
                    fluffy.drive.actionBuilder(fluffy.drive.pose)
                            .strafeTo(RR_PARK_FINAL_LEFT.position)
                            .build());
        }
        else if (s.equals("C")){
            Actions.runBlocking(
                    fluffy.drive.actionBuilder(fluffy.drive.pose)
                            .strafeTo(RR_PARK_FINAL_CENTER.position)
                            .build());
        }
        else {
            Actions.runBlocking(
                    fluffy.drive.actionBuilder(fluffy.drive.pose)
                            .strafeTo(RR_PARK_FINAL_RIGHT.position)
                            .build());
        }

    }

}

package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.TankDrive;
@Autonomous(name="SplineTest2", group="testing")
public final class SplineTest2 extends LinearOpMode {
    ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

            waitForStart();
            timer.reset();
            RobotLog.i("Start trajectory build: " + timer.milliseconds());

            Action t = drive.actionBuilder(beginPose)
                    .splineTo(new Vector2d(30, 30), Math.PI / 2)
                    .splineTo(new Vector2d(60, 0), Math.PI)
                    //.strafeTo(new Vector2d(0, 24))
                    .build();
            RobotLog.i("End trajectory build: " + timer.milliseconds());
            drive.pose = new Pose2d(1,0,0.05);  // relocalization!
            Actions.runBlocking(t);

        } else {
            throw new RuntimeException();
        }
    }

    public class LogAction implements Action {
        @Override
        public boolean run(TelemetryPacket t) {
            RobotLog.i("End Time: ", timer.milliseconds());
            return false;
        }
    }

    public Action logAction() {
        return new LogAction();
    }
}

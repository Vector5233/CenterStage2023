package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.checkerframework.checker.units.qual.A;
@Autonomous()
public class ActionsTest extends LinearOpMode {

    public static int LIFT_POSITION = 400;
    AutoFluffy fluffy;
    Action trajectory1, trajectory2, trajectory3;
    Action raiseLift = new RaiseLift();
    public void runOpMode() {
        initialize();
        waitForStart();
        Actions.runBlocking(trajectory1);
        //Actions.runBlocking(trajectory2);
        Actions.runBlocking(new ParallelAction(new RaiseLift2(),
                trajectory3));
        /* This third action does not execute properly */
    }

    public class RaiseLift implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket t) {
            fluffy.sendLiftToPosition(LIFT_POSITION);
            return false;
        }
    }

    public class RaiseLift2 implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket t) {
            if (fluffy.getLiftPosition() < LIFT_POSITION) {
                fluffy.setLiftPower(0.3);
                return true;
            }
            else {
                fluffy.setLiftPower(0.0);
                return false;
            }
        }
    }

    public void initialize() {
        fluffy = new AutoFluffy(this);
        fluffy.drive.pose = new Pose2d(0,0,0);
        trajectory1 = fluffy.drive.actionBuilder(fluffy.drive.pose)
                .lineToX(48)
                .afterDisp(24, new InstantAction(() -> fluffy.raiseFinger()))
                .build();
        trajectory2 = fluffy.drive.actionBuilder(new Pose2d(48,0,0))   // NOTE: must be continuous with
                // projected location or bot will first try to drive to given pose ^^^
                .strafeTo(new Vector2d(48,6))
                .afterTime(1.0, new InstantAction(()-> fluffy.raiseLift()))
                .waitSeconds(1)
                .build();
        trajectory3 = fluffy.drive.actionBuilder(new Pose2d(48,0,0))
                .strafeTo(new Vector2d(48,18))
                .build();

    }
}

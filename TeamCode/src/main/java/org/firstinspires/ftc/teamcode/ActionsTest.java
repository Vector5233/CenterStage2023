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
import com.qualcomm.robotcore.hardware.DcMotor;


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
        Actions.runBlocking(new RaiseLift(500));
        sleep(3000);
        Actions.runBlocking(new RaiseLift(0, 0.3));

    }

    public class RaiseLift implements Action {
        int targetPosition = LIFT_POSITION;   // == 400 ticks
        double power = fluffy.LIFT_POWER;
        public RaiseLift() {   // allow for default value
        }

        public RaiseLift(int target) {
            this.targetPosition = target;
        }

        public RaiseLift(int target, double power) {
            this.targetPosition = target;
            this.power = power;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket t) {
            boolean initialized = false;
            if (!initialized) {
                initialized = true;
                fluffy.sendLiftToPosition(targetPosition, power);
            }
            t.put("Lift position: ", fluffy.getLiftPosition());
            t.put("Target: ", targetPosition);
            telemetry.update();
            return fluffy.isLiftBusy();
        }


    }



    public void initialize() {
        fluffy = new AutoFluffy(this);
        fluffy.drive.pose = new Pose2d(0,0,0);


    }
}

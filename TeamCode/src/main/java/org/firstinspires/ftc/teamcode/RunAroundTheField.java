package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous
public class RunAroundTheField extends LinearOpMode {
    AutoFluffy fluffy;
    public void runOpMode() throws InterruptedException {
        fluffy = new AutoFluffy(this);
        waitForStart();
        //Actions.runBlocking(
        //        drive.actionBuilder.build();



        //);



    }


}

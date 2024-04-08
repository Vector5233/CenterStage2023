package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class IntakeTest extends OpMode {
    ActiveIntake  intake;
    public double forward;
    public double strafe;
    public double turn;
    public void init(){
        intake = new ActiveIntake(this);
    }
    public void loop(){
        setIntakeDrive();
        if(gamepad1.right_bumper){
            intake.collect();
        } else if(gamepad1.left_bumper){
            intake.eject();
        } else {
            intake.stop();
        }

    }

    public void setIntakeDrive(){
        if (gamepad1.left_bumper){
            forward = -gamepad1.left_stick_y*.25;
            strafe = gamepad1.left_stick_x*.25;
            turn = .75*gamepad1.right_stick_x*.5;
        }else{
            forward = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            turn = .75*gamepad1.right_stick_x;
        }
        intake.setDrive(forward, strafe, turn);
    }
}

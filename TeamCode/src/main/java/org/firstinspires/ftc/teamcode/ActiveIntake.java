package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


public class ActiveIntake {
    OpMode op;
    DcMotor intakeMotor, leftFront, leftBack, rightFront, rightBack;
    public static double COLLECT_POWER = 1;
    public static double EJECT_POWER = -0.5;
    public static double STOP_POWER = 0;
    public static double THRESHOLD = .15;
    public ActiveIntake(OpMode op) {
        this.op = op;
        this.init();
    }
    public void init(){
        intakeMotor = op.hardwareMap.dcMotor.get("intakeMotor");

        leftFront = op.hardwareMap.dcMotor.get("leftFront");
        leftBack = op.hardwareMap.dcMotor.get("leftBack");
        rightFront = op.hardwareMap.dcMotor.get("rightFront");
        rightBack = op.hardwareMap.dcMotor.get("rightBack");

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);

        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    public void collect(){
        intakeMotor.setPower(COLLECT_POWER);
    }
    public void eject(){
        intakeMotor.setPower(EJECT_POWER);
    }
    public void stop(){
        intakeMotor.setPower(STOP_POWER);
    }
    public void setDrive(double forward, double strafe, double turn) {
        double leftFrontPower = trimPower(forward + strafe + turn);
        double rightFrontPower = trimPower(forward - strafe - turn);
        double leftBackPower = trimPower(forward - strafe + turn);
        double rightBackPower = trimPower(forward + strafe - turn);
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);
    }
    public double trimPower(double Power) {
        if (Math.abs(Power) < THRESHOLD) {
            Power = 0;
        }
        return Power;
    }
}

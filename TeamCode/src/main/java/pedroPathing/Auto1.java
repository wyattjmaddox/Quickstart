package pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous
public class Auto1 extends LinearOpMode {

    Follower follower;
    Timer pathTimer, actionTimer, opModeTimer;

    int pathState;

    private final Pose startPose = new Pose(9, 15, Math.toRadians(0));
    private final Pose endPose = new Pose(100, 15, Math.toRadians(0));

    private PathChain Move1;

    public void buildPaths() {
        Move1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(endPose)))
                .setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading())
                .build();
    }

    public void autonomousPathUpdate(){
        switch (pathState){
            case 0:
                follower.followPath(Move1);
                break;
        }
        break;
    }

    public void setPathState(int pState){
        pathState = pState;
        pathTimer.resetTimer();
    }



    @Override
    public void runOpMode(){

        waitForStart();
        pathTimer = new Timer();
        opModeTimer = new Timer();
        opModeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();

        while (opModeIsActive()){

            follower.update();
            autonomousPathUpdate();
        }
    }
}

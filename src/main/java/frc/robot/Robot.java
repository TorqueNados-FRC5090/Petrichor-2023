package frc.robot;

// Import Constants
import static frc.robot.Constants.ArmIDs.ROTATION_FOLLOWER_ID;
import static frc.robot.Constants.ArmIDs.ROTATION_ID;
import static frc.robot.Constants.ArmIDs.SLIDER_ID;
import static frc.robot.Constants.ArmIDs.TELESCOPE_FOLLOWER_ID;
import static frc.robot.Constants.ArmIDs.TELESCOPE_ID;
import static frc.robot.Constants.ControllerPorts.OPERATOR_PORT;

// Camera imports
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
// Command imports
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
// Misc imports
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.ArmConstants.ArmState;
import frc.robot.misc_subclasses.Dashboard;
import frc.robot.misc_subclasses.Limelight;
// Subsystem and subclass imports
import frc.robot.subsystems.Arm;

public class Robot extends TimedRobot {
    private RobotContainer robotContainer;

    // Commands
    private Command autonCommand;

    // Subsystem and subclass objects
    private Arm arm;
    private Dashboard dashboard;
    private Limelight limelight;
    
    // Other objects
    private XboxController operatorController;
    private Compressor compressor;
    private double autonStartTime;

    // Used to test the arm
    private double rotationPos;
    private double sliderPos;
    private double telescopePos;
    private int prev;
    private int i;

    // This function is run when the robot is first started up and should be used
    // for any initialization code.
    @Override
    public void robotInit() {
        // Start the camera feed
        CameraServer.startAutomaticCapture();

        // Construct objects
        robotContainer = new RobotContainer();
        operatorController = new XboxController(OPERATOR_PORT);
        arm = new Arm(ROTATION_ID, ROTATION_FOLLOWER_ID, TELESCOPE_ID, TELESCOPE_FOLLOWER_ID, SLIDER_ID);
        limelight = new Limelight();
        dashboard = new Dashboard();
        compressor = new Compressor(PneumaticsModuleType.CTREPCM);
        
    }

    // This function is called once at the start of auton
    @Override
    public void autonomousInit() {
        // Get the command to be used in auton
        autonCommand = robotContainer.getAutonomousCommand();
        autonStartTime = Timer.getFPGATimestamp();
        // Schedule the command if there is one
        if (autonCommand != null)
            autonCommand.schedule();

        arm.goTo(ArmState.DROPOFF_MED);
    }

    // This function is called every 20ms during auton
    @Override
    public void autonomousPeriodic() { 
        double currentTime = Timer.getFPGATimestamp() - autonStartTime;
        if (currentTime > 3 && currentTime < 3.2)
            robotContainer.getClaw().open();

        if (currentTime > 4 && currentTime < 4.2)
            arm.goTo(ArmState.ZERO);
    }
    
    // This function is called once at the start of teleop
    @Override
    public void teleopInit() {
        // This makes sure that the autonomous command stops when teleop starts
        if (autonCommand != null)
            autonCommand.cancel();

        // Start the compressor
        compressor.enableDigital();
    }

    // This function is called every 20ms during teleop
    @Override
    public void teleopPeriodic() {
        robotContainer.teleopPeriodic();

        // Pressing Right bumper makes the arm go to the preset of the drop of high position
        if (operatorController.getRightBumper())
            arm.goTo(ArmState.DROPOFF_HIGH);

        // Pressing Y makes the arm go to the preset of the drop of medium position
        if (operatorController.getYButtonPressed())
            arm.goTo(ArmState.DROPOFF_MED);

        // Pressing Left trigger makes the arm go to the preset of the floor pickup position
        if (operatorController.getXButtonPressed())
            arm.goTo(ArmState.INTERMEDIATE);

        // Pressing A makes the arm go to the preset of the zero position
        if (operatorController.getAButtonPressed())
            arm.goTo(ArmState.ZERO);

        // Pressing B makes the arm go to the preset of the balance position
        if (operatorController.getBButtonPressed())
            arm.goTo(ArmState.BALANCE);

        // Pressing Left bumper makes the arm go to the preset of the human pickup position
        if (operatorController.getLeftBumperPressed())
            arm.goTo(ArmState.PICKUP_HUMAN);
        
        // Pressing Left trigger makes the arm go to the preset of the floor pickup position
        if (operatorController.getLeftTriggerAxis() > 0)
            arm.goTo(ArmState.PICKUP_FLOOR);

    }

    // This function is called every 20ms while the robot is enabled
    @Override
    public void robotPeriodic() {    
        // Print data to the dashboard
        dashboard.printLimelightData(limelight);
        dashboard.printBasicDrivetrainData(robotContainer.getDrivetrain());
        dashboard.printArmData(arm);

        // Run any functions that always need to be running
        CommandScheduler.getInstance().run();
        limelight.updateLimelightTracking();
    }

    @Override
    public void testPeriodic() {
        if(operatorController.getAButton())
            arm.getSliderMotor().set(.15);
        else if(operatorController.getBButton())
            arm.getSliderMotor().set(-.15);
        else
            arm.getSliderMotor().set(0); 

        if(operatorController.getXButton())
            arm.getTelescopeMotor().set(.1);
        else if(operatorController.getYButton())
            arm.getTelescopeMotor().set(-.1);
        else
            arm.getTelescopeMotor().set(0); 

        if(operatorController.getRightBumper())
            arm.getRotationMotor().set(.05);
        else if(operatorController.getRightTriggerAxis() > .5)
            arm.getRotationMotor().set(-.05);
        else
            arm.getRotationMotor().set(0); 
    }




/* --------------- OLD TEST MODE --------------- */
    /*

    //----------- test functionality below -----------
    // This function is called once at the start of a test
    @Override
    public void testInit() {
        // Init values for arm testing
        rotationPos = 0;
        telescopePos = 0;
        sliderPos = 0;
        i = 0;
        prev = -1;
    }

    // This function is called every 20ms while testing
    @Override
    public void testPeriodic() {
        // Get the state of the dpad
        int curr = operatorController.getPOV();
    
        // Arm's telescope is controlled by the bumpers
        if (operatorController.getLeftBumperPressed())
            arm.telescopeGoTo(--telescopePos);
        else if (operatorController.getRightBumperPressed())
            arm.telescopeGoTo(++telescopePos);

        // Arm's slider is controlled by left and right dpad
        if (curr == 270 && curr != prev) {
            sliderPos -= .5;
            arm.sliderGoTo(sliderPos);
        }
        else if (curr == 90 && curr != prev){
            sliderPos += .5;
            arm.sliderGoTo(sliderPos);
        }
        
        // Arm's rotation is controlled by up and down dpad
        if (curr == 180 && curr != prev)
            arm.rotationGoTo(--rotationPos);
        else if (curr == 0 && curr != prev)
            arm.rotationGoTo(++rotationPos);

        // Pressing B resets the arm to where it was when the robot was powered on
        if (operatorController.getBButtonPressed())
            arm.goTo(ArmState.ZERO);

        // Testing configuration button
        if (operatorController.getAButtonPressed()) {
            //arm.rotationGoTo(50);
            //rotationPos = 50;
            arm.goTo(ArmState.PICKUP_FLOOR);
        }

        // Control the claw in test mode
        if (operatorController.getStartButtonPressed())
            robotContainer.getClaw().open();
        else if (operatorController.getBackButtonPressed())
            robotContainer.getClaw().close();

        // Print the arm positions every 2 sec
        if(i == 100) {
            System.out.println( "Rotation Position : " + arm.getRotationPid().getRatioPos());
            System.out.println( "Telescope Position : " + arm.getTelescopePid().getRatioPos());
            System.out.println( "Slider Position : " + arm.getSliderPid().getRatioPos());
            i = 0;
        }
        i++;
        
        // The dpad's previous value is updated for debounce
        prev = curr; 
    }
    */
}
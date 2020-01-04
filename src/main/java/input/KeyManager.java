package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class KeyManager implements KeyListener {

    public boolean P1_up, P1_down, P1_left, P1_right, P1_force, P1_dirLeft, P1_dirRight, P1_rotLeft, P1_rotRight, P1_cancellation, P1_sprint, P1_dropOrCallBall,
            P2_up, P2_down, P2_left, P2_right, P2_force, P2_dirLeft, P2_dirRight, P2_rotLeft, P2_rotRight, P2_cancellation, P2_sprint, P2_dropOrCallBall;
    private boolean[] keys;
    private boolean releaseFlagP1Up, releaseFlagP1Left, releaseFlagP1RT, releaseFlagP2Up, releaseFlagP2Left, releaseFlagP2RT;
    public static Controller controller1, controller2;
    private float controller1axisX, controller1axisY, controller2axisX, controller2axisY;
    public static float deadZone1 = 0.23f, deadZone2 = 0.23f;

    public KeyManager() {
        keys = new boolean[256];
        try {
            Controllers.create();
        } catch (LWJGLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println("number of all recognized controllers (Xbox and non-Xbox): "+Controllers.getControllerCount());
        recognizeControllers();
    }

    public void recognizeControllers() {
        Controllers.poll();
        if (Controllers.getControllerCount() > 0) {
            for (int i = 1; i < Controllers.getControllerCount(); i++) {
                if (Controllers.getController(i).getName().startsWith("Controller", 0)) {
                    if ((controller1 != null) && (controller2 == null)) {
                        controller2 = Controllers.getController(i);
                    }
                    if (controller1 == null) {
                        controller1 = Controllers.getController(i);
                    }
                }
            }

        }
    }

    public void tick() {

        Controllers.clearEvents();
        Controllers.destroy();
        controller1 = null;
        controller2 = null;
        recognizeControllers();
        //System.out.println("number of all recognized controllers (Xbox and non-Xbox): "+Controllers.getControllerCount());

        //controller1
        P1_up = keys[KeyEvent.VK_W];
        P1_down = keys[KeyEvent.VK_S];
        P1_left = keys[KeyEvent.VK_A];
        P1_right = keys[KeyEvent.VK_D];
        P1_force = keys[KeyEvent.VK_V];
        P1_dirLeft = keys[KeyEvent.VK_T];
        P1_dirRight = keys[KeyEvent.VK_Y];
        P1_rotLeft = keys[KeyEvent.VK_G];
        P1_rotRight = keys[KeyEvent.VK_H];
        P1_cancellation = keys[KeyEvent.VK_C];
        P1_sprint = keys[KeyEvent.VK_Q];
        P1_dropOrCallBall = keys[KeyEvent.VK_E];

        if (controller1 != null) {
            controller1axisY = 0;
            controller1axisX = 0;

            //using flags to skip default -1 value of Xbox pad axes
            //P1 moving up
            if ((controller1.getAxisValue(0) != -1) && (controller1.getPovY() != -1)) {
                releaseFlagP1Up = true;
            }
            if (releaseFlagP1Up == true) {
                if ((controller1.getAxisValue(0) < -deadZone1)) {
                    controller1axisY = controller1.getAxisValue(0); //Left analog
                }
                if ((controller1.getPovY() < -deadZone1)) {
                    P1_up = true; //D-Pad
                }
            }
            if (keys[KeyEvent.VK_W]) {
                P1_up = true; //keyboard
            }
            //P1 moving down
            if ((controller1.getPovY() > deadZone1) || keys[KeyEvent.VK_S]) {
                P1_down = true; ///D-Pad / keyboard
            }
            if (controller1.getAxisValue(0) > deadZone1) {
                controller1axisY = controller1.getAxisValue(0); //Left analog
            }
            //System.out.println("Pad1: LA Y value: "+controller1.getAxisValue(0));
            //System.out.println("Pad1: D-Pad Y value: "+controller1.getPovY());
            //P1 moving left
            if ((controller1.getAxisValue(1) != -1) && (controller1.getPovX() != -1)) {
                releaseFlagP1Left = true;
            }
            if (releaseFlagP1Left == true) {
                if (controller1.getAxisValue(1) < -deadZone1) {
                    controller1axisX = controller1.getAxisValue(1);
                }
                if (controller1.getPovX() < -deadZone1) {
                    P1_left = true; //D-Pad
                }
            }
            if (keys[KeyEvent.VK_A]) {
                P1_left = true; //keyboard
            }
            //P1 moving right
            if ((controller1.getPovX() > deadZone1) || keys[KeyEvent.VK_D]) {
                P1_right = true; //D-Pad / keyboard
            }
            if (releaseFlagP1Left == true) {
                controller1axisX = controller1.getAxisValue(1); //to make sure default -1 won't interfere with movement
            }

            //System.out.println("Pad1: LA X value: "+controller1.getAxisValue(1));
            //System.out.println("Pad1: D-Pad X value: "+controller1.getPovX());
            P1_force = (controller1.isButtonPressed(1) || keys[KeyEvent.VK_V]); //button B
            //System.out.println("Pad1: X button value: "+controller1.isButtonPressed(2));
            P1_cancellation = (controller1.isButtonPressed(0) || keys[KeyEvent.VK_B]); //button A
            //System.out.println("Pad1: A button value: "+controller1.isButtonPressed(0));
            P1_rotLeft = (controller1.isButtonPressed(4) || keys[KeyEvent.VK_G]); //button LB
            //System.out.println("Pad1: LB button value: "+controller1.isButtonPressed(4));
            P1_rotRight = (controller1.isButtonPressed(5) || keys[KeyEvent.VK_H]); //button RB
            //System.out.println("Pad1: RB button value: "+controller1.isButtonPressed(5));
            P1_sprint = (controller1.isButtonPressed(3) || keys[KeyEvent.VK_Q]); //button Y
            //System.out.println("Pad1: Y button value: "+controller1.isButtonPressed(3));
            P1_dropOrCallBall = (controller1.isButtonPressed(2) || keys[KeyEvent.VK_E]); //button X
            //System.out.println("Pad1: B button value: "+controller1.isButtonPressed(1));

            //P1 direction left
            if (controller1.getAxisValue(4) > deadZone1 || (keys[KeyEvent.VK_T])) {
                P1_dirLeft = true; //left trigger/keyboard
            }

            //P1 direction right
            if (controller1.getAxisValue(4) != -1) {
                releaseFlagP1RT = true;
            }
            if ((releaseFlagP1RT == false) && (controller1.getAxisValue(4) == -1)) {
                releaseFlagP1RT = false;
            }

            if (releaseFlagP1RT == true) {
                if (controller1.getAxisValue(4) < -deadZone1) {
                    P1_dirRight = true; //right trigger
                }
            }
            if (keys[KeyEvent.VK_Y]) {
                P1_dirRight = true; //right trigger
            }
            //System.out.println("Pad1: triggers value: "+controller1.getAxisValue(4));
        }

        //controller2
        P2_up = keys[KeyEvent.VK_UP];
        P2_down = keys[KeyEvent.VK_DOWN];
        P2_left = keys[KeyEvent.VK_LEFT];
        P2_right = keys[KeyEvent.VK_RIGHT];
        P2_dirLeft = keys[KeyEvent.VK_U];
        P2_dirRight = keys[KeyEvent.VK_I];
        P2_force = keys[KeyEvent.VK_L];
        P2_rotLeft = keys[KeyEvent.VK_J];
        P2_rotRight = keys[KeyEvent.VK_K];
        P2_cancellation = keys[KeyEvent.VK_M];
        P2_sprint = keys[KeyEvent.VK_B];
        P2_dropOrCallBall = keys[KeyEvent.VK_N];

        if (controller2 != null) {
            controller2axisY = 0;
            controller2axisX = 0;

            //using flags to skip default -1 value of Xbox pad axes  && (controller1.getPovY()!=-1)
            //P2 moving up
            if ((controller2.getAxisValue(0) != -1) && (controller2.getPovY() != -1)) {
                releaseFlagP2Up = true;
            }
            if (releaseFlagP2Up == true) {
                if (controller2.getAxisValue(0) < -deadZone2) {
                    controller2axisY = controller2.getAxisValue(0);
                }
                if (controller2.getPovY() < -deadZone2) {
                    P2_up = true; //Left analog
                }
            }
            if (keys[KeyEvent.VK_UP]) {
                P2_up = true; //keyboard
            }
            //P2 moving down
            if ((controller2.getPovY() > deadZone2) || keys[KeyEvent.VK_DOWN]) {
                P2_down = true; //Left analog/D-Pad
            }
            if (controller2.getAxisValue(0) > deadZone2) {
                controller2axisY = controller2.getAxisValue(0);
            }
            //P2 moving left
            if ((controller2.getAxisValue(1) != -1) && (controller2.getPovX() != -1)) {
                releaseFlagP2Left = true;
            }
            if (releaseFlagP2Left == true) {
                if (controller2.getPovX() < -deadZone2) {
                    P2_left = true; //Left analog
                }
                if (controller2.getAxisValue(1) < -deadZone2) {
                    controller2axisX = controller2.getAxisValue(1);
                }
            }
            if (keys[KeyEvent.VK_LEFT]) {
                P2_left = true; //keyboard
            }
            //P2 moving right
            if ((controller2.getPovX() > deadZone2) || keys[KeyEvent.VK_RIGHT]) {
                P2_right = true; //D-Pad / keyboard
            }
            if (releaseFlagP2Left == true) {
                controller2axisX = controller2.getAxisValue(1); //Left analog
            }

            P2_force = (controller2.isButtonPressed(1) || keys[KeyEvent.VK_L]); //button X
            P2_cancellation = (controller2.isButtonPressed(0) || keys[KeyEvent.VK_M]); //button A
            P2_rotLeft = (controller2.isButtonPressed(4) || keys[KeyEvent.VK_J]); //button LB
            P2_rotRight = (controller2.isButtonPressed(5) || keys[KeyEvent.VK_K]); //button RB
            P2_sprint = (controller2.isButtonPressed(3) || keys[KeyEvent.VK_B]); //button Y
            P2_dropOrCallBall = (controller2.isButtonPressed(2) || keys[KeyEvent.VK_N]); //button B
            //P2 direction left
            if (controller2.getAxisValue(4) > deadZone2 || keys[KeyEvent.VK_U]) {
                P2_dirLeft = true;//P2_dirLeft = true; //left trigger
            }
            //P2 direction right
            if (controller2.getAxisValue(4) != -1) {
                releaseFlagP2RT = true;
            }
            if ((releaseFlagP2RT == false) && (controller2.getAxisValue(4) == -1)) {
                releaseFlagP2RT = false;
            }

            if (releaseFlagP2RT == true) {
                if (controller2.getAxisValue(4) < -deadZone2) {
                    P2_dirRight = true;//P2_dirRight = true; //right trigger
                }
            }
            if (keys[KeyEvent.VK_I]) {
                P2_dirRight = true; //right trigger
            }
            // System.out.println("Pad2, triggers value: "+controller2.getAxisValue(4));
        }
    }

    public float getController1axisX() {
        return controller1axisX;
    }

    public float getController1axisY() {
        return controller1axisY;
    }

    public float getController2axisX() {
        return controller2axisX;
    }

    public float getController2axisY() {
        return controller2axisY;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        //System.out.println("Key pressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.prop.tank;

import java.awt.Color;
import robocode.*;

/**
 *
 * @author jpala
 */
public class MyRobot extends AdvancedRobot {
    boolean chocado = false;
    boolean pararCañon = true;
    @Override
    public void run() {
        turnLeft(getHeading());
        setRadarColor(Color.YELLOW);
        while(chocado==false){
            ahead(20);
        }
        while(pararCañon)
        {
            setTurnGunRight(100);
            execute();
        }
    } 
    public void mueveCañon(boolean op){
        if(true)
        {
            setTurnGunRight(100);
            execute();
        }else{
            setTurnGunRight(0);
            execute();
        }
    }
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        double angulo = e.getHeading() - getHeading();
        setTurnGunRight(angulo);
        double old = e.getBearing();
        if(e.getBearing()== old){
            pararCañon= false;
            mueveCañon(false);
            fire(1);
            execute();
        }else{
            mueveCañon(true);
        }
            scan();

    } 
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        //turnLeft(180);
    }
    @Override
    public void onHitWall(HitWallEvent e) {
        chocado = true;
        turnLeft(180);
    }
}

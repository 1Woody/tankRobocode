/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.prop.tank;

import robocode.HitByBulletEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 *
 * @author jpala
 */
public class Torre extends Robot{
    private static double bearingThreshold = 5;
        @Override
    public void run() {
        turnLeft(getHeading());
        while(true)
        {
            turnGunLeft(90);
            turnRadarLeft(90);
        }
    } 
    double normalizeBearing(double bearing){
        while(bearing > 180) bearing -=360;
        while(bearing < -180) bearing +=360;
        return bearing;  
    }
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        if(normalizeBearing(e.getBearing()) < bearingThreshold){
            fire(1);
        }
    } 
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        //turnLeft(180);
    }
}

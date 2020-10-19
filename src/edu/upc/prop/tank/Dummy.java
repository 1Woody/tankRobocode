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
public class Dummy extends Robot{
    @Override
    public void run() {
        turnLeft(getHeading());
        while(true)
        {
            ahead(500);
            turnRight(90);
        }
    } 
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    } 
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        //turnLeft(180);
    }
}

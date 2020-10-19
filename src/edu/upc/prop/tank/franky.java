/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.prop.tank;

import java.awt.Color;
import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 *
 * @author javier Palacios i Daniel Benavente
 */
public class franky extends AdvancedRobot {
  double energiaEnemic = 100;
  int direccio = 1;
  int direccioArma = 1;
  
    /**
     *
     */
    @Override
  public void run() {
    setBodyColor(new Color(0, 0, 0));
    setGunColor(new Color(200,0,0));
    setRadarColor(new Color(90,0,0));
    setBulletColor(new Color(10,0,0));
    setScanColor(new Color(255,40,40));
    setAdjustRadarForRobotTurn(true);
    setAdjustRadarForGunTurn(true);
    setAdjustGunForRobotTurn(true);
    setTurnGunRight(360);
    setTurnRadarRight(360);
    while(true){
        if (getRadarTurnRemaining() == 0.0){
            setTurnRadarRight(360);
        }
        execute();
    }
  }
  
  @Override
  public void onScannedRobot(ScannedRobotEvent e) {
    // Càlcul del posicionament que utilitzarem respecte el robot detectat.
    setTurnRight(normalizeBearing(e.getBearing()+90 - 30*direccio));

    // Comprovació energia
    double difEnergia = energiaEnemic-e.getEnergy();
    if (difEnergia>0 && difEnergia<=3) {
       direccio = -direccio;
       setAhead((e.getDistance()/4)*direccio); 
    }
    // càlcul del radar i la presició de tir.
    double BearingAbsolut = getHeadingRadians() + e.getBearingRadians();
    setTurnRadarRight(normalizeBearing(getHeading() - getRadarHeading() + e.getBearing()));
    setTurnGunRightRadians(Utils.normalRelativeAngle(BearingAbsolut - 
            getGunHeadingRadians() + (e.getVelocity() * Math.sin(e.getHeadingRadians() - BearingAbsolut) / 13.0)));
    //Tir
    if(e.getDistance()<100){
        fire(3);
    }else{
        fire(2);
    }
    // Actualitzem el valor de l'energia.
    energiaEnemic = e.getEnergy();
  }
  
  @Override
  public void onHitByBullet(HitByBulletEvent e){
           direccio = -direccio;
           setAhead((200)*direccio);
  }
  
  double normalizeBearing(double angle) {
    while (angle >  180) angle -= 360;
    while (angle < -180) angle += 360;
    return angle;
  }
  
  
  
}
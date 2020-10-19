/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.prop.tank;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 *
 * @author jpala
 */
public class Iron_giant extends AdvancedRobot {
  double previousEnergy = 100;
  int movementDirection = 1;
  int gunDirection = 1;
  int fallos = 0;

  @Override
  public void run() {
    setAdjustRadarForRobotTurn(true);
    setAdjustRadarForGunTurn(true);
    setAdjustGunForRobotTurn(true);
    setTurnGunRight(360);
    setTurnRadarRight(360);
    fallos=0;
    while(true){
        if (getRadarTurnRemaining() == 0.0){
            setTurnRadarRight(360);
        }
        execute();
    }
  }
  
  @Override
  public void onScannedRobot(ScannedRobotEvent e) {
        // Stay at right angles to the opponent
        setTurnRight(normalizeBearing(e.getBearing()+90 - 30*movementDirection));

      // If the bot has small energy drop,
      // assume it fired
      double changeInEnergy = previousEnergy-e.getEnergy();
      if (changeInEnergy>0 && changeInEnergy<=3) {
           movementDirection = -movementDirection;
           // el fet de fer una operació retrasa el temps que executa la instrucció i li permet
           //   moures avans de canviar de direcció i tornar a moures.
           setAhead((e.getDistance()/4+25)*movementDirection); 
       }
      // When a bot is spotted,
      // sweep the gun and radar
      double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
      setTurnRadarRight(normalizeBearing(getHeading() - getRadarHeading() + e.getBearing()));
      
      setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - 
            getGunHeadingRadians() + (e.getVelocity() * Math.sin(e.getHeadingRadians() - absoluteBearing) / 13.0)));
      //setTurnGunRight(normalizeBearing(getHeading() - getGunHeading() + normalizeBearing(e.getBearing())));
      //gunDirection = -gunDirection;
      //setTurnGunRight(99999*gunDirection);

      // Fire directly at target
      if (getEnergy() > 3){
        if(e.getDistance()<100){
            fire(3);
        }else{//(e.getDistance() > 100 && e.getDistance() < 400){
            fire(2);
        }
      } else{
          
      }

      // Track the energy level
      previousEnergy = e.getEnergy();
  }
  /*@Override
  public void onHitWall(HitWallEvent e) {
        turnLeft(180);
        recarga();
  }
  private void recarga(){
      while(getEnergy()<80){
            ahead(100);
      }
  }*/
  @Override
  public void onBulletMissed(BulletMissedEvent event){
      fallos++;
      if(fallos > 7){
          //turnGunRight(360*gunDirection);
          //run();
      }
  }
  @Override
  public void onBulletHit(BulletHitEvent event){
      fallos = 0;
  }
  
  @Override
  public void onHitByBullet(HitByBulletEvent e){
           movementDirection = -movementDirection;
           setAhead((200)*movementDirection);
  }
  
  double normalizeBearing(double angle) {
    while (angle >  180) angle -= 360;
    while (angle < -180) angle += 360;
    return angle;
  }
  
  
  
}

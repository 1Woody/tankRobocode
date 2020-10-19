/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.prop.tank;

import robocode.*;
import robocode.util.Utils;

/**
 *
 * @author 
 * //       1.Para arreglar el oscilamiento tendriamos que hacer una lista de enemigos
 *          donde guardar cada uno y asi saber cual esta mas cerca y en cual centrarse.
 *          2. Movimiento hace que los tiros sean malos, falta controlar disparos distancia
 *          (puede que a distancia solo esquivar). tambien controlar cuando falle, cuando de
 *          una bala en la pared, tu vida y tu energia, etc.
 * 
 */
public class test extends AdvancedRobot {
    private byte scanDirection = 1;
    private byte moveDirection = 1;
    public static boolean m = true;
    //Enemy
    public static String name = "";
    public static double dist = 0.0;
            
    //public static double ene = true; 
    //private EnemyBot enemy = new EnemyBot();
    @Override
    public void run() {
        setAdjustRadarForRobotTurn(true);
        //setAdjustRadarForGunTurn(true);
        setAdjustGunForRobotTurn(true);
        //movement(true);
        //ahead(300);
        while(true){
           // movement();
            doMove();
            execute();
        }
        
    } 
    /*
    public boolean movement(boolean m) {
       while(m == true){
            if (scanDirection == 1) scanDirection = -1;
            turnRadarRight(360 * scanDirection);
        } 
        return false;
    }*/
    public void movement() {
        scanDirection *= -1;
        setTurnRadarRight(360 * scanDirection);
    }
    
    public boolean scanNewEnemy (ScannedRobotEvent e){
        boolean avoid = false;
        if ("".equals(name)){
            name = e.getName();
            dist = e.getDistance();
        }else if(e.getName().equals(name)){
            dist = e.getDistance();
        } else {
            if(e.getDistance() < dist){
                name = e.getName();
                dist = e.getDistance();
            }else{
                avoid = true;
            }
        }
        return avoid;
    }
    
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        //m = movement(false);
        //if (avoid == false){

            //double enemypos;
            /*
            setTurnRadarRight(normalizeBearing(getHeading() - getRadarHeading() + e.getBearing()));
            setTurnGunRight(normalizeBearing(getHeading() - getGunHeading() + normalizeBearing(e.getBearing())));
            */

            double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
            setTurnRight(normalizeBearing(e.getBearing()) + 90);  //movementadd
            
            //prediccion de tiro
            
            setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - 
            getGunHeadingRadians() + (e.getVelocity() * Math.sin(e.getHeadingRadians() - absoluteBearing) / 13.0)));
            
            if(/*e.getDistance() <= 600 && m == false*/true) {
                if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10){
                    setFire(Math.min(400 / e.getDistance(), 3));
                    //execute();
                }   
            }
            // si hacemos todo en un solo execute no podemos ir escaneando mientras fijamos
            // pero si lo separamos el cañon no sigue en tiempo real al enemigo

            /*if (e.getDistance() > 200){
                scanDirection *= -1; // cambia el valor
                turnRadarRight(360 * scanDirection);
            //}*/
        //}
    }
    
    public void doMove() {
            // mover cada 20 cliks
            if (getTime() % 20 == 0) {
                    moveDirection *= -1;
                    ahead(150 * moveDirection);
            }
           
    }
    
    @Override
    public void onHitWall(HitWallEvent e) { 
        //moveDirection *= -1; 
        moveDirection *= -1;
        setAhead(100 * moveDirection);
        setTurnRight(20);
        
    }
    
    @Override
    public void onHitRobot(HitRobotEvent e) { 
        moveDirection *= -1; 
    }
    
    // 
    /*double absoluteBearing(double x1, double y1, double x2, double y2) {
	double xo = x2-x1;
	double yo = y2-y1;
	double hyp = Point2D.distance(x1, y1, x2, y2);
	double arcSin = Math.toDegrees(Math.asin(xo / hyp));
	double bearing = 0;

	if (xo > 0 && yo > 0) { // both pos: lower-Left
		bearing = arcSin;
	} else if (xo < 0 && yo > 0) { // x neg, y pos: lower-right
		bearing = 360 + arcSin; // arcsin is negative here, actuall 360 - ang
	} else if (xo > 0 && yo < 0) { // x pos, y neg: upper-left
		bearing = 180 - arcSin;
	} else if (xo < 0 && yo < 0) { // both neg: upper-right
		bearing = 180 - arcSin; // arcsin is negative here, actually 180 + ang
	}

	return bearing;
    }*/
    
    // normalizes a bearing to between +180 and -180
    double normalizeBearing(double angle) {
	while (angle >  180) angle -= 360;
	while (angle < -180) angle += 360;
	return angle;
    }
}

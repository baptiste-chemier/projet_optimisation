/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outils;

/**
 *
 * @author Epulapp
 */
public class Outils {
    
    public static double getDistance(String latitude1, String longitude1, String latitude2, String longitude2) {
        double lat1, lat2, long1, long2, radLat1, radLat2, theta, radTheta, dist;

        lat1 = Double.parseDouble(latitude1);
        long1 = Double.parseDouble(longitude1);
        lat2 = Double.parseDouble(latitude2);
        long2 = Double.parseDouble(longitude2);

        radLat1 = Math.toRadians(lat1);
        radLat2 = Math.toRadians(lat2);

        if (long2 < long1) {
            theta = long1 - long2;
        } else {
            theta = long2 - long1;
        }

        radTheta = Math.toRadians(theta);
        dist = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);
        dist = Math.acos(dist);
        dist = dist * 180 / (Math.PI);
        dist = dist * 60 * 1.1515;

        return dist * 1.609344;
    }

}

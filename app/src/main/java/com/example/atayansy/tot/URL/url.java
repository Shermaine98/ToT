package com.example.atayansy.tot.URL;

/**
 * Created by shermainesy on 11/30/15.
 */
public class url {

    /**
     * Shermaine's IP Address
     **/
    public static String ip = "http://192.168.1.4:8081/ToT/";

    /**Geraldine's IP Address**/
    public static String ip2 = "http://192.168.1.9:8084/ToT";

    /**GET IP ADDRESS**/

  /*
    public void getIp() {

        InetAddress i = null;
        String ipAddress = "";

        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) en.nextElement();
                Enumeration ee = ni.getInetAddresses();
                while (ee.hasMoreElements()) {
                    i = (InetAddress) ee.nextElement();
                    System.out.println(i.getHostAddress());
                    if (i.getHostAddress().toString().contains("192")) {
                        System.out.println("IN");
                        ipAddress = i.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        ip = "http://" + ipAddress + ":8081/ToT/";
        System.out.println(ip);


    }
    */
}

package rijve.shovon.easygo;

import android.widget.Toast;

import java.util.Random;

public class Calculate {
    private float walkingThreshold = .8f;
    private float gyroscopeThreshold = 1f;
    private float walkingSteps;
    private float walkingDistance;
    private long previousWalkedTime;
    private static long minimum_step_per_second = 400;//ms
    public Calculate(float walkingThreshold , float gyroscopeThreshold){
        walkingDistance=0; // Should be in feet..
        walkingSteps = 0;
        this.gyroscopeThreshold = gyroscopeThreshold;
        this.walkingThreshold = walkingThreshold;
        previousWalkedTime = System.currentTimeMillis();
    }

    public boolean isDirectionOk(boolean direction , float gyroMagnitude){
            if(direction && gyroMagnitude<gyroscopeThreshold) return true;
            return false;
    }

    public void hasWalked(float accelerometerMagnitude , float gyroscopeMagnitude){
        long currentTime = System.currentTimeMillis();

        //accelerometerMagnitude = 5.1f;
        //gyroscopeMagnitude = 0.0f;
        System.out.println(accelerometerMagnitude+"    "+gyroscopeMagnitude);
        if(accelerometerMagnitude>=walkingThreshold && gyroscopeMagnitude<gyroscopeThreshold && (currentTime-previousWalkedTime)>minimum_step_per_second){
            //System.out.println(accelerometerMagnitude+"    "+gyroscopeMagnitude);
            previousWalkedTime = currentTime;
            increamentDistance();
        }

    }

    public float getWalkingDistance(){
        return walkingDistance;
    }
    public void increamentDistance(){
        walkingSteps++;
        walkingDistance += .60;
        //System.out.println(walkingDistance+"WALK");
    }

    public float[] calculateData(float direction , float x1, float y1 , float z1 , String previous_nodeId){
        //@
        // get data from the source node eg: x,y,z
        // create new node and calculate x,y,z based on source node..
        //Extra
//        Random rand = new Random();
//        int n = rand.nextInt(359);
//        walkingDistance = (float)n;
        //extra
        int node_id;
        float x=x1,y=y1,z=z1;
        z = z1;
        System.out.println("Direction: "+direction+" Distance: "+walkingDistance);
        float theta=0;
        if(direction>0 && direction<90){
            theta = 90-direction;
            x = ((float)Math.cos(Math.toRadians((double)theta))*walkingDistance)+x1;
            y = ((float)Math.sin(Math.toRadians((double)theta))*walkingDistance)+y1;
        }
        else if(direction>90 && direction<180){
            theta = 180-direction;
            x = ((float)Math.sin(Math.toRadians((double)theta))*walkingDistance)+x1;
            y = y1- ((float)Math.cos(Math.toRadians((double)theta))*walkingDistance);
        }
        else if(direction>180 && direction<270){
            theta = 270-direction;
            x = x1-((float)Math.cos(Math.toRadians((double)theta))*walkingDistance);
            y = y1- ((float)Math.sin(Math.toRadians((double)theta))*walkingDistance);
        }
        else if(direction>270 && direction<360){
            theta  = 360-direction;
            x = x1- ((float)Math.sin(Math.toRadians((double)theta))*walkingDistance);
            y = y1+ ((float)Math.cos(Math.toRadians((double)theta))*walkingDistance);
        }
        else if(direction==0){
            x = x1;
            y = y1+walkingDistance;
        }
        else if(direction==90){
            x = x1+walkingDistance;
            y = y1;
        }
        else if(direction==180){
            x = x1;
            y = y1-walkingDistance;
        }
        else if(direction==270){
            x = x1-walkingDistance;
            y = y1;
        }
        System.out.println("x: "+x+" y: "+y+" z: "+z);
        float details[] = new float[]{x,y,z};
        return details;



    }

}

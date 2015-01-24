package to2.dice.GUI.animation;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Utils {
	public static Vector3f randomLocation() {
		return new Vector3f((float) Math.random() * 4.5f - 2.5f, (float) Math.random() * 8 - 4, -0.35f);
	}
	
	public static double calcDistance(Spatial dice1, Spatial dice2) {
		RigidBodyControl rbg1 = dice1.getControl(RigidBodyControl.class);
		RigidBodyControl rbg2 = dice2.getControl(RigidBodyControl.class);
		if (rbg1  == null || rbg2 == null) {
			return Double.MAX_VALUE;
		} else {
			Vector3f v1 = rbg1.getPhysicsLocation();
			Vector3f v2 = rbg2.getPhysicsLocation();
			return FastMath.sqrt((v1.x - v2.x) * (v1.x - v2.x) + (v1.y - v2.y) * (v1.y - v2.y) + (v1.z - v2.z) * (v1.z - v2.z));
		}
	}
	
}

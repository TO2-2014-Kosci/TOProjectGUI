package to2.dice.GUI.animation;

import com.jme3.math.Vector3f;

public class Util {
	public static Vector3f randomLocation() {
		return new Vector3f((float) Math.random() * 4.5f - 2.5f, (float) Math.random() * 8 - 4, -0.35f);
	}
}

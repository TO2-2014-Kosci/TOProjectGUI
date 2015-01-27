package to2.dice.GUI.animation;

import com.jme3.math.Vector3f;

public class AnotherPutControl extends AbstractPutControl {

	public AnotherPutControl(int diceName) {
		super(diceName);
		putLocation = new Vector3f(-diceName, -6, 0.35f);
	}
}

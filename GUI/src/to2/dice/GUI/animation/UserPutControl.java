package to2.dice.GUI.animation;

import com.jme3.math.Vector3f;

public class UserPutControl extends AbstractPutControl {

	public UserPutControl(int diceName, int diceNumber) {
		super(diceName);
		this.putLocation = new Vector3f(-7, diceNumber / 2 - diceName, 0.4f);
	}
}

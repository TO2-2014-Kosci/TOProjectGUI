package to2.dice.GUI.animation;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class UserPutControl extends AbstractControl {
	private int number;
	private int diceName;
	private int diceNumber;

	public UserPutControl(int diceName, int diceNumber) {
		super();
		super.setEnabled(false);
		this.diceName = diceName;
		this.diceNumber = diceNumber;
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void controlUpdate(float tpf) {
		RigidBodyControl diceControl = spatial.getControl(RigidBodyControl.class);
		if (number != 0) {
			diceControl.setEnabled(true);
			int numberValue = 0;
			synchronized (spatial) {
				numberValue = number;
			}
			switch (numberValue) {
			case 1:
				diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.HALF_PI, new Vector3f(1, 0, 0)));
				break;
			case 2:
				diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, new Vector3f(0, 1, 0)));
				break;
			case 3:
				diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.ZERO_TOLERANCE,
						new Vector3f(1, 0, 0)));
				break;
			case 4:
				diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.PI, new Vector3f(1, 0, 0)));
				break;
			case 5:
				diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.HALF_PI, new Vector3f(0, 1, 0)));
				break;
			case 6:
				diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, new Vector3f(1, 0, 0)));
				break;
			}
			diceControl.setLinearVelocity(new Vector3f(0, 0, 0));
			diceControl.setAngularVelocity(new Vector3f(0, 0, 0));
			diceControl.setPhysicsLocation(new Vector3f(-7, diceNumber / 2 - diceName, 0.4f));
			spatial.getControl(AbstractRollControl.class).setEnabled(false);
			Geometry d6 = ((Geometry) spatial);
			d6.getMaterial().setColor("Diffuse", ColorRGBA.White);
			number = 0;
		} else {
			diceControl.setEnabled(false);
			this.setEnabled(false);
		}
	}

	public void setNumberToPut(int number) {
		synchronized (spatial) {
			this.number = number;
			this.setEnabled(true);
		}
	}
}

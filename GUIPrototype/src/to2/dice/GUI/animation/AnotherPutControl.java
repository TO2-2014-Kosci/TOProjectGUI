package to2.dice.GUI.animation;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class AnotherPutControl extends AbstractControl {

	private int number;
	private int diceName;

	public AnotherPutControl(int diceName) {
		super();
		super.setEnabled(false);
		this.diceName = diceName;
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void controlUpdate(float tpf) {
		RigidBodyControl diceControl = this.spatial.getControl(RigidBodyControl.class);
		int numberValue = 0;
		synchronized (this.spatial) {
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
		diceControl.setPhysicsLocation(new Vector3f(-diceName, -6, 0.35f));
		diceControl.update(0);
		diceControl.setEnabled(false);
		this.setEnabled(false);

	}

	public void setNumberToPut(int number) {
		synchronized (this.spatial) {
			this.number = number;
			this.setEnabled(true);
		}
	}

}

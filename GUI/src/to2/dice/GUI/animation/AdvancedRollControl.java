package to2.dice.GUI.animation;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

public class AdvancedRollControl extends AbstractRollControl {

	private Quaternion targetRotate;
	private int steps;
	private int diceName;
	
	private Vector3f[] controlPoints = { new Vector3f(0, 1, 0), // 1
	new Vector3f(1, 0, 0), // 2
	new Vector3f(0, 0, 1), // 3
	new Vector3f(0, 0, -1), // 4
	new Vector3f(-1, 0, 0), // 5
	new Vector3f(0, -1, 0) // 6
};
	
	public AdvancedRollControl(int diceName, Spatial[] dices) {
		super();
		super.setEnabled(false);
		this.diceName = diceName;
	}

	@Override
	protected void controlRender(RenderManager arg0, ViewPort arg1) {
		// TODO Auto-generated method stub

	}
	

	@Override
	protected void controlUpdate(float arg0) {
		RigidBodyControl diceControl = spatial.getControl(RigidBodyControl.class);
		if (startRoll) {
			diceControl.setEnabled(true);
			diceControl.setAngularFactor(1);
			switch (number) {
			case 1:
				targetRotate = new Quaternion().fromAngleAxis(FastMath.HALF_PI, new Vector3f(1, 0, 0));
				break;
			case 2:
				targetRotate = new Quaternion().fromAngleAxis(-FastMath.HALF_PI, new Vector3f(0, 1, 0));
				break;
			case 3:
				targetRotate = new Quaternion().fromAngleAxis(FastMath.ZERO_TOLERANCE, new Vector3f(1, 0, 0));
				break;
			case 4:
				targetRotate = new Quaternion().fromAngleAxis(FastMath.PI, new Vector3f(1, 0, 0));
				break;
			case 5:
				targetRotate = new Quaternion().fromAngleAxis(FastMath.HALF_PI, new Vector3f(0, 1, 0));
				break;
			case 6:
				targetRotate = new Quaternion().fromAngleAxis(-FastMath.HALF_PI, new Vector3f(1, 0, 0));
				break;
			}
			diceControl.setLinearVelocity(new Vector3f(6, 0, -15));
			diceControl.setPhysicsLocation(new Vector3f(-3, 5 / 2 - diceName, 5));
			diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Utils.randomLocation()));
			Geometry d6 = (Geometry) spatial;
			d6.getMaterial().setColor("Diffuse", ColorRGBA.White);
			startRoll = false;
		} else {
			Quaternion currentRotation = diceControl.getPhysicsRotation();
			Vector3f location = diceControl.getPhysicsLocation();
			if (steps > 0 && steps < 20 ) {
				diceControl.setAngularVelocity(Vector3f.ZERO.clone());
				currentRotation.slerp(targetRotate, 1.0f / steps);
				diceControl.setPhysicsRotation(currentRotation);
				steps--;
			} else if (steps == 0) {
				if (getUpNumber(currentRotation) != number) {
					steps = 25;
				}
				if (location.z > 0) {
					diceControl.applyCentralForce(new Vector3f(5, 5, 0));
				}
			} else if (steps > 0) {
				steps--;
			}
			if (location.z > 2) {
				Vector3f currentLinearVelocity = diceControl.getLinearVelocity();
				if (currentLinearVelocity.z > 0) {
					currentLinearVelocity.z = -currentLinearVelocity.z;
					diceControl.setLinearVelocity(currentLinearVelocity);
				}
			}
			if (location.z < -1) {
				diceControl.setPhysicsLocation(new Vector3f(-3, 5 / 2 - diceName, 0.5f));
			}
			if (location.x > 4.0) {
				diceControl.applyCentralForce(new Vector3f(-5, 0, 0));
			} else if (location.x < -2.0) {
				diceControl.applyCentralForce(new Vector3f(5, 0, 0));
			}
			if (location.y > 4.0) {
				diceControl.applyCentralForce(new Vector3f(0, -5, 0));
			} else if (location.y < -4.0) {
				diceControl.applyCentralForce(new Vector3f(0, 5, 0));
			}
		}
		
	}
	
	private int getUpNumber(Quaternion currentRotation) {
		Vector3f up = new Vector3f(0, 0, 1);
		Matrix3f currentRotateMatrix = currentRotation.toRotationMatrix();
		currentRotateMatrix = currentRotateMatrix.invert();
		up = currentRotateMatrix.mult(up).normalizeLocal();
		int actualNumber = -1;
		float minDistance = 10000;
		for (int i = 0; i < controlPoints.length; i++) {
			float distance = up.distance(controlPoints[i]);
			if (distance < minDistance) {
				minDistance = distance;
				actualNumber = i;
			}
		}
		actualNumber += 1;
		return actualNumber;
	}
	
	@Override
	public void setNumberToRoll(int number) {
		super.setNumberToRoll(number);
		steps = 75;
	}
}

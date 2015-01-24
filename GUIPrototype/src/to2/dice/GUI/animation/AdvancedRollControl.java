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
	private Spatial[] dices;
	private int steps;
	
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
		this.dices = dices;
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
			diceControl.setLinearVelocity(new Vector3f(3, 0, -10));
			diceControl.setPhysicsLocation(new Vector3f(-3, 0, 5));
			diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Utils.randomLocation()));
			Geometry d6 = (Geometry) spatial;
			d6.getMaterial().setColor("Diffuse", ColorRGBA.White);
//			CollisionResults col = new CollisionResults();
//			for (Spatial d: dices) {
//				if (!d.equals(spatial)) {
//					if (spatial.collideWith(d.getWorldBound(), col) > 0) {
//						
//					}
//					System.out.println(col);
//				}
//			}
			while (isCollision()) {
				diceControl.setPhysicsLocation(Utils.randomLocation());
			}
			startRoll = false;
		} else {
			System.out.println(steps);
			Quaternion currentRotation = diceControl.getPhysicsRotation();
			if (steps > 0 && (steps < 20 || diceControl.getLinearVelocity().distance(Vector3f.ZERO) < 0.1)) {
				diceControl.setAngularFactor(0);
//				diceControl.clearForces();
				currentRotation.slerp(targetRotate, 1f / steps);
				diceControl.setPhysicsRotation(currentRotation);
				steps--;
			} else if (steps == 0) {
				diceControl.setAngularFactor(1);
				diceControl.clearForces();
				if (getUpNumber(currentRotation) != number) {
					steps = 30;
				}
			} else if (steps > 0) {
				steps--;
			}
		}
		
	}
	
	private boolean isCollision() {
		for (Spatial d: dices) {
			if (!d.equals(spatial)) {
				if (Utils.calcDistance(spatial, d) < 0.9) {
					return true;
				}
			}
		}
		return false;
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
		steps = 100;
	}
}

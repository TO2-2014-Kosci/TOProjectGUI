package to2.dice.GUI.controllers;

import java.util.concurrent.atomic.AtomicInteger;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class DiceControl extends AbstractControl {

	private int result = -1;

	public DiceControl(Vector3f[] controlPoints, AtomicInteger evaluatorsRunning) {
		super();

	}

	RigidBodyControl getNode() {
		return spatial.getControl(RigidBodyControl.class);
	}

	@Override
	protected void controlUpdate(float tpf) {

		Vector3f angularVelocity = getNode().getAngularVelocity();

		Vector3f loc = new Vector3f();
		getNode().getPhysicsLocation(loc);

		float ANGULAR_STOP = 0.01f;

		if (angularVelocity.length() > ANGULAR_STOP) {
			return;
		}

		Vector3f linearVelocity = getNode().getLinearVelocity();

		float LINEAR_STOP = 0.01f;

		if (linearVelocity.length() > LINEAR_STOP) {
			return;
		}

		Vector3f up = new Vector3f(0, 0, 1);
		Matrix3f rot = getNode().getPhysicsRotation().toRotationMatrix();
		rot = rot.invert();
		up = rot.mult(up).normalizeLocal();

		int chosenControl = -1;
		// for ( int i =0; i < controlPoints.length; i++ ) {
		// float distance = up.distance(controlPoints[i]);
		// if ( distance < minDistance ) {
		// minDistance = distance;
		// chosenControl = i;
		// }
		// }

		setFinalResult(chosenControl + 1);
		// finishedCounter.decrementAndGet();
		setEnabled(false);
	}

	private void setFinalResult(int i) {
		result = i;
	}

	public int getResult() {
		return result;
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {

	}

	@Override
	public Control cloneForSpatial(Spatial spatial) {
		return this;
	}

}

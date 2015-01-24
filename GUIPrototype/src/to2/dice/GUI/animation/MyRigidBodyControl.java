package to2.dice.GUI.animation;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;

public class MyRigidBodyControl extends RigidBodyControl implements PhysicsCollisionListener{
	
	public MyRigidBodyControl(CollisionShape shape, float mass, BulletAppState aps) {
		super(shape, mass);
		aps.getPhysicsSpace().addCollisionListener(this);
	}


	@Override
	public void collision(PhysicsCollisionEvent event) {
		System.out.println("dupa");
		
	}
	

}

import java.awt.Canvas;
import java.util.Random;

import com.jme3.app.SimpleApplication;


import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.HullCollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jogamp.newt.event.NEWTEventConsumer;
 
public class Test extends SimpleApplication {
 
	public Test() {
		super();
		createCanvas();
		startCanvas(true);
	}
	
	public Canvas getCanvas() {
		return ((JmeCanvasContext)this.getContext()).getCanvas();
	}
 
	@Override
	public void simpleInitApp() {
		//this.setDisplayStatView(false);
//		this.setDisplayFps(false);
		BulletAppState bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
//		bulletAppState.getPhysicsSpace().enableDebug(assetManager);
		bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, 0, -10));
		this.settings.setBitsPerPixel(32);
		//this.flyCam.setEnabled(false);
		this.settings.setFrameRate(60);
		this.cam.setLocation(new Vector3f(0, 0, 7));
		this.cam.lookAt(new Vector3f(6, 0, 0), Vector3f.UNIT_Z);
		Spatial[] dices = new Spatial[5];
		this.assetManager.registerLocator("./assets", FileLocator.class);
		
		Random rand=  new Random();
		for (int i = 0; i < 5; i++) {
			dices[i] = this.assetManager.loadModel("Model/Dice/dice.j3o");
			dices[i].rotate(rand.nextFloat() * 3.14f, rand.nextFloat() * 3.14f, rand.nextFloat() * 3.14f);
//			dices[i].setLocalTranslation(6, -3 + 1.5f * i, 0);
			dices[i].setLocalTranslation(6, 0, 2 * i);
			CollisionShape diceShape = CollisionShapeFactory.createDynamicMeshShape((Node) dices[i]); //u¿ywamy Dynamic bo maj¹ byæ kolizje
			RigidBodyControl diceBody = new RigidBodyControl(diceShape, 1);
			dices[i].addControl(diceBody);
			rootNode.attachChild(dices[i]);
			bulletAppState.getPhysicsSpace().add(diceBody);
		}
		Spatial box = this.assetManager.loadModel("Model/Box/box.j3o");
		box.setLocalTranslation(6, 0, -1);
		RigidBodyControl landscape = new RigidBodyControl(CollisionShapeFactory.createMeshShape((Node) box), 0);
		box.addControl(landscape);
		bulletAppState.getPhysicsSpace().add(landscape);
		rootNode.attachChild(box);
		AmbientLight ambient = new AmbientLight();
		ambient.setColor(ColorRGBA.White);
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(6f, 0f, -7f).normalizeLocal());
		sun.setColor(ColorRGBA.White.mult(1.5f));
		
		PointLight point = new PointLight();
		point.setPosition(new Vector3f(0, 0, 15));
		point.setColor(ColorRGBA.White.mult(0.6f));
		PointLight pointBack = new PointLight();
		pointBack.setPosition(new Vector3f(15, 0, 15));
		pointBack.setColor(ColorRGBA.White.mult(0.6f));
		PointLight pointLeft = new PointLight();
		pointLeft.setPosition(new Vector3f(15, 10, 15));
		pointLeft.setColor(ColorRGBA.White.mult(0.6f));
		PointLight pointRight = new PointLight();
		pointRight.setPosition(new Vector3f(15, 10, 15));
		pointRight.setColor(ColorRGBA.White.mult(0.6f));
		rootNode.addLight(point);
		rootNode.addLight(pointBack);
		rootNode.addLight(pointRight);
		rootNode.addLight(pointLeft);
		rootNode.addLight(sun);

		rootNode.addLight(ambient);
	}
}
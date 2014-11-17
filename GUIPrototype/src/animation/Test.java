package animation;
import java.awt.Canvas;
import java.util.Random;

import model.Dices;

import com.jme3.app.SimpleApplication;


import com.jme3.asset.TextureKey;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.HullCollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
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
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jogamp.newt.event.NEWTEventConsumer;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
 
public class Test extends SimpleApplication {
 
	public Test() {
		super();
		createCanvas();
		startCanvas(true);
	}
	
	public Canvas getCanvas() {
		return ((JmeCanvasContext)this.getContext()).getCanvas();
	}
	
	Spatial[] dices;
 
	@Override
	public void simpleInitApp() {
		// TODO wykorzystaæ guiNode aby dodaæ tabele i koœci gracza
		this.setDisplayStatView(false);
		this.setDisplayFps(false);
		BulletAppState bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		bulletAppState.getPhysicsSpace().enableDebug(assetManager);
		bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, 0, -10));
		bulletAppState.getPhysicsSpace().setAccuracy(1/300f);
		this.settings.setBitsPerPixel(32);
		this.flyCam.setEnabled(false);
		this.settings.setFrameRate(60);
		this.cam.setLocation(new Vector3f(-10, 0, 12));
		this.cam.lookAt(new Vector3f(-3, 0, 0), Vector3f.UNIT_Z);
		dices = new Spatial[5];
		this.assetManager.registerLocator("./assets", FileLocator.class);
		for (int i = 0; i < 5; i++) {
			dices[i] = this.assetManager.loadModel("Model/Dice/dice.j3o");
			//dices[i].rotate(rand.nextFloat() * 3.14f, rand.nextFloat() * 3.14f, rand.nextFloat() * 3.14f);
			//dices[i].setLocalTranslation(0, 0, 2 * i);
			CollisionShape diceShape = CollisionShapeFactory.createDynamicMeshShape((Node) dices[i]); //u¿ywamy Dynamic bo maj¹ byæ kolizje
			RigidBodyControl diceBody = new RigidBodyControl(diceShape, 1);
			dices[i].addControl(diceBody);
			rootNode.attachChild(dices[i]);
			bulletAppState.getPhysicsSpace().add(diceBody);
		}
		Spatial box = this.assetManager.loadModel("Model/Box/box.j3o");
		box.setLocalTranslation(0, 0, -1);
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
		inputManager.addMapping("Shake", (Trigger)new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("Put", (Trigger)new KeyTrigger(KeyInput.KEY_P));
		inputManager.addMapping("Select", (Trigger)new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(actionListener, new String[]{"Shake", "Put", "Select"});
	}
	
	private ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean keyPressed, float tpf) {
			if (name.equals("Shake")) {
				for (int i = 0; i < 5; i++) {
					dices[i].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0, 0, 2 * i));
					dices[i].getControl(RigidBodyControl.class).activate();
				}
			} else if (name.equals("Put")) {
				for (int i = 0; i < 5; i++) {
					dices[i].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(-7, -4 + 2 * i, -1.5f));
					dices[i].getControl(RigidBodyControl.class).activate();
				}
			} else if (name.equals("Select") && !keyPressed) {
				// Reset results list.
				CollisionResults results = new CollisionResults();
				// Convert screen click to 3d position
				Vector2f click2d = inputManager.getCursorPosition();
				Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
				Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
				// Aim the ray from the clicked spot forwards.
				Ray ray = new Ray(click3d, dir);
				rootNode.collideWith(ray, results);
				if (results.size() > 0) {
					Spatial target = results.getClosestCollision().getGeometry().getParent().getParent();
					if (target.getName().equals("Model/Dice/dice.blend")) {
						target.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0, 0, 6));
						target.getControl(RigidBodyControl.class).activate();
					}
				}
			}
		}
	};
}
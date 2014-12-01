package to2.dice.GUI.views;

import java.awt.Canvas;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.system.JmeSystem;

import to2.dice.GUI.controllers.GameAnimController;

//TODO inheritance
public class GameAnimation extends SimpleApplication{
	private GameAnimController gameAnimController;
	private Spatial[] dices;
	
	public GameAnimation(GameAnimController gameAnimController) {
		super();
		AppSettings settings = new AppSettings(true);
		settings.setAudioRenderer(null);
		this.setSettings(settings);
		createCanvas();
		startCanvas(true);
		this.gameAnimController = gameAnimController;
	}

	public Canvas getCanvas(){
		return ((JmeCanvasContext)this.getContext()).getCanvas();
	}

	@Override
	public void simpleInitApp() {
		// TODO Auto-generated method stub
		// TODO wykorzystaæ guiNode aby dodaæ tabele i koœci gracza
		this.setDisplayStatView(false);
		this.setDisplayFps(false);
		BulletAppState bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
//		bulletAppState.getPhysicsSpace().enableDebug(assetManager);
		bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, 0, -10));
		bulletAppState.getPhysicsSpace().setAccuracy(1/300f);
		this.settings.setBitsPerPixel(32);
		this.flyCam.setEnabled(false);
		this.settings.setFrameRate(60);
		this.cam.setLocation(new Vector3f(-10, 0, 12));
		this.cam.lookAt(new Vector3f(-3, 0, 0), Vector3f.UNIT_Z);
		setDices(new Spatial[5]);
		this.assetManager.registerLocator("./assets", FileLocator.class);
		for (int i = 0; i < 5; i++) {
			getDices()[i] = this.assetManager.loadModel("Model/Dice/dice.j3o");
			//dices[i].rotate(rand.nextFloat() * 3.14f, rand.nextFloat() * 3.14f, rand.nextFloat() * 3.14f);
			getDices()[i].setLocalTranslation(0, 0, 2 * i);
			CollisionShape diceShape = CollisionShapeFactory.createDynamicMeshShape((Node) getDices()[i]); //u¿ywamy Dynamic bo maj¹ byæ kolizje
			RigidBodyControl diceBody = new RigidBodyControl(diceShape, 0.1f);
			getDices()[i].addControl(diceBody);
			rootNode.attachChild(getDices()[i]);
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
		inputManager.addListener(gameAnimController, new String[]{"Shake", "Put", "Select"});
	}

	public Spatial[] getDices() {
		return dices;
	}

	public void setDices(Spatial[] dices) {
		this.dices = dices;
	}
}

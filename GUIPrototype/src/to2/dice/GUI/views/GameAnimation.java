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
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.system.JmeSystem;

import to2.dice.GUI.controllers.GameAnimController;
import to2.dice.GUI.model.Model;
import to2.dice.game.Player;

//TODO inheritance
public class GameAnimation extends SimpleApplication{
	private GameAnimController gameAnimController;
	private Model model;
	private Spatial[] userDice;
	private Spatial[] anotherDice;
	
	public GameAnimation(Model model, GameAnimController gameAnimController) {
		super();
		AppSettings settings = new AppSettings(true);
		settings.setAudioRenderer(null);
		this.setSettings(settings);
		FilterPostProcessor fpp=new FilterPostProcessor(assetManager);
	    BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);        
	    fpp.addFilter(bloom);
	    viewPort = new ViewPort("Cam", this.cam);
	    viewPort.addProcessor(fpp);
		createCanvas();
		startCanvas(true);
		this.gameAnimController = gameAnimController;
		this.model = model;
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
		setUserDice(new Spatial[model.getGameSettings().getDiceNumber()]);
		setAnotherDice(new Spatial[model.getGameSettings().getDiceNumber()]);
		this.assetManager.registerLocator("./assets", FileLocator.class);
		for (int i = 0; i < 5; i++) {
			getUserDice()[i] = this.assetManager.loadModel("Model/Dice/dice.j3o");
			CollisionShape diceShape = CollisionShapeFactory.createDynamicMeshShape((Node) getUserDice()[i]); //u¿ywamy Dynamic bo maj¹ byæ kolizje
			RigidBodyControl diceBody = new RigidBodyControl(diceShape, 0.0001f);
			getUserDice()[i].addControl(diceBody);
			rootNode.attachChild(getUserDice()[i]);
			bulletAppState.getPhysicsSpace().add(diceBody);
			getUserDice()[i].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(-7, -4 + 2 * i, -1.5f));
			
			
			getAnotherDice()[i] = this.assetManager.loadModel("Model/Dice/dice.j3o");
			CollisionShape diceShapeA = CollisionShapeFactory.createDynamicMeshShape((Node) getAnotherDice()[i]); //u¿ywamy Dynamic bo maj¹ byæ kolizje
			RigidBodyControl diceBodyA = new RigidBodyControl(diceShapeA, 0.0001f);
			getAnotherDice()[i].addControl(diceBodyA);
			rootNode.attachChild(getAnotherDice()[i]);
			bulletAppState.getPhysicsSpace().add(diceBodyA);
			getAnotherDice()[i].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(i, -6, -1f));
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

	public Spatial[] getUserDice() {
		return userDice;
	}

	public void setUserDice(Spatial[] dice) {
		this.userDice = dice;
	}

	public Spatial[] getAnotherDice() {
		return anotherDice;
	}

	public void setAnotherDice(Spatial[] anotherDice) {
		this.anotherDice = anotherDice;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		Player currentPlayer = model.getGameState().getCurrentPlayer();
		if (currentPlayer != null) {
			if (currentPlayer.getName().equals(model.getLogin())) {
				// u¿ytkownik
			} else {
				for(int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
					getAnotherDice()[i].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(i, -6, -1f));
				}
				// inny gracz
			}
		}
	}
	
	public void selectDice(int i) {
		if (model.getSelectedDice()[i]) {
			getUserDice()[i].addLight(new PointLight());
		} else {
			getUserDice()[i].removeLight(new PointLight());
		}
		getUserDice()[i].getControl(RigidBodyControl.class).activate();
	}
}

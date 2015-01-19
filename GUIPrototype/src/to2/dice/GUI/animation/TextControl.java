package to2.dice.GUI.animation;

import com.jme3.font.BitmapText;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class TextControl extends AbstractControl {
	private BitmapText bitmapText;
	private String text;
	
	public TextControl(BitmapText bitmapText) {
		this.bitmapText = bitmapText;
		this.setEnabled(false);
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void controlUpdate(float tpf) {
		bitmapText.setText(text);
//		bitmapText.setLocalTranslation((w - bitmapText.getLineWidth()) / 2, h - 25, 0);
		this.setEnabled(false);
	}
	
	public void setText(String text) {
		this.text = text;
		this.setEnabled(true);
	}
	
	

}

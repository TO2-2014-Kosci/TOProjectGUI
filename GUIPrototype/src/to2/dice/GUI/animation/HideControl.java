package to2.dice.GUI.animation;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.control.AbstractControl;

public class HideControl extends AbstractControl {
	private boolean hide = false;

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {

	}

	@Override
	protected void controlUpdate(float tpf) {
		if (hide) {
			spatial.setCullHint(CullHint.Always);
		} else {
			spatial.setCullHint(CullHint.Dynamic);
		}
		super.setEnabled(false);
	}

	public void setHide(boolean hide) {
		synchronized (spatial) {
			this.hide = hide;
			super.setEnabled(true);
		}
	}
}

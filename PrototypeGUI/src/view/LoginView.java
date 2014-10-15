package view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.RowLayout;

public class LoginView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LoginView(Composite parent, int style) {
		super(parent, style);
		setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Text text = new Text(this, SWT.BORDER);
		
		Button btnLogin = new Button(this, SWT.NONE);
		btnLogin.setText("Login");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

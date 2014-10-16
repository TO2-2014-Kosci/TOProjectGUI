package laucher;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;
import views.LoginView;


public class Launcher {
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shlKoci = new Shell();
		shlKoci.setSize(800, 600);
		shlKoci.setText("Ko\u015Bci");
		shlKoci.setLayout(new BorderLayout(0, 0));
		new LoginView(shlKoci, SWT.NONE);
		shlKoci.open();
		shlKoci.layout();
		while (!shlKoci.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}

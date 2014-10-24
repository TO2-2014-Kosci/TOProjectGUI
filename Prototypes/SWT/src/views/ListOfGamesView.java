package views;

import org.eclipse.swt.widgets.Composite;

public class ListOfGamesView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ListOfGamesView(Composite parent, int style) {
		super(parent, style);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

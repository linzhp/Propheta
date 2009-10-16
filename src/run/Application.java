package run;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public class Application extends ApplicationWindow {

	public Application() {
		super(null);
	}

	public void run() {
		setBlockOnOpen(true);
		open();
		Display.getCurrent().dispose();
	}

	protected Control createContents(Composite parent) {
		Shell shell=parent.getShell();
		MenuManager fileMenuManager = new MenuManager("File");
		Menu menuBar = new Menu(shell, SWT.BAR);
		fileMenuManager.fill(menuBar, -1);
		shell.setMenuBar(menuBar);
		 
		Label label = new Label(parent, SWT.CENTER);
		label.setText("Hello, World");
		return label;
	}
	
	public static void main(String[] args) {
		new Application().run();
	}
}

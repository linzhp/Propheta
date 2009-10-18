package run;

import newProject.NewProjectAction;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;


public class Application extends ApplicationWindow {


	public Application() {
		super(null);
		addMenuBar();
	}

	public void run() {
		setBlockOnOpen(true);
		open();
		Display.getCurrent().dispose();
	}

	@Override
	protected Control createContents(Composite parent) {
		 
		Label label = new Label(parent, SWT.CENTER);
		label.setText("Hello, World");
		
		parent.setSize(1024, 768);
//		parent.pack();
		return parent;
	}
	
	@Override
	protected MenuManager createMenuManager(){
		MenuManager mainMenu = new MenuManager(null);
		//File Menu
		MenuManager fileMenuManager = new MenuManager("文件");
		fileMenuManager.add(new NewProjectAction());
		
		mainMenu.add(fileMenuManager);
		return mainMenu;
	}
	
	public static void main(String[] args) {
		new Application().run();
	}
}

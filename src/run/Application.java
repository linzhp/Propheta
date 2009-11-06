package run;

import newProject.NewProjectAction;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

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

	protected Control createContents(Composite parent) {
		Composite contentArea = (Composite)super.createContents(parent);
		contentArea.setLayout(new FillLayout());
		return contentArea;
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
	
	public Composite getMainContent(){
		return (Composite)getContents();
	}
	
	public static void main(String[] args) {
		instance = new Application();
		instance.run();
	}
	
	private static Application instance;
	
	public static Application getInstance()
	{
		return instance;
	}
}

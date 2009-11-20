package run;

import gui.GUI;
import newProject.NewProjectAction;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

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
		Composite mainComposite = (Composite)super.createContents(parent);
		//contentArea.setLayout(new FillLayout());//TODO 暂时设为FillLayout
		mainComposite.setLayout(new FormLayout());//改为FormLayout hezhimin 11-20
		
		
		
		//分栏条
		final Sash sash=new Sash(mainComposite,SWT.VERTICAL);
		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 0); // Attach to top
		fd.bottom = new FormAttachment(100, 0); // Attach to bottom
		fd.left = new FormAttachment(25, 0); // Attach halfway across
		sash.setLayoutData(fd);
		sash.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((FormData) sash.getLayoutData()).left = new FormAttachment(0, event.x);
			    sash.getParent().layout();		    
			}			  
		});


		//composite to display the WBS
		Composite treeArea=new Composite(mainComposite,SWT.H_SCROLL|SWT.BORDER);
		treeArea.setLayout(new FillLayout());
		fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.left = new FormAttachment(0, 0);
		fd.right = new FormAttachment(sash, 0);
		treeArea.setLayoutData(fd);  
		
		
		//composite to display the charts
		Composite contentArea=new Composite(mainComposite,SWT.H_SCROLL|SWT.BORDER);
		contentArea.setLayout(new FillLayout());
		fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.left = new FormAttachment(sash, 0);
		fd.right = new FormAttachment(100, 0);
		contentArea.setLayoutData(fd);  
		
	    GUI.setContentArea(contentArea);
	    GUI.setTreeArea(treeArea);
	    
		
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

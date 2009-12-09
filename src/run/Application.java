package run;

import entity.EstimateNode;
import estimation.NewProjectAction;
import gui.ContentArea;
import gui.GUI;
import gui.TreeArea;

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
		
		
		
		//分栏条(左右)
		final Sash sash1=new Sash(mainComposite,SWT.VERTICAL);
		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 0); // Attach to top
		fd.bottom = new FormAttachment(100, 0); // Attach to bottom
		fd.left = new FormAttachment(25, 0); // Attach halfway across
		sash1.setLayoutData(fd);
		sash1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((FormData) sash1.getLayoutData()).left = new FormAttachment(0, event.x);
			    sash1.getParent().layout();		    
			}			  
		});


		//composite to display the WBS
		TreeArea treeArea=new TreeArea(mainComposite,SWT.BORDER);
		treeArea.setLayout(new FillLayout());
		fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.left = new FormAttachment(0, 0);
		fd.right = new FormAttachment(sash1, 0);
		treeArea.setLayoutData(fd);  
		
		
		//composite to display the content
		Composite rightArea=new Composite(mainComposite,SWT.NONE);
		rightArea.setLayout(new FormLayout());
		fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.left = new FormAttachment(sash1, 0);
		fd.right = new FormAttachment(100, 0);
		rightArea.setLayoutData(fd);  
		
		//分栏条 （上下）
		final Sash sash2=new Sash(rightArea,SWT.HORIZONTAL);
		fd = new FormData();
		fd.top = new FormAttachment(50, 0); // Attach to top
		
		fd.left = new FormAttachment(0, 0); 
		fd.right=new FormAttachment(100,0);
		sash2.setLayoutData(fd);
		sash2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((FormData) sash2.getLayoutData()).top = new FormAttachment(0, event.y);
			    sash2.getParent().layout();		    
			}			  
		});
		
		//右上部窗口
		ContentArea topContentArea=new ContentArea(rightArea,SWT.BORDER);
		topContentArea.setLayout(new FillLayout());
		fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(sash2, 0);
		fd.left = new FormAttachment(0, 0);
		fd.right = new FormAttachment(100, 0);
		topContentArea.setLayoutData(fd);  
		
		//右下部窗口
		ContentArea buttomContentArea=new ContentArea(rightArea,SWT.BORDER);
		buttomContentArea.setLayout(new FillLayout());
		fd = new FormData();
		fd.top = new FormAttachment(sash2, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.left = new FormAttachment(0, 0);
		fd.right = new FormAttachment(100, 0);
		buttomContentArea.setLayoutData(fd);  
		
		
	    GUI.setTreeArea(treeArea);
	    GUI.setTopContentArea(topContentArea);
	    GUI.setButtomContentArea(buttomContentArea);
	    
		GUI.getTreeArea().dispalyTree();		
		
		return mainComposite;
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

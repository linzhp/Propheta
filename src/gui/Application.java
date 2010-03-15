package gui;

import java.sql.SQLException;

import data.database.dataAccess.DataBaseAccess;
import estimation.detailedEstimate.DEInput;
import estimation.detailedEstimate.DEShowResult;
import gui.actions.NewProjectAction;
import gui.tabs.ParameterArea;
import gui.widgets.tree.TreeArea;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class Application extends ApplicationWindow {

	public Application() {
		super(null);
		addMenuBar();
		addStatusLine();
	}

	public void run() {
		setBlockOnOpen(true);
		open();
	}

	private void dispose() {
		DataBaseAccess.disposeConnections();
		GUI.getToolkit().dispose();
		Display.getCurrent().dispose();
	}

	public static void main(String[] args) throws SQLException {

		/** 我们可以做个类似Eclipse的启动界面，显示读取的进度 **/

		instance = new Application();
		instance.run();
		instance.dispose();
	}

	@Override
	public void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Propheta");
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite mainComposite = (Composite) super.createContents(parent);
		mainComposite.setLayout(new FormLayout());// 改为FormLayout hezhimin 11-20

		// 分栏条(左右)
		final Sash sash1 = new Sash(mainComposite, SWT.VERTICAL);
		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 0); // Attach to top
		fd.bottom = new FormAttachment(100, 0); // Attach to bottom
		fd.left = new FormAttachment(25, 0); // Attach halfway across
		sash1.setLayoutData(fd);
		sash1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				((FormData) sash1.getLayoutData()).left = new FormAttachment(0,
						event.x);
				sash1.getParent().layout();
			}
		});

		// composite to display the WBS
		TreeArea treeArea = new TreeArea(mainComposite, SWT.BORDER);
		treeArea.setLayout(new FillLayout());
		fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.left = new FormAttachment(0, 0);
		fd.right = new FormAttachment(sash1, 0);
		treeArea.setLayoutData(fd);

		// composite to display the content
		Composite rightArea = new Composite(mainComposite, SWT.NONE);
		rightArea.setLayout(new GridLayout(1, false));
		fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.left = new FormAttachment(sash1, 0);
		fd.right = new FormAttachment(100, 0);
		rightArea.setLayoutData(fd);

		topContentArea = new CTabFolder(rightArea, SWT.BORDER);
		topContentArea.setDragDetect(true);
		topContentArea.setSimple(false);
		topContentArea.setMaximizeVisible(true);
		topContentArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		topContentArea.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void maximize(CTabFolderEvent event) {
				topContentArea.setMinimized(false);
				topContentArea.setMaximized(true);
				buttomContentArea.setMinimized(true);
				buttomContentArea.setMaximized(false);
				buttomContentArea.setLayoutData(new GridData(SWT.FILL,
						SWT.FILL, true, false));
				topContentArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
						true, true));
				topContentArea.getParent().layout(true);
			}

			@Override
			public void restore(CTabFolderEvent event) {
				topContentArea.setMaximized(false);
				buttomContentArea.setMinimized(false);
				buttomContentArea.setLayoutData(new GridData(SWT.FILL,
						SWT.FILL, true, true));
				topContentArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
						true, true));
				topContentArea.getParent().layout(true);

			}

			@Override
			public void close(CTabFolderEvent event) {
				CTabItem closingItem = (CTabItem) event.item;
				ParameterArea tab = (ParameterArea) closingItem.getControl();
				if (tab.getIsInformationChanged() == true) {
					MessageBox box = new MessageBox(Display.getCurrent()
							.getActiveShell(), SWT.ICON_QUESTION | SWT.YES
							| SWT.NO | SWT.CANCEL);
					box.setText("提示");
					box.setMessage("节点信息已被修改，是否保存这些修改?");
					int result = box.open();
					if (result == SWT.YES) {
						if (tab.getClass() == NodeBasicInformationPage.class) {
							NodeBasicInformationPage nbi_page = (NodeBasicInformationPage) tab;
							nbi_page.saveNodeBasicInformation();
						} else if (tab.getClass() == DEInput.class) {
							DEInput deInput = (DEInput) tab;
							DEShowResult deShowResult = new DEShowResult(
									deInput, false);
							deShowResult.run();
						}
						event.doit = true;
					} else if (result == SWT.NO) {
						event.doit = true;
					} else if (result == SWT.CANCEL) {
						event.doit = false;
					}
				}
				super.close(event);
			}

		});

		buttomContentArea = new CTabFolder(rightArea, SWT.BORDER);
		buttomContentArea.setDragDetect(true);
		buttomContentArea.setSimple(false);
		buttomContentArea.setMaximizeVisible(true);
		buttomContentArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		buttomContentArea.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void maximize(CTabFolderEvent event) {
				buttomContentArea.setMinimized(false);
				buttomContentArea.setMaximized(true);
				topContentArea.setMaximized(false);
				topContentArea.setMinimized(true);
				topContentArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
						true, false));
				buttomContentArea.setLayoutData(new GridData(SWT.FILL,
						SWT.FILL, true, true));
				buttomContentArea.getParent().layout(true);
			}

			@Override
			public void restore(CTabFolderEvent event) {
				buttomContentArea.setMaximized(false);
				topContentArea.setMinimized(false);
				topContentArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
						true, true));
				buttomContentArea.setLayoutData(new GridData(SWT.FILL,
						SWT.FILL, true, true));
				buttomContentArea.getParent().layout(true);

			}
		});

		GUI.setTreeArea(treeArea);
		GUI.setTopContentArea(topContentArea);
		GUI.setButtomContentArea(buttomContentArea);

		GUI.getTreeArea().dispalyTree();

		return mainComposite;
	}

	@Override
	protected MenuManager createMenuManager() {
		MenuManager mainMenu = new MenuManager(null);
		// File Menu
		MenuManager fileMenuManager = new MenuManager("文件");
		fileMenuManager.add(new NewProjectAction());

		mainMenu.add(fileMenuManager);
		return mainMenu;
	}

	/*
	 * @Override protected StatusLineManager createStatusLineManager() {
	 * StatusLineManager statusLineManager=new StatusLineManager();
	 * statusLineManager.add(new )
	 * 
	 * return statusLineManager; }
	 */

	public Composite getMainContent() {
		return (Composite) getContents();
	}

	private static Application instance;
	private CTabFolder topContentArea;
	private CTabFolder buttomContentArea;

	public static Application getInstance() {
		return instance;
	}
}

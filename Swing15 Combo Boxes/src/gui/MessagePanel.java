package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import controller.MessageServer;

public class MessagePanel extends JPanel implements ProgressDialogListener {

	private static final long serialVersionUID = 1L;
	private JTree serverTree;
	private ServerTreeCellRenderer treeCellRenderer;
	private ServerTreeCellEditor treeCellEditor;
	private ProgressDialog progressDialog;

	private Set<Integer> selectedServers;
	private MessageServer messageServer;
	SwingWorker<List<Message>, Integer> worker;
	
	public MessagePanel(JFrame parent) {

		progressDialog = new ProgressDialog(parent, "Messages downloading...");
		progressDialog.setListener(this);
		
		messageServer = new MessageServer();
		selectedServers = new TreeSet<Integer>();
		selectedServers.add(0);
		selectedServers.add(3);
		selectedServers.add(4);
		selectedServers.add(5);

		treeCellRenderer = new ServerTreeCellRenderer();
		treeCellEditor = new ServerTreeCellEditor();

		serverTree = new JTree(createTree());
		serverTree.setCellRenderer(treeCellRenderer);
		serverTree.setCellEditor(treeCellEditor);
		serverTree.setEditable(true);

		serverTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		treeCellEditor.addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				ServerInfo info = (ServerInfo) treeCellEditor
						.getCellEditorValue();

				int serverId = info.getId();

				if (info.isChecked()) {
					selectedServers.add(serverId);
				} else {
					selectedServers.remove(serverId);
				}

				messageServer.setSelectedServers(selectedServers);

				retrieveMessages();
			}

			@Override
			public void editingCanceled(ChangeEvent e) {
			}
		});

		setLayout(new BorderLayout());

		add(new JScrollPane(serverTree), BorderLayout.CENTER);
	}

	private void retrieveMessages() {

		progressDialog.setMaximum(messageServer.getMessagesCount());
		progressDialog.setVisible(true);

		worker = new SwingWorker<List<Message>, Integer>() {

			@Override
			protected void done() {
				progressDialog.setVisible(false);
				if (isCancelled()) return;
				
				try {
					List<Message> retrievedMessages = get();
					System.out.println("Retrieved " + retrievedMessages.size()
							+ " messages");
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}

			}

			@Override
			protected void process(List<Integer> counts) {
				int retrieved = counts.get(counts.size() - 1);

				progressDialog.setValue(retrieved);
			}

			@Override
			protected List<Message> doInBackground() throws Exception {

				List<Message> retrievedMessages = new ArrayList<Message>();
				int count = 0;
				for (Message message : messageServer) {
					
					if (isCancelled()) break; 
					System.out.println(message.getTitle());

					retrievedMessages.add(message);
					count++;
					publish(count);
				}
				return retrievedMessages;
			}
		};

		worker.execute();
	}

	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode server11 = new DefaultMutableTreeNode(
				new ServerInfo("New York", 0, selectedServers.contains(0)));
		DefaultMutableTreeNode server12 = new DefaultMutableTreeNode(
				new ServerInfo("Boston", 1, selectedServers.contains(1)));
		DefaultMutableTreeNode server13 = new DefaultMutableTreeNode(
				new ServerInfo("New Orleans", 2, selectedServers.contains(2)));
		DefaultMutableTreeNode server14 = new DefaultMutableTreeNode(
				new ServerInfo("San Fransisco", 3, selectedServers.contains(3)));

		branch1.add(server11);
		branch1.add(server12);
		branch1.add(server13);
		branch1.add(server14);

		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
		DefaultMutableTreeNode server21 = new DefaultMutableTreeNode(
				new ServerInfo("York", 4, selectedServers.contains(4)));
		DefaultMutableTreeNode server22 = new DefaultMutableTreeNode(
				new ServerInfo("London", 5, selectedServers.contains(5)));
		DefaultMutableTreeNode server23 = new DefaultMutableTreeNode(
				new ServerInfo("Dundee", 6, selectedServers.contains(6)));
		DefaultMutableTreeNode server24 = new DefaultMutableTreeNode(
				new ServerInfo("Bristol", 7, selectedServers.contains(7)));

		branch2.add(server21);
		branch2.add(server22);
		branch2.add(server23);
		branch2.add(server24);

		top.add(branch1);
		top.add(branch2);

		return top;
	}

	@Override
	public void progressDialogCancelled() {
		if (worker != null) {
			worker.cancel(true);
		}
	}
}

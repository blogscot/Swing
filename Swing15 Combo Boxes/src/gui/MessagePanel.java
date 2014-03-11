package gui;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.BorderLayout;

class ServerInfo{
    private String name;
    private int id;

    @Override
    public String toString() {
        return  name;
    }

    public ServerInfo(String name, int id) {
        this.name = name;
        this.id = id;


    }
}
public class MessagePanel extends JPanel {

    private JTree serverTree;

    public MessagePanel() {

        serverTree = new JTree(createTree());

        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        serverTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)serverTree.getLastSelectedPathComponent();

                Object userObject = node.getUserObject();
                System.out.println(userObject);
            }
        });

        setLayout(new BorderLayout());

        add(new JScrollPane(serverTree), BorderLayout.CENTER);
    }

    private DefaultMutableTreeNode createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
        DefaultMutableTreeNode server11 = new DefaultMutableTreeNode(new ServerInfo("New York", 0));
        DefaultMutableTreeNode server12 = new DefaultMutableTreeNode(new ServerInfo("Boston", 1));
        DefaultMutableTreeNode server13 = new DefaultMutableTreeNode(new ServerInfo("New Orleans", 2));
        DefaultMutableTreeNode server14 = new DefaultMutableTreeNode(new ServerInfo("San Fransisco",  3));

        branch1.add(server11);
        branch1.add(server12);
        branch1.add(server13);
        branch1.add(server14);

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server21 = new DefaultMutableTreeNode(new ServerInfo("York", 4));
        DefaultMutableTreeNode server22 = new DefaultMutableTreeNode(new ServerInfo("London", 5));
        DefaultMutableTreeNode server23 = new DefaultMutableTreeNode(new ServerInfo("Dundee",6));
        DefaultMutableTreeNode server24 = new DefaultMutableTreeNode(new ServerInfo("Bristol", 7));

        branch2.add(server21);
        branch2.add(server22);
        branch2.add(server23);
        branch2.add(server24);

        top.add(branch1);
        top.add(branch2);

        return top;
    }
}

package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.BorderLayout;

public class MessagePanel extends JPanel {

    private JTree serverTree;

    public MessagePanel() {

        serverTree = new JTree(createTree());

        setLayout(new BorderLayout());

        add(new JScrollPane(serverTree), BorderLayout.CENTER);
    }

    private DefaultMutableTreeNode createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
        DefaultMutableTreeNode server11 = new DefaultMutableTreeNode("New York");
        DefaultMutableTreeNode server12 = new DefaultMutableTreeNode("Boston");
        DefaultMutableTreeNode server13 = new DefaultMutableTreeNode("New Orleans");
        DefaultMutableTreeNode server14 = new DefaultMutableTreeNode("San Francisco");

        branch1.add(server11);
        branch1.add(server12);
        branch1.add(server13);
        branch1.add(server14);

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server21 = new DefaultMutableTreeNode("York");
        DefaultMutableTreeNode server22 = new DefaultMutableTreeNode("London");
        DefaultMutableTreeNode server23 = new DefaultMutableTreeNode("Dundee");
        DefaultMutableTreeNode server24 = new DefaultMutableTreeNode("Bristol");

        branch2.add(server21);
        branch2.add(server22);
        branch2.add(server23);
        branch2.add(server24);

        top.add(branch1);
        top.add(branch2);

        return top;
    }
}

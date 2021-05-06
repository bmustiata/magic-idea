package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.TreeItem;

import javax.swing.*;
import java.awt.event.*;

public class EntryEditor extends JDialog {
    private final MgTreeNode<? extends TreeItem> mgTreeNode;

    interface NameDescLongDescAction {
        void call(String name, String description, String longDescription);
    }

    interface DescLongDescAction {
        void call(String description, String longDescription);
    }

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JEditorPane longDescriptionEditorPane;

    private NameDescLongDescAction nameDescLongDescAction;
    private DescLongDescAction descLongDescAction;

    public EntryEditor(MgTreeNode<? extends TreeItem> mgTreeNode, String title) {
        this.mgTreeNode = mgTreeNode;

        setTitle(title);
        setContentPane(this.contentPane);
        setModal(true);
        getRootPane().setDefaultButton(this.buttonOK);

        this.buttonOK.addActionListener(e -> onOK());
        this.buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        this.nameTextField.setEnabled(false);

        // call onCancel() on ESCAPE
        this.contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.nameTextField.getDocument().addDocumentListener((ChangeListener) content -> {
            this.buttonOK.setEnabled(!content.isBlank());
        });
    }

    public void setInitialData(TreeItem selectedTreeItem) {
        this.nameTextField.setText(selectedTreeItem.getName());
        this.descriptionTextField.setText(selectedTreeItem.getDescription());
        this.longDescriptionEditorPane.setText(selectedTreeItem.getLongDescription());
    }

    private void onOK() {
        if (this.nameDescLongDescAction != null) {
            this.nameDescLongDescAction.call(
                    nameTextField.getText(),
                    descriptionTextField.getText(),
                    longDescriptionEditorPane.getText()
            );
        } else if (this.descLongDescAction != null) {
            this.descLongDescAction.call(
                    descriptionTextField.getText(),
                    longDescriptionEditorPane.getText()
            );
        } else {
            dispose();
            throw new IllegalStateException("No OK action was configured.");
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void onOk(NameDescLongDescAction nameDescLongDescAction) {
        this.nameDescLongDescAction = nameDescLongDescAction;
        this.nameTextField.setEnabled(true);
    }

    public void onOk(DescLongDescAction descLongDescAction) {
        this.descLongDescAction = descLongDescAction;
    }
}

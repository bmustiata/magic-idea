package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.Group;

import javax.swing.*;
import java.awt.event.*;

public class GroupEditor extends JDialog {
    private final MgTreeNode<Group> group;

    interface Action {
        void call(String name, String description, String longDescription);
    }

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JEditorPane longDescriptionEditorPane;
    private GroupEditor.Action okCallback;

    public GroupEditor(MgTreeNode<Group> group) {
        this.group = group;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        nameTextField.getDocument().addDocumentListener((ChangeListener) content -> {
            buttonOK.setEnabled(!content.isBlank());
        });
    }

    private void onOK() {
        this.okCallback.call(
                nameTextField.getText(),
                descriptionTextField.getText(),
                longDescriptionEditorPane.getText()
        );
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void onOk(GroupEditor.Action okCallback) {
        this.okCallback = okCallback;
    }
}

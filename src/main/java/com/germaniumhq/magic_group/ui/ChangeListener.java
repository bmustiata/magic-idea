package com.germaniumhq.magic_group.ui;

import lombok.SneakyThrows;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public interface ChangeListener extends DocumentListener {
    @SneakyThrows
    @Override
    default void insertUpdate(DocumentEvent e) {
        Document document = e.getDocument();
        String content = document.getText(0, document.getLength());

        this.onUpdate(content);
    }

    @SneakyThrows
    @Override
    default void removeUpdate(DocumentEvent e) {
        Document document = e.getDocument();
        String content = document.getText(0, document.getLength());

        this.onUpdate(content);
    }

    @SneakyThrows
    @Override
    default void changedUpdate(DocumentEvent e) {
        Document document = e.getDocument();
        String content = document.getText(0, document.getLength());

        this.onUpdate(content);
    }

    void onUpdate(String content);
}

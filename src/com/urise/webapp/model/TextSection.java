package com.urise.webapp.model;

import java.io.Serial;
import java.util.Objects;

public class TextSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String contentText;

    public TextSection(String contentText) {
        Objects.requireNonNull(contentText, "contentText must not be null");
        this.contentText = contentText;
    }

    public String getContentText() {
        return contentText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(contentText, that.contentText);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contentText);
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "contentText='" + contentText + '\'' +
                '}';
    }
}

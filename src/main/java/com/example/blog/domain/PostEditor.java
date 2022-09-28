package com.example.blog.domain;

import lombok.Getter;

@Getter
public class PostEditor {

    private final String title;
    private final String content;

    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private PostEditor(PostEditorBuilder builder) {
        this.title = builder.title;
        this.content = builder.content;
    }

    public static PostEditorBuilder builder() {
        return new PostEditorBuilder();
    }

    public static class PostEditorBuilder {

        private String title;
        private String content;

        public PostEditorBuilder() {
        }

        public PostEditorBuilder title(String title) {
            if (title != null) {
                this.title = title;
            }
            return this;
        }

        public PostEditorBuilder content(String content) {
            if (content != null) {
                this.content = content;
            }
            return this;
        }

        public PostEditor build() {
            return new PostEditor(this);
        }
    }
}

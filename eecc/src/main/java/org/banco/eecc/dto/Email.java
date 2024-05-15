package org.banco.eecc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Email {
    private String to;
    private String subject;
    private String body;
    private byte[] attachment;
    private String nameAttachment; // Nuevo campo

    private Email(Builder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.body = builder.body;
        this.attachment = builder.attachment;
        this.nameAttachment = builder.nameAttachment; // Asignación del nuevo campo
    }

    public static class Builder {
        private String to;
        private String subject;
        private String body;
        private byte[] attachment;
        private String nameAttachment; // Nuevo campo en Builder

        public Builder withTo(String to) {
            this.to = to;
            return this;
        }

        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withAttachment(byte[] attachment) {
            this.attachment = attachment;
            return this;
        }

        public Builder withNameAttachment(String nameAttachment) { // Nuevo método en Builder
            this.nameAttachment = nameAttachment;
            return this;
        }

        public Email build() {
            return new Email(this);
        }
    }

    // Getters
}
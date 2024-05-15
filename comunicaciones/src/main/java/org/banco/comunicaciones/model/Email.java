package org.banco.comunicaciones.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Email implements Serializable {
    private String to;
    private String subject;
    private String body;
    private byte[] attachment;
    private String nameAttachment;

    public Email() {
    }
}
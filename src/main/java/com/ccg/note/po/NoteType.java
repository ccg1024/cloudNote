package com.ccg.note.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class NoteType implements Serializable {

    private Integer typeId;
    private String typeName;
    private  Integer userId;

    @Override
    public String toString() {
        return "NoteType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", userId=" + userId +
                '}';
    }
}

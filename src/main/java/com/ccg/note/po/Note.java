package com.ccg.note.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Note implements Serializable {

    private Integer noteId;  //
    private String title;  //
    private String content;  //
    private Integer typeId;  //
    private Date pubTime;  //

    private String typeName;
    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", typeId=" + typeId +
                ", pubTime=" + pubTime +
                '}';
    }
}

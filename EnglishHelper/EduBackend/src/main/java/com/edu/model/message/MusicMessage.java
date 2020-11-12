package com.edu.model.message;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MusicMessage extends BaseMessage {
    /**
     * 音乐
     */
    private Music Music;

}

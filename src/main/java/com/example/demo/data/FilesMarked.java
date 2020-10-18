package com.example.demo.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("FilesMarked")
public class FilesMarked {
    @Id
    private String fileName;

    private boolean marked;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    @Override
    public String toString() {
        return "FilesMarked{" +
                "fileName='" + fileName + '\'' +
                ", marked=" + marked +
                '}';
    }
}

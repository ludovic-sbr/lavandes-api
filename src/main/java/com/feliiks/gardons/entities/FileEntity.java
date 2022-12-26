package com.feliiks.gardons.entities;

public class FileEntity {
    private Long id;
    private String name;
    private String path;
    private String publicUrl;

    public FileEntity() {
    }

    public FileEntity(String name, String path, String publicUrl) {
        this.name = name;
        this.path = path;
        this.publicUrl = publicUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }
}

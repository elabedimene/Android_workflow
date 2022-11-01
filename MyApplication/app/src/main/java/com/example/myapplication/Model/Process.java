package com.example.myapplication.Model;

public class Process {
    private String id;
    private String key;
    private String category;
    private String description;
    private String name;
    private int version;
    private String resource;
    private String deploymentId;
    private String diagram;
    private boolean suspended;
    private String  tenantId;
    private String versionTag;
    private String historyTimeToLive;
    private boolean startableInTasklist;


    public Process()
    {

    }
    public Process(String id, String key, String category, String description, String name, int version, String resource, String deploymentId, String diagram, boolean suspended, String tenantId, String versionTag, String historyTimeToLive, boolean startableInTasklist) {
        this.id = id;
        this.key = key;
        this.category = category;
        this.description = description;
        this.name = name;
        this.version = version;
        this.resource = resource;
        this.deploymentId = deploymentId;
        this.diagram = diagram;
        this.suspended = suspended;
        this.tenantId = tenantId;
        this.versionTag = versionTag;
        this.historyTimeToLive = historyTimeToLive;
        this.startableInTasklist = startableInTasklist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getDiagram() {
        return diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getVersionTag() {
        return versionTag;
    }

    public void setVersionTag(String versionTag) {
        this.versionTag = versionTag;
    }

    public String getHistoryTimeToLive() {
        return historyTimeToLive;
    }

    public void setHistoryTimeToLive(String historyTimeToLive) {
        this.historyTimeToLive = historyTimeToLive;
    }

    public boolean isStartableInTasklist() {
        return startableInTasklist;
    }

    public void setStartableInTasklist(boolean startableInTasklist) {
        this.startableInTasklist = startableInTasklist;
    }
}

package com.example.zystarter.FileUploadConfig;

import org.apache.commons.lang3.StringUtils;

public enum FileContentType {
    DEFAULT("default","application/octet-stream"),
    JPG("jpg", "image/jpeg"),
    TIFF("tiff", "image/tiff"),
    GIF("gif", "image/gif"),
    JFIF("jfif", "image/jpeg"),
    PNG("png", "image/png"),
    TIF("tif", "image/tiff"),
    ICO("ico", "image/x-icon"),
    JPEG("jpeg", "image/jpeg"),
    WBMP("wbmp", "image/vnd.wap.wbmp"),
    FAX("fax", "image/fax"),
    NET("net", "image/pnetvue"),
    JPE("jpe", "image/jpeg"),
    RP("rp", "image/vnd.rn-realpix"),
    PDF("pdf", "application/pdf");

    private String prefix;
    private String type;

    public String getPrefix() {
        return prefix;
    }
    public String getType() {
        return type;
    }

    FileContentType(String prefix, String type) {
        this.prefix = prefix;
        this.type = type;
    }

    public static String getType(String prefix){
        if(StringUtils.isEmpty(prefix)){
            return DEFAULT.getType();
        }
        prefix = prefix.substring(prefix.lastIndexOf(".")+1);
        for (FileContentType value : FileContentType.values()) {
            if(prefix.equalsIgnoreCase(value.getPrefix())){
                return value.getType();
            }
        }
        return DEFAULT.getType();
    }

}

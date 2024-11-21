package com.example.zystarter.FileUploadFactory;

public class FileUploadException extends Exception {
    public FileUploadException(String msg,Throwable e){
        super(msg,e);
    }

    public FileUploadException(String msg){
        super(msg);
    }
}

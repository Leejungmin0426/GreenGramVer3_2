package com.green.greengram.common.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyFileUtilsTest {
    private final String FILE_DIRECTORY = "D:/2024-02/download/greengram_ver3";
    private MyFileUtils myFileUtils;


    @BeforeEach
    void setUp(){
        myFileUtils = new MyFileUtils(FILE_DIRECTORY);
    }
    @Test
    void deleteFolder() {
        String path = String.format("%s/user/ddd", myFileUtils.getUploadPath());
        myFileUtils.deleteFolder(path, false);
    }
}
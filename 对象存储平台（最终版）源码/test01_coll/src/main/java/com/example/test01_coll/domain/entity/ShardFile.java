package com.example.test01_coll.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShardFile {
    Integer no;
    String totalMD5;
    String ownMD5;
    MultipartFile file;
}

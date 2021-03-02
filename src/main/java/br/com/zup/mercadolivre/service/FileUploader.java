package br.com.zup.mercadolivre.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploader {

    List<String> send(List<MultipartFile> files);

}

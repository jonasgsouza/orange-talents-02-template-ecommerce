package br.com.zup.mercadolivre.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile({"default", "test"})
public class DevFileUploader implements FileUploader {

    @Override
    public List<String> send(List<MultipartFile> files) {
        return files.stream().map(f -> "http://link-to-/" + f.getName()).collect(Collectors.toList());
    }
}

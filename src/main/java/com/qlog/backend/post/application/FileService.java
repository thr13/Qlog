package com.qlog.backend.post.application;

import com.qlog.backend.post.domain.model.File;
import com.qlog.backend.post.domain.model.Post;
import com.qlog.backend.post.domain.repository.FileRepository;
import com.qlog.backend.post.exception.BlankFileNameException;
import com.qlog.backend.post.exception.FailedCreateDirectoryException;
import com.qlog.backend.post.exception.FailedStoreFileException;
import com.qlog.backend.post.exception.NotFoundFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final Path path;

    public FileService(FileRepository fileRepository, @Value("${file.upload-dir}") String upload) {
        this.fileRepository = fileRepository;
        this.path = Paths.get(upload).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.path);
        } catch (IOException e) {
            throw new FailedCreateDirectoryException("파일 업로드 디렉토리 생성 실패");
        }
    }

    //파일 저장
    @Transactional
    public void storeFile(MultipartFile multipartFile, Post post) {
        String originalFilename = multipartFile.getOriginalFilename();

        if (originalFilename == null) {
            throw new BlankFileNameException("파일 이름이 비어있습니다");
        }

        String fileName = UUID.randomUUID() + "_" + originalFilename;
        Path location = this.path.resolve(fileName);

        try {
            Files.copy(multipartFile.getInputStream(), location);
        } catch (IOException e) {
            throw new FailedStoreFileException(fileName + "저장 실패");
        }

        File file = new File(
                post,
                originalFilename,
                location.toString(),
                multipartFile.getSize(),
                multipartFile.getContentType()
        );
        fileRepository.save(file);
    }

    //파일 다운로드
    @Transactional(readOnly = true)
    public File getFile(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(
                () -> new NotFoundFileException(fileId + "해당 파일을 찾을 수 없습니다")
        );
    }
}

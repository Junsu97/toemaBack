package junsu.personal.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String upload(MultipartFile file) throws  Exception;
    int fileDelete(String fileName) throws Exception;
    Resource getImage(String fileName) throws Exception;
}

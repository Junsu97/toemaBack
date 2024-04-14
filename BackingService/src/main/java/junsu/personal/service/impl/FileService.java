package junsu.personal.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import junsu.personal.repository.ImageRepository;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.repository.TeacherUserRepository;
import junsu.personal.service.IFileService;
import junsu.personal.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService implements IFileService {
    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;
    private final StudentUserRepository studentUserRepository;
    private final TeacherUserRepository teacherUserRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile file) throws Exception{
        log.info(this.getClass().getName() + ".upload Start!!!!");
        log.info(bucket);
        if(file.isEmpty()){
            log.info("FILE 비어있음!!!!!");
            return null;
        }

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String saveFileName = DateUtil.getDateTime("HHmmss") + "." + extension;

        File uploadFile = convert(file);
        amazonS3Client.putObject(
                new PutObjectRequest(
                    bucket, saveFileName, uploadFile
                ).withCannedAcl(CannedAccessControlList.PublicRead)
        );

        removeNewFile(uploadFile);
        log.info("amazonS3 URL : " + amazonS3Client.getUrl(bucket, saveFileName).toString());

        log.info(this.getClass().getName() + ".upload End!!!!");
        return amazonS3Client.getUrl(bucket, saveFileName).toString();
    }

    private File convert(MultipartFile file) {
        log.info("user.dir system property : " + System.getProperty("user.dir"));
        log.info("file origin name : " +file.getOriginalFilename());

        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        log.info("convertFile : " +  convertFile.getPath());

        try(FileOutputStream fos = new FileOutputStream(convertFile)){
            fos.write(file.getBytes());
        }catch (FileNotFoundException fe){
            log.info("파일이 비어있습니다.");
            fe.printStackTrace();
            return null;
        }catch (IOException ie){
            log.info("입출력에러");
            ie.printStackTrace();
            return null;
        }

        return convertFile;
    }

    private void removeNewFile(File targetFile){
        if(targetFile.delete()){
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }


    @Override
    public int fileDelete(String fileName) throws Exception{
        int res = 0;
        try{
            amazonS3Client.deleteObject(bucket, fileName);
            res = 1;
        }catch (AmazonS3Exception e){
            e.printStackTrace();;
        }catch (SdkClientException e){
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public Resource getImage(String fileName) throws Exception{
        Resource resource = null;
        try {
            S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucket, fileName));
            S3ObjectInputStream inputStream = s3Object.getObjectContent();

            resource = new InputStreamResource(inputStream);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return resource;
    }

}
